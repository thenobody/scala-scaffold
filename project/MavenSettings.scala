import sbt._
import sbt.Keys._

object MavenSettings {

  val mavenPublishSettings = Seq(

    publishMavenStyle := true,

    publishTo <<= version { (v: String) =>
      val nexus = "https://my.repository.com/nexus/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "content/repositories/releases")
    },

    credentials ++= {
      val sonatype = ("Sonatype Nexus Repository Manager", "my.repository.com")
      def loadMavenCredentials(file: java.io.File): Seq[Credentials] = {
        xml.XML.loadFile(file) \ "servers" \ "server" map (s => {
          val host = (s \ "id").text
          val realm = sonatype._1
          val hostToUse = "my.repository.com"
          Credentials(realm, hostToUse, (s \ "username").text, (s \ "password").text)
        })
      }
      val ivyCredentials = Path.userHome / ".ivy2" / ".credentials"
      val mavenCredentials = Path.userHome / ".m2" / "settings.xml"
      (ivyCredentials.asFile, mavenCredentials.asFile) match {
        case (ivy, _) if ivy.canRead => Credentials(ivy) :: Nil
        case (_, mvn) if mvn.canRead => loadMavenCredentials(mvn)
        case _ => Nil
      }
    },

    resolvers ++= Seq(
      "My Repository - Releases" at "https://my.repository.com/nexus/content/repositories/releases",
      "My Repository - Snapshot" at "https://my.repository.com/nexus/content/repositories/snapshots"
    )
  )
}
