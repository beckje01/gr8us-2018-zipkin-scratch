# A Scratch Env for Zipkin Experiments

This is a simple set of microservices setup to allow experimentation with Zipkin tracing.

## Running Zipkin 

```
docker-compose up -d
```

This will start zipkin listening to [http://127.0.0.1:9411](http://127.0.0.1:9411)

## Running the Services

All 3 services are setup as a multi-project gradle build you can run from the root of this repo:

```bash
#Ratpack  127.0.0.1:5050
./gradlew ratpack:run

#Grails 127.0.0.1:8080
./gradlew grails:boRu

#Micronaut 127.0.0.1:8081
./gradlew microanut:run
```

## Experiments

Try making requests to some of the endpoints below you can then search in zipkin and see how the spans etc were created.

Some great areas to try are adding tags and events to spans. Or try some curl requests adding B3 headers to force tracing to start on some calls.

### Endpoints

* [http://localhost:8081/hello](http://localhost:8081/hello) -- Simple Hello World
* [http://localhost:8081/other](http://localhost:8081/other) -- Call Micronaut -> Grails
* [http://localhost:5050/zip/hello](http://localhost:5050/zip/hello) -- This will send a request to micronaut / hello app
