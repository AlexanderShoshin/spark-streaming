package streaming.jobs

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import streaming.utils.StreamProc.SocketStream

object SocketWordsCount extends App {
  val batchTime = args(0).toInt
  val socketPort = args(1).toInt
  val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCount")
  val ssc = new StreamingContext(conf, Seconds(batchTime))
  ssc.checkpoint("/training/spark/stream")

  ssc
      .socketTextStream("sandbox.hortonworks.com", socketPort)
      .countWords
      .print()

  ssc.start()
  ssc.awaitTermination()
}