package Review2

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


object KafkaProducerApp extends App {
  val props: Properties = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("acks", "all")
  props.put("batch.size", "16384") // Increase batch size
  props.put("compression.type", "gzip") // Enable compression
  props.put("buffer.memory", "33554432") // Increase buffer memory

  val producer = new KafkaProducer[String, String](props)
  val topic = "text_topic"

  try {
    for (i <- 1 to 10) {
      val message = s"Message $i"
      val record = new ProducerRecord[String, String](topic, message)
      producer.send(record)
      println(s"Sent message: $message")
    }
  } catch {
    case e: Exception => e.printStackTrace()
  } finally {
    producer.close()
  }
}



import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig, NewTopic}

import scala.jdk.CollectionConverters._

object KafkaTopicCreator {
  def main(args: Array[String]): Unit = {
    // Setting up the Bootstrap Configuration
    val bootstrapServer = "localhost:9092"
    val props = new Properties()
    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)

    // Creating Admin Client
    val adminClient = AdminClient.create(props)

    // Creating a new topic
    val topicName = "my-topic"
    val numPartitions = 1
    val replicationFactor = 1
    val newTopic = new NewTopic(topicName, numPartitions, replicationFactor.toShort)

    // Creating the topic
    adminClient.createTopics(List(newTopic).asJava).all().get()

    // Closing the admin client
    adminClient.close()

    println(s"Topic $topicName created successfully.")
  }
}