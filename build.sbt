name := "spark-streaming"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % Provided,
  "org.apache.spark" %% "spark-streaming" % "1.6.0" % Provided,
  "org.apache.spark" %% "spark-streaming-kafka" % "1.6.0"
)

mergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
  case x => (mergeStrategy in assembly).value(x)
}