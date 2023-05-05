package io.fitcentive.scheduler.infrastructure.settings

import com.typesafe.config.Config
import io.fitcentive.scheduler.domain.config._
import io.fitcentive.scheduler.services.SettingsService
import io.fitcentive.sdk.config.{GcpConfig, SecretConfig}
import play.api.Configuration

import javax.inject.{Inject, Singleton}

@Singleton
class AppConfigService @Inject() (config: Configuration) extends SettingsService {

  override def pubSubServiceAccountStringCredentials: String =
    config.get[String]("gcp.pubsub.service-account-string-credentials")

  override def envConfig: EnvironmentConfig = EnvironmentConfig.fromConfig(config.get[Config]("environment"))
  config.get[String]("gcp.pubsub.service-account-string-credentials")

  override def gcpConfig: GcpConfig = GcpConfig(project = config.get[String]("gcp.project"))

  override def secretConfig: SecretConfig = SecretConfig.fromConfig(config.get[Config]("services"))

  override def pubSubConfig: AppPubSubConfig =
    AppPubSubConfig(
      topicsConfig = TopicsConfig.fromConfig(config.get[Config]("gcp.pubsub.topics")),
      subscriptionsConfig = SubscriptionsConfig.fromConfig(config.get[Config]("gcp.pubsub.subscriptions"))
    )
}
