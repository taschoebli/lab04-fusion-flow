# Flowing Retail

This project simulates a typical business logic workflow using event-driven architecture. The implementation is based on
the implementation of [flowing retail](https://github.com/scs-edpo/lab04-flowing-retail).
We use Apache Kafka and Camunda Process orchestration to facilitate our workflow. 

## How to run
### Building the project
Use the Maven task "flowing-retail-kafka" to clean and rebuild all projects in the application.

### Launching the application
There is a [docker-compose](runner/docker-compose/docker-compose.yml) script that, when executed, launches all relevant project in docker.

### Accessing the Camunda Cockpit
To have an overview of the processes and the running instances, access the Camunda Cockpit [here.](http://localhost:8091/)
Use the following credentials to log in:

- Username: flowingRetail
- Password: flowingRetail

### Start a new process
To start a new process, access [this link.](http://localhost/8091/book_test.html) Once you enter a date for your booking
and press "book", you should be able to see that a new process instance has started, the process
should be waiting for receiving the bank payment, as can be seen here:

![docs/ProcessWaitingForPayment.png](docs/ProcessWaitingForPayment.png)

## Our Process
So far, we use Kafka as a message broker, passing messages between bounded contexts and processes.
The booking process, as well as the Accounting process, is orchestrated using a Camunda process definition. 

Booking Process:

![docs/booking_process.png](docs/booking_process.png)

Accounting Process:

//insert here

The Booking process waits for the Bank Payment Retrieved message, originating from the Accounting process, after it 
notifies the accounting process, that a new booking has been made. We skip the entire process, if the customer
decided to pay by card, since then we already have collected the money and the booking is valid.
