package io.fitcentive.scheduler.modules.providers

import com.google.auth.oauth2.ServiceAccountCredentials
import io.fitcentive.scheduler.api.SchedulerApi
import io.fitcentive.scheduler.infrastructure.contexts.PubSubExecutionContext
import io.fitcentive.scheduler.infrastructure.handlers.MessageEventHandlers
import io.fitcentive.scheduler.infrastructure.pubsub.SubscriptionManager
import io.fitcentive.scheduler.services.SettingsService
import io.fitcentive.sdk.gcp.pubsub.{PubSubPublisher, PubSubSubscriber}

import java.io.ByteArrayInputStream
import javax.inject.{Inject, Provider}
import scala.concurrent.ExecutionContext

class SubscriptionManagerProvider @Inject() (
  publisher: PubSubPublisher,
  settingsService: SettingsService,
  _schedulerApi: SchedulerApi,
)(implicit ec: PubSubExecutionContext)
  extends Provider[SubscriptionManager] {

  trait SubscriptionEventHandlers extends MessageEventHandlers {
    override def schedulerApi: SchedulerApi = _schedulerApi
    override implicit def executionContext: ExecutionContext = ec
  }

  override def get(): SubscriptionManager = {
    val credentials =
      ServiceAccountCredentials
        .fromStream(new ByteArrayInputStream(settingsService.pubSubServiceAccountStringCredentials.getBytes()))
        .createScoped()
    new SubscriptionManager(
      publisher = publisher,
      subscriber = new PubSubSubscriber(credentials, settingsService.gcpConfig.project),
      config = settingsService.pubSubConfig,
      environment = settingsService.envConfig.environment
    ) with SubscriptionEventHandlers
  }
}
