# Weather Service
A Java weather microservice


## Prerequisite
1.  Java 11 (latest long term support version)
2.  Maven 3.6.2+
3.  [kn](https://github.com/knative/client) (make sure it's installed and properly configured to connect to your cluster)


### Mac
1.  brew tap AdoptOpenJDK/openjdk
2.  brew cask install adoptopenjdk11
3.  export JAVA_HOME=`/usr/libexec/java_home -v 11`
4.  brew install maven


## Setup
1. `echo 'WEATHER_API_TOKEN=YOUR_TOKEN_HERE' > .env`


## Tests
1. `./mvnw test`


## Development
1.  `./mvnw quarkus:dev`


## OpenShift
To deploy on OpenShift, run the following, but make sure to replace with your own Dockerhub username.

```
cd $PROJECT_ROOT
cp ./src/main/docker/Dockerfile.jvm .
docker login
docker build -t your_dockerhub_username_here/weather-service .
docker push your_dockerhub_username_here/weather-service
kn service create weather-service --namespace yournamespace_here --image registry.hub.docker.com/your_dockerhub_username_here/weather-service:latest --env WEATHER_API_TOKEN=your_weather_api_token_here --force
```


## Misc
### Packaging

The application can be packaged using `./mvnw package`.
It produces the `weather-service-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/weather-service-1.0-SNAPSHOT-runner.jar`.

### Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/weather-service-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.
