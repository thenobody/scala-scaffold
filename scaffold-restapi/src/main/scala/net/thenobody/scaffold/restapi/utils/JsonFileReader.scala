package net.thenobody.scaffold.restapi.utils

import spray.json.DefaultJsonProtocol._
import spray.json._

import scala.io.Source

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
object JsonFileReader {
  
  def readFileToString(path: String): String = Source.fromInputStream(getClass.getResourceAsStream(path)).getLines().reduce(_ + _)

  def readMapFromFile(path: String): Map[String, Double] = readFileToString(path).parseJson.convertTo[Map[String, Double]]
  
  def readMapOfSeqFromFile(path: String): Map[String, Seq[String]] = readFileToString(path)
    .parseJson.convertTo[Map[String, Seq[String]]]
}
