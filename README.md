# Apache Kafka Microservice

## Описание

Программа представляет собой микросервис для обработки сообщений и транзакций с использованием Apache Kafka в качестве системы сообщений.

## Архитектура

Программа состоит из нескольких компонентов: контроллеров, сервисов, репозиториев и конфигурационных классов.

В качестве интеграционной шины используется Apache Kafka.

Присутствует поддержка транзакций и отправка сообщений в Kafka-топики.

## Ключевые компоненты

- KafkaProducer: сервис для отправки сообщений в Kafka-топики.
- ProducerController: контроллер для обработки запросов на отправку сообщений и транзакций через Kafka.
- MessageController: контроллер для отправки сообщений в другой микросервис через HTTP с использованием WebClient.
- KafkaConfiguration: класс конфигурации Kafka, создающий темы Kafka на основе конфигурационных свойств.
- KafkaProducerConfig: конфигурация Kafka Producer, определяющая свойства соединения и сериализаторы для Kafka Producer.
- KafkaStreamsConfig: конфигурация Kafka Streams для обработки сообщений в реальном времени.

## Использование Kafka

- Все сообщения отправляются в Kafka-топики, что обеспечивает асинхронную и отказоустойчивую обработку сообщений.
- Поддерживается отправка сообщений как в обычном, так и в транзакционном режиме.
- Для обработки сообщений в реальном времени используется Kafka Streams.

## Дополнительные компоненты

Присутствует конфигурация безопасности Spring Security и фильтр CORS для обеспечения безопасности и кросс-доменных запросов соответственно.

## Заключение

Программа представляет собой комплексное решение для работы с сообщениями и транзакциями, основанное на Apache Kafka. Использование Kafka обеспечивает масштабируемость, отказоустойчивость и возможность обработки сообщений в реальном времени, что делает эту программу подходящим выбором для различных микросервисных архитектур.

## Обновления

- Добавлены два контроллера: один для отправки сообщений, а другой для выполнения транзакций.
- Добавлена поддержка Kafka Streams для обработки данных в реальном времени с использованием библиотеки Apache Kafka Streams.

## Интеграции

- Интеграция Kafka Manager: Добавлен Kafka Manager для управления и мониторинга кластера Kafka.
- Настройка CMake и Zookeeper: Настроен CMake для работы с проектом и добавлена интеграция с Zookeeper для управления кластером Kafka.

## Разделение на микросервисы

Проект разделен на 2 микросервиса: Первый микросервис отвечает за отправку данных, второй - за их прием. Кроме того, в проекте подключены Zookeeper и Kafka Manager для обеспечения управления и мониторинга кластера Kafka.


https://github.com/ReksarGames/MicroTwo/tree/master
