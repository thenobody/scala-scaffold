package net.thenobody.scaffold.rdbms

import javax.sql.DataSource

import com.typesafe.config.Config
import com.zaxxer.hikari.HikariDataSource

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
object HikariDataSourceProvider extends DataSourceProvider {
  def getDataSource(configuration: Config): DataSource = {
    val driver = configuration.getString("scaffold.rdbms.driver")
    val host = configuration.getString("scaffold.rdbms.host")
    val database = configuration.getString("scaffold.rdbms.database")
    val port = configuration.getString("scaffold.rdbms.port")
    val username = configuration.getString("scaffold.rdbms.username")
    val password = configuration.getString("scaffold.rdbms.password")

    val url = s"jdbc:$driver://$host:$port/$database"

    val dataSource = new HikariDataSource()
    dataSource.setJdbcUrl(url)
    dataSource.setUsername(username)
    dataSource.setPassword(password)

    return dataSource
  }
}
