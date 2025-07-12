# Deploy no Render - Configurações Necessárias

## Variáveis de Ambiente Obrigatórias

Configure as seguintes variáveis de ambiente no painel do Render:

```
MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
MONGODB_DATABASE=timemanagement
JWT_SECRET=your-secure-jwt-secret-key-at-least-32-characters
JWT_EXPIRATION=86400000
PORT=8080
```

## Configurações do Serviço no Render

- **Build Command**: `mvn clean package -DskipTests`
- **Start Command**: `java -jar target/*.jar --spring.profiles.active=prod`
- **Environment**: Java
- **Node Version**: (deixar em branco para Java)

## Problemas Resolvidos

### 1. SSL/TLS com MongoDB Atlas
- Atualizado para Java 21 (melhor suporte SSL)
- Configurações específicas de TLS no comando de inicialização
- Timeout e retry configurados para conexões MongoDB

### 2. Configuração de Produção
- Profile específico `prod` com logs otimizados
- Configurações de timeout para MongoDB Atlas
- SSL/TLS otimizado para ambiente cloud

### 3. Variáveis de Ambiente
- Usando `PORT` em vez de `SERVER_PORT` (padrão Render)
- Configurações específicas para MongoDB Atlas

## Troubleshooting

Se ainda houver problemas SSL, verifique:
1. URL de conexão MongoDB Atlas está correta
2. IP do Render está na whitelist do MongoDB Atlas (usar 0.0.0.0/0 temporariamente)
3. Credenciais do MongoDB estão corretas
4. Database exists ou tem permissão de criação
