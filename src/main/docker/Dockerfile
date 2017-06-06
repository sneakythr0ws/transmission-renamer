FROM openjdk:8-jre-alpine

ENV JAVA_OPTS=""

VOLUME /tmp

# add directly the war
ADD "target/*.jar" /app.jar

RUN sh -c 'touch /app.jar'

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]