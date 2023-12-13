FROM openjdk:17-jdk-alpine
EXPOSE 8081
COPY target/auth-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]