# Games News Aggregator

[![](https://img.shields.io/badge/Developed%20by-halcyon-blue)](https://github.com/HalcyonsDev)

## About project

The application is an aggregator of gaming news in Russian, which asynchronously collects and processes data from various sources, stores it in a database and gives it to the client. The application uses a microservice architecture, live updates using websockets when new news is found and a monitoring system. There is also a telegram bot that sends the latest news and has the same capabilities as the rest api

## Microservices

1. Eureka Server. It  is used as a service registry. It allows microservices to register themselves and discover other services within the system dynamically.
2. Aggregator Service. it contains a rest api for working with received news, the logic of live updates with websocket, saving news to the database and sending them via kafka to a microservice with a telegram bot.
3. Collector Service. it asynchronously parses data about news from the gaming industry from various sources, processes and sends them via kafka to the aggregator microservice.
4. Telegram Bot. Bot, which has the same capabilities as the rest api, sends the latest news to all users.

## Technologies

### Java 17

Java is a high-level, object-oriented programming language designed to be platform-independent, running on any system with a Java Virtual Machine (JVM). It is widely used for building enterprise-scale applications, mobile apps, web applications, and server-side software.

### Maven
Maven is a build automation and project management tool primarily used for Java projects. It simplifies the process of managing project dependencies, building, and deploying software by using a standardized project object model (POM) file.

### Spring Framework

Spring Framework is a comprehensive, modular framework for building enterprise-level Java applications. It provides a wide range of functionalities, including dependency injection, aspect-oriented programming, transaction management, and support for building web applications with Spring MVC.

### Spring Boot

Spring Boot is an extension of the Spring Framework that simplifies the process of building standalone, production-ready Spring applications. It provides pre-configured templates and embedded servers, eliminating much of the boilerplate configuration typically required in Spring applications.

### Spring Data

Spring Data is a part of the Spring Framework that simplifies data access and manipulation across various databases and data storage technologies. It provides a consistent and easy-to-use abstraction layer for database interactions, supporting both relational and non-relational databases.

### Spring Cloud

Spring Cloud provides tools for building cloud-native applications, offering features like service discovery, configuration management, and circuit breakers. It integrates with Spring Boot to simplify developing scalable microservices.

### Apache Kafka

Apache Kafka is a distributed streaming platform used for building real-time data pipelines and streaming applications. It provides high-throughput, low-latency messaging, and is designed to handle large volumes of data efficiently.

### Websocket

WebSocket is a protocol for real-time, full-duplex communication between a client and server over a single TCP connection. It enables low-latency data exchange, ideal for applications like chat, gaming, and live updates.

### PostgreSQL

PostgreSQL is an advanced, open-source relational database management system (RDBMS). It is known for its robustness, extensibility, and support for SQL standards.

### Liquibase

Liquibase is an open-source database schema change management tool. It allows developers to track, version, and deploy database changes seamlessly across different environments.

### Prometheus

Prometheus is an open-source monitoring and alerting toolkit designed for reliability and scalability. It collects and stores metrics as time series data, providing powerful querying capabilities and alerting functionalities.

### Grafana

Grafana is an open-source analytics and monitoring platform that visualizes time series data from various sources. It provides customizable dashboards and alerting, making it ideal for monitoring applications and infrastructure.

### Docker

Docker is a platform that allows you to package, deploy, and run applications using containers. Containers are lightweight and portable environments that ensure consistency across different systems.

### Jib

Jib is a tool for building Docker and OCI container images for Java applications. It simplifies the process by directly building images from your Java code without needing a Dockerfile.

### Telegrambots

TelegramBots is java library for creating Telegram bots in Java. It provides an easy-to-use API to interact with Telegram's Bot API, allowing developers to build bots that can send and receive messages, handle commands, and perform various interactions within Telegram.

### Jsoup

Jsoup is a Java library for working with real-world HTML. It allows you to parse, manipulate, and extract data from HTML documents with a simple and intuitive API. Jsoup is commonly used for web scraping and handling HTML content.