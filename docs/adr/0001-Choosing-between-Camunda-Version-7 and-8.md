# Choosing between Camunda Version 7 and 8

Date: 2024-04-09

## Status

Accepted

## Context

Within exercise 5 we were asked to discuss if Camunda 8 should be implemented into our existing software project.

## Decision

We will not switch to Camunda 8. The current implementation fully meets the requirements which have been posed by the exercises and by the chosen business process/logic. Check the [README.md](../../README_Assignment1.md) for more information.


## Consequences

Practically none. Choosing Camunda Version 7 means that we can not use newly introduced feature in Camunda Version 8 such as:

- The new Zeebe process engine enables operating in a cloud environment
- Many new connectors - for example - enabling that generic REST API calls can be made in a very easy manner
- Horizontal scalability which allows for extremly high performance and many process instances running concurrently.

In hindsight we would have implemented Camunda Version 8 from the beginning. First, using the connectors from Camunda Version 8 we could have saved time when implementating previous Exercises/Assignments. Second, by choosing Camunda Version 8 from the beginning we would have made our software project more future proof since Camunda 7 is not recommended for new projects (except student proejcts) anymore.