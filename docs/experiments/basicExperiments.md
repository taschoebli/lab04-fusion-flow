# Experimenting with Kafka (I/II)
These experiments were based on the [provided example](https://github.com/scs-edpo/lab02Part1-kafka-producer-consumer).

## The impact of load and batch size on processing latency
Producers can be individually configured by as set of parameters. As described in the book "Kafka: The Definitive Guide"
the two parameters *batch.size* and *linger.ms* do influence processing latency.

### batch.size
> Therefore, setting the batch size too large will not cause delays in sending messages; it will just use more memory for the batches. Setting the batch size too small will add some overhead because the producer will need to send mes‐ sages more frequently. (Narkhede et al., p. 50)

### linger.ms
> By setting linger.ms higher than 0, we instruct the producer to wait a few milliseconds to add additional messages to the batch before sending it to the brokers. This increases latency but also increases throughput (Narkhede et al., p. 50)

Let's verify this theoretical knowledge with an experiment. 

### Experiment setup
First we set the parameter as followed
> batch.size = 16000

This means a 16'000 bytes of memory are allocated to gather kafka messages before they are sent to a certain partition.

> linger.ms = 1000

The producer waits 1000 milliseconds before sending a batch of messages.

Then, change the parameters as followed an examine the difference in latency.
> batch.size = 64000
> linger.ms = 10000

### Experiment results
* In the first run, it takes less than 2 seconds that a batch is consumed.
* In the second run, it takes more than 7 seconds that a batch is consumed.

*Interpretation:* By systematically adjusting batch sizes and load, you can effectively examine and understand the behavior of Kafka under different conditions. This will help you tune Kafka configurations for optimal performance in our specific use case.

## The risk of data loss due to dropped messages

## The outage of Zookeeper

## The risk of data loss due to consumer lag

## The risk of data loss due to offset misconfigurations
