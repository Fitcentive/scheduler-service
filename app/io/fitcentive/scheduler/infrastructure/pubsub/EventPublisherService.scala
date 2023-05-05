package io.fitcentive.scheduler.infrastructure.pubsub

import io.fitcentive.registry.events.meetup.{MeetupDecision, MeetupReminder, ParticipantAddedToMeetup}
import io.fitcentive.registry.events.scheduled.{
  CancelPreviouslyScheduledMeetupReminderForLater,
  ScheduleMeetupReminderForLater
}
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

  override def publishCancelPreviouslyScheduledMeetupReminderForLater(meetupId: UUID): Future[Unit] =
    CancelPreviouslyScheduledMeetupReminderForLater(meetupId)
      .pipe(publisher.publish(publisherConfig.cancelScheduledMeetupReminderForLaterTopic, _))

  override def publishScheduleMeetupReminderForLater(meetupId: UUID, later: Long): Future[Unit] =
    ScheduleMeetupReminderForLater(meetupId, later)
      .pipe(publisher.publish(publisherConfig.scheduleMeetupReminderForLaterTopic, _))

  override def publishMeetupReminder(meetupId: UUID, meetupName: String, targetUser: UUID): Future[Unit] =
    MeetupReminder(meetupId, meetupName, targetUser)
      .pipe(publisher.publish(publisherConfig.meetupReminderTopic, _))

  override def publishMeetupDecisionMade(
    meetupId: UUID,
    meetupOwnerId: UUID,
    meetupParticipantId: UUID,
    hasAccepted: Boolean
  ): Future[Unit] =
    MeetupDecision(meetupId, meetupOwnerId, meetupParticipantId, hasAccepted)
      .pipe(publisher.publish(publisherConfig.meetupDecisionTopic, _))

  override def publishParticipantAddedToMeetup(
    meetupId: UUID,
    meetupOwnerId: UUID,
    meetupParticipantId: UUID
  ): Future[Unit] =
    ParticipantAddedToMeetup(meetupId, meetupOwnerId, meetupParticipantId)
      .pipe(publisher.publish(publisherConfig.participantAddedToMeetupTopic, _))

}
