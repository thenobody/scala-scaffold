import sbt._
import BuildSettings._
import ZipSettings._

object Build extends Build {

  lazy val root = Project("scaffold", file("."))
    .aggregate(
      scaffoldCore,
      scaffoldDataPlatform,
      scaffoldRestApi
    ).settings(commonSettings: _*)

  lazy val scaffoldCore = Project("scaffold-core", file("scaffold-core"))
    .settings(commonSettings: _*)

  lazy val scaffoldDataPlatform = Project("scaffold-data-platform", file("scaffold-data-platform"))
    .settings(commonSettings ++ zipSettings: _*)
    .dependsOn(scaffoldCore)

  lazy val scaffoldRestApi = Project("scaffold-restapi", file("scaffold-restapi"))
    .settings(commonSettings ++ fatJarPublishSettings: _*)
    .dependsOn(scaffoldCore)
}
