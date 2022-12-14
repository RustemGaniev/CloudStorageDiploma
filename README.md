# Дипломная работа “Облачное хранилище”

## Постановка задачи
Разработать приложение - REST-сервис. Сервис должен предоставить REST интерфейс для возможности загрузки файлов 
и вывода списка уже загруженных файлов пользователя.
Подробнее о постановке задачи по [ссылке](./taskDescription.md)

## Технологии использованные в проекте
- Spring boot
- Maven сборщик проектов
- Docker
- JUnit 5, Mockito тестирование
- Liquibase миграции баз данных
- Java-jwt для генерации токена
- PostgreSQL база данных
- Logback логгирование, файл с настройками `src/main/resources/logback-spring.xml`

## Архитектура приложения
![](./cloudStorage-uml-diagram.png)
В приложении реализована классическая трехслойная архитектура Spring Web MVC, состоящая из трех ключевых компонентов:
Controller, Service, Repository.

В дополнении к основным компонентам, реализован компонент GzipUtils. Данный компонент создан для сжатия файлов перед
загрузкой в базу данных. Он нужен для уменьшение объема файла в целях экономичного хранения в базе данных.

Чтобы изменить максимально допустимый для загрузки размер файла нужно в `src/main/resources/application.properties`
изменить аттрибут `spring.servlet.multipart.max-file-size` и `spring.servlet.multipart.max-request-size`.

Для тестовых проверок сервиса в базу данный загружен пользователь `user@gmail.com` пароль `password`

## Описание сборки и запуска приложения
### Сборка и запуск клиентского Web приложения (FRONT)
Согласно условиям задания, клиентское приложение (разработанное за рамками данного проекта) должно быть запущено
в docker контейнере используя docker-compose. Ниже описано создание и процедура запуска FRONT приложения:
- Выполнить шаги по клонированию репозитория [FRONT](https://github.com/netology-code/jd-homeworks/tree/master/diploma/netology-diplom-frontend).
- Скопировать `DockerfileFRONT` в папку проекта фронта - /card-transfer/ (содержащую файл
  `package.json`). 
- Собрать docker образ выполнив команду<br>
  `docker build -f DockerfileFRONT -t frontcloudstorage .`<br>
### Сборка и запуск REST сервиса
- Собрать приложение, выполнив `mvn clean package`
- Перейти корневую папку проекта и собрать docker образ выполнив команду в корневой папке проекта<br>
  `docker build -f DockerfileRESTAPP -t restapp .`<br>
### Сборка и запуск базы данных Postgres
- Перейти корневую папку проекта и собрать docker образ выполнив команду в корневой папке проекта<br>
  `docker build -f DockerfilePostgres -t postgres .`<br>
### Запуск в docker-compose
Для запуска всех приложений в docker-compose выполнить команду:<br>
`docker-compose up` при добавлении ключа `-d` приложение запустится в фоновом режиме<br>
(Файл с описанием docker-compose находится в корневой папке проекта `docker-compose.yml`)