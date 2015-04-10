package net.thenobody.scaffold.restapi.service.exception

import spray.http.StatusCode
import spray.json._
import DefaultJsonProtocol._

class InvalidRequestException(val status: StatusCode, val message: String, val cause: Throwable) extends RuntimeException(message, cause) {

  def this(status: StatusCode, message: String) = this(status, message, null)

  override def toString = Map("error" -> message).toJson.toString
}
