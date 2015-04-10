package net.thenobody.scaffold.hdfs.scalding

import com.twitter.scalding.FixedPathSource
import cascading.tuple.Fields

import cascading.scheme.local.{TextDelimited => CLTextDelimited}
import cascading.scheme.hadoop.{TextLine => CHTextLine, TextDelimited => CHTextDelimited}

case class TsvCompressed(
  p: String,
  override val fields: Fields = Fields.ALL,
  override val skipHeader: Boolean = false,
  override val writeHeader: Boolean = false
) extends DelimitedSchemeCompressed(p)

class DelimitedSchemeCompressed(p: String) extends FixedPathSource(p) {
  //override these as needed:
  val fields = Fields.ALL
  //This is passed directly to cascading where null is interpretted as string
  val types: Array[Class[_]] = null
  val separator = "\t"
  val skipHeader = false
  val writeHeader = false
  //These should not be changed:
  override def localScheme = new CLTextDelimited(fields, skipHeader, writeHeader, separator, types)

  override def hdfsScheme: cascading.scheme.Scheme[org.apache.hadoop.mapred.JobConf, org.apache.hadoop.mapred.RecordReader[_, _], org.apache.hadoop.mapred.OutputCollector[_, _], _, _] = {
    val temp = new CHTextDelimited(fields, skipHeader, writeHeader, separator, types)
    temp.setSinkCompression(CHTextLine.Compress.ENABLE)
    temp
  }
}