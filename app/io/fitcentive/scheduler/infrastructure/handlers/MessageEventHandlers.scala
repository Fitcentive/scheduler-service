package io.fitcentive.scheduler.infrastructure.handlers

import io.fitcentive.scheduler.api.SchedulerApi
import io.fitcentive.scheduler.domain.events.{
  CancelScheduledMeetupForLaterEvent,
  EventHandlers,
  EventMessage,
  ScheduleMeetupReminderForLaterEvent
}

import scala.concurrent.{ExecutionContext, Future}

trait MessageEventHandlers extends EventHandlers {

  def schedulerApi: SchedulerApi
  implicit def executionContext: ExecutionContext

  override def handleEvent(event: EventMessage): Future[Unit] =
    event match {
      case event: ScheduleMeetupReminderForLaterEvent =>
        schedulerApi.scheduleMeetupReminderForLater(event.meetupId).map(_ => ())
      case event: CancelScheduledMeetupForLaterEvent =>
        schedulerApi.cancelPreviouslyScheduledMeetupReminderForLater(event.meetupId).map(_ => ())
      case _ => Future.failed(new Exception("Unrecognized event"))
    }
}
