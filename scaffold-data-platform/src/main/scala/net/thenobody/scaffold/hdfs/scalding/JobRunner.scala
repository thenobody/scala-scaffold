package net.thenobody.scaffold.hdfs.scalding

import com.twitter.scalding.{Job, Tool, Args}
import org.apache.hadoop

object JobRunner {
  //jobConstructor must new up a Job - it must NOT return an already instantiated job.
  //the args parameter will be parsed into a Args object and passed to your jobConstructor function.
  def runJob(args: Array[String], jobConstructor: (Args) => Job): Int = {
    val tool: Tool = new Tool
    tool.setJobConstructor(jobConstructor)
    hadoop.util.ToolRunner.run(new hadoop.conf.Configuration, tool, args)
  }

  def runAndGetJob[T <: Job](args: Array[String], jobConstructor: (Args) => T): T = {
    val tool: UberTool[T] = new UberTool[T]
    tool.setJobConstructor(jobConstructor)
    hadoop.util.ToolRunner.run(new hadoop.conf.Configuration, tool, args)
    tool.job
  }
}

class UberTool[T <: Job] extends Tool {
  var job: T = null.asInstanceOf[T]

  override def getJob(args : Args): T =
    if( rootJob.isDefined ) {
      // TODO Alternative to this nasty asInstanceOf is to copy paste entire Tool here and replace "Job" with "T"
      // or work out a smarter way.
      job = rootJob.get.apply(args).asInstanceOf[T]
      job
    }
    else
      sys.error("setJobConstructor was not called!")
}