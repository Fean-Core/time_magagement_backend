#!/bin/bash

# Wait for network connectivity
echo "Waiting for network connectivity..."
until ping -c1 google.com &>/dev/null; do
    echo "Waiting for network..."
    sleep 1
done

# Set Java options for better SSL/TLS support
export JAVA_OPTS="-Djdk.tls.client.protocols=TLSv1.2,TLSv1.3 \
                  -Dcom.sun.net.ssl.checkRevocation=false \
                  -Djdk.tls.trustNameService=true \
                  -Djavax.net.ssl.trustStore=/opt/java/openjdk/lib/security/cacerts \
                  -Djavax.net.ssl.trustStorePassword=changeit"

# Add MongoDB specific SSL options
export MONGODB_OPTS="-Dcom.mongodb.ssl.allowInvalidHostnames=false \
                     -Dcom.mongodb.ssl.allowInvalidCertificates=false"

echo "Starting Time Management Backend..."
exec java $JAVA_OPTS $MONGODB_OPTS -jar app.jar --spring.profiles.active=prod
