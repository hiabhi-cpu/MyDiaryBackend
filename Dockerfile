# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project files into the container
COPY pom.xml .
COPY src ./src

# Build the application, skipping tests
RUN mvn clean package -DskipTests=true

# Debug: List the contents of the /app/target directory after build
RUN ls -l /app/target

# Stage 2: Add MySQL and Run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Install MySQL Server
RUN apt-get update && \
    apt-get install -y mysql-server && \
    apt-get clean

# Copy the JAR file from the build stage
COPY --from=build /app/target/MyDiaryBackend-0.0.1.jar app.jar

# Set environment variables for MySQL
ENV MYSQL_DATABASE=mydiarydb
ENV MYSQL_USER=myuser
ENV MYSQL_PASSWORD=mypassword
ENV MYSQL_ROOT_PASSWORD=rootpassword
ENV MYSQL_HOST=localhost
ENV MYSQL_PORT=3306
ENV DB_URL=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE}

# Initialize MySQL database
RUN service mysql start && \
    mysql -u root -p${MYSQL_ROOT_PASSWORD} -e "CREATE DATABASE ${MYSQL_DATABASE};" && \
    mysql -u root -p${MYSQL_ROOT_PASSWORD} -e "CREATE USER '${MYSQL_USER}'@'%' IDENTIFIED BY '${MYSQL_PASSWORD}';" && \
    mysql -u root -p${MYSQL_ROOT_PASSWORD} -e "GRANT ALL PRIVILEGES ON ${MYSQL_DATABASE}.* TO '${MYSQL_USER}'@'%'; FLUSH PRIVILEGES;"

# Expose MySQL port (optional for external access)
EXPOSE 3306

# Expose the application port
EXPOSE 8080

# Command to start both MySQL and the application
CMD service mysql start && \
    java -jar -Dspring.datasource.url=${DB_URL} \
              -Dspring.datasource.username=${MYSQL_USER} \
              -Dspring.datasource.password=${MYSQL_PASSWORD} \
              app.jar
