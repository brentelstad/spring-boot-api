FROM maven:3.6.2-amazoncorretto-8 AS BUILD

COPY ../src src

RUN mvn clean install

RUN mvn spring-boot:run