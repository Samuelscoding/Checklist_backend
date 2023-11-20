FROM openjdk:22-ea-jdk

WORKDIR /example

USER root
COPY ./certificate.cer /example/certificate.cer
RUN keytool -import -alias ldap -file /Users/savasta/projects/Checklist_project/Backend/certificate.cer -keystore /etc/ssl/certs/java/cacerts -trustcacerts -storepass changeit -noprompt
USER savasta

COPY ./target/app.jar ./app.jar
CMD ["java", "-jar", "app.jar"]

