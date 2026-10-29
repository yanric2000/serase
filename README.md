# Startup

App para guardar bebidas, aprender a reproduzi-las e achar eventos perto. O nome comercial ainda é **startup**.

API e front sobem em containers separados. Quem clona o repositório precisa do [Docker Desktop](https://www.docker.com/products/docker-desktop/). O passo a passo, a checagem de que subiu e os erros comuns estão em [docs/desenvolvimento.md](docs/desenvolvimento.md).

```sh
cd api
docker compose up --build
```

```sh
cd apps/mobile
docker compose up --build
```

A API fica em http://localhost:8080 e o app web em http://localhost:4200.

## Documentação

| Guia | Conteúdo |
|------|----------|
| [docs/desenvolvimento.md](docs/desenvolvimento.md) | Subir, testar e destravar o ambiente |
| [docs/project-overview.md](docs/project-overview.md) | Produto e escopo |
| [docs/architecture.md](docs/architecture.md) | Camadas do front e onde colocar código |
| [docs/redux.md](docs/redux.md) | Plano de estado com Redux Toolkit |
| [api/README.md](api/README.md) | Endpoints e papéis |
| [apps/mobile/README.md](apps/mobile/README.md) | Container do app web |
