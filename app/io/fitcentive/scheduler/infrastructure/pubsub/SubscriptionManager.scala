package io.fitcentive.scheduler.infrastructure.pubsub

import io.fitcentive.registry.events.scheduled.reminder.{
  CancelPreviouslyScheduledMeetupReminderForLater,
  ScheduleMeetupReminderForLater
}
import io.fitcentive.registry.events.scheduled.transition.{
  CancelPreviouslyScheduledMeetupStateTransitionForLater,
  ScheduleMeetupStateTransitionTimeForLater
}
import io.fitcentive.scheduler.domain.config.AppPubSubConfig
import io.fitcentive.scheduler.domain.events.EventHandlers
import io.fitcentive.scheduler.infrastructure.AntiCorruptionDomain
import io.fitcentive.scheduler.infrastructure.contexts.PubSubExecutionContext
import io.fitcentive.sdk.gcp.pubsub.{PubSubPublisher, PubSubSubscriber}
import io.fitcentive.sdk.logging.AppLogger

import scala.concurrent.Future
import scala.util.chaining.scalaUtilChainingOps

class SubscriptionManager(
  publisher: PubSubPublisher,
  subscriber: PubSubSubscriber,
  config: AppPubSubConfig,
  environment: String
)(implicit ec: PubSubExecutionContext)
  extends AppLogger
  with AntiCorruptionDomain {

  self: EventHandlers =>

  initializeSubscriptions

  final def initializeSubscriptions: Future[Unit] = {
    for {
      _ <- Future.sequence(config.topicsConfig.topics.map(publisher.createTopic))
      _ <-
        subscriber
          .subscribe[ScheduleMeetupReminderForLater](
            environment,
            config.subscriptionsConfig.scheduleMeetupReminderForLaterSubscription,
            config.topicsConfig.scheduleMeetupReminderForLaterTopic
          )(_.payload.toDomain.pipe(handleEvent))
      _ <-
        subscriber
          .subscribe[CancelPreviouslyScheduledMeetupReminderForLater](
            environment,
            config.subscriptionsConfig.cancelPreviouslyScheduledMeetupReminderForLaterSubscription,
            config.topicsConfig.cancelScheduledMeetupReminderForLaterTopic
          )(_.payload.toDomain.pipe(handleEvent))
      // Meetup state transition events
      _ <-
        subscriber
          .subscribe[ScheduleMeetupStateTransitionTimeForLater](
            environment,
            config.subscriptionsConfig.scheduleMeetupStateTransitionForLaterSubscription,
            config.topicsConfig.scheduleMeetupStateTransitionForLaterTopic
          )(_.payload.toDomain.pipe(handleEvent))
      _ <-
        subscriber
          .subscribe[CancelPreviouslyScheduledMeetupStateTransitionForLater](
            environment,
            config.subscriptionsConfig.cancelPreviouslyScheduledMeetupStateTransitionForLaterSubscription,
            config.topicsConfig.cancelScheduledMeetupStateTransitionForLaterTopic
          )(_.payload.toDomain.pipe(handleEvent))
      _ = logInfo("Subscriptions set up successfully!")
    } yield ()
  }
}
