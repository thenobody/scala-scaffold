name := "scaffold-core"

libraryDependencies ++= Seq(
  "io.spray" %%  "spray-json" % "1.3.1",
  "redis.clients" % "jedis" % "2.6.1-VDNA",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "org.springframework" % "spring-context" % "4.1.1.RELEASE",
  "com.zaxxer" % "HikariCP-java6" % "2.2.4",
  "mysql" % "mysql-connector-java" % "5.1.18",
  "com.googlecode.flyway" % "flyway-core" % "2.3.1",
  "net.sf.uadetector" % "uadetector-core" % "0.9.22",
  "net.sf.uadetector" % "uadetector-resources" % "2014.10"
)
