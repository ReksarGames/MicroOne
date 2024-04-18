FROM openjdk:22-jdk

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

LABEL authors="legio"
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "/app.jar"]
