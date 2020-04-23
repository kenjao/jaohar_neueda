FROM openjdk:8-jdk-alpine
ADD target/neueda-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9090
CMD -p 9090:9090
ENTRYPOINT ["java", "-jar", "app.jar"]
