import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KafkaWordCount extends App {
  val batchTime = args(0)
  val topic = args(1)
  val conf = new SparkConf().setMaster("local[*]").setAppName("KafkaListener")
  val ssc = new StreamingContext(conf, Seconds(batchTime.toInt))
  ssc.checkpoint("/training/spark/stream")
  val topics = List((topic, 1)).toMap
  val topicLines = KafkaUtils.createStream(ssc, "sandbox.hortonworks.com:2181", "unique_id", topics)
  val wordsCount = topicLines
      .flatMap{case (_, line) => line.split(" ")}
      .map(word => (word, 1))
      .updateStateByKey((value: Seq[Int], state: Option[Int]) => {Some(state.getOrElse(0) + value.size)})
  wordsCount.print()
  ssc.start()
  ssc.awaitTermination()
}