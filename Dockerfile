# Stage 1: Build the application using Maven and JDK 25
FROM maven:3.9.9-eclipse-temurin-25 AS build
WORKDIR /app

# Copy the pom.xml file to download dependencies first (optimises Docker caching)
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

# Render injects the port dynamically, 8080 is a sensible local fallback
EXPOSE 8080

# Command to execute your Spring Boot backend
ENTRYPOINT ["java", "-jar", "app.jar"]
