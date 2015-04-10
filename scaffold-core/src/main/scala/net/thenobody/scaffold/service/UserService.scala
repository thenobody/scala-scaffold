package net.thenobody.scaffold.service

import net.thenobody.scaffold.model.User

/**
 * @author Anton Vanco <vanco.anton@gmail.com>
 */
trait UserService {
  def getUsers: Set[User]
  def getUser(userId: String): User
  def addUser(user: User): Unit
  def userExists(userId: String): Boolean
}
