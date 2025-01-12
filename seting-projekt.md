1. открыть Docker Desktop
2. Запустить DockerFile для поддержания докера Kafka docker-compose.yml
3. Открыть PostgreSQL
4. Открыть IntelliJ IDEA Ultimate 2023.3.1
5. Настроить application.properties
   под вашу систему:
   spring.kafka.bootstrap-servers=localhost:9092
   spring.datasource.url=jdbc:postgresql://localhost:5432/library
   spring.datasource.username=postgres
   spring.datasource.password=P@ssword
   server.port=8080
6. Запустить основной класс LibraryApplication:
   «Нажав на зеленую кнопку плей»
7. Окрыть любой браузер например, Яндекс
8. Ввести в адресную
   строку
   http://localhost:8080/swagger-ui/index.html#/
   для запуска swagger

Данная программа написана в приложении
Jetbrains IntelliJ IDEA Ultimate 2023.3.1,  при использовании
jdk 19 Amazon Corretto version 19.0.2,  на  java version 17
и использовании  Spring boot 3.4.1



