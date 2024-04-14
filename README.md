# Fusion Flow
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

We explicitly (see ADR) decided not to use Zeebe, the new process engine of Camunda 8, since we are using Camunda 7.

In lecture 6, [Sagas](https://www.youtube.com/watch?v=0UTOLRTwOX0) and "Stateful Resilience Patterns" were introduced. We use the "Epic Saga" (traditional saga pattern) in our project. The Saga pattern is a way to manage transactions that span multiple services. A saga is a sequence of local transactions. Each local transaction updates the database and publishes a message or event to trigger the next local transaction in the saga. If a local transaction fails because it violates a business rule then the saga executes a series of compensating transactions that undo the changes that were made by the preceding local transactions. The saga pattern is used in the booking process. If the customer decides to pay by card, the booking is directly confirmed. If the customer decides to pay by invoice, the booking is confirmed after the bank payment has been received. If the bank payment is not received within 30 days, the booking is cancelled. The saga pattern is used to manage the transactions of the booking process.

As resilience pattern, human intervention is used. In the accounting process, we best saw fit that, after an automatic rejection of a customer, our accounts have the final word on rejecting or approving a customer. Our software calls an external relational database to see any customer that was put on the blacklist, if we find that a customer has been rejected in the past, we will mark him for human review. To not lose out on a possible sale due to the automatic rejection, this manual review step serves as a fallback, we provide the customer name and his booking to a human agent, who has last call on wether to reject a booking or if we take the chance and allow a booking. Even if we allow a rejected booking manually, the booking gets automatically cancelled if we do not receive the payment within 30 days. 



## ADR (Architecture Decision Records)
Please find all ADRs in the [docs/adr](docs/adr) folder.
- Choosing between Camunda 7 and 8
- Distinction between Orchestration and Choreography


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
The accounting process first passes the customer through an automatic booking review, where we access an external database to see if we have rejected this customer in the past. if this is the case, we flag the customer for a human review, where an accountant can take any necessary steps to deem a booking valid or invalid.
If the booking is deemed valid, either automatically through our system or by a human accountant, we set the process on hold and wait for the money to arrive. Our software waits for the arrival of a Kafka message from our bank, confirming that we received the money from the customer. Camunda automatically continues the process to cancel the order,
if we do not receive any money within 30 days.

## Collaboration
All team members contributed equally to the group project.
- [Luzi Schöb](https://github.com/taschoebli)
- [David Seger](https://github.com/DavidSeger)
- [Christoph Zweifel](https://github.com/c2fel)

## Reflections & lessons learned
- We learned how to use Apache Kafka as a message broker and how to connect services via Kafka topics.
- We learned how to use Camunda for process orchestration.
- We learned how to use Docker to containerize our services.
- We learned how to use Maven & Spring to build our projects.
- We all did not have any experience with Kafka  & Camunda. We would have profited more from the guest lecture from the Camunda team (Niall Deehan) if we had more experience with the tool. It would have been great if Camunda 8 was introduced in the lecture, since it is the recommended version for new projects. We regret having started with Camunda 7.
- Group projects with small group size (not more than 3 people) are more efficient and effective. We had a good communication and could easily coordinate our tasks.
