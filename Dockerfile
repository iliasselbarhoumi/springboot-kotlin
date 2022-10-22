FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} shop-app.jar
ENTRYPOINT ["java","-jar","/shop-app.jar"]