---
name: feature-scaffold
description: >-
  Scaffolds a new Nx feature library under libs/features with services, hooks,
  components, and public index. Use when creating a business feature or module.
---

# Scaffold de feature

## Checklist

1. Criar `libs/features/<kebab-name>/` com `project.json` tags `type:feature`, `scope:shared`
2. Adicionar path em `tsconfig.base.json`: `@startup/features/<name>` → `libs/features/<name>/src/index.ts`
3. Estrutura mínima:

```
src/
  components/
  hooks/
  services/
  store/          # opcional — slice Redux Toolkit (<name>Slice.ts)
  index.ts        # API pública
```

4. Exportar só o necessário em `index.ts`
5. Compor na page em `apps/mobile/src/pages/` — page fina

## Template `project.json`

```json
{
  "name": "feature-<name>",
  "sourceRoot": "libs/features/<name>/src",
  "projectType": "library",
  "tags": ["type:feature", "scope:shared"]
}
```

## Referência

Copiar padrões de [libs/features/auth](../../../libs/features/auth/src/index.ts).

## Proibido

- Importar outra feature diretamente
- Colocar cliente HTTP duplicado (usar `@startup/shared` api client)
