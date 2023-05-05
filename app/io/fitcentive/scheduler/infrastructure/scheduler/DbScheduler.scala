package io.fitcentive.scheduler.infrastructure.scheduler

import com.github.kagkarlsson.scheduler.Scheduler
import com.github.kagkarlsson.scheduler.task.{Task, TaskInstanceId}
import io.circe.syntax._
import io.fitcentive.scheduler.domain.events.{
  CancelScheduledMeetupForLaterEvent,
  EventMessage,
  ScheduleMeetupReminderForLaterEvent
}
import io.fitcentive.scheduler.domain.scheduler.BackgroundScheduler

import java.time.Instant
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class DbScheduler @Inject() (scheduler: Scheduler, oneTimeTask: Task[String])(implicit ec: ExecutionContext)
  extends BackgroundScheduler {

  import DbScheduler._

  override def schedule(eventMessage: EventMessage): Future[Unit] = {
    Future.fromTry {
      Try {
        eventMessage match {
          case CancelScheduledMeetupForLaterEvent(meetupId) =>
            val taskId = s"schedule-meetup-reminder-for-later-${meetupId.toString}"
            scheduler.cancel(TaskInstanceId.of(ScheduleOneTimePubSubMessageTaskName, taskId))

          case ScheduleMeetupReminderForLaterEvent(meetupId, later) =>
            // First cancel any previous task for this event, if any
            val taskId = s"schedule-meetup-reminder-for-later-${meetupId.toString}"
            val taskInstance =
              oneTimeTask.instance(taskId, ScheduleMeetupReminderForLaterEvent(meetupId, later).asJson.noSpaces)
            scheduler.schedule(taskInstance, Instant.ofEpochMilli(later))
        }
      }
    }
  }
}

object DbScheduler {
  val ScheduleOneTimePubSubMessageTaskName: String = "ScheduleOneTimePubSubMessage"
}
