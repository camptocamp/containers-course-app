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
