import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import StreamingContext._

object WordCount extends App {
  val conf = new SparkConf().setMaster("local[*]").setAppName("NetworkWordCount")
  val ssc = new StreamingContext(conf, Seconds(5))
  ssc.checkpoint("/training/spark/stream")
  val lines = ssc.socketTextStream("sandbox.hortonworks.com", 7777)
  val wordsCount = lines
      .flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .updateStateByKey((value: Seq[Int], state: Option[Int]) => {Some(state.getOrElse(0) + value.size)})
  wordsCount.print()
  ssc.start()
  ssc.awaitTermination()
}