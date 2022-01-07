#Stage 1 Build
FROM gradle:jdk11 AS build
ENV APP_HOME=/home/gradle/filmapi
COPY --chown=gradle:gradle . $APP_HOME
WORKDIR $APP_HOME
RUN gradle clean bootJar --no-daemon

#Stage 2 Service Image
FROM bellsoft/liberica-openjdk-alpine:11
ENV APP_HOME=/home/gradle/filmapi
ENV MODULE_NAME=filmapi
COPY --from=build $APP_HOME/build/libs/$MODULE_NAME.jar /opt/$MODULE_NAME.jar

EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar", "/opt/filmapi.jar"]