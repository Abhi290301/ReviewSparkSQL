package Review2

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.broadcast

object BroadCastJoins {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName("Broad Cast Joins")
      .getOrCreate()
    val sc = spark.sparkContext

//    spark.conf.set("spark.sql.autoBroadcastJoinThreshold", 104857600)
    sc.setLogLevel("OFF")
    println("spark.sparkContext.version")
    val rdd = spark.read.option("header", "true").option("delimiter", "true").option("inferSchema", "true")
      .parquet("C:\\tmp\\output\\Joins.parquet")
    val df = rdd.toDF()
    df.printSchema()
    df.show(truncate = false)

    //Creating a smaller Dataframe
    import spark.implicits._
    val rdd2 = Seq(("HP", "Himachal Pradesh"), ("PUN", "PUNJAB"), ("HR", "HARYANA"), ("Uk", "Utrakhand"))
    val smallerDF = rdd2.toDF("CODE", "STATE FULL NAME")
    smallerDF.show(false)

    df.join(
      broadcast(smallerDF),
      smallerDF("CODE") === df("State")
    ).show(false)

    df.join(
      broadcast(smallerDF),
      smallerDF("CODE") === df("State")
    ).explain(false)

    smallerDF.join(
     (df),
      df("State") === smallerDF("CODE")
    ).explain(false)


    while (true) {
      Thread.sleep(6000)
    }
  }

}
