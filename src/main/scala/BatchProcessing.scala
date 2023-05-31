import org.apache.spark.sql.SparkSession

object BatchProcessing {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Batch processing")
      .master("local")
      .getOrCreate()

    val sc = spark.sparkContext
    sc.setLogLevel("OFF")
    val rdd = spark.read.option("Delimeter","|").option("inferSchema","true").csv("C:\\tmp\\projects_smaller.csv")
    val columns = Seq("ID","Name","EID","SNAme","YNAme","hds","jhsdh","hdgk","dgfks","sfssd")
    import spark.implicits._
    val df = rdd.toDF(columns:_*)
    df.printSchema()
    df.show(1000,false)
    rdd.printSchema()
  }
}
