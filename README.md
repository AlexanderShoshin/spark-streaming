## Description

Several examples of spark streaming with different input sources.

## Testing environment

Program was tested on HDP 2.4 sandbox.

## How to deploy

1. Make spark-streaming-assembly-1.0.jar by running sbt command from project root:
```
sbt clean assembly
```
"/training/spark/streamData"
2. Copy spark-streaming-assembly-1.0.jar from target/scala-2.10/ to machine with Spark installed.
3. Run one of the jobs:
- kafka word count
```
spark-submit \
--class streaming.jobs.KafkaWordsCount \
spark-streaming-assembly-1.0.jar \
<batch_time_in_seconds> \
<kafke_tipic_name>
```
- file words count
```
spark-submit \
--class streaming.jobs.FileWordsCount \
spark-streaming-assembly-1.0.jar \
<batch_time_in_seconds> \
<input_file_name>
```
- socket words count
```
spark-submit \
--class streaming.jobs.SocketWordsCount \
spark-streaming-assembly-1.0.jar \
<batch_time_in_seconds> \
<socket_port_to_listen>
```
- socket errors filter
```
spark-submit \
--class streaming.jobs.SocketErrorsFilter \
spark-streaming-assembly-1.0.jar \
<batch_time_in_seconds> \
<socket_port_to_listen>
```