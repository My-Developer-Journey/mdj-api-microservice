# Build Stage
FROM openjdk:17-jdk-slim AS build

RUN apt-get update && apt-get install -y maven dos2unix netcat && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY . .

# Format script and compile
RUN dos2unix wait-for-it.sh && chmod +x wait-for-it.sh
RUN mvn clean package -DskipTests

# Runtime Stage
FROM openjdk:17-jdk-slim

# 🆕 Install netcat for wait-for-it.sh
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY --from=build /app/target/*.jar blog-api.jar
COPY --from=build /app/wait-for-it.sh .

EXPOSE 8080