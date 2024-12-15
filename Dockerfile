# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests=true
RUN ls -l /app/target

# Stage 2: Add MariaDB and Run the application
FROM openjdk:17-jdk-slim

WORKDIR /app

# Install MariaDB Server (a MySQL-compatible database)
RUN apt-get update && \
    apt-get install -y mariadb-server && \
    apt-get clean

COPY --from=build /app/target/MyDiaryBackend-0.0.1.jar app.jar

# Set environment variables
ENV MYSQL_DATABASE=mydiarydb
ENV MYSQL_USER=myuser
ENV MYSQL_PASSWORD=mypassword
ENV MYSQL_ROOT_PASSWORD=rootpassword
ENV MYSQL_HOST=localhost
ENV MYSQL_PORT=3306
ENV DB_URL=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE}

# Initialize the MariaDB database
RUN service mysql start && \
    mysql -u root -e "CREATE DATABASE ${MYSQL_DATABASE};" && \
    mysql -u root -e "CREATE USER '${MYSQL_USER}'@'%' IDENTIFIED BY '${MYSQL_PASSWORD}';" && \
    mysql -u root -e "GRANT ALL PRIVILEGES ON ${MYSQL_DATABASE}.* TO '${MYSQL_USER}'@'%'; FLUSH PRIVILEGES;"

EXPOSE 3306
EXPOSE 8080

CMD service mysql start && \
    java -jar -Dspring.datasource.url=${DB_URL} \
              -Dspring.datasource.username=${MYSQL_USER} \
              -Dspring.datasource.password=${MYSQL_PASSWORD} \
              app.jar
