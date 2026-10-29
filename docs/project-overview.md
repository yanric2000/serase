# Startup - Gerenciador de Bebidas

## 1. Visão Geral do Projeto

Um aplicativo móvel para amantes de bebidas, permitindo que os usuários salvem, favoritem e organizem seus chopps e cervejas preferidos. No futuro, o aplicativo será expandido para incluir uma variedade maior de bebidas, como drinks, vinhos, cachaças e runs.

O objetivo principal é oferecer uma experiência unificada, aproveitando as capacidades do React para desenvolvimento multiplataforma (mobile, com potencial para web no futuro) a partir de uma única base de código.

## 2. Tecnologias Utilizadas

### 2.1. Frontend
- **Framework**: React (com React Native para mobile).
- **Gerenciamento de Estado**: React hooks, TanStack Query (dados de servidor) e Redux Toolkit (estado global de cliente). O plano de adoção, com o Zustand atual como legado, está em [redux.md](redux.md).
- **Monorepo**: Nx (para gerenciar projetos de aplicação e bibliotecas compartilhadas).
- **Testes**: Jest.
- **Navegação (Mobile)**: React Navigation.
- **Outras**: TypeScript para tipagem estática.

### 2.2. Backend
- **Linguagem**: Java.
- **Framework**: Spring Boot.

### 2.3. Ferramentas e Infraestrutura
- **Ambiente local**: Docker. A API (`api/docker-compose.yml`) e o front web (`apps/mobile/docker-compose.yml`) sobem separados.
- **Deploy (Frontend)**: A ser definido (considerando Netlify inicialmente para web, e plataformas específicas para mobile).

## 3. Arquitetura do Projeto

A arquitetura do frontend segue um padrão **feature-based**, organizado em camadas para garantir escalabilidade, manutenção e clareza. Detalhes completos da estrutura de pastas, regras de dependência, fluxo de dados (UI → Hooks → Service → API) e gerenciamento de estado podem ser encontrados em [docs/architecture.md](docs/architecture.md).

**Princípios Chave:**
- **Separação de Preocupações**: Componentes de UI (presentational) separados da lógica de negócio e consumo de API.
- **Reutilização**: Bibliotecas compartilhadas para UI genérica, clientes de API, hooks e utilitários.
- **Modularidade**: Features como módulos independentes que encapsulam suas próprias lógicas, hooks e serviços.
- **Padronização**: Uso do Nx para enforcing de regras e automação, e skills Cursor para guiar o desenvolvimento.

## 4. Funcionalidades Prioritárias (MVP)

### 4.1. Autenticação e Gestão de Usuários
- **Registro de Usuário**: Permite que novos usuários criem uma conta.
- **Login**: Autenticação de usuários existentes.
- **Perfil Básico**: Exibição e edição de informações básicas do usuário.

### 4.2. Gestão de Bebidas (Painel Administrativo)
- Um painel de admin (inicialmente web ou uma interface simplificada no próprio app para cadastro) para:
    - Cadastrar novas bebidas (inicialmente chopps e cervejas).
    - Definir categorias de bebidas.
    - Campos obrigatórios para bebidas:
        - Nome
        - Descrição
        - Ingredientes
    - Campos opcionais para bebidas:
        - Modo de Preparo (especialmente relevante para drinks futuros).
        - Campos específicos (e.g., teor alcoólico para cervejas, tipo de lúpulo, etc.).

## 5. Funcionalidades Futuras

- **Expansão de Tipos de Bebidas**: Inclusão de drinks, vinhos, cachaças, runs, etc.
- **Recomendações Personalizadas**: Motor de recomendação baseado nos ingredientes favoritos do usuário e outras preferências.
- **Notificações**: Alertas sobre novas bebidas, promoções ou informações relevantes.
- **Favoritos e Listas**: Criação de listas personalizadas de bebidas e marcação de favoritos.
- **Interação Social**: Compartilhamento de bebidas, avaliações e comentários.
- **Versão Web**: Expansão para uma interface web utilizando a mesma base de código React.
