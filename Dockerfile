FROM eclipse-temurin:21-jre-alpine
LABEL author="sharad-oza"

WORKDIR /app
COPY target/investment-engine-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5477

ENTRYPOINT ["java", "-jar" , "app.jar"]