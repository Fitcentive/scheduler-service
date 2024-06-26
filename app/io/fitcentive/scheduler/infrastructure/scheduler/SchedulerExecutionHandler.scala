package io.fitcentive.scheduler.infrastructure.scheduler

import com.github.kagkarlsson.scheduler.task.{ExecutionContext, TaskInstance, VoidExecutionHandler}
import io.circe.parser._
import io.fitcentive.scheduler.domain.events.{
  ScheduleMeetupReminderForLaterEvent,
  ScheduleMeetupStateTransitionTimeForLaterEvent
}
import io.fitcentive.scheduler.services.MessageBusService
import io.fitcentive.sdk.logging.AppLogger

import javax.inject.{Inject, Singleton}

@Singleton
class SchedulerExecutionHandler @Inject() (messageBus: MessageBusService)
  extends VoidExecutionHandler[String]
  with AppLogger {

  override def execute(taskInstance: TaskInstance[String], executionContext: ExecutionContext): Unit = {
    // todo - this is tacky, if there are other events, we would need to nest them, which isn't great
    decode[ScheduleMeetupReminderForLaterEvent](taskInstance.getData) match {
      case Left(value) =>
        decode[ScheduleMeetupStateTransitionTimeForLaterEvent](taskInstance.getData) match {
          case Left(err) => logError(s"Failed to decode EventMessage payload: $err")
          case Right(value) =>
            value match {
              case ScheduleMeetupStateTransitionTimeForLaterEvent(meetupId, scheduleTransitionAtMillis) =>
                logInfo(s"Publishing meetup state transition event for meetupId: $meetupId")
                messageBus.publishScheduledMeetupStateTransition(meetupId)

              case _ => logError(s"Error - unknown scheduler message type")
            }
        }

      case Right(value) =>
        value match {
          case ScheduleMeetupReminderForLaterEvent(meetupId, scheduleReminderAtMillis) =>
            logInfo(s"Publishing meetup reminder event for meetupId: $meetupId")
            messageBus.publishScheduledMeetupReminder(meetupId)

          case _ => logError(s"Error - unknown scheduler message type")
        }
    }
  }
}
