package net.thenobody.scaffold.rdbms

import javax.sql.DataSource

import com.typesafe.config.Config

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
trait DataSourceProvider {
  def getDataSource(configuration: Config): DataSource
}
