package io.fitcentive.scheduler.infrastructure.scheduler

import com.github.kagkarlsson.scheduler.Scheduler
import com.github.kagkarlsson.scheduler.task.{Task, TaskInstanceId}
import io.circe.syntax._
import io.fitcentive.scheduler.domain.events._
import io.fitcentive.scheduler.domain.scheduler.BackgroundScheduler

import java.time.Instant
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

@Singleton
class DbScheduler @Inject() (scheduler: Scheduler, oneTimeTask: Task[String])(implicit ec: ExecutionContext)
  extends BackgroundScheduler {

  import DbScheduler._

  scheduler.start();

  override def schedule(eventMessage: EventMessage): Future[Unit] = {
    Future.fromTry {
      Try {
        eventMessage match {
          case CancelScheduledMeetupForLaterEvent(meetupId) =>
            val taskId = s"schedule-meetup-reminder-for-later-${meetupId.toString}"
            scheduler.cancel(TaskInstanceId.of(ScheduleOneTimePubSubMessageTaskName, taskId))

          case ScheduleMeetupReminderForLaterEvent(meetupId, later) =>
            val taskId = s"schedule-meetup-reminder-for-later-${meetupId.toString}"
            val taskInstance =
              oneTimeTask.instance(taskId, ScheduleMeetupReminderForLaterEvent(meetupId, later).asJson.noSpaces)
            Try {
              // Optionally cancel task if it exists
              scheduler.cancel(TaskInstanceId.of(ScheduleOneTimePubSubMessageTaskName, taskId))
            } match {
              case Failure(exception) => scheduler.schedule(taskInstance, Instant.ofEpochMilli(later))
              case Success(value)     => scheduler.schedule(taskInstance, Instant.ofEpochMilli(later))
            }

          case CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent(meetupId) =>
            val taskId = s"schedule-meetup-state-transition-for-later-${meetupId.toString}"
            scheduler.cancel(TaskInstanceId.of(ScheduleOneTimePubSubMessageTaskName, taskId))

          case ScheduleMeetupStateTransitionTimeForLaterEvent(meetupId, later) =>
            val taskId = s"schedule-meetup-state-transition-for-later-${meetupId.toString}"
            val taskInstance =
              oneTimeTask.instance(
                taskId,
                ScheduleMeetupStateTransitionTimeForLaterEvent(meetupId, later).asJson.noSpaces
              )
            Try {
              // Optionally cancel task if it exists
              scheduler.cancel(TaskInstanceId.of(ScheduleOneTimePubSubMessageTaskName, taskId))
            } match {
              case Failure(exception) => scheduler.schedule(taskInstance, Instant.ofEpochMilli(later))
              case Success(value)     => scheduler.schedule(taskInstance, Instant.ofEpochMilli(later))
            }
        }
      }
    }
  }
}

object DbScheduler {
  val ScheduleOneTimePubSubMessageTaskName: String = "ScheduleOneTimePubSubMessage"
}
