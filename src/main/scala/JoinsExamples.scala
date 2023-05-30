import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}
object JoinsExamples {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local")
      .appName("Joins testing")
      .getOrCreate()
    spark.sparkContext.setLogLevel("OFF")
    val data = Seq(
      Row(120, "Abhishek Chandel", "abhishekchandel@xenonstack.com",20,"DataOps", 100000, 20000, "M", List("Scala,Spark,Java"), "HP", "IN"),
      Row(121, "Shashank Sharma", "shashank@xenonstack.com",10,"CloudOps", 150000, 15000, "M", List("Go,React,Python"), "HP", "IN"),
      Row(122, "Sahil Rana", "sahilrana@xenonstack.com",10,"CloudOps", 100000, 18000, "M", List("Go,React,Python"), "PUN", "IN"),
      Row(123, "Sahil Kaushik", "sahil@xenonstack.com",10,"CloudOps", 100000, 19000, "M", List("Go,React,Python"), "HR", "IN"),
      Row(124, "Nikita", "nikita@xenonstack.com",20,"DataOps", 10000, 13000, "F", List("Go,C++,Python"), "HP", "IN"),
      Row(125, "Garima", "garima@xenonstack.com",10,"CloudOps", 105000, 16000, "F", List("Scala,Spark,Java"), "HR", "IN"),
      Row(126, "Shivani", "shivani@xenonstack.com",20,"DataOps", 100500, 12000, "F", List(""), "HP", "IN"),
      Row(127, "Anchal", "anchal@xenonstack.com",10,"CloudOps", 20000, 10000, "F", List("Go,Java,Python"), "HP", "IN"),
      Row(128, "Satish", "Satish@xenonstack.com",10,"CloudOps", 45200, 19000, "", List("Scala,Spark,Java"), "Uk", "IN"),
      Row(129, "Shubham", "shubham@xenonstack.com",20,"DataOps", 65000, 16000, "M", List("Scala,Spark,Java"), "HP", "IN"),
      Row(130, "Ritik", "ritik@xenonstack.com", 30, "DataOps", 65000, 15000, "T", List(""), "HR", "IN")
    )
    val columns = new StructType(Array(
      StructField("EID",IntegerType,nullable = true),
      StructField("Name",StringType,nullable = true),
      StructField("E mail",StringType,nullable = true),
      StructField("Dept_ID",IntegerType,nullable = true),
      StructField("Team",StringType,nullable = true),
      StructField("Salary",IntegerType,nullable = true),
      StructField("Bonus",IntegerType,nullable = true),
      StructField("Gender",StringType,nullable = true),
      StructField("Languages",ArrayType(StringType),nullable = true),
      StructField("State",StringType,nullable = true),
      StructField("Country",StringType,nullable = true)
    )
    )
   val empdf = spark.createDataFrame(spark.sparkContext.parallelize(data),columns)
    empdf.show(false)
    val data2 =Seq(10->"AWS",20->"Azure")
    val rdd = spark.sparkContext.parallelize(data2)
      val column2 = Seq("DeptID","DepartmentName")
    import spark.implicits._
    val defDF = rdd.toDF(column2:_*)
      defDF.show(false)
val innerJoin = empdf.join(defDF,empdf("Dept_ID") === defDF("DeptID"),"inner")
    innerJoin.show(false)

    val outerJoin = empdf.join(defDF, empdf("Dept_ID") === defDF("DeptID"), "outer")
    outerJoin.show(false)
    empdf.createOrReplaceTempView("Emp")
    defDF.createOrReplaceTempView("Dept")
    spark.sql("Select * from Emp e,Dept d where e.Dept_ID = d.DeptID").show(false)
    spark.sql (" select * from EMP e INNER JOIN DEPT d ON e.Dept_ID == d.DeptID" ).show ( false )
//    spark.sql (" select * from EMP e OUTER JOIN DEPT d ON e.Dept_ID == d.DeptID" ).show ( false )




    //Writing the parquet file
//    defDF.write.csv("C:\\tmp\\output\\newcsv.csv")

    spark.read.option("inferSchema","true").csv("C:\\tmp\\output\\newcsv.csv").show(false)
//    outerJoin.write.partitionBy("DepartmentName","Gender").parquet("C:\\tmp\\output\\Joins.parquet")
while (true){
  Thread.sleep(6000)
}
}
}