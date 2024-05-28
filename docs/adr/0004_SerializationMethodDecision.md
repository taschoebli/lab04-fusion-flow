# Camunda Java Delegate vs. Expression vs. External

Date: 2024-05-28

## Status

Accepted

## Context

In the lecture we were introduced to Avro, a serialization framework created and maintained by Apache. Avro can be used to serialize and deserialize kafka messages in streams and topics, providing advantages like
a fully described schema, support in a variety of programming languages, flexible schemas and that the messages themselves contain their schema. This means that a software can deserialize an Avro message without knowing the schema beforehand.
Practically speaking, this means that Avro provides an out-of-box serialization tool, with which the need for explicit schemas being shared falls away. 

## Decision

We decided not to use Avro for this project. The advantages are evident in real, connected and evolving projects, where schemas get changed and stakeholders exist, that are not directly involved in developing your application. With Avro you can evolve schemas without any worry for versioning or sharing the schema, and without having to use the same programming language between serializers and deserializers.
However, our project is not meant for distribution to be used by third party developers, and the schemas are unlikely to evolve over the course of the project. Supporting factors for this decision are also the increased message size when using avro, and that setting up and maintaining Avro schemes takes a long time until it runs smoothly, especially since nobody in our team has any experience working with Avro. 
With no real advantages for our project and time being taken from working with the core of the application, the Kafka stream processing, we decided not to invest the time needed to work with Avro, and keep using the jason serialization that we already knew.

## Consequences
For our immediate project, there won't be too many consequences. However, it will mean that, if we ever decide to continue this project in any form, we would have to reevaluate the worth of using Avro, and perhaps spend the time needed to migrate to this new Avro framework. 
