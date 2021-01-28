FROM openjdk:8 as builder

ENV BUILD_DIR=/root/build/
WORKDIR $BUILD_DIR
COPY build.gradle gradlew $BUILD_DIR
COPY gradle $BUILD_DIR/gradle

# Initialize Gradle in a docker build layer to avoid re-downloading gradle binary everytime
RUN ./gradlew -q projects

COPY . $BUILD_DIR 
RUN ./gradlew build

# Unpacking the war to allow later addition of the data.json configuration file into the contextpath
# To deploy your application to the / context, use the name ROOT.war, the directory name ROOT, or the context file ROOT.xml (case insensitive).
RUN mkdir $BUILD_DIR/ROOT
WORKDIR $BUILD_DIR/ROOT
RUN find $BUILD_DIR -type f -name *.war -exec unzip '{}' ';' 

# Deploy the war inside a jetty server, see jetty container documentation here : https://hub.docker.com/_/jetty/
FROM jetty:9.4.35-jre8-slim
COPY --from=builder /root/build/ROOT $JETTY_BASE/webapps/ROOT
