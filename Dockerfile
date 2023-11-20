FROM openjdk:22-ea-jdk

ENV LDAP_CERTIFICATE="./certificate.cer"
ENV LDAP_CERTIFICATE_PASS="changeit"

WORKDIR /example

COPY ./target/authentication-0.0.1.jar ./authentication-0.0.1.jar

EXPOSE 7000

CMD ["java", "-jar", "authentication-0.0.1.jar"]