---
name: react-architecture
description: >-
  Guides folder placement and import rules for the Startup Nx React Native
  monorepo (feature-based layers). Use when adding pages, features, refactors,
  or when the user asks where code belongs.
---

# Arquitetura React — Startup

Fonte da verdade: [docs/architecture.md](../../../docs/architecture.md)

## Camadas

| Código | Local |
|--------|--------|
| Providers, App shell | `apps/mobile/src/app/` |
| Rotas / guards | `apps/mobile/src/routes/` |
| Layouts (stacks/tabs) | `apps/mobile/src/layouts/` |
| Telas de rota | `apps/mobile/src/pages/<Name>Page/` |
| Negócio | `libs/features/<name>/` |
| UI genérica, API client | `libs/shared/src/` |

## Dependências permitidas

- `pages` → `features`, `shared`, `layouts`
- `features` → `shared` only (não outras features)
- `shared` → nada de `features` ou `apps`
- Sem HTTP/fetch em componentes de UI

## Estado

- UI local → `useState`
- Servidor → TanStack Query em hooks da feature (via services)
- Global de cliente → Redux Toolkit, um store em `apps/mobile/src/store/`, slice em `libs/features/<name>/src/store/`. Plano e regras: [docs/redux.md](../../../docs/redux.md) e skill `redux-state`. O `authStore` Zustand é legado até a fase 2 desse plano.

## Componentes

- `*View.tsx`: apresentação, só props
- Page ou hook: dados e navegação

## React Native

React Navigation substitui React Router: `routes/` + `layouts/` + `pages/`.
