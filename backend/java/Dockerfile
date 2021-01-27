FROM openjdk:8 as builder

ENV BUILD_DIR=/root/build/
WORKDIR $BUILD_DIR
COPY build.gradle gradlew $BUILD_DIR
COPY gradle $BUILD_DIR/gradle

# download dependencies
#RUN ./gradlew build -x :bootRepackage -x test --continue
RUN ./gradlew -q projects

COPY . $BUILD_DIR 
RUN ./gradlew build

# Deploy the war inside a jetty server, see jetty container documentation here : https://hub.docker.com/_/jetty/
FROM jetty:9.4.35-jre8-slim
# To deploy your application to the / context, use the name ROOT.war, the directory name ROOT, or the context file ROOT.xml (case insensitive).
COPY --from=builder /root/build/build/libs/*.war $JETTY_BASE/webapps/ROOT.war
