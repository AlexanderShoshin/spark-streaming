package streaming.jobs

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SocketErrorsFilter extends App {
  val batchTime = args(0).toInt
  val socketPort = args(1).toInt
  val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCount")
  val ssc = new StreamingContext(conf, Seconds(batchTime))

  ssc
      .socketTextStream("sandbox.hortonworks.com", socketPort)
      .filter(_.toLowerCase.contains("error"))
      .print()

  ssc.start()
  ssc.awaitTermination()
}