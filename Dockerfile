FROM openjdk:11-jre
COPY target/*.jar monitoring.jar
ENTRYPOINT ["java", "-jar", "monitoring.jar"]