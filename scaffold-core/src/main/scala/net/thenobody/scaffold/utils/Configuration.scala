package net.thenobody.scaffold.utils

import java.io.File

import com.typesafe.config.{Config, ConfigFactory, ConfigParseOptions, ConfigSyntax}

object Configuration {
  def getConfig(path: String): Config = ConfigFactory.parseFile(
    new File(path),
    ConfigParseOptions.defaults().setSyntax(ConfigSyntax.CONF)
  ).withFallback(ConfigFactory.load)
}
