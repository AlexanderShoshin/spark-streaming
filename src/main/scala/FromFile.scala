import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import StreamingContext._

object FromFile extends App {
  val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCount")
  val ssc = new StreamingContext(conf, Seconds(5))
  val lines = ssc.textFileStream("/training/spark/streamData")
  val wordsCount = lines
      .flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_+_)
      .transform(_.sortBy(_._2, ascending = false))
  wordsCount.print()
  ssc.start()
  ssc.awaitTermination()
}