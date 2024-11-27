# Stage 1: Build the application
FROM maven:3.9.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project files into the container
COPY pom.xml .
COPY src ./src

# Build the application, skipping tests
RUN mvn clean package -DskipTests=true

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/MyDiaryBackend-0.0.1.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
