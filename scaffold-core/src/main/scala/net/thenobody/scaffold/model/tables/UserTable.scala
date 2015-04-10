package net.thenobody.scaffold.model.tables

import scala.slick.driver.MySQLDriver.simple._

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
class UserTable(tag: Tag) extends Table[(String, String, String, String)](tag, "users") {
  def id = column[String]("id", O.PrimaryKey)
  def name = column[String]("name")
  def password = column[String]("password")
  def email = column[String]("email")

  def * = (id, name, password, email)
}