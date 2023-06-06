package Review2

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger

object SparkKafkaTopicExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Spark Kafka Topic Example")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val bootstrapServers = "localhost:9092"
    val topicName = "my-topic"

    // Create a topic
    val createTopicCommand =
      s"""
         |kafka-topics.sh --create \\
         |--bootstrap-server $bootstrapServers \\
         |--topic $topicName \\
         |--partitions 3 \\
         |--replication-factor 1
         |""".stripMargin

    val createTopicProcess = Runtime.getRuntime.exec(createTopicCommand)
    createTopicProcess.waitFor()

    println(s"Topic $topicName created")

    // Describe the topic
    val describeTopicCommand =
      s"""
         |kafka-topics.sh --describe \\
         |--bootstrap-server $bootstrapServers \\
         |--topic $topicName
         |""".stripMargin

    val describeTopicProcess = Runtime.getRuntime.exec(describeTopicCommand)
    val describeTopicOutput = describeTopicProcess.getInputStream

    val outputLines = scala.io.Source.fromInputStream(describeTopicOutput).getLines()
    outputLines.foreach(println)

    describeTopicOutput.close()

    // Start consuming data from the topic
    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServers)
      .option("subscribe", topicName)
      .option("startingOffsets", "earliest")
      .load()

    val query = df.writeStream
      .format("console")
      .outputMode("append")
      .trigger(Trigger.ProcessingTime("5 seconds"))
      .start()

    query.awaitTermination()
  }
}
