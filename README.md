# Weather Service
A Java weather microservice

## Prerequisite
1.  Java 11 (latest long term support version)
2.  Maven 3.6.2+

### Mac
1.  brew tap AdoptOpenJDK/openjdk
2.  brew cask install adoptopenjdk11
3.  export JAVA_HOME=`/usr/libexec/java_home -v 11`
4.  brew install maven

## Setup
1.


## First Setup
```
mvn io.quarkus:quarkus-maven-plugin:1.5.0.Final:create \
    -DprojectGroupId=org.acme \
    -DprojectArtifactId=weather-service \
    -DclassName="org.acme.resources.WeatherResource" \
    -Dpath="/weather"
cd weather
```


# weather-service project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `weather-service-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/weather-service-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/weather-service-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.
