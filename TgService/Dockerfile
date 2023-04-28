FROM openjdk:18
COPY target/TgNewService-1.0-SNAPSHOT.jar TgService.jar
#Добавленный код, который ты предложила добавить.
#Я правильно понимаю, что он тянет все зависимости с родительского помника?
#И правильно ли я его добавил? Просто вроде и так работает, без него
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY src/ ./src/
RUN mvn clean package

ENTRYPOINT ["java", "-jar", "TgService.jar"]