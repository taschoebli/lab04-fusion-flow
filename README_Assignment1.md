# Fusion Flow Assignment # 1
This project simulates a typical business logic workflow using event-driven architecture. The implementation is based on
the implementation of [flowing retail](https://github.com/scs-edpo/lab04-flowing-retail).
We use Apache Kafka and Camunda Process orchestration to facilitate our workflow. 

## Changes Since First Hand-In
On the 9th of May, we have received feedback regarding our submission of Assignment 1. Besides improving the code documentation and more in-depth argumentation in the ADRs, we have also adressed to two major points of criticism:
1. Incomplete submission of exercise 1 "Experimenting with Kafka"
2. Too few complexity in patterns and BPMN models

### Experimenting with Kafka
The following experiments - as per exercise 1 - are documented [here](docs/experiments/basicExperiments.md):
- The impact of load and batch size on processing latency
- the risk of data loss due to dropped messages
- the outage of Zookeeper
- the risk of data loss due to consumer lag

Additional experiments, such as the following, are presented [here](docs/experiments/additionalExperiments.pdf):
- The risk of data loss due offset misconfigurations based on [Lab02Part2-kafka-EyeTracking](https://github.com/scs-edpo/lab02Part2-kafka-EyeTracking)
- Subscribing to a non-existent topic
- Attempting to overwhelm a Kafka server with topics

### Added complexity to assignment 1
- To boost complexity, both one additional Human Intervention and one Stateful Retry has been added. Please see [further down](#Our-process) for more details about the updated booking and accounting contexts.

## General description
This project simulates the possible process of a customer booking an event at the Fusion Arena St. Gallen. Thereby, focus is on customers that pay via invoice. Since there is a need for a bank payment without live payment processing, the process has to make sure and check that the payment has been received before the booking is confirmed. The customer is given a time of 30 days to make the payment.


## Explicit references to the concepts of the lecture
Apache Kafka is used as an open-source distributed event streaming platform. All four implemented services, [accounting](kafka/java/accounting), [booking](kafka/java/booking), [notification](kafka/java/notification) and [qrInvoice](kafka/java/qrInvoice) are connected via Kafka topics. Have a look at the messages folder for the corresponding kafka topics of each service. Kafka is used for the communication between the services and the orchestration of the processes.

An event notification is for example created when a new booking is made. One Kafka topic was used called "flowing-retail" where events are distinguished among each other by name and service type. The advantage are decoupling, dependency inversion and that changes in one service do not affect the other services. The biggest disadvantage is that no statement of overall behaviour can be made.

No event-carried state transfer is used in this project. There was no need to update a local copy of data in the services via events.

Camunda is used for the orchestration of the processes. The main process is the booking process, which is started when a new booking is made. The sub process is the accounting process, which is started when the booking process is waiting for the bank payment. The processes are orchestrated using the Camunda BPM platform for process & workflow orchestration. See in ADR section for explicit differentiation between orchestration and choreography.

We explicitly (see ADR) decided not to use Zeebe, the new process engine of Camunda 8, since we are using Camunda 7.

In lecture 6, [Sagas](https://www.youtube.com/watch?v=0UTOLRTwOX0) and "Stateful Resilience Patterns" were introduced. The saga we decided on for our project is the "parallel" saga. The saga uses a central mediator (in our case this is the booking service), and asynchronous communication with eventual consistency. The saga is easier to implement, and suits our needs for this project quite well, asynchronous communication is used to trigger the notification service and the accounting subprocess, the data will be consistent once the accounting subprocess has completed. The accounting process runs in parallel to the communication with our costumer, we decided on this pattern because it allows us to keep our main process running while the accounting department deals with background tasks.

As resilience patterns, we use human interventions and stateful retries. In the accounting process, we best saw fit that, after an automatic rejection of a customer, our accountants have the final word on rejecting or approving a customer. Our software calls an external relational database to see any customer that was put on the blacklist, if we find that a customer has been rejected in the past, we will mark him for human review. To not lose out on a possible sale due to the automatic rejection, this manual review step serves as a fallback, we provide the customer name and his booking to a human agent, who has last call on whether to reject a booking or if we take the chance and allow it. Even if we allow a rejected booking manually, the booking gets automatically cancelled if we do not receive the payment within 30 days.

In our main process, the booking service, we implemented a stateful retry in case the customer chooses to pay by credit card. The service we implemented to process credit card payments would, in real life, contact the credit card processor to ensure that the payment has been processed successfully, but in our implementation we simulate this by declaring the booking as paid in 80% of the time, while throwing a Java exception signaling that the payment failed in 20% of the cases. Camunda automatically retries verifying the booking every minute, and it does that up to 5 times. If the payment still failed by the 5th try, the Java delegate throws an BPMN error, which triggers another human intervention pattern, where a customer representative can contact the customer to resolve any payment issues.  

Stateful retry allows our software to be more autonomous and reduces human error handling. Since our example uses a third party payment service when the customer pays via credit card, we depend on the availability and stability of software that we have no control over, so it is appropriate to have an extra safety feature in place here, to increase resilience. Instead of having to introduce a complicated logic to deal with credit card payment failure, Camunda keeps the state of the booking in its system and retries to contact the service in predefined intervals, increasing the chance of a payment going through without anybody having to intervene.

If the payment, despite all efforts, couldn't be completed, we have grounds to belief that something is wrong with the credit card itself. We fall back on a human employee who can get in contact with the customer and resolve the issue by mail or on the phone. This is another resilience pattern as introduced to us in the lectures.

## ADR (Architecture Decision Records)
Please find all ADRs in the [docs/adr](docs/adr) folder.
- Choosing between Camunda 7 and 8
- Distinction between Orchestration and Choreography
- The general guide on how we implement external business logic in Camunda


## Building the project
Use the Maven task "flowing-retail-kafka" to clean and rebuild all projects in the application. To run the Flowing Retail project you first need to be sure that all the relevant projects have been built at least once: (Maven Window -> Run Maven Build "green play button" )

```
  cd .\kafka\java\
  mvn clean install
```

Then you can execute:

```
  docker-compose -f docker-compose.yml up --build
```

(omit --build if you have already built the images):

```
  docker-compose -f docker-compose.yml up
```
from the directory [runner/docker-compose](runner/docker-compose).

### Launching the application
There is a [docker-compose](runner/docker-compose/docker-compose.yml) script that, when executed, launches all relevant project in docker.

### Accessing the Camunda cockpit
To have an overview of the processes and the running instances, you have to access the Camunda Cockpits. It is suggested to use a private window (Incognito mode) to avoid any caching issues.

- [Main process](http://localhost:8091/)
- [Sub process](http://localhost:8097/)

Use the following credentials to log in:

- Username: flowingRetail
- Password: flowingRetail

Or find them here for [main](kafka/java/booking/src/main/resources/application.yaml) and [sub](kafka/java/accounting/src/main/resources/application.yaml) process.

## Our process
We use Kafka as a message broker, passing messages between bounded contexts/domains and processes.
The main (=booking) process, as well as the sub (=accounting) process, is orchestrated using Camunda process definitions, here, Camunda is responsible for communicating within domains.

Booking Process:
![docs/booking_process.png](docs/booking_process.png)

Accounting Process:
![docs/accounting_process.png](docs/accounting_process.png)

When a customer creates a booking, and pays by invoice, the booking process notifies the accounting process, that a new booking has been made. It then waits for the Bank Payment Retrieved message, which is generated once the subprocess has completed. Parallel to the execution of the subprocess, our booking process sends the invoice by mail to the customer.

If the customer decided to pay by card, we contact the card payment organization to see if the payment has been made. We retry this up to 5 times if no payment has been made, with 1 minute intervals between retries. If there is no payment after 5 minutes, our delegate throws a Camunda BPM error, causing our process to create a human review task to find out what's going on with this booking.

The accounting subprocess first passes the customer through an automatic booking review, where we access an external database to see if we have rejected this customer in the past. if this is the case, we flag the customer for a human review, where an accountant can take any necessary steps to deem a booking valid or invalid.
If the booking is deemed valid, either automatically through our system or by a human accountant, we set the process on hold and wait for the money to arrive. Our software waits for the arrival of a Kafka message from our bank, confirming that we received the money from the customer. Camunda automatically continues the process to cancel the order,
if we do not receive any money within 30 days. In any case, we deem the booking valid (either cancelled or paid), and notify out main process that the accounting side of the booking is done.

### Start a new process
To start a new process, access [this link.](http://localhost:8091/booking.html) Once you enter a date, a payment method and a name for your booking, press "book". You should be able to see that a new main process instance has started. 

We have set up some test cases to go through the different logic paths of our application:

- To see the whole logic for a valid booking using invoice as a payment system, simply enter a date, a mail, and check the box "pay by invoice". The sub process should be waiting for receiving the bank payment, as can be seen here:

![docs/ProcessWaitingForPayment.png](docs/ProcessWaitingForPayment.png)

- If you want to test out the human resilience pattern within our subprocess, enter a date and check the "pay by invoice" box. Enter the following e-mail: "David.seger@bluewin.ch", as this is the e-mail address of a customer that has caused us a great deal of trouble in the past, and is thus on the blacklist for manual customer review. It should look like this in the subprocess cockpit:

![docs/ProcessWaitingForHumanCustomerReview](docs/ProcessWaitingForHumanCustomerReview.png)

- To see how our stateful retry fares, enter a date and an e-mail, but do not check the "pay by invoice" checkbox. Credit card payment fails in 1/5th of all cases, so you might have to trigger a few bookings to see a stateful retry (and even more bookings to get a case that has to be moved to further human review). If you are patient, you should be greeted with the following inside the cockpit of our main process:

![docs/MainProcessStatefulRetry](docs/MainProcessStatefulRetry.png)

## Implementation Details and Related Architectural Decisions
### Main Process: Booking
To be able to start up a new process, we created a simple HTML interface that is served via the spring framework. The Template takes a date and
a boolean, indicating whether the customer wants to pay by card or invoice. This form sends out a request to a REST API endpoint, also provided with Spring, controlled by our [ShopRestController](kafka/java/booking/src/main/java/io/flowing/retail/booking/rest/ShopRestController.java) class.
This endpoint builds the start message "booking created" that triggers our main Camunda Process. Camunda automatically checks for the payment type via direct expression, to choose the orchestration path.
The QR invoice factory task is implemented by the [QrInvoiceAdapter](kafka/java/booking/src/main/java/io/flowing/retail/booking/flow/QrInvoiceAdapter.java) class.
This delegate sends out a REST GET request to our [qrInvoice microservice](kafka/java/qrInvoice/src/main/java/qrInvoice/rest/QrFactoryRestController.java). 
The microservice simply takes the amount that was handed to it via the API and creates a Base64 encoded string to return to the caller. We decided for a direct dependency, via REST call since the rest of the process depends on the availability of the QR invoice,
we want to continue the process without being blocked by excessive waiting for an asynchronous answer. This creates stronger coupling and requires the QR service to be more resilient to outages,
but because sending out an invoice is a central task in a checkout process, and we have enough waiting time in the following subprocess, we don't want to possibly block the process so early on.
With the QR now created, we add this to the Camunda process message as a variable and continue on down the process to the parallel executed branches.
Sending the invoice to the customer is simple, we chose to utilize an event instead of a command, as it is not all that time sensitive when the customer receives the bill.
The [SendInvoiceAdapter](kafka/java/booking/src/main/java/io/flowing/retail/booking/flow/SendInvoiceAdapter.java) delegate sends out a Kafka message containing the QR bill string to any interested services (in this case, the [notification service](kafka/java/notification/src/main/java/io/flowing/retail/notification/messages/MessageListener.java), which catches the event and sends out the bill).
Once again we opted for an event based trigger instead of a command, as this execution branch runs in parallel to another branch, which can take up to 30 days to complete, thus actually sending out the invoice is neither blocking the rest of the process, nor is it time sensitive compared to the 30-day time limit posed by the subprocess called in the paralel branch.

While the invoice gets sent out to the customer, the [InvoiceCreatedAdapter](kafka/java/booking/src/main/java/io/flowing/retail/booking/flow/InvoiceCreatedAdapter.java) delegate sends out a similar, but distinct, event via Kafka, which triggers the accounting subprocess. A event is sufficient in this case, since paying a bill can take up to 30 days anyway, so time is not of the essence.
This branch now waits for a Camunda message “BankTransferRetrievedNew”, which will be triggered via Kafka event "PaymentHandled" by the accounting process, once that has dealt with the new booking. The Kafka event that triggers the message is caught in the bookings [MessageListener](kafka/java/booking/src/main/java/io/flowing/retail/booking/messages/MessageListener.java). The "BankTransferRetrievedNew" Camunda message signifies the end of the booking process.

If the incoming booking request is paid by credit card, Camunda hands the booking to our [CreditCardAdapter](kafka/java/booking/src/main/java/io/flowing/retail/booking/flow/CreditCardAdapter.java). This delegate simulates contacting a third party payment service and checking the payment status. We use probability to fail 1/5th of all incoming credit card payments. When a credit card payment fails, we throw a Java exception. We decided on a delegate expression since Camunda is great at dealing with error coming from these type of implementations: Camunda automatically saves the booking state, and retries the delegate execution at a later time (we opted for one retry every minute, with 5 retries being the maximum). If a payment is still invalid after this time, the code opts for throwing a controlled BPM Error: We included the possibility of such an error in our process model, and added an execution branch that only gets accessed if that BPM error gets thrown. This branch includes a human task, meant to represent a customer service employee contacting the customer to resolve any payment issues.

Using this combination of automatic, stateful retries on execution errors, controlled BPM errors that represent business errors, and human intervention if all else fails, we implemented a resilience pattern that improves our codes stability and adaptability to error cases.

### Sub Process: Accounting
Our Accounting Microservice is subscribed to the kafka message sent out by the main process, we listen for it in the services [MessageListener](kafka/java/accounting/src/main/java/io/flowing/retail/accounting/messages/MessageListener.java).
Once the Camunda process is kicked off by this message, the software checks the creditworthiness of the customer by calling an external Database, containing a blacklist of customers that are not trustworthy. This is done in [ApproveCustomerAdapter](kafka/java/accounting/src/main/java/io/flowing/retail/accounting/flow/ApproveCustomerAdapter.java) delegate. An external MySQL database is queried to see if the customer has been blacklisted in the past. At the moment, there are 3 customers blacklisted. When an new booking is made, the customer is checked against the blacklist. A new field, email, is added to the [booking form](http://localhost:8091/booking.html). If the customer is not blacklisted, the process continues with the "accepted" execution path.

If the customer is approved, the process moves on to a passive state of waiting for a notification by the bank, that we received the money. The bank can reach us via a REST call, the controller for which is implemented in [AccountingRestController](kafka/java/accounting/src/main/java/io/flowing/retail/accounting/rest/AccountingRestController.java).
Our [PaymentReceivedAdapter](kafka/java/accounting/src/main/java/io/flowing/retail/accounting/flow/PaymentReceivedAdapter.java) class is the delegate tasked with handling this banking notification, here one could insert any business logic needed to bring our accounts into a correct state (e.g. mark the invoice as paid). This is another possibly boundary crossing Camunda throw message event, in case we need to inform further microservices about the successful payment.
Once that delegate is executed, our process moves on to the [PaymentHandledAdapter](kafka/java/accounting/src/main/java/io/flowing/retail/accounting/flow/PaymentHandledAdapter.java), where we create the PaymentHandled Kafka event, notifying our main process that this process instance has completed, and the customer that we received his payment.

In case the customer was found to be on our Blacklist, our stateful resistance pattern kicks in. We don't want to lose out on any possible sales, thus we give a human accountant the chance to review the customers case, before making a final decision on whether to cancel the booking or to allow it. This is implemented as a Camunda user task. The accountant can log into Camunda, check the running processes, claim any flagged bookings and review their case. In the topright corner of the camunda interface, the user can select the Tasklist in order to find all the open user tasks and make the final decision on the validity of the booking.
If the accountant decides to allow the booking, we return to the "accepted" execution path, where we wait for payment, as described above.
If the booking has, once again, be deemed untrustworthy, or if we do not receive any notice by our bank that the payment has been made within 30 days of the booking, we begin cutting our losses and cancel the booking.
This happens in the [CancelOrderAdapter](kafka/java/accounting/src/main/java/io/flowing/retail/accounting/flow/CancelOrderAdapter.java) (here we can include any accounting business logic needed to bring us to a consistent state),
and in the [InvoiceVoidedAdapter](kafka/java/accounting/src/main/java/io/flowing/retail/accounting/flow/InvoiceVoidedAdapter.java) (this is meant for any domain boundary crossing logic, here we would call the notification service to send an email informing the customer that their booking has been voided, and create a Kafka message that could be read by any interested Microservice),
respectively. Finally, as the accounting department is in a consistent state and all relevant interested services have been notified, of the booking state, we finish the process by notifying the main booking process, again through the [PaymentHandledAdapter](kafka/java/accounting/src/main/java/io/flowing/retail/accounting/flow/PaymentHandledAdapter.java) delegate.

### Cross-Domain Communication

As described above, we decided for a mostly asynchronous, event based communication between domains. We could have used commands for retrieving the QR invoice and checking the customer blacklist,
but since these are simple fetch/get operations for requesting a resource, we opted for direct coupling via REST APIs, as commands would be more appropriate if we had to provide a larger amount of input 
data into these services, or if the underlying process of creating the invoice and checking the DB consisted of a more complex workflow than a few lines of code.

Below is a diagram explaining our event and API call flow across domains and services, depicted here is the "best-case scenario" of a customer choosing to pay with invoice and paying the bill on time:

![event_flow_across_domains](docs/diagrams/event_flow_across_domains.png)

The Camunda processes described in the sections above are what is happening inside the booking and the accounting services respectively.

## Collaboration
All team members contributed equally to the group project.
- [Luzi Schöb](https://github.com/taschoebli)
- [David Seger](https://github.com/DavidSeger)
- [Christoph Zweifel](https://github.com/c2fel)

## Reflections & lessons learned
- We learned how to use Apache Kafka as a message broker and how to connect services via Kafka topics.
- We learned how to use Camunda for process orchestration.
- We learned the best practices of modelling processes in Camunda. 
- We learned how to use Docker to containerize our services.
- We learned how to use Maven & Spring to build our projects.
- We all did not have any experience with Kafka  & Camunda. We would have profited more from the guest lecture from the Camunda team (Niall Deehan) if we had more experience with the tool. It would have been great if Camunda 8 was introduced in the lecture, since it is the recommended version for new projects. We regret having started with Camunda 7.
- Group projects with small group size (not more than 3 people) are more efficient and effective. We had a good communication and could easily coordinate our tasks.
