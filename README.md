# Flowing Retail

This project simulates a typical business logic workflow using event-driven architecture. The implementation is based on
the implementation of [flowing retail](https://github.com/scs-edpo/lab04-flowing-retail).
We use Apache Kafka and Camunda Process orchestration to facilitate our workflow. 

## General description
This project simulates the possible process of a customer booking an event at the Fusion Arena St. Gallen. Thereby, focus is on customers that pay via invoice. Since there is a need for a bank payment without live payment processing, the process has to make sure and check that the payment has been received before the booking is confirmed. The customer is given a time of 30 days to make the payment.

## Explicit references to the concepts of the lecture
Apache Kafka is used as an open-source distributed event streaming platform. All seven implemented services, [accounting](kafka/java/accounting), [bank](kafka/java/bank), [booking](kafka/java/booking), [monitor](kafka/java/monitor), [notification](kafka/java/notification), [payment](kafka/java/payment) and [qrInvoice](kafka/java/qrInvoice) are connected via Kafka topics. Have a look at the messages folder for the corresponding kafka topics of each service. Kafka is used for the communication between the services and the orchestration of the processes.

An event notification is for example created when a new booking is made. One Kafka topic was used called "flowing-retail" where events are distinguished among each other by name and service type. The advantage are decoupling, dependency inversion and that changes in one service do not affect the other services. The biggest disadvantage is that no statement of overall behaviour can be made.

No event-carried state transfer is used in this project. There was no need to update a local copy of data in the services via events.

Camunda is used for the orchestration of the processes. The main process is the booking process, which is started when a new booking is made. The sub process is the accounting process, which is started when the booking process is waiting for the bank payment. The processes are orchestrated using the Camunda BPM platform for process & workflow orchestration. See in ADR section for explicit differentiation between orchestration and choreography.






## Building the project
Use the Maven task "flowing-retail-kafka" to clean and rebuild all projects in the application. To run the Flowing Retail project you first need to be sure that all the relevant projects have been built at least once: (Maven Window -> Run Maven Build "green play button" )

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

### Launching the application
There is a [docker-compose](runner/docker-compose/docker-compose.yml) script that, when executed, launches all relevant project in docker.

### Accessing the Camunda cockpit
To have an overview of the processes and the running instances, you have to access the Camunda Cockpits.

- [Main process](http://localhost:8091/)
- [Sub process](http://localhost:8097/)

Use the following credentials to log in:

- Username: flowingRetail
- Password: flowingRetail

Or find them here for [main](kafka/java/booking/src/main/resources/application.yaml) and [sub](kafka/java/accounting/src/main/resources/application.yaml) process.

### Start a new process
To start a new process, access [this link.](http://localhost:8091/booking.html) Once you enter a date for your booking and press "book", you should be able to see that a new main process instance has started. 

The sub process should be waiting for receiving the bank payment, as can be seen here:

![docs/ProcessWaitingForPayment.png](docs/ProcessWaitingForPayment.png)

## Our process
So far, we use Kafka as a message broker, passing messages between bounded contexts and processes.
The main (=booking) process, as well as the sub (=accounting) process, is orchestrated using a Camunda process definition. 

Booking Process:
![docs/booking_process.png](docs/booking_process.png)

Accounting Process:
![docs/accounting_process.png](docs/accounting_process.png)

The booking process waits for the Bank Payment Retrieved message, originating from the Accounting process, after it notifies the accounting process, that a new booking has been made. We skip the entire process, if the customer decided to pay by card, since then we already have collected the money and the booking is valid.

## ADR (Architecture Decision Records)
Please find all ADRs in the [docs/adr](docs/adr) folder.
