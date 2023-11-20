FROM openjdk:22-ea-jdk

WORKDIR /example

#COPY ./target/app.jar ./app.jar
COPY ./target/app-jar-with-dependencies.jar ./app-jar-with-dependencies.jar

EXPOSE 7000

CMD ["java", "-jar", "app-jar-with-dependencies.jar"]