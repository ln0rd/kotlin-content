FROM gradle:jdk11 AS build
RUN mkdir /src
COPY --chown=gradle:gradle . /src
WORKDIR /src
RUN gradle clean build --no-watch-fs --no-daemon -x check

FROM openjdk:11.0.13-jdk-slim as boot
EXPOSE 8080
RUN mkdir /app
COPY --from=build /src/build/libs/clearing.jar /app/clearing.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/clearing.jar"]
