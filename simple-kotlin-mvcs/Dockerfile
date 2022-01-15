FROM openjdk:8
MAINTAINER leonardolb
WORKDIR /opt/app

ARG JAR_FILE=build/libs/*
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]