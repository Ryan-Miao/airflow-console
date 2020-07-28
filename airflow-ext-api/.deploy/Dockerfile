FROM openjdk:8-jdk-alpine
ARG profile
ENV SPRING_PROFILES_ACTIVE ${profile}
ARG JAR_FILE

COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]