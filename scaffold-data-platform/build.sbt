import sbt.Keys._
import sbt._

name := "scaffold-data-platform"

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-client" % "2.3.0-cdh5.0.2",
  "io.spray" %% "spray-json" % "1.3.1"
)
