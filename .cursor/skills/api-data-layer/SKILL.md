---
name: api-data-layer
description: >-
  Implements UI → Hook → Service → API for Startup. Use for endpoints, TanStack
  Query hooks, services, or API client changes.
---

# Camada de dados

## Fluxo obrigatório

```
Page → feature hook → feature service → @startup/shared apiClient
```

## Cliente HTTP

- Config: `libs/shared/src/config/env.ts`
- Client: `libs/shared/src/api/client.ts`
- Não usar `fetch` em `.tsx` de UI

## Service (sem React)

```typescript
import { apiClient } from '@startup/shared';

export async function getResource() {
  return apiClient.get<Resource>('/path');
}
```

## Hook (TanStack Query)

```typescript
import { useQuery } from '@tanstack/react-query';
import { getResource } from '../services/resourceService';

export function useResource() {
  return useQuery({ queryKey: ['resource'], queryFn: getResource });
}
```

## QueryClient

Registrado em `apps/mobile/src/app/providers.tsx`.

Cache de servidor fica neste hook. Não duplicar a mesma resposta num slice Redux nem introduzir RTK Query. Estado de cliente compartilhado segue [docs/redux.md](../../../docs/redux.md).

## Erros

Services lançam `ApiError` do shared; hooks/pages tratam `isError` / boundaries conforme necessidade.

## Exemplo completo

Ver `libs/features/auth/src/services/authService.ts` e `hooks/useAuthStatus.ts`.
