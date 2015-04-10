package net.thenobody.scaffold.restapi.service.model

import spray.http.HttpEntity
import spray.httpx.unmarshalling._

import scala.util.matching.Regex

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
case class UserForm(id: String, name: String, password: String, email: String) {
  require(id.nonEmpty)
  require(name.nonEmpty)
  require(password.nonEmpty)
  require(email.nonEmpty)
}

object Form {
  implicit val regexUnmarshaller = new Unmarshaller[Regex] {
    def apply(entity: HttpEntity) = Right(entity.asString.r)
  }
}
