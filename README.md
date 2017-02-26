# LogisticCenter
Java веб-приложение "логистический центр". 
Для развертывания Spring используется Spring boot, в качестве сборщика - Maven. 
Пользовательский интерфейс - одностраничный сайт, реализующий rest+ajax. 
Информация хранится в in-memory БД HSQLDB. Запросы в базу данных генерируются при помощи стандартного CRUDRepository Spring Data JPA, 
а так же создания собственных запросов механизмом префиксов и имен полей.
Используются только аннотации.
Реализована валидация данных форм ввода: javax.validation и javax.validation.ConstraintValidator
Приложение поддерживает CRUD операции с водителями и рейсами, а так же позволяет подсчитывать суммарный расход 
топлива каждым конкретным водителем или за определенный период


Для сборки и запуска из корневой директории проекта выполнить команды:

mvn clean install

mvn spring-boot:run -pl app-web
