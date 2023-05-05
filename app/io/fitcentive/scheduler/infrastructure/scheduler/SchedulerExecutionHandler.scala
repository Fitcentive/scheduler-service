package io.fitcentive.scheduler.infrastructure.scheduler

import com.github.kagkarlsson.scheduler.task.{ExecutionContext, TaskInstance, VoidExecutionHandler}
import io.circe.parser.decode
import io.fitcentive.scheduler.api.SchedulerApi
import io.fitcentive.scheduler.domain.events.{EventMessage, ScheduleMeetupReminderForLaterEvent}
import io.fitcentive.scheduler.services.MessageBusService
import io.fitcentive.sdk.logging.AppLogger

import javax.inject.{Inject, Singleton}

@Singleton
class SchedulerExecutionHandler @Inject() (messageBus: MessageBusService, schedulerApi: SchedulerApi)
  extends VoidExecutionHandler[String]
  with AppLogger {

  override def execute(taskInstance: TaskInstance[String], executionContext: ExecutionContext): Unit =
    decode[EventMessage](taskInstance.getData) match {
      case Left(value) => logError(s"Failed to decode EventMessage payload: $value")

      case Right(value) =>
        value match {
          case ScheduleMeetupReminderForLaterEvent(meetupId, scheduleReminderAtMillis) =>
            messageBus.publishScheduledMeetupReminder(meetupId)

          case _ => logError(s"Error - unknown scheduler message type")
        }
    }
}
