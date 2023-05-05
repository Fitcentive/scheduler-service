package io.fitcentive.scheduler.domain.config

import com.typesafe.config.Config

case class EnvironmentConfig(environment: String)

object EnvironmentConfig {
  def fromConfig(config: Config): EnvironmentConfig =
    EnvironmentConfig(environment = config.getString("runtime-environment"))
}
