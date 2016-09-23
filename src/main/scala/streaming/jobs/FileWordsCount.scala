package streaming.jobs

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import streaming.utils.StreamProc.FileStream

object FileWordsCount extends App {
  val batchTime = args(0).toInt
  val inputFile = args(1)
  val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCount")
  val ssc = new StreamingContext(conf, Seconds(batchTime))

  ssc
      .textFileStream(inputFile)
      .countWords
      .print()

  ssc.start()
  ssc.awaitTermination()
}