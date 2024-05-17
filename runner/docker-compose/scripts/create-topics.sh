echo "waiting"
cub kafka-ready -b kafka:9092 1 20

kafka-topics --create --bootstrap-server 'kafka:9092' --topic 'locationPartitionedBookings' --replication-factor 1 --partitions 4
#kafka-topics --create --bootstrap-server 'kafka:9092' --topic 'bookings_simple' --replication-factor 1 --partitions 4


sleep infinity
