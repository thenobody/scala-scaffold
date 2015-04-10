package net.thenobody.scaffold.service

import net.thenobody.scaffold.model.User
import net.thenobody.scaffold.model.tables.UserTable
import net.thenobody.scaffold.utils.MySqlDatabaseProvider

import scala.slick.driver.MySQLDriver.simple._

/**
 * @author Anton Vanco <vanco.anton@gmail.com>
 */
class MySqlUserService(val databaseProvider: MySqlDatabaseProvider) extends UserService {

  private def query(implicit session: Session) = {
    val users = TableQuery[UserTable]

    for { user <- users } yield (user.id, user.name, user.password, user.email)
  }
  
  def buildUsersFromResult(rows: Seq[(String, String, String, String)]): Iterable[User] = rows.map(buildUserFromResult)

  def buildUserFromResult(row: (String, String, String, String)): User = row match {
    case (id, name, password, email) => User(id, name, password, email)
  }

  def retrieveUsers(implicit session: Session): Iterable[User] = buildUsersFromResult(query.run)

  def retrieveUser(userId: String)(implicit session: Session): User = {
    buildUsersFromResult(query.filter(row => row._1 === userId).run).head
  }

  def getUsers: Set[User] = databaseProvider.execute {
    implicit session => retrieveUsers
  }.toSet

  def getUser(userId: String): User = databaseProvider.execute {
    implicit session => retrieveUser(userId)
  }

  def addUser(user: User): Unit = databaseProvider.execute {
    implicit session => persistUser(user)
  }

  def persistUser(user: User)(implicit session: Session): Unit = {
    val users = TableQuery[UserTable]

    users += (user.id, user.name, user.password, user.email)
  }

  def userExists(userId: String): Boolean = {
    databaseProvider.execute {
      implicit session => buildUsersFromResult(query.filter(row => row._1 === userId).run).nonEmpty
    }
  }
}