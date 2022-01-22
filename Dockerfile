FROM openjdk:11
EXPOSE 8080
ADD target/simple-url-shortener.jar simple-url-shortener.jar
ENTRYPOINT ["java", "-jar", "/simple-url-shortener.jar"]