package app.place2meet.places.infrastructure.settings

import app.place2meet.places.domain.config.FourSquareConfig
import app.place2meet.places.services.SettingsService
import app.place2meet.sdk.config.{JwtConfig, SecretConfig, ServerConfig}
import com.typesafe.config.Config
import play.api.Configuration

import javax.inject.{Inject, Singleton}

@Singleton
class AppConfigService @Inject() (config: Configuration) extends SettingsService {

  override def keycloakServerUrl: String = config.get[String]("keycloak.server-url")

  override def secretConfig: SecretConfig = SecretConfig.fromConfig(config.get[Config]("services"))

  override def fourSquareConfig: FourSquareConfig = FourSquareConfig.fromConfig(config.get[Config]("foursquare"))

  override def userServiceConfig: ServerConfig = ServerConfig.fromConfig(config.get[Config]("services.user-service"))

  override def serverConfig: ServerConfig = ServerConfig.fromConfig(config.get[Config]("http-server"))

  override def keycloakConfigRaw: Config = config.get[Config]("keycloak")

  override def jwtConfig: JwtConfig = JwtConfig.apply(config.get[Config]("jwt"))
}
