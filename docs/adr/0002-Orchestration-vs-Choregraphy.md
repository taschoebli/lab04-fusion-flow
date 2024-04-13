# Orchestration vs Choreography

Date: 2024-04-13

## Status

Accepted

## Context

Within exercise 3 we were introduced to Camunda as the process orchestration engine for our software project. In this ADR choreography is compared to orchestration and a decision will be made.  

## Decision

The main process is the booking process, which is started when a new booking is made. The sub process is the accounting process, which is started when the booking process is waiting for the bank payment. These two processes are orchestrated using the Camunda BPM platform for process & workflow orchestration.

All the other services are not part of the orchestration. The services communicate with each other by sending messages to Kafka. The services are not aware of each other and do not know the order in which they are executed. They are only aware of the messages they receive and the messages they send.

In lecture 4 the difference between choreography (= event-driven communication) and orchestration (= command-based communication) was explained. By using camunda, commands are introduced (eg. Send invoice, Retrieve bank transfer payment, Approve customer, Cancel order) and this is an clear indication for orchestration.


## Consequences

The direction of dependency (= decision to couple) in orchestration when commands are used is on the sending side whereas in choreography the direction of dependency is on the receiving side. This means that in orchestration the sender of the command is dependent on the receiver of the command. In choreography the receiver of the event is dependent on the sender of the event. This can for example be seen in the delegate expressions in the camunda model (eg. by clicking on a service task like Approve customer). Generally, the question, Is it OK with the component emitting an event if that event is ignored?, can be used to decide between events and commands. If answered with Yes, it is an event, if answered with No, it is a command.