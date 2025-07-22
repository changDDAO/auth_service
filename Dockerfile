FROM amazoncorretto:17
LABEL authors="changhoyoun"

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} auth_service.jar

EXPOSE 8081

ENTRYPOINT ["java","-Duser.timezone=Asia/Seoul","-Dspring.profiles.active=prod", "-jar", "/auth_service.jar"]