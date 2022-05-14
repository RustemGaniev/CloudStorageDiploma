# Дипломная работа “Облачное хранилище”

## Постановка задачи
Требования к заданию изложены [здесь](./taskDescription.md)

### Технологии использованные в проете
- Spring boot
- Maven сборщик проктов 
- Docker
- JUnit 5, Mockito тестирование
- Liquibase миграции баз данных
- Java-jwt для генерации токена
- PostgreSQL база данных
- Logback логгирование файл с настройками `src/main/resources/logback-spring.xml`

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