import AssemblyKeys._

name := "scaffold-restapi"

libraryDependencies ++= {
  val sprayVersion = "1.3.1"
  Seq(
    "io.spray" %% "spray-can" % sprayVersion,
    "io.spray" %% "spray-routing" % sprayVersion,
    "io.spray" %% "spray-json" % sprayVersion,
    "com.typesafe.akka" %% "akka-actor" % "2.3.6",
    "com.typesafe.akka" %% "akka-slf4j" % "2.3.6",
    "org.springframework" % "spring-context" % "4.1.1.RELEASE"
  )
}

mainClass in assembly := Some("net.thenobody.scaffold.restapi.Boot")
