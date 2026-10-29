# Guia do desenvolvedor

Como subir o projeto, conferir se está no ar e achar o código certo. API e front são containers separados: um pode rodar sem o outro.

Pré-requisito: [Docker Desktop](https://www.docker.com/products/docker-desktop/) aberto. Node, JDK e Maven não precisam estar instalados na máquina.

## 1. Subir a API

Num terminal, na raiz do repositório:

```sh
cd api
docker compose up --build
```

A primeira vez baixa Postgres, Maven e as dependências Java. Espere a linha `Started StartupApplication`.

| Serviço | Endereço |
|---------|----------|
| API | http://localhost:8080 |
| Postgres | `localhost:5432`, banco/usuário/senha `startup` |

Confira o cadastro e o login no PowerShell. Troque o e-mail se ele já existir. Use aspas simples em volta do JSON.

```powershell
Invoke-WebRequest -Method Post -Uri http://localhost:8080/auth/register -ContentType "application/json" -Body '{"email":"dev@startup.dev","password":"senha-segura"}'
```

`StatusCode` `201` é cadastro novo. `409` é e-mail que já existe. Os dois significam que a API respondeu.

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/auth/login -ContentType "application/json" -Body '{"email":"dev@startup.dev","password":"senha-segura"}'
```

A resposta traz `token` e `userId`. Sem token, `GET /drinks` responde `401`.

Para parar: `Ctrl+C` no terminal do compose, depois `docker compose down` dentro de `api`. O volume do Postgres permanece. `docker compose down -v` apaga os dados.

Contrato dos endpoints: [api/README.md](../api/README.md).

## 2. Subir o front

Noutro terminal:

```sh
cd apps/mobile
docker compose up --build
```

A primeira vez executa `npm ci` dentro do volume `web_node_modules` e demora. Quando o Vite indicar o endereço, abra http://localhost:4200.

A tela inicial mostra o título `OLÁ MUNDO`. Sem a API no ar, a sessão aparece deslogada e a página continua abrindo.

O código da sua máquina entra no container por volume. Salvar um arquivo recarrega o navegador. Dependências novas exigem recriar o volume:

```sh
docker compose down
docker volume rm mobile_web_node_modules
docker compose up --build
```

O nome do volume pode aparecer com outro prefixo. Liste com `docker volume ls` e apague o que termina em `web_node_modules`.

Para parar: `Ctrl+C` e `docker compose down` dentro de `apps/mobile`.

## 3. O que cada pasta faz

| Pasta | Papel |
|-------|--------|
| `api` | API Spring Boot, Postgres e Flyway |
| `apps/mobile` | App React Native, servido na web pelo Vite |
| `libs/features` | Regra de negócio: UI da feature, hooks, services, store |
| `libs/shared` | UI genérica e cliente HTTP |
| `docs/architecture.md` | Camadas e para onde vai código novo |
| `docs/redux.md` | Plano do estado global com Redux Toolkit |
| `docs/project-overview.md` | Produto e escopo |

Fluxo de uma tela: page → hook da feature → service → `apiClient` em `libs/shared`. A page não chama a API direto.

## 4. Testes

Com a API já construída pelo compose, os testes Java rodam na imagem de build:

```sh
cd api
docker build --target build -t startup-api-test .
docker run --rm startup-api-test mvn -B test
```

Com o front no ar:

```sh
cd apps/mobile
docker compose exec web npx nx test mobile
```

## 5. Problemas comuns

**`docker` não conecta no daemon.** Abra o Docker Desktop e espere o ícone ficar estável. Rode `docker info` antes do compose.

**Porta 8080, 5432 ou 4200 ocupada.** Veja o processo com `netstat -ano | findstr :8080` (troque a porta). Pare o outro processo ou mude o mapeamento no `docker-compose.yml` daquele app, só no lado do host (`"8081:8080"`).

**API sobe e cai na hora.** Leia o log do compose. O caso típico é o Postgres ainda iniciando; o compose espera o healthcheck. Se o volume antigo ficou inconsistente, `docker compose down -v` em `api` e suba de novo.

**A aba mostra o título Mobile e a página fica em branco.** O JavaScript quebrou no navegador. Abra o DevTools com F12, aba Console. O `console.log` do app aparece ali, não no terminal do Docker. A causa que já apareceu foi `process is not defined` em `libs/shared/src/config/env.ts`: no Vite a URL da API entra por `process.env.EXPO_PUBLIC_API_URL` com ponto, não com colchetes, porque o colchete não é substituído e o navegador não tem `process`.

**Front abre em branco ou o Vite não acha arquivo.** Confira se o comando rodou em `apps/mobile` e se o volume aponta para a raiz do repositório (`../..`).

## 6. SHA-1 para o login do Google (Android)

O console do Google pede o SHA-1 do certificado que assina o app Android. Isso não sai do container do Vite. O certificado de debug já está no projeto, e o `keytool` vem com o JDK da máquina (o container web não tem Java).

Pacote Android, em `apps/mobile/android/app/build.gradle`: `com.mobile`.

No PowerShell, na raiz do repositório:

```powershell
keytool -list -v -keystore apps/mobile/android/app/debug.keystore -alias androiddebugkey -storepass android
```

Use a linha `SHA1:` no cliente OAuth Android do GCP, junto com o pacote `com.mobile`. A senha `android` e o alias `androiddebugkey` são os do `signingConfigs.debug` desse `build.gradle`. Essa chave é só de debug. O app de produção precisa de outro keystore, e esse arquivo de produção não entra no git.

**Login no navegador não gruda depois de recarregar.** O token fica na memória do processo. Recarregar a página limpa a sessão até existir persistência.

**Catálogo (`POST /drinks`) responde 403.** O cadastro público cria usuário `USER`. Publicar bebida, receita ou evento exige `ADMIN`:

```sh
docker compose exec postgres psql -U startup -d startup -c "UPDATE users SET role = 'ADMIN' WHERE email = 'dev@startup.dev';"
```

Rode isso em `api`, com o compose da API no ar. O token antigo passa a valer como admin na próxima requisição, porque a API lê o papel no banco a cada request.
