# Build Stage
FROM openjdk:17-jdk-slim AS build

RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime Stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar blog-api.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "blog-api.jar"]