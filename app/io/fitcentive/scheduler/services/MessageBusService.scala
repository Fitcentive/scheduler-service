package io.fitcentive.scheduler.services

import com.google.inject.ImplementedBy
import io.fitcentive.scheduler.infrastructure.pubsub.EventPublisherService

import java.util.UUID
import scala.concurrent.Future

@ImplementedBy(classOf[EventPublisherService])
trait MessageBusService {
  def publishScheduledMeetupReminder(meetupId: UUID): Future[Unit]
}
