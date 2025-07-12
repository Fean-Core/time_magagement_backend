# Deploy no Render - Configurações Necessárias

## Configurações do Serviço no Render

### Tipo de Serviço
- **Tipo**: Web Service
- **Environment**: Docker
- **Região**: Escolha a mais próxima (ex: Oregon)

### Build & Deploy Settings
- **Build Command**: (deixar em branco para Docker)
- **Start Command**: (deixar em branco para Docker - usa o CMD do Dockerfile)
- **Dockerfile Path**: ./Dockerfile

### Para deploys que falham com "start.sh not found":
Se você receber este erro, force o comando direto no Render:
- **Start Command**: `java $JAVA_OPTS -jar app.jar --spring.profiles.active=prod`

### Variáveis de Ambiente Obrigatórias

Configure as seguintes variáveis de ambiente no painel do Render:

```
MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
MONGODB_DATABASE=timemanagement
JWT_SECRET=your-secure-jwt-secret-key-at-least-32-characters
JWT_EXPIRATION=86400000
PORT=8080
```

### Health Check Endpoint
- **Health Check Path**: `/actuator/health`

## Para Deploy via Git (não Docker)

Se preferir não usar Docker, configure assim:

- **Environment**: Java
- **Build Command**: `mvn clean package -DskipTests`
- **Start Command**: `java -Djdk.tls.client.protocols=TLSv1.2,TLSv1.3 -Dcom.sun.net.ssl.checkRevocation=false -jar target/*.jar --spring.profiles.active=prod`

## Problemas Resolvidos

### 1. SSL/TLS com MongoDB Atlas
- Atualizado para Java 21 (melhor suporte SSL)
- Configurações específicas de TLS no JVM
- Timeout e retry configurados para conexões MongoDB

### 2. Script de Inicialização
- Removido script personalizado (causa problemas no Render)
- Usando comando Java direto no Dockerfile
- Configurações SSL via variáveis de ambiente

### 3. Configuração de Produção
- Profile específico `prod` com logs otimizados
- Configurações de timeout para MongoDB Atlas
- Usando JRE em vez de JDK para menor tamanho da imagem

## Verificações Importantes

### 1. MongoDB Atlas Network Access
- Adicione `0.0.0.0/0` na whitelist do MongoDB Atlas
- Ou adicione os IPs específicos do Render

### 2. Database User Permissions
- Usuário deve ter permissões de read/write no database `timemanagement`

### 3. Connection String Format
- Deve incluir `?retryWrites=true&w=majority&appName=Cluster0`
- Username e password devem estar URL-encoded se contiverem caracteres especiais

### 4. JWT Secret
- Deve ter pelo menos 32 caracteres para segurança
- Use um gerador de string aleatória para criar

## Exemplo de URI MongoDB

```
mongodb+srv://seu-usuario:sua-senha@cluster0.abcde.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
```

## Troubleshooting

### Erro: "./start.sh: not found"
Este erro indica que o Render está tentando executar um script que não existe. Para corrigir:

1. **No painel do Render**:
   - Vá para Settings > Environment
   - Em "Start Command", defina: `java $JAVA_OPTS -jar app.jar --spring.profiles.active=prod`
   - Salve e faça redeploy

2. **Alternativa via Dockerfile**:
   - Certifique-se de que o Dockerfile termina com:
   ```dockerfile
   CMD ["java", "-jar", "app.jar"]
   ```

3. **Se ainda não funcionar**:
   - Deixe "Start Command" em branco no Render
   - O Render usará o comando definido no Dockerfile

### Se o deploy falhar:
1. Verifique os logs do Render para erros específicos
2. Confirme que todas as variáveis de ambiente estão configuradas
3. Teste a conexão MongoDB Atlas localmente primeiro
4. Verifique se o IP do Render está na whitelist do MongoDB

### Se a aplicação não iniciar:
1. Verifique o health check em `/actuator/health`
2. Revise os logs de startup da aplicação
3. Confirme que a porta 8080 está sendo usada
4. Teste as variáveis de ambiente
