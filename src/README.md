# Лабораторная работа: Stream API, Gson, Lombok

Автор: Нарольский Максим Витальевич 
Группа: Фит 251

## Описание
Обработка списка посетителей библиотеки из books.json с помощью Stream API.
JSON читается через Gson, стандартные методы классов генерирует Lombok. JDK 25.

## Сборка и запуск
Требуется JDK 25 и Maven.

    mvn compile
    mvn exec:java -Dexec.args="books.json"

Файл books.json лежит в корне проекта.