## Kafka & ZooKeeper

#### Kafka

![](https://kafka.apache.org/21/images/kafka-apis.png)

- [Kafka 운영자가 말하는 처음 접하는 Kafka](http://www.popit.kr/kafka-%EC%9A%B4%EC%98%81%EC%9E%90%EA%B0%80-%EB%A7%90%ED%95%98%EB%8A%94-%EC%B2%98%EC%9D%8C-%EC%A0%91%ED%95%98%EB%8A%94-kafka/)
- [Kafka 운영자가 말하는 Kafka Consumer Group](http://www.popit.kr/kafka-consumer-group/)
- [Kafka 운영자가 말하는 Producer ACKS](http://www.popit.kr/kafka-%EC%9A%B4%EC%98%81%EC%9E%90%EA%B0%80-%EB%A7%90%ED%95%98%EB%8A%94-producer-acks/)
- [Kafka 운영자가 말하는 Kafka 서버 실전 로그 분석](http://www.popit.kr/%EC%B9%B4%ED%94%84%EC%B9%B4-%EC%9A%B4%EC%98%81%EC%9E%90%EA%B0%80-%EB%A7%90%ED%95%98%EB%8A%94-%EC%B9%B4%ED%94%84%EC%B9%B4-%EC%84%9C%EB%B2%84-%EC%8B%A4%EC%A0%84-%EB%A1%9C%EA%B7%B8-%EB%B6%84%EC%84%9D/)
- [Kafka 운영자가 말하는 TIP](http://www.popit.kr/kafka-%EC%9A%B4%EC%98%81%EC%9E%90%EA%B0%80-%EB%A7%90%ED%95%98%EB%8A%94-tip/)
- [Kafka 운영자가 말하는 Topic Replication](https://www.popit.kr/kafka-%EC%9A%B4%EC%98%81%EC%9E%90%EA%B0%80-%EB%A7%90%ED%95%98%EB%8A%94-topic-replication/)

![](https://www.confluent.io/wp-content/uploads/chart-kafka-infrastructure@2x.png)

> The basic architecture of Kafka is organized around a few key terms: topics, producers, consumers, and brokers.
>
> All Kafka messages are organized into topics. If you wish to send a message you send it to a specific topic and if you wish to read a message you read it from a specific topic. A consumer pulls messages off of a Kafka topic while producers push messages into a Kafka topic. Lastly, Kafka, as a distributed system, runs in a cluster. Each node in the cluster is called a Kafka broker.
>
> https://sookocheff.com/post/kafka/kafka-in-a-nutshell/

#### ZooKeeper

- [https://data-flair.training/blogs/zookeeper-in-kafka/](https://data-flair.training/blogs/zookeeper-in-kafka/)

![](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/05/2018-05-11.png)

![](https://d2h0cx97tjks2p.cloudfront.net/blogs/wp-content/uploads/sites/2/2018/05/Role-of-ZooKeeper-in-Kafka-01.jpg)
