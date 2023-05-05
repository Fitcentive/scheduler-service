package io.fitcentive.scheduler.domain.config

import com.typesafe.config.Config

case class DatabaseConfig(url: String, username: String, password: String)

object DatabaseConfig {
  def fromConfig(config: Config): DatabaseConfig =
    DatabaseConfig(
      url = config.getString("url"),
      username = config.getString("username"),
      password = config.getString("password")
    )
}
