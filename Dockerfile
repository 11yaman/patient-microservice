FROM openjdk:17-jdk-alpine
EXPOSE 8083
COPY target/patient-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]