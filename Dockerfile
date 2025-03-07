FROM openjdk:21-jdk-slim

RUN apt-get update && apt-get install -y tzdata
ENV TZ=Asia/Vladivostok

WORKDIR /app

COPY target/InternshipTestJava-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

