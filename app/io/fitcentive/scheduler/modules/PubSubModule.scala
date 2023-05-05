package io.fitcentive.scheduler.modules

import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.inject.{AbstractModule, Provides}
import io.fitcentive.scheduler.infrastructure.pubsub.SubscriptionManager
import io.fitcentive.scheduler.modules.providers.SubscriptionManagerProvider
import io.fitcentive.scheduler.services.SettingsService
import io.fitcentive.sdk.gcp.pubsub.PubSubPublisher

import java.io.ByteArrayInputStream
import javax.inject.Singleton

class PubSubModule extends AbstractModule {

  @Provides
  @Singleton
  def providePubSubPublisher(settingsService: SettingsService): PubSubPublisher = {
    val credentials =
      ServiceAccountCredentials
        .fromStream(new ByteArrayInputStream(settingsService.pubSubServiceAccountStringCredentials.getBytes()))
        .createScoped()
    new PubSubPublisher(credentials, settingsService.gcpConfig.project)
  }

  override def configure(): Unit = {
    bind(classOf[SubscriptionManager]).toProvider(classOf[SubscriptionManagerProvider]).asEagerSingleton()
  }

}
