# Flowing Retail

This project simulates a typical business logic workflow using event-driven architecture. The implementation is based on
the implementation of [flowing retail](https://github.com/scs-edpo/lab04-flowing-retail).
We use Apache Kafka and Camunda Process orchestration to facilitate our workflow. 

## How to run
### docker-compose
To run the Flowing Retail project you first need to be sure that all the relevant projects have been built at least once: (Maven Window -> Run Maven Build "green play button" )

```
  $ cd .\kafka\java\
  $ mvn clean install
```

Then you can execute:

```
  $ docker-compose -f docker-compose.yml up --build
```

(omit --build if you have already built the images):

```
  $ docker-compose -f docker-compose.yml up
```
from the directory [runner/docker-compose](runner/docker-compose).

### Camunda

Open http://localhost:8091/camunda/app/welcome/default/#!/login in your browser and login with the credentials that can be found here: [kafka/java/booking/src/main/resources/application.yaml](kafka/java/booking/src/main/resources/application.yaml)