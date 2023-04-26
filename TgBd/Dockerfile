FROM openjdk:17
COPY target/TgBd-1.0-SNAPSHOT.jar TgBd.jar
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY src/ ./src/
RUN mvn clean package
ENTRYPOINT ["java", "-jar", "TgBd.jar"]