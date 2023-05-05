package io.fitcentive.scheduler.api

import io.fitcentive.scheduler.services.MessageBusService

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SchedulerApi @Inject() (messageBusService: MessageBusService)(implicit ec: ExecutionContext) {

  def scheduleMeetupReminderForLater(meetupId: UUID): Future[Unit] = ???

  def cancelPreviouslyScheduledMeetupReminderForLater(meetupId: UUID): Future[Unit] = ???
}
