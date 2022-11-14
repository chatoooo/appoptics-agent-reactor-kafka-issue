# Appoptics Agent - Reactor Kafka Issue
You need to have kafka running. You can use docker-compose
```shell
   docker-compose up -d
   ``` 

### Run the app
Without enabled agent it works - prints "doOnConsumer called"

```shell
   ./gradlew run
   ```

With enabled agent it does not work - throws exception "java.lang.IllegalStateException: You must call one of receive*() methods before using doOnConsumer"

```shell
   APPOPTICS_SERVICE_KEY=<service-key> ./gradlew run
   ```

### Observed behavior
Without AO agent - `Flux.usingWhen` producer is created before `doOnConsumer` is called

With AO agent - `Flux.usingWhen` producer is created after `doOnConsumer` is called

Unable to reproduce with pure reactor code. 

## APM Agent version
- 6.22.0 and before - works - no support for reactor.io
- 6.23.0+ - throws an exception

## Test env
All behaves the same
### Java version
- Java 11.0.17 (temurin)
- Java 17.0.5 (temurin)

### OS
- Windows
- Alpine Linux on k8s