# Time Management Backend API

Backend API para aplicativo de gestão de tempo desenvolvido em Spring Boot com MongoDB Atlas.

## Características

- **Framework**: Spring Boot 3.2.0
- **Banco de Dados**: MongoDB Atlas
- **Autenticação**: JWT (JSON Web Tokens)
- **Documentação**: OpenAPI/Swagger
- **Validação**: Bean Validation
- **Segurança**: Spring Security

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/timemanagement/
│   │   ├── TimeManagementApplication.java
│   │   ├── config/             # Configurações
│   │   ├── controller/         # Controllers REST
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── exception/         # Tratamento de exceções
│   │   ├── model/            # Modelos/Entidades
│   │   ├── repository/       # Repositórios MongoDB
│   │   ├── security/         # Configuração de segurança
│   │   └── service/          # Serviços de negócio
│   └── resources/
│       └── application.yml   # Configurações da aplicação
└── test/                     # Testes unitários e integração
```

## Endpoints da API

### Autenticação
- `POST /api/auth/register` - Registrar usuário
- `POST /api/auth/login` - Login do usuário
- `POST /api/auth/refresh` - Renovar token

### Usuários
- `GET /api/users/profile` - Perfil do usuário
- `PUT /api/users/profile` - Atualizar perfil

### Categorias
- `GET /api/categories` - Listar categorias
- `POST /api/categories` - Criar categoria
- `PUT /api/categories/{id}` - Atualizar categoria
- `DELETE /api/categories/{id}` - Excluir categoria

### Tarefas
- `GET /api/tasks` - Listar tarefas
- `POST /api/tasks` - Criar tarefa
- `PUT /api/tasks/{id}` - Atualizar tarefa
- `DELETE /api/tasks/{id}` - Excluir tarefa
- `PUT /api/tasks/{id}/complete` - Marcar como concluída

### Registros de Tempo
- `GET /api/time-entries` - Listar registros
- `POST /api/time-entries` - Criar registro
- `PUT /api/time-entries/{id}` - Atualizar registro
- `DELETE /api/time-entries/{id}` - Excluir registro
- `GET /api/time-entries/analytics` - Analytics de tempo

## Configuração

### Variáveis de Ambiente

```bash
# MongoDB Atlas
MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/timemanagement
MONGODB_DATABASE=timemanagement

# JWT
JWT_SECRET=your-jwt-secret-key
JWT_EXPIRATION=86400000

# Server
SERVER_PORT=8080
```

### application.yml

```yaml
spring:
  data:
    mongodb:
      uri: ${MONGODB_URI}
      database: ${MONGODB_DATABASE}
  security:
    jwt:
      secret: ${JWT_SECRET}
      expiration: ${JWT_EXPIRATION:86400000}
server:
  port: ${SERVER_PORT:8080}
```

## Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- MongoDB (local via Docker ou MongoDB Atlas)

### Opção 1: MongoDB Local com Docker

1. Clone o repositório:
```bash
git clone <repository-url>
cd time-management-backend
```

2. Inicie o MongoDB local com Docker:
```bash
docker-compose up -d
```

3. Execute a aplicação:
```bash
mvn spring-boot:run
```

### Opção 2: MongoDB Atlas

1. Clone o repositório:
```bash
git clone <repository-url>
cd time-management-backend
```

2. Copie o arquivo de template e configure as variáveis:
```bash
cp .env.template .env
```

3. Edite o arquivo `.env` com suas credenciais do MongoDB Atlas:
```bash
MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/timemanagement
MONGODB_DATABASE=timemanagement
JWT_SECRET=your-jwt-secret-key
JWT_EXPIRATION=86400000
SERVER_PORT=8080
```

4. Execute a aplicação:
```bash
mvn spring-boot:run
```

### Endpoints Disponíveis

4. A API estará disponível em: `http://localhost:8080`

5. Documentação Swagger UI: `http://localhost:8080/swagger-ui.html`

6. Health Check: `http://localhost:8080/actuator/health`

## Desenvolvimento

### Build
```bash
mvn clean compile
```

### Testes
```bash
mvn test
```

### Package
```bash
mvn clean package
```

## Tecnologias Utilizadas

- **Spring Boot 3.2.0** - Framework principal
- **Spring Data MongoDB** - Integração com MongoDB
- **Spring Security** - Segurança e autenticação
- **JWT** - Tokens de autenticação
- **Bean Validation** - Validação de dados
- **OpenAPI/Swagger** - Documentação da API
- **Spring Boot DevTools** - Desenvolvimento
- **Spring Boot Test** - Testes

## Estrutura do Banco de Dados

### Coleções MongoDB

#### users
```json
{
  "_id": "ObjectId",
  "email": "string",
  "password": "string (hashed)",
  "name": "string",
  "avatar": "string (optional)",
  "preferences": {
    "theme": "string",
    "notifications": "boolean"
  },
  "createdAt": "Date",
  "updatedAt": "Date"
}
```

#### categories
```json
{
  "_id": "ObjectId",
  "name": "string",
  "color": "string",
  "icon": "string",
  "userId": "ObjectId",
  "createdAt": "Date",
  "updatedAt": "Date"
}
```

#### tasks
```json
{
  "_id": "ObjectId",
  "title": "string",
  "description": "string",
  "priority": "enum[LOW, MEDIUM, HIGH]",
  "status": "enum[PENDING, IN_PROGRESS, COMPLETED]",
  "dueDate": "Date",
  "estimatedTime": "number (minutes)",
  "categoryId": "ObjectId",
  "userId": "ObjectId",
  "createdAt": "Date",
  "updatedAt": "Date"
}
```

#### time_entries
```json
{
  "_id": "ObjectId",
  "taskId": "ObjectId",
  "userId": "ObjectId",
  "startTime": "Date",
  "endTime": "Date",
  "duration": "number (seconds)",
  "description": "string",
  "createdAt": "Date",
  "updatedAt": "Date"
}
```

## Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
