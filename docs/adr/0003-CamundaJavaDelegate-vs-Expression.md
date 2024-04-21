# Camunda Java Delegate vs. Expression vs. External

Date: 2024-04-21

## Status

Accepted

## Context

Camunda, the engine we use for process orchestration, has different ways of implementing business logic. One can decide between external, expressions or java delegates. There are different use cases for the different implementation types.

## Decision

We focus on using the Java delegate expression to implement our business logic. This has multiple reasons: 

- We can exchange business logic quite easily when using java code and docker to deploy software
- It's easy to work with complex business logic when you have a fully fledged out programming language and an IDE to work with (especially for debugging)
- Since we use Java Bean as a framework, we need to use a Java delegate expression instead of the Java Class implementation, for the dependency injection to work
- External and Expression implementations require adding another technology to our tech stack, a new expression language and possibly scripting languages, since we all have experience working with Java and the Bean framework, we feel more comfortable implementing a new project quickly using these technologies

For more information about the differences between the implementation methods, please refer to the [following guide](https://docs.camunda.io/docs/components/best-practices/development/invoking-services-from-the-process-c7/) published by Camunda themselves.

## Consequences

We add another layer of dependency between our orchestration engine and our business logic implementation, which could be avoided in some places when using expressions. This adds more points of failures and complexity. Performance can suffer when a server has to run multiple JVMs additionally to the Camunda Engine. 
