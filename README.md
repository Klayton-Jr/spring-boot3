# Spring-Boot 3
Projeto para estudo do spring boot 3 com java 17

Para poder rodar o projeto via linha de comando, em um ambiente produtivo por exemplo, usa-se esse comando abaixo:
```sh
java -Dspring.profiles.active=prod -DDATASOURCE_URL=jdbc:mysql://localhost:3306/vollmed_api -DDATASOURCE_USERNAME=root -DDATASOURCE_PASSWORD=password -jar build/libs/api-0.0.1-SNAPSHOT.jar
```