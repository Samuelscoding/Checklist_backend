FROM openjdk:22-ea-jdk

WORKDIR /example

COPY .env ./

COPY ./target/authentication-1.0-SNAPSHOT.jar ./authentication-1.0-SNAPSHOT.jar

EXPOSE 5500

ENTRYPOINT ["java", "-jar", "authentication-1.0-SNAPSHOT.jar"]
