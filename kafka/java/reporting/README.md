# Reporting

## Stateless Operations
The following stateless operations have been implemented in the [FilterProcessesToLocationsTopology](../../../kafka/java/reporting/src/main/java/io/flowing/retail/reporting/topology/FilterProcessesToLocationsTopology.java) class:
- **Filtering**: Filter the "bookings" stream by location and create three branches based on the location id. 
![../../../docs//diagrams/topology_filter_arena.drawio.png](../../../docs//diagrams/topology_filter_arena.drawio.png)

JSON Object for better understanding of the data received in a kafka stream:
```json
{
  "locationId": 11,
  "bookingKey": "105957",
  "productName": "Auf der Suche nach Gulliver",
  "customerName": "John Doe",
  "bookingDateTime": "2024-05-09 14:30:21",
  "eventDateTime": "2024-05-15 18:00:00",
  "amount": 150,
  "paymentStatusIsPaid": true,
  "timestamp": "2024-05-09 14:30:25"
  }
```


## Interactive Queries
In order to fulfill the requirement "Interactive queries" from exercise 9, several topologies have been introduced. The can all be queried from the [ReportingService](../../../kafka/java/reporting/src/main/java/io/flowing/retail/reporting/application/ReportingService.java) by applying the following methods:


- [FilterProcessesToLocations](../../../kafka/java/reporting/src/main/java/io/flowing/retail/reporting/topology/FilterProcessesToLocationsTopology.java) The idea is to get the bookings and filter them by location. The call is `/locationMonitor`: Returns the total amount of bookings per location.
The following image shows corresponding reporting monitor:
![ReportingMonitor](../../../docs/ReportingMonitor.png)


- [CombineStreamsAndPrepareForReporting](../../../kafka/java/reporting/src/main/java/io/flowing/retail/reporting/topology/CombineStreamsAndPrepareForReportingTopology.java) The idea is to get statistics from a certain location by combining two streams. The call is `/sessionMonitor`: Returns averageSessionDelay, percentageOfCustomersTooLate, numberOfLateCustomers, totalLostTime and numberOfSessions per location.
The following image shows corresponding reporting monitor:
  ![SessionMonitor](../../../docs/SessionMonitor.png)


- [Window](../../../kafka/java/reporting/src/main/java/io/flowing/retail/reporting/topology/WindowTopology.java) The idea is to get the number of bookings per day per location. The call is `/windowMonitor`: Returns number of bookings per day per location.
  The following image shows corresponding reporting monitor:
  ![WindowMonitor](../../../docs/WindowMonitor.png)


- [FraudDetection](../../../kafka/java/reporting/src/main/java/io/flowing/retail/reporting/topology/FraudDetectionTopology.java) The idea is to measure fraudulent behaviour. In case a booking within a minute is made by the same person, an entry will be made here. This feature is used to detect an misuse of for example a stolen credit card.  The call is `/fraudDetection`: Returns count and customer Name of a fraudulent booking.
  ![FraudDetection](../../../docs/FraudDetection.png)


The reporting monitor is accessible through the Javalin web server with the following address:
http://localhost:7070

## Windowed Operations
Two windowed operations have been implemented. The first one is a tumbling window in order to get the events per day per location. The code of the figure depicted below can be found here: [WindowToplogy](../../../kafka/java/reporting/src/main/java/io/flowing/retail/reporting/topology/WindowTopology.java)

![WindowEventDateTimeCount](../../../docs/diagrams/topology_window_eventDateTimeCount.png)

The second windowed operation is a hopping window...

