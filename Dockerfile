# Dockerfile
FROM openjdk:22-oracle AS build
FROM maven:3.9.7-eclipse-temurin-22 AS maven
WORKDIR /usr/src/myapp/deasfio-btg
COPY . .
RUN mvn clean install -DskipTests -U
ENV SPRING_RABBITMQ_HOST host.docker.internal
ENV SPRING_RABBITMQ_PORT 5672
ENV SPRING_RABBITMQ_USERNAME guest
ENV SPRING_RABBITMQ_PASSWORD guest
WORKDIR /usr/src/myapp/deasfio-btg/target
EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","/usr/src/myapp/deasfio-btg/target/desafio-btg-0.0.4-SNAPSHOT.jar"]