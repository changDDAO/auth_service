FROM amazoncorretto:17
LABEL authors="changhoyoun"

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} auth_service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/auth_service.jar"]