package net.thenobody.scaffold.restapi.service

import net.thenobody.scaffold.utils.JsonFormats._
import net.thenobody.scaffold.model.User
import net.thenobody.scaffold.restapi.service.model.UserForm
import net.thenobody.scaffold.restapi.service.util.users.UsersUtil
import spray.http.{HttpHeaders, StatusCodes}
import spray.httpx.SprayJsonSupport._
import spray.routing._

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
trait UsersService extends HttpService {
  val usersPath = path("users")
  val userIdPath = pathPrefix("users" / Segment)

  def usersUtil: UsersUtil

  def handleGetUsers = complete {
    usersUtil.getUsers
  }

  def handleGetUser(user: User) = complete {
    user
  }

  def handlePostUser = formFields('id, 'name, 'password, 'email).as(UserForm) { userForm: UserForm =>
    if (usersUtil.userExists(userForm)) {
      reject(MalformedFormFieldRejection("id", s"${userForm.id} already exists"))
    } else requestUri { uri =>
      respondWithHeader(HttpHeaders.Location(uri + s"/${userForm.id}")) {
        complete {
          usersUtil.addUser(userForm)
          StatusCodes.Created
        }
      }
    }
  }
}
