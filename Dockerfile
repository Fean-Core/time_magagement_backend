FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Install curl for health checks
RUN apk add --no-cache curl

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Use environment variables for Java options
ENV JAVA_OPTS="-Djdk.tls.client.protocols=TLSv1.2,TLSv1.3 -Dcom.sun.net.ssl.checkRevocation=false -Djdk.tls.trustNameService=true"

CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.profiles.active=prod"]