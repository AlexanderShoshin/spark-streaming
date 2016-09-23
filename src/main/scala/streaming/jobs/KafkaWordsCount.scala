package streaming.jobs

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import streaming.utils.StreamProc.KafkaStream

object KafkaWordsCount extends App {
  val batchTime = args(0).toInt
  val topic = args(1)
  val conf = new SparkConf().setMaster("local[*]").setAppName("KafkaListener")
  val ssc = new StreamingContext(conf, Seconds(batchTime))
  ssc.checkpoint("/training/spark/stream")
  val topics = List((topic, 1)).toMap

  KafkaUtils
      .createStream(ssc, "sandbox.hortonworks.com:2181", "unique_id", topics)
      .countWords
      .print()

  ssc.start()
  ssc.awaitTermination()
}