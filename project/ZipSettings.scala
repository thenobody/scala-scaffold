import java.io.File

import sbt.Keys._
import sbt._
import sbtassembly.Plugin.AssemblyKeys._

object ZipSettings {
  lazy val assembleArtifact = TaskKey[java.io.File]("assemble-artifact")
  lazy val zipPath = TaskKey[java.io.File]("assemble-artifact-path")

  val zipSettings = Seq(
    crossPaths := true,
    assembleArtifact <<= (assembly, zipPath, baseDirectory) map {
      (artifact: File, out: File, base: File) =>
        IO.delete(out)
        IO.delete(new File("scaffold-data-platform.jar"))
        IO.copyFile(artifact, new File("./scaffold-data-platform.jar"))
        IO.copyDirectory(new File("./scaffold-data-platform/scripts/"), new File("./scripts/"))
        if ((Process("zip scaffold-data-platform/target/scaffold-data-platform.zip -FSr scripts/ scaffold-data-platform.jar -x *.pyc") !) != 0) {
          sys.error("Aborting release  couldn't create the zip file")
        }
        IO.delete(new File("scaffold-data-platform.jar"))
        IO.delete(new File("./scripts/"))
        out
    },
    zipPath <<= target map {
      (t: File) =>
        t / "scaffold-data-platform.zip"
    }
  ) ++ addArtifact(Artifact("scaffold-data-platform", "dist", "zip"), assembleArtifact).settings
}
