package net.thenobody.scaffold.utils

import javax.sql.DataSource

import scala.slick.driver.MySQLDriver.simple._

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
class MySqlDatabaseProvider(val dataSource: DataSource) {

  val database: Database = Database.forDataSource(dataSource)

  def execute[T](callback: Session => T): T = {
    database.withSession {
      implicit session => callback(session)
    }
  }

}