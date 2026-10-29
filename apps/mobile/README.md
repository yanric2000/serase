# App web — Startup

O passo a passo completo está em [docs/desenvolvimento.md](../../docs/desenvolvimento.md).

Interface React Native servida no navegador via Vite. O container traz o Node e as dependências. Não é preciso instalar Node na máquina.

Este compose não sobe a API. A API tem o próprio compose em `api`, na raiz do repositório.

## Subir

Com o Docker em execução:

```sh
cd apps/mobile
docker compose up --build
```

Abra `http://localhost:4200`.

A primeira subida roda `npm ci` dentro do volume `web_node_modules` e pode demorar. As próximas reutilizam esse volume. O código do repositório entra por volume, então editar um arquivo na máquina recarrega a página.

O app chama a API em `http://localhost:8080` (padrão de `libs/shared/src/config/env.ts`). Suba a API à parte quando for usar dados reais. Sem a API, a tela abre e a sessão fica deslogada.

## O que este container cobre

A versão web, que é a mesma base React Native compilada para o navegador. Emulador Android e Xcode entram só quando for gerar o app nativo, e esses SDKs ficam na máquina de quem for fazer esse build.

## Testes dentro do container

Com o serviço no ar:

```sh
docker compose exec web npx nx test mobile
```
