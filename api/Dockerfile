FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn -B -DskipTests package \
  && cp target/startup-0.0.1-SNAPSHOT.jar /app/application.jar

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/application.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
