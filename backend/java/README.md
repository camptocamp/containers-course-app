# Java demo application

TODO describe

# Run the application

You can easily run the application using maven or gradle. It will start a Tomcat server listening on localhost:8080

## With Maven
./mvnw spring-boot:run

## With Gradle
./gradlew bootRun

# How-to build the application

## With Maven

```
mvn clean install
```

This produces the resulting war here : 
```
./target/containers-course-app-0.0.1-SNAPSHOT.war
```


## With Gradle

```
./gradlew build
```
This produces the resulting war here :
```
./build/libs/containers-course-app-0.0.1-SNAPSHOT.war
```

# List of available endpoints

https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#production-ready-endpoints-exposing-endpoints
https://spring.io/blog/2020/03/25/liveness-and-readiness-probes-with-spring-boot

http://localhost:8081/actuator/health
http://localhost:8081/actuator/info
http://localhost:8081/actuator/metrics
http://localhost:8081/actuator/env
http://localhost:8081/actuator/prometheus

curl -X POST localhost:8081/actuator/shutdown

