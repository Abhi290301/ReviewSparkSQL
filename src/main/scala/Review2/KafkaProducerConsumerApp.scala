package Review2

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.clients.consumer.{KafkaConsumer, ConsumerRecords}
import scala.jdk.CollectionConverters._
object KafkaProducerConsumerApp extends App {

  // Kafka Producer
  private val producerProps = new Properties()
  producerProps.put("bootstrap.servers", "localhost:9092")
  producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](producerProps)
  private val producerTopic = "my-topic"

  // Kafka Consumer
  private val consumerProps = new Properties()
  consumerProps.put("bootstrap.servers", "localhost:9092")
  consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  consumerProps.put("group.id", "my-group")

  val consumer = new KafkaConsumer[String, String](consumerProps)
  private val consumerTopic = "my-topic"

  try {
    // Produce Messages
    for (i <- 1 to 10) {
      val message = s"Message $i"
      val record = new ProducerRecord[String, String](producerTopic, message)
      producer.send(record)
      println(s"Produced message: $message")
    }

    // Consume Messages
    consumer.subscribe(java.util.Collections.singletonList(consumerTopic))
    while (true) {
      val records: ConsumerRecords[String, String] = consumer.poll(java.time.Duration.ofMillis(100))

      for (record <- records.asScala) {
        println(s"Consumed message: ${record.value()}")
      }
    }
  } catch {
    case e: Exception => e.printStackTrace()
  } finally {
    producer.close()
    consumer.close()
  }
}

