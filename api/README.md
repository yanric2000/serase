# API Startup

Back-end da startup. O nome comercial ainda não foi definido; **startup** é o nome de trabalho do código, do banco e do pacote Java.

A organização segue o guia [auth-api](https://github.com/Fernanda-Kipper/auth-api): Spring Boot, Spring Security com JWT stateless, PostgreSQL e Flyway. O recurso de exemplo `product` daquele repositório foi substituído pelo domínio real:

- **bebidas** que a pessoa pode guardar
- **receitas** para aprender a reproduzir
- **eventos** próximos para ir conhecer

Papéis, no mesmo espírito do guia:

- `USER` — cadastro público. Lista bebidas, receitas e eventos, e gerencia as próprias bebidas salvas.
- `ADMIN` — publica bebidas, receitas e eventos. O cadastro público não escolhe esse papel.

O passo a passo completo, com a checagem de cadastro e login, está em [docs/desenvolvimento.md](../docs/desenvolvimento.md).

## Subir localmente

Só o Docker. JDK e Maven ficam dentro da imagem.

```sh
cd api
docker compose up --build
```

A API fica em `http://localhost:8080`. O Postgres sobe no mesmo compose, na porta `5432`, com banco, usuário e senha `startup`. Esse compose não sobe o front: o app web tem o próprio arquivo em `apps/mobile/docker-compose.yml`.

O navegador chama a API em `localhost:8080`. Os dois containers não precisam da mesma rede.

No emulador Android use `http://10.0.2.2:8080`. No app, defina `EXPO_PUBLIC_API_URL` se a URL padrão (`http://localhost:8080`) não alcançar a máquina.

Variáveis opcionais:

| Variável | Padrão local |
|----------|----------------|
| `DATABASE_URL` | no compose: `jdbc:postgresql://postgres:5432/startup` |
| `DATABASE_USERNAME` | `startup` |
| `DATABASE_PASSWORD` | `startup` |
| `JWT_SECRET` | valor só de desenvolvimento; troque fora da sua máquina |

Para publicar o catálogo, promova um usuário já cadastrado:

```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'voce@email.com';
```

## Endpoints

Autenticação pública:

- `POST /auth/register` — `{ "email", "password" }` (senha com pelo menos 8 caracteres). Responde `201`.
- `POST /auth/login` — `{ "email", "password" }`. Responde `{ "token", "userId" }`.

Com header `Authorization: Bearer <token>`:

- `GET /auth/session` — `{ "userId" }`
- `GET /drinks` e `GET /drinks/{id}`
- `POST /drinks` — ADMIN. `{ "name", "description", "category" }`
- `GET /recipes` e `GET /recipes/{id}`. Filtro opcional `?drinkId=`
- `POST /recipes` — ADMIN. `{ "drinkId", "title", "ingredients", "steps" }`
- `GET /events` e `GET /events/{id}`
- `GET /events?latitude=&longitude=&radiusKm=` — eventos dentro do raio (padrão 25 km), do mais perto ao mais longe. `distanceKm` vem preenchido.
- `GET /events?city=` — eventos da cidade
- `POST /events` — ADMIN. `{ "name", "description", "venue", "city", "latitude", "longitude", "startsAt" }`
- `GET /saved-drinks` — só as da pessoa autenticada
- `POST /saved-drinks` — `{ "drinkId", "note" }`
- `DELETE /saved-drinks/{id}`

`startsAt` é data local ISO-8601, por exemplo `2026-11-01T20:00:00`.
