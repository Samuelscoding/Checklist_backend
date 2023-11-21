FROM openjdk:22-ea-jdk

WORKDIR /example

USER root
COPY ./certificate.cer /example/certificate.cer
RUN update-ca-certificates
#RUN keytool -printcert -file /example/certificate.cer
#RUN keytool -list -keystore "/Program Files/Java/jdk-21/lib/security/cacerts" -storepass changeit
#RUN keytool -import -alias ldap -file /example/certificate.cer -keystore /etc/ssl/certs/java/cacerts -trustcacerts -storepass changeit -noprompt

COPY ./target/app.jar ./app.jar
CMD ["java", "-jar", "app.jar"]

