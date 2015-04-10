package net.thenobody.scaffold.restapi.service.util.users

import net.thenobody.scaffold.model.User
import net.thenobody.scaffold.restapi.service.model.UserForm
import net.thenobody.scaffold.service.UserService

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
class UsersUtil(userService: UserService) {
  def getUsers: Iterable[User] = userService.getUsers
  def getUser(userId: String): User = userService.getUser(userId)

  def addUser(userForm: UserForm): Unit = userService.addUser(userForm)

  def userExists(userForm: UserForm): Boolean = userService.userExists(userForm.id)

  implicit def userFormToUser(userForm: UserForm): User = User(
    userForm.id,
    userForm.name,
    userForm.password,
    userForm.email
  )
}
