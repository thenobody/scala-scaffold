package net.thenobody.scaffold.restapi.service.model

import net.thenobody.scaffold.model.User
import net.thenobody.scaffold.restapi.service.exception.InvalidRequestException
import net.thenobody.scaffold.restapi.service.util.users.UsersUtil
import spray.http.StatusCodes._

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
trait Conversions {

  def usersUtil: UsersUtil

  implicit def stringToUser(userId: String): User = try {
    usersUtil.getUser(userId)
  } catch {
    case cause : Throwable => throw new InvalidRequestException(NotFound, "Invalid user " + userId, cause)
  }

}
