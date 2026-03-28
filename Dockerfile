FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]