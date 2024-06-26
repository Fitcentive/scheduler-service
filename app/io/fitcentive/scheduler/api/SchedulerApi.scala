package io.fitcentive.scheduler.api

import io.fitcentive.scheduler.domain.events.{
  CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent,
  CancelScheduledMeetupForLaterEvent,
  ScheduleMeetupReminderForLaterEvent,
  ScheduleMeetupStateTransitionTimeForLaterEvent
}
import io.fitcentive.scheduler.infrastructure.scheduler.DbScheduler

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class SchedulerApi @Inject() (dbScheduler: DbScheduler)(implicit ec: ExecutionContext) {

  def scheduleMeetupReminderForLater(meetupId: UUID, scheduleAt: Long): Future[Unit] =
    Future.fromTry {
      Try {
        dbScheduler.schedule(ScheduleMeetupReminderForLaterEvent(meetupId, scheduleAt))
      }
    }

  def cancelPreviouslyScheduledMeetupReminderForLater(meetupId: UUID): Future[Unit] =
    Future.fromTry {
      Try {
        dbScheduler.schedule(CancelScheduledMeetupForLaterEvent(meetupId))
      }
    }

  def scheduleMeetupTransitionForLater(meetupId: UUID, scheduleAt: Long): Future[Unit] =
    Future.fromTry {
      Try {
        dbScheduler.schedule(ScheduleMeetupStateTransitionTimeForLaterEvent(meetupId, scheduleAt))
      }
    }

  def cancelPreviouslyScheduledMeetupTransitionForLater(meetupId: UUID): Future[Unit] =
    Future.fromTry {
      Try {
        dbScheduler.schedule(CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent(meetupId))
      }
    }

}
