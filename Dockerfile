# Использовать официальный образ OpenJDK
FROM openjdk:22-jdk

# Устанавливаем рабочую директорию
WORKDIR /app

# Устанавливаем переменную среды для файла JAR
ARG JAR_FILE=target/*.jar

# Копируем JAR файл в контейнер
COPY ${JAR_FILE} app.jar

# Копируем файл конфигурации в контейнер
COPY src/main/resources/application.yml /app/application.yml

# Указываем автора для образа
LABEL authors="legio"

# Открываем порт 9001 для внешнего доступа
EXPOSE 9001

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.config.location=file:/app/application.yml"]
