package io.fitcentive.scheduler.infrastructure.handlers

import io.fitcentive.scheduler.api.SchedulerApi
import io.fitcentive.scheduler.domain.events._
import io.fitcentive.sdk.logging.AppLogger

import scala.concurrent.{ExecutionContext, Future}

trait MessageEventHandlers extends EventHandlers with AppLogger {

  def schedulerApi: SchedulerApi
  implicit def executionContext: ExecutionContext

  override def handleEvent(event: EventMessage): Future[Unit] =
    event match {
      case event: ScheduleMeetupReminderForLaterEvent =>
        logInfo("ScheduleMeetupReminderForLaterEvent received from Google PubSub")
        schedulerApi.scheduleMeetupReminderForLater(event.meetupId, event.scheduleReminderAtMillis).map(_ => ())

      case event: CancelScheduledMeetupForLaterEvent =>
        schedulerApi.cancelPreviouslyScheduledMeetupReminderForLater(event.meetupId).map(_ => ())

      case event: ScheduleMeetupStateTransitionTimeForLaterEvent =>
        schedulerApi.scheduleMeetupTransitionForLater(event.meetupId, event.scheduleTransitionAtMillis).map(_ => ())

      case event: CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent =>
        schedulerApi.cancelPreviouslyScheduledMeetupTransitionForLater(event.meetupId).map(_ => ())

      case _ => Future.failed(new Exception("Unrecognized event"))
    }
}
