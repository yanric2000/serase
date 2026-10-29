---
name: redux-state
description: >-
  Redux Toolkit rules for Startup client state. Use when adding a slice,
  selector, store, Provider, global state, or when the user mentions Redux,
  useSelector, or replacing Zustand.
---

# Estado com Redux Toolkit

Fonte: [docs/redux.md](../../../docs/redux.md). Guia oficial: [Style Guide](https://redux.js.org/style-guide/).

Ainda não está instalado. Até a fase 1 do plano, não criar store paralelo. O Zustand em `libs/features/auth` é legado da fase 2; feature nova não copia esse arquivo.

## Onde colocar

| Peça | Arquivo |
|------|---------|
| Store único | `apps/mobile/src/store/store.ts` |
| Hooks tipados | `apps/mobile/src/store/hooks.ts` |
| Slice da feature | `libs/features/<nome>/src/store/<nome>Slice.ts` |

A feature exporta reducer, actions e selectors. Ela não importa o store do app.

## O que vai no slice

Estado de cliente compartilhado por mais de uma tela: sessão, filtro que sobrevive à navegação.

Fica de fora:

- Resposta de API (TanStack Query no hook)
- Formulário enquanto a pessoa digita (`useState`)
- Toggle que só uma tela usa (`useState`)
- `fetch` dentro do reducer

## Forma do slice

- `createSlice`, ações como eventos (`signedOut`), não setters genéricos
- Payload com `PayloadAction<T>`
- Selectors `selectAlgo` no mesmo arquivo
- Efeito colateral (limpar token, navegar) no hook ou thunk, nunca no reducer
- Componente `*View.tsx` só recebe props; page ou hook lê o store com `useAppSelector`

## Proibido

- `connect`, `createStore`, pastas `actions/` e `reducers/`
- RTK Query (o cache de servidor já é TanStack Query)
- Redux Saga, Redux Observable
- Segundo store
- Mutar objeto vindo do `useAppSelector`
- Importar outra feature para registrar o reducer dela
