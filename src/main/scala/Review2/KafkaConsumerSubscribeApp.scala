package Review2

import java.util.Properties
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import scala.jdk.CollectionConverters._

object KafkaConsumerSubscribeApp extends App {
  val props: Properties = new Properties()
  props.put("group.id", "test")
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("enable.auto.commit", "true")
  props.put("auto.commit.interval.ms", "1000")

  val consumer = new KafkaConsumer[String, String](props)
  val topics = List("topic_text")

  try {
    consumer.subscribe(topics.asJava)

    while (true) {
      val records: ConsumerRecords[String, String] = consumer.poll(java.time.Duration.ofMillis(100))

      records.asScala.foreach { record =>
        val key = record.key()
        val value = record.value()
        val topic = record.topic()
        val partition = record.partition()
        val offset = record.offset()

        println(s"Received message: topic = $topic, partition = $partition, offset = $offset, key = $key, value = $value")
      }
    }
  } catch {
    case e: Exception => e.printStackTrace()
  } finally {
    consumer.close()
  }
}
