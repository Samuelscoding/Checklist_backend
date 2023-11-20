$JAVA_HOME/bin/keytool -import -alias ldap -file $LDAP_CERTIFICATE -keystore "\Program Files\Java\jdk-21\lib\security\cacerts" -trustcacerts -storepass $LDAP_CERTIFICATE_PASS -noprompt
pwd
ls -la /app
ls -la /app/app.jar
java -jar /app/app.jar
