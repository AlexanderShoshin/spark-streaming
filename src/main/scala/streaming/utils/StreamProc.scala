package streaming.utils

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}

object StreamProc {
  implicit class SocketStream(lines: ReceiverInputDStream[String]) {
    def countWords = {
      lines
          .flatMap(line => line.split(" "))
          .map(word => (word, 1))
          .updateStateByKey((value: Seq[Int], state: Option[Int]) => {Some(state.getOrElse(0) + value.size)})
    }
  }

  implicit class KafkaStream(topicLines: ReceiverInputDStream[(String, String)]) {
    def countWords = {
      topicLines
          .flatMap{case (topic, line) => line.split(" ")}
          .map(word => (word, 1))
          .updateStateByKey((value: Seq[Int], state: Option[Int]) => {Some(state.getOrElse(0) + value.size)})
    }
  }

  implicit class FileStream(lines: DStream[String]) {
    def countWords = {
      lines
          .flatMap(line => line.split(" "))
          .map(word => (word, 1))
          .reduceByKey(_+_)
          .transform(_.sortBy(_._2, ascending = false))
    }
  }
}