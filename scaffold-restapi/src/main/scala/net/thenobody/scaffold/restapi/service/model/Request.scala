package net.thenobody.scaffold.restapi.service.model

import net.thenobody.scaffold.model.User

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
abstract class Request {
  def user: User
}

case class DetailUserRequest(user: User, date: String) extends Request
