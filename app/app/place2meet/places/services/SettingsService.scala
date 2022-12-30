package app.place2meet.places.services

import app.place2meet.places.domain.config.FourSquareConfig
import app.place2meet.places.infrastructure.settings.AppConfigService
import app.place2meet.sdk.config.{JwtConfig, SecretConfig, ServerConfig}
import com.google.inject.ImplementedBy
import com.typesafe.config.Config

@ImplementedBy(classOf[AppConfigService])
trait SettingsService {
  def keycloakConfigRaw: Config
  def jwtConfig: JwtConfig
  def keycloakServerUrl: String
  def serverConfig: ServerConfig
  def userServiceConfig: ServerConfig
  def secretConfig: SecretConfig
  def fourSquareConfig: FourSquareConfig
}
