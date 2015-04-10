import MavenSettings._
import net.imagini.sbt.ReleaseExtra._
import sbt.Keys._
import sbt._
import sbtassembly.Plugin.AssemblyKeys._
import sbtassembly.Plugin._
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease.ReleasePlugin._
import sbtrelease.ReleaseStateTransformations._
import sbtrelease.ReleaseStep

object BuildSettings {

  lazy val basicSettings = Seq(
    javaOptions := Seq("-target", "1.7", "-source", "1.7"),
    parallelExecution in Test := false,
    organization := "net.imagini",
    scalaVersion := "2.10.4",
    scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
  )

  lazy val fatJarPublishSettings = Seq(
    artifact in (Compile, assembly) := {
      val art = (artifact in (Compile, assembly)).value
      art.copy(`classifier` = Some("assembly"))
    }
  ) ++ addArtifact(artifact in (Compile, assembly), assembly)

  lazy val assemblyStrategyProcessing = Seq(
//    test in assembly := {},
    mergeStrategy in assembly <<= (mergeStrategy in assembly) {
      (old) => {
        case s if s.endsWith(".class") => MergeStrategy.last
        case s if s.endsWith("project.clj") => MergeStrategy.concat
        case s if s.endsWith("xsd") => MergeStrategy.last
        case s if s.endsWith("dtd") => MergeStrategy.last
        case s if s.endsWith("xml") => MergeStrategy.last
        case s if s.endsWith("html") => MergeStrategy.last
        case s if s.endsWith("txt") => MergeStrategy.last
        case s if s.endsWith("properties") => MergeStrategy.last
        case s if s.endsWith("license") => MergeStrategy.last
        case s if s.endsWith("META-INF") => MergeStrategy.last
        case s if s.endsWith("RSA") => MergeStrategy.last
        case s if s.endsWith("mailcap") => MergeStrategy.last
        case s if s.endsWith("mimetypes.default") => MergeStrategy.last
        case s if s.endsWith("package.jdo") => MergeStrategy.last
        case s if s.endsWith("META-INF/spring.tooling") => MergeStrategy.last
        case x => old(x)
      }
    }
  )

  lazy val commonDependecies = Seq(
    resolvers ++= Seq(
//      "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
      "Clouder" at "https://repository.cloudera.com/artifactory/cloudera-repos",
      "Concurrent Maven Repo" at "http://conjars.org/repo"
    ),
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.2.0",
      "org.slf4j" % "slf4j-log4j12" % "1.7.7",
      "joda-time" %  "joda-time" % "2.4",
      "com.github.nscala-time" %% "nscala-time" % "1.4.0",
      "org.scalatest" %% "scalatest" % "2.2.1" % "test",
      "org.mockito" % "mockito-all" % "1.10.8" % "test"
    )
  )

  lazy val commonReleaseSettings = releaseSettings ++ Seq(
    releaseProcess := Seq[ReleaseStep](
      checkoutBranch("develop"),
      pullBranch("develop"),
      checkSnapshotDependencies,
      checkoutBranch("master"),
      pullBranch("master"),
      mergeBranch("develop"),
      inquireVersions,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      pushBranch("master"),
      checkoutBranch("develop"),
      mergeBranch("master"),
      setNextVersion,
      commitNextVersion,
      pushBranch("develop"),
      pushBranch("--tags")
    )
  )

  lazy val commonAssemblySettings = assemblySettings ++
    addArtifact(Artifact("scaffold", "assembly"), sbtassembly.Plugin.AssemblyKeys.assembly) ++
    Seq(jarName in assembly := { s"${name.value}.jar" }) ++
    assemblyStrategyProcessing

  lazy val commonSettings = Defaults.coreDefaultSettings ++
    basicSettings ++
    commonDependecies ++
    commonReleaseSettings ++
    commonAssemblySettings ++
    mavenPublishSettings ++
    addCommandAlias("releaseWithDefaults", "release with-defaults")
}
