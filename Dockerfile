# Stage 1: Build the application using Maven and JDK 25
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy the pom.xml file to download dependencies first
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the final jar package
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime image using JDK 25
FROM eclipse-temurin:25-jre-noble

WORKDIR /app

# Copy the compiled jar from the build stage
COPY --from=build /app/target/janja-fc-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Render provides PORT dynamically; 8080 is the local fallback
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]