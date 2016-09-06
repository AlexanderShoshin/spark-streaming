import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ErrorsFilter extends App {
  val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCount")
  val ssc = new StreamingContext(conf, Seconds(5))
  val lines = ssc.socketTextStream("sandbox.hortonworks.com", 7777)
  val errorLines = lines.filter(_.toLowerCase.contains("error"))
  errorLines.print()
  ssc.start()
  ssc.awaitTermination()
}