FROM openjdk:18-alpine
EXPOSE 8080
ADD build/libs/etaskify.jar etaskify.jar
ENTRYPOINT ["java","-jar","etaskify.jar"]