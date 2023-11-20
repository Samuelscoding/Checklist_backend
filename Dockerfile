FROM openjdk:22-ea-jdk

ENV LDAP_CERTIFICATE="./certificate.cer"
ENV LDAP_CERTIFICATE_PASS="changeit"

WORKDIR /example

COPY ./target/app.jar ./app.jar

EXPOSE 5500

CMD ["java", "-jar", "app.jar"]