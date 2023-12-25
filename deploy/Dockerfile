FROM openjdk:17

EXPOSE 8080

RUN mkdir /usr/src/myapp

mvn clean package

COPY deploy_spring_test-0.0.1-SNAPSHOT.jar /usr/src/myapp

WORKDIR /usr/src/myapp

CMD ["java","-jar","deploy_spring_test-0.0.1-SNAPSHOT.jar"]

