$JAVA_HOME/bin/keytool -import -alias ldap -file $LDAP_CERTIFICATE -keystore /usr/java/openjdk-22-ea-jdk/lib/security/cacerts -trustcacerts -storepass $LDAP_CERTIFICATE_PASS -noprompt
java -jar /example/app.jar
