# Plano — Redux Toolkit

Plano para o estado global do app. A implementação ainda não começou: a sessão hoje está em Zustand (`libs/features/auth/src/store/authStore.ts`). Estado novo de cliente segue este plano. Dados de servidor continuam no TanStack Query.

Referências usadas:

- [Style Guide oficial do Redux](https://redux.js.org/style-guide/)
- [Usage Guide do Redux Toolkit](https://redux.js.org/toolkit/usage/usage-guide)
- [Estrutura de pastas](https://redux.js.org/faq/code-structure)

## Decisão

Usar **Redux Toolkit** (`@reduxjs/toolkit`) e **React Redux** (`react-redux`), com `createSlice` e `configureStore`. Um único store na aplicação, em `apps/mobile/src/store/store.ts`.

Redux clássico (action types manuais, `createStore`, `connect`, pastas `actions/` e `reducers/`) fica de fora. O style guide trata o Toolkit como a forma padrão de escrever Redux.

## Onde cada estado mora

O style guide pede para avaliar o lugar de cada valor. Nesta base:

| Tipo | Onde | Exemplo |
|------|------|---------|
| UI efêmera de uma tela | `useState` no componente | campo de busca aberto, modal |
| Formulário em edição | estado local do componente | e-mail e senha enquanto a pessoa digita |
| Dados da API | TanStack Query, no hook da feature | lista de bebidas, sessão vinda de `GET /auth/session` |
| Estado de cliente compartilhado | slice da feature | `userId` logado, filtros que várias telas leem |

O style guide recomenda [RTK Query para buscar dados](https://redux.js.org/style-guide/#use-rtk-query-for-data-fetching). Aqui a exceção é consciente: a camada HTTP já é TanStack Query (`docs/architecture.md`, skill `api-data-layer`). RTK Query e TanStack Query resolvem o mesmo problema (cache, deduplicação, loading). Os dois juntos geram duas fontes da verdade. **Não adicionar RTK Query.**

Thunk fica para lógica de cliente que precisa de `dispatch` e `getState` e não é uma chamada de API. Chamada HTTP continua em `services/`, disparada pelo hook com Query.

## Pastas

O style guide pede feature folders e a lógica Redux da feature num único arquivo (padrão ducks / slice). Isso já combina com `libs/features/<nome>/`.

```
apps/mobile/src/store/
  store.ts          # configureStore, um store só
  hooks.ts          # useAppDispatch, useAppSelector
  index.ts          # reexporta store e hooks

libs/features/auth/src/store/
  authSlice.ts      # state, reducers, actions, selectors
```

O reducer da feature é registrado no `configureStore` do app. A feature exporta o reducer e os selectors pelo `index.ts`. O app importa esses reducers. A feature não importa o store do app (evita ciclo e mantém a regra de `shared` / `features` não dependerem do app).

```ts
// apps/mobile/src/store/store.ts
import { configureStore } from '@reduxjs/toolkit';
import { authReducer } from '@startup/features/auth';

export const store = configureStore({
  reducer: {
    auth: authReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
```

```ts
// apps/mobile/src/store/hooks.ts
import { useDispatch, useSelector } from 'react-redux';
import type { AppDispatch, RootState } from './store';

export const useAppDispatch = useDispatch.withTypes<AppDispatch>();
export const useAppSelector = useSelector.withTypes<RootState>();
```

`AppProviders` envolve a árvore com `<Provider store={store}>`, por fora do `QueryClientProvider`.

## Slice de referência (auth)

Substitui o Zustand atual. A regra de negócio da sessão (entrar, sair, guardar `userId`) mora no slice, não no componente.

```ts
// libs/features/auth/src/store/authSlice.ts
import { createSlice, type PayloadAction } from '@reduxjs/toolkit';

type AuthState = {
  userId: string | null;
};

const initialState: AuthState = {
  userId: null,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    sessionSet(state, action: PayloadAction<string>) {
      state.userId = action.payload;
    },
    signedOut(state) {
      state.userId = null;
    },
  },
});

export const { sessionSet, signedOut } = authSlice.actions;
export const authReducer = authSlice.reducer;

export const selectUserId = (state: { auth: AuthState }) => state.auth.userId;
export const selectIsAuthenticated = (state: { auth: AuthState }) =>
  state.auth.userId != null;
```

O tipo do selector usa o pedaço `auth` para a feature não importar `RootState` do app. No app, `useAppSelector(selectUserId)` continua tipado.

Ações descrevem o que aconteceu (`sessionSet`, `signedOut`), no formato `auth/sessionSet` que o `createSlice` gera. Evitar setters genéricos (`setState`, `updateField`) quando a regra cabe num evento.

O reducer calcula o próximo estado. O componente só despacha o evento. Dentro do `createSlice` a “mutação” é do Immer; o estado real continua imutável. Fora do reducer, não alterar objetos que vieram do store.

## O que não entra no store

- Promessas, funções, class instances, `Map`/`Set`. O style guide exige estado e actions serializáveis para o DevTools funcionar.
- Rascunho de formulário. O style guide pede para manter o form no componente e despachar uma vez, quando a pessoa conclui.
- Resposta de lista da API. Isso é cache do Query (`queryKey` + hook).
- Token no log de produção. Em desenvolvimento o DevTools mostra o estado. O `configureStore` já desliga o DevTools no build de produção.

## Leitura nos componentes

Hooks, não `connect`. Vários `useAppSelector` pequenos, cada um com uma função `selectAlgo`. Selector memoizado com `createSelector` quando derivar lista filtrada ou dado caro. Nome sempre `select` + o que devolve: `selectUserId`, `selectSavedDrinkIds`.

Componente de apresentação (`*View.tsx`) recebe props. Quem lê o store é a page ou um hook da feature (`useAuthStatus`), para o view continuar testável sem Redux.

## Fases

1. **Fundação.** Instalar `@reduxjs/toolkit` 2.x e `react-redux` 9.x (os hooks `useDispatch.withTypes` / `useSelector.withTypes` são dessa versão). Criar `store.ts`, `hooks.ts` e o `Provider`. Store ainda sem comportamento de produto, com um teste que confere o estado inicial.
2. **Auth.** Trocar `authStore.ts` (Zustand) pelo `authSlice.ts`. `signOut` limpa o `userId` e chama `setAuthToken(null)` num thunk ou no hook, porque efeito colateral não mora no reducer. Apagar o Zustand quando nenhum import restar.
3. **Próximas features.** Bebidas salvas, filtros de eventos e preferências de UI compartilhadas nascem como slice só se mais de uma tela precisar do valor. O resto fica em `useState` ou no Query.
4. **Testes.** Reducer puro: `authReducer(initial, signedOut())`. Page: render com `<Provider store={store}>`. Não mockar o reducer inteiro se o estado inicial já cobre o caso.

Cada fase fecha com os testes da page e do reducer da feature. A fase 2 remove a dependência `zustand` do `package.json` quando o último uso sair.

## Regras que evitam erro

Essenciais no [style guide](https://redux.js.org/style-guide/):

- Não mutar estado fora do Immer do slice.
- Reducer sem efeito colateral: sem `fetch`, sem `Date.now()`, sem gravar token.
- Um store só. Ninguém importa `store` em componente; usa os hooks.
- Lógica de transição no reducer, não no `onPress` que monta o próximo objeto na mão.
- Estado nomeado pelo dado (`auth`, `savedDrinks`), não pelo componente (`HomePageReducer`).

Fortes para este repositório:

- Feature folder + um arquivo de slice.
- TypeScript nos payloads (`PayloadAction<string>`).
- Hooks tipados `useAppDispatch` / `useAppSelector`.
- Selectors com prefixo `select`.
- Thunk só quando a lógica precisa ler o store. HTTP permanece no service + Query.
- Sem Redux Saga e sem Redux Observable.

## Skills

- `.cursor/skills/redux-state/` — regras ao criar slice, selector ou mexer no store
- `.cursor/skills/react-architecture/` — onde o estado fica
- `.cursor/skills/api-data-layer/` — Query continua dono do servidor
- `.cursor/skills/feature-scaffold/` — pasta `store/` passa a ser slice, não Zustand
