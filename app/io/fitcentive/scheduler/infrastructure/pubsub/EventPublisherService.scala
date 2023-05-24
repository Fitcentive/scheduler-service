package io.fitcentive.scheduler.infrastructure.pubsub

import io.fitcentive.registry.events.scheduled.reminder.ScheduledMeetupReminder
import io.fitcentive.registry.events.scheduled.transition.ScheduledMeetupStateTransition
import io.fitcentive.scheduler.domain.config.TopicsConfig
import io.fitcentive.scheduler.infrastructure.contexts.PubSubExecutionContext
import io.fitcentive.scheduler.services.{MessageBusService, SettingsService}
import io.fitcentive.sdk.gcp.pubsub.PubSubPublisher

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import scala.util.chaining.scalaUtilChainingOps

@Singleton
class EventPublisherService @Inject() (publisher: PubSubPublisher, settingsService: SettingsService)(implicit
  ec: PubSubExecutionContext
) extends MessageBusService {

  private val publisherConfig: TopicsConfig = settingsService.pubSubConfig.topicsConfig

  override def publishScheduledMeetupStateTransition(meetupId: UUID): Future[Unit] =
    ScheduledMeetupStateTransition(meetupId)
      .pipe(publisher.publish(publisherConfig.scheduledMeetupStateTransitionTopic, _))

  override def publishScheduledMeetupReminder(meetupId: UUID): Future[Unit] =
    ScheduledMeetupReminder(meetupId)
      .pipe(publisher.publish(publisherConfig.scheduledMeetupReminderTopic, _))

}
