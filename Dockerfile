FROM openjdk:15
ADD build/libs/restaurant-backend-0.0.1-SNAPSHOT.jar restaurant-backend-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "restaurant-backend-0.0.1-SNAPSHOT.jar"]