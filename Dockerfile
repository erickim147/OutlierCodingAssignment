FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=build/libs/api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} dockerapp.jar
ENTRYPOINT ["java", "-jar", "/dockerapp.jar"]