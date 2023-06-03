ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

resolvers += Resolver.bintrayRepo("cakesolutions", "maven")
libraryDependencies ++= Seq(

  //Spark Dependencies
  "org.apache.spark" %% "spark-core" % "3.3.2"
  , "org.apache.spark" %% "spark-sql" % "3.3.2",

  //Apache Hadoop Dependencies
  "org.apache.hadoop" % "hadoop-client" % "3.3.2",
  "org.apache.hadoop" % "hadoop-hdfs-test" % "0.22.0",
  "org.apache.spark" %% "spark-hive" % "3.3.2" % "provided",

  //Kafka Dependencies
  "org.apache.kafka" % "kafka-clients" % "3.4.0",
  "org.apache.kafka" %% "kafka" % "3.4.0",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.4.0"
)

// JDK Setup
javaHome := Some(file("C:\\jdk-11.0.0.1\\bin"))
javacOptions ++= Seq("-source", "11", "-target", "11")

//Scala Testing Libraries
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % Test
libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0"

lazy val root = (project in file("."))
  .settings(
    name := "Reviews"
  )
