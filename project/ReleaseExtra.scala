package net.imagini.sbt

import sbt._
import sbtrelease.ReleasePlugin.ReleaseKeys._
import sbtrelease._
import sbtrelease.Utilities._

object ReleaseExtra {

  def cmd(args: Any*): ProcessBuilder = Process("git" +: args.map(_.toString))

  def mergeBranch(branch: String) = ReleaseStep(
    action = {
      st: State =>
        if ((cmd("merge", branch) !) != 0 ) {
          sys.error("Aborting release! Couldn't merge " + branch)
        }
        st
    }
  )

  def checkoutBranch(branch: String) = ReleaseStep(
    action = {
      st: State =>
        val vc = st.extract.get(versionControlSystem).getOrElse(sys.error("Aborting release. Working directory is not a repository of a recognized VCS."))
        vc.checkRemote("origin " + branch)
        if ((cmd("checkout", branch) !) != 0) {
          sys.error("Aborting release! Couldn't check out " + branch)
        }
        st
    }
  )

  def pushBranch(branch : String) = ReleaseStep(
    action = {
      st: State =>
        if ((cmd("push", "origin", branch) !) != 0) {
          sys.error("Aborting release! Couldn't push " + branch)
        }
        st
    }
  )

  def pullBranch(branch: String) = ReleaseStep(
    action = {
      st: State =>
        if ((cmd("pull", "origin", branch) !) != 0) {
          sys.error("Aborting the release! Couldn't pull " + branch)
        }
        st
    }
  )

  lazy val commitNextVersionFix = ReleaseStep(
    action = {
      st: State =>
        val vcs: Vcs = st.extract.get(versionControlSystem).getOrElse(sys.error("Aborting release. Working directory is not a repository of a recognized VCS."))
        vcs.add("version.sbt") !! st.log
        val status = (vcs.status !!) trim

        val newState = if (status.nonEmpty) {
          val (state, msg) = st.extract.runTask(commitMessage, st)
          vcs.commit(msg) ! st.log
          state
        } else {
          st
        }
        newState
    }
  )
}