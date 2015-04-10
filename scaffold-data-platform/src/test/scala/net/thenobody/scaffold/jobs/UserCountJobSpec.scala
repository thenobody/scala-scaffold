package net.thenobody.scaffold.jobs

import com.twitter.scalding.{FieldConversions, Tsv, SequenceFile, JobTest}
import org.scalatest.{Matchers, FlatSpec}

import scala.collection.mutable

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
class UserCountJobSpec extends FlatSpec with Matchers with FieldConversions {

  "UserCountJob" should "count users" in {
    JobTest(args => new UserCountJob(args))
      .arg("input", "input-path")
      .arg("output", "output-path")
      .source(SequenceFile("input-path", UserCountJob.inputSchema), List(
        ("id1", "name1", "password1", "email1"),
        ("id2", "name2", "password2", "email2")
      )).sink[Int](Tsv("output-path", UserCountJob.outputSchema)) { buffer: mutable.Buffer[Int] =>
        buffer.toList.head should be {2}
      }.runHadoop
      .finish
  }

}
