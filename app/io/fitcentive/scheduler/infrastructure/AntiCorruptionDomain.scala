package io.fitcentive.scheduler.infrastructure

import io.fitcentive.registry.events.scheduled.reminder.{
  CancelPreviouslyScheduledMeetupReminderForLater,
  ScheduleMeetupReminderForLater
}
import io.fitcentive.registry.events.scheduled.transition.{
  CancelPreviouslyScheduledMeetupStateTransitionForLater,
  ScheduleMeetupStateTransitionTimeForLater,
  ScheduledMeetupStateTransition
}
import io.fitcentive.scheduler.domain.events._

trait AntiCorruptionDomain {

  implicit class ScheduleMeetupReminderForLaterEvent2Domain(event: ScheduleMeetupReminderForLater) {
    def toDomain: ScheduleMeetupReminderForLaterEvent =
      ScheduleMeetupReminderForLaterEvent(event.meetupId, event.scheduleReminderAtMillis)
  }

  implicit class CancelScheduledMeetupReminderForLaterEvent2Domain(
    event: CancelPreviouslyScheduledMeetupReminderForLater
  ) {
    def toDomain: CancelScheduledMeetupForLaterEvent = CancelScheduledMeetupForLaterEvent(event.meetupId)
  }

  implicit class ScheduleMeetupStateTransitionTimeForLaterEvent2Domain(
    event: ScheduleMeetupStateTransitionTimeForLater
  ) {
    def toDomain: ScheduleMeetupStateTransitionTimeForLaterEvent =
      ScheduleMeetupStateTransitionTimeForLaterEvent(event.meetupId, event.scheduleTransitionAtMillis)
  }

  implicit class CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent2Domain(
    event: CancelPreviouslyScheduledMeetupStateTransitionForLater
  ) {
    def toDomain: CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent =
      CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent(event.meetupId)
  }

  implicit class ScheduledMeetupStateTransitionEvent2Domain(event: ScheduledMeetupStateTransition) {
    def toDomain: ScheduledMeetupStateTransitionEvent =
      ScheduledMeetupStateTransitionEvent(event.meetupId)
  }
}
