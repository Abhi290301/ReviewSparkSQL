package Review2

import java.util.Properties
import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig, NewTopic}
import scala.jdk.CollectionConverters._
object KafkaTopicExample {
  def main(args: Array[String]): Unit = {
    val bootstrapServers = "localhost:9092"
    val topicName = "my-topic"
    val numPartitions = 3
    val replicationFactor = 1

    val props = new Properties()
    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 6000000)

    val adminClient = AdminClient.create(props)

    // Create a new topic
    val newTopic = new NewTopic(topicName, numPartitions, replicationFactor.toShort)
    adminClient.createTopics(List(newTopic).asJava).all().get()
    println("Topic created")

    // Describe the topic
    val topicDescription = adminClient.describeTopics(List(topicName).asJava).all().get()
    val desc = topicDescription.get(topicName)
    println(s"Topic name: ${desc.name()}, Partitions: ${desc.partitions().size()}, Replication factor: ${desc.partitions().get(0).replicas().size()}")

    adminClient.close()
  }
}
