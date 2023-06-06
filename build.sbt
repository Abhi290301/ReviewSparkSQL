ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

resolvers += Resolver.bintrayRepo("cakesolutions", "maven")
libraryDependencies ++= Seq(

  //Spark Dependencies
  "org.apache.spark" %% "spark-core" % "3.3.2"
  , "org.apache.spark" %% "spark-sql" % "3.3.2",
  "org.apache.spark" %% "spark-streaming" % "3.3.2",
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.3.2",
  "org.apache.avro" % "avro" % "1.11.0",
  "org.apache.kafka" % "kafka-clients" % "3.4.0",
  "com.google.protobuf" % "protobuf-java" % "3.22.2",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.15.1",

  //Apache Hadoop Dependencies
  "org.apache.hadoop" % "hadoop-client" % "3.3.2",
  "org.apache.hadoop" % "hadoop-hdfs-test" % "0.22.0",
  "org.apache.spark" %% "spark-hive" % "3.3.2" % "provided",

)
libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "3.4.0",
  "org.apache.kafka" % "kafka-clients" % "3.4.0",
  "org.apache.kafka" % "kafka_2.13" % "3.4.0",
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.3.2"
)

// JDK Setup
javaHome := Some(file("C:\\jdk-11.0.0.1\\bin"))
javacOptions ++= Seq("-source", "11", "-target", "11")


lazy val root = (project in file("."))
  .settings(
    name := "Reviews"
  )
