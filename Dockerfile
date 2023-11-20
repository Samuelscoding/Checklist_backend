FROM openjdk:22-ea-jdk

ENV LDAP_CERTIFICATE="./certificate.cer"
ENV LDAP_CERTIFICATE_PASS="changeit"

WORKDIR /example

COPY ./target/app.jar ./app.jar
CMD ["java", "-jar", "app.jar"]

COPY ./docker-entrypoint.sh ./docker-entrypoint.sh
RUN ["chmod", "+x", "./docker-entrypoint.sh"]
ENTRYPOINT [ "docker-entrypoint,sh" ]