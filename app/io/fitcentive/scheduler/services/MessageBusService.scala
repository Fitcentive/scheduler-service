package io.fitcentive.scheduler.services

import com.google.inject.ImplementedBy
import io.fitcentive.scheduler.infrastructure.pubsub.EventPublisherService

import java.util.UUID
import scala.concurrent.Future

@ImplementedBy(classOf[EventPublisherService])
trait MessageBusService {
  def publishMeetupDecisionMade(
    meetupId: UUID,
    meetupOwnerId: UUID,
    meetupParticipantId: UUID,
    hasAccepted: Boolean
  ): Future[Unit]
  def publishParticipantAddedToMeetup(meetupId: UUID, meetupOwnerId: UUID, meetupParticipantId: UUID): Future[Unit]
  def publishMeetupReminder(meetupId: UUID, meetupName: String, targetUser: UUID): Future[Unit]
  def publishCancelPreviouslyScheduledMeetupReminderForLater(meetupId: UUID): Future[Unit]
  def publishScheduleMeetupReminderForLater(meetupId: UUID, later: Long): Future[Unit]
}
