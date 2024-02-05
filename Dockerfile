FROM openjdk:22-ea-jdk

ENV LDAP_CERTIFICATE="/example/certificate.cer"
ENV LDAP_CERTIFICATE_PASS="changeit"

WORKDIR /example

USER root
COPY ./certificate.cer /example/certificate.cer
#RUN keytool -printcert -file /example/certificate.cer
#RUN keytool -list -keystore "/Program Files/Java/jdk-21/lib/security/cacerts" -storepass changeit
#RUN keytool -import -alias ldap -file /example/certificate.cer -keystore /etc/ssl/certs/java/cacerts -trustcacerts -storepass changeit -noprompt

COPY ./target/app.jar ./app.jar

COPY docker-entrypoint.sh /example/docker-entrypoint.sh
RUN ["chmod", "+x", "/example/docker-entrypoint.sh"]
ENTRYPOINT "/example/docker-entrypoint.sh"

