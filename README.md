# Weather Service
A Java weather microservice.


## Prerequisites
1.  OCP 4.4 with Serverless Operator installed
2.  Java 11+
3.  Maven 3.6.2+
4.  [kn](https://github.com/knative/client) (make sure it's installed and properly configured to connect to your cluster)
5.  Valid account and API token from [OpenWeather](https://openweathermap.org/api)
6.  `oc new-project knativetutorial`


### Mac
Install Java and Maven
```
brew tap AdoptOpenJDK/openjdk
brew cask install adoptopenjdk11
export JAVA_HOME=`/usr/libexec/java_home -v 11`
brew install maven
```


## Setup
`echo 'WEATHER_API_TOKEN=YOUR_TOKEN_HERE' > .env`


## Tests
`./mvnw test`


## Development
`./mvnw quarkus:dev`


## OpenShift
To deploy on OpenShift, run the following, but make sure to replace with your own Dockerhub username and your workspace name.  Note uses `kn`.

```
cd $PROJECT_ROOT

# to build locally
# ./mvnw clean package -Dquarkus.s2i.base-jvm-image=fabric8/s2i-java:latest-java11 -Dquarkus.openshift.env-vars.weather-api-token.value=weather_api_token_here -Dquarkus.kubernetes-client.trust-certs=true -Dquarkus.container-image.build=true -Djavax.net.ssl.trustStore=$JAVA_HOME/lib/security/cacert -Djavax.net.ssl.trustStorePassword=changeit
# cp ./src/main/docker/Dockerfile.jvm .

# to build completely using docker and jvm
# cp ./src/main/docker/Dockerfile.build .

# to build completely using docker and native
cp ./src/main/docker/Dockerfile.multistage .

# push to docker
docker login
docker build -t your_dockerhub_username_here/weather-service .
docker push your_dockerhub_username_here/weather-service

# deploy
kn service create weather-service --namespace yournamespace_here --image registry.hub.docker.com/your_dockerhub_username_here/weather-service:latest --env WEATHER_API_TOKEN=your_weather_api_token_here --force
```


## CodeReady workspace
Open a terminal and run the following.

```
ssh-keygen -t rsa
cat ~/.ssh/id_rsa.pub  # Add this key to your github profile
git config --global user.name "Jon Keam"
git config --global user.email jkeam@redhat.com
```

Also create a `~/.ssh/config` that looks like
```
Host github.com
IdentityFile ~/.ssh/id_rsa
```

Now you can clone your repo, something like this:
```
git clone git@github.com:jkeam/weather-service.git`
cd weather-service
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


## Serverless Installation
These instructions are just here for reference, as you can find the full instructions [here](https://docs.openshift.com/container-platform/4.4/serverless/installing_serverless/installing-openshift-serverless.html).

1.  Install Serverless Operator
2.  `oc new-project knative-serving`
3.  Create `serving.yml`
```
apiVersion: operator.knative.dev/v1alpha1
kind: KnativeServing
metadata:
    name: knative-serving
    namespace: knative-serving
```
4.  `oc apply -f ./serving.yml`
5.  Verify it is up
```
oc get knativeserving.operator.knative.dev/knative-serving -n knative-serving --template='{{range .status.conditions}}{{printf "%s=%s\n" .type .status}}{{end}}'
```
and look for:
```
DependenciesInstalled=True
DeploymentsAvailable=True
InstallSucceeded=True
Ready=True
```
6.  `oc new-project knative-eventing`
7.  Create `eventing.yml`
```
apiVersion: operator.knative.dev/v1alpha1
kind: KnativeEventing
metadata:
    name: knative-eventing
    namespace: knative-eventing
```
8.  `oc apply -f ./eventing.yml`
9.  Run the following to verify that it is ready
```
oc get knativeeventing.operator.knative.dev/knative-eventing \
  -n knative-eventing \
  --template='{{range .status.conditions}}{{printf "%s=%s\n" .type .status}}{{end}}'
```
And wait for this result:
```
InstallSucceeded=True
Ready=True
```
