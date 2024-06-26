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

### Acknowledgments modes
As learned in the ASSE course in the fall semester there are usually three modes of operations with messaging services such as MQTT or in this case Kafka.

> acks=0 means that a message is considered to be written successfully to Kafka if the producer managed to send it over the network.
> acks=1 means that the leader will send either an acknowledgment or an error the moment it got the message and wrote it to the partition data file
> acks=all means that the leader will wait until all in-sync replicas got the message before sending back an acknowledgment or an error. (Narkhede et al., p. 49)

### Retries
> the value of the retries parameter will control how many times the producer will retry sending the message before giving up and notifying the client of an issue. (Narkhede et al., p. 50)

### Experiment setup
Let's test three different cases to set up the properties. During each test, we will shut down the Kafka container and examine what happens to the running instances of the Producer and Consumer.
```properties
   acks=0
   retries=0
```
A message is considered successfully sent as soon as the producer has sent it. The producer will never emit an error if the broker becomes unavailable.

```properties
   acks=1
   retries=0
```
A message is considered successfully sent as soon as the producer received acknowledgement for it. The producer will never emit an error if the broker becomes unavailable, but in this case, its sent messages are no longer acknowledged.

```properties
   acks=1
   retries=1
```
A message is considered successfully sent as soon as the producer received acknowledgement for it. If the broker becomes unavailable, the producer will retry once to resend the original message and receive eventually acknowledgement. 

As we only have one producer and one consumer, it does not make sense to test acks=all.

### Experiment results
As expected, in the first case messages are certainly lost as there is nothing put in place to resend anything that has been sent during we have shutdown the kafka service. Adding acks=1 helps to a certain degree, not every messages gets lost. Finally, adding both acks=1 and retries=1, will lead to a sufficient grade of safety.

*Interpretation:* By adjusting Kafka’s reliability settings and simulating various failure scenarios, you can effectively examine the risk of data loss due to dropped messages. This helps to understand the trade-offs between the acks and retries parameter, such that we can configure Kafka for optimal durability in our use case.

## The outage of Zookeeper
Let's try to artificially shutdown Zookeeper.

### Apache Zookeeper
> Apache Zookeeper is considered an integral part of a Kafka service as it maintain the list of brokers that are currently members of a cluster. (Narkhede et al., p. 95-96)

### Experiment setup
1. Start the Kafka cluster, the Producer and finally the Consumer
2. Messages are being sent
3. Shutdown Zookeeper artificially by
> docker-compose stop zookeeper
4. Messages are *still* being sent
5. Boot up Zookeeper again

### Experiment results
All messages are received without interruption. As per the definition of Apache Zookeeper it is in control of managing the list of topics. Thus, during an outage of Zookeeper we would not be able to add new topics to Kafka.
*Interpretation:* In case, we had an application which dynamically creates additional Kafka topic, you have to keep this issue in mind. But for our current use case, it is not relevant.

## The risk of data loss due to consumer lag
### Consumer lag
> For Kafka consumers, the most important thing to monitor is the consumer lag. Measured in number of messages, this is the difference between the last message pro‐ duced in a specific partition and the last message processed by the consumer. (Narkhede et al., p. 243)

### Experiment setup
Let's try to artificially induce some latency to the Producer and Consumer in two different case.

1. The Producer is now limited to sent a message every 500 ms. The Consumer is limited to process a received messages every 500 ms.
2. The Producer is now limited to sent a message every 100 ms. The Consumer is limited to process a received messages every 900 ms.

### Experiment results
Case 2 dramatically shows that the is huge pile of messages piling up. To fix this, we would need to amend the *log.retention.ms* or *log.retention.bytes* parameters within the Kafka service
*Interpretation:* We have to keep in mind how consumer lag can affect every Kafka application eventually and that we can influence this by the retention configuration.
