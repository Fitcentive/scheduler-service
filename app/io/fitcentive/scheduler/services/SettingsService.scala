package io.fitcentive.scheduler.services

import com.google.inject.ImplementedBy
import io.fitcentive.scheduler.domain.config.{AppPubSubConfig, DatabaseConfig, EnvironmentConfig}
import io.fitcentive.scheduler.infrastructure.settings.AppConfigService
import io.fitcentive.sdk.config.{GcpConfig, SecretConfig}

@ImplementedBy(classOf[AppConfigService])
trait SettingsService {
  def pubSubServiceAccountStringCredentials: String
  def databaseConfig: DatabaseConfig
  def gcpConfig: GcpConfig
  def pubSubConfig: AppPubSubConfig
  def envConfig: EnvironmentConfig
  def secretConfig: SecretConfig
}
