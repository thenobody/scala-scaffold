package net.thenobody.scaffold.jobs

import com.twitter.scalding.{Args, Job, SequenceFile, Tsv}
import net.thenobody.scaffold.hdfs.scalding.JobRunner

class UserCountJob(args: Args) extends Job(args) {
  val inputPath = args("input")
  val outputPath = args("output")

  SequenceFile(inputPath, UserCountJob.inputSchema)
    .read
    .groupAll {
      _.size('count)
    }.write(Tsv(outputPath, UserCountJob.outputSchema))
}

object UserCountJob {
  val inputSchema = Seq('id, 'name, 'password, 'email)
  val outputSchema = Seq('count)

  def main(argv: Array[String]): Unit = {
    JobRunner.runJob(argv, (args: Args) => new UserCountJob(args))
  }
}