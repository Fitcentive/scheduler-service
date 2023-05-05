package io.fitcentive.scheduler.infrastructure

import io.fitcentive.registry.events.scheduled.{
  CancelPreviouslyScheduledMeetupReminderForLater,
  ScheduleMeetupReminderForLater
}
import io.fitcentive.scheduler.domain.events.{CancelScheduledMeetupForLaterEvent, ScheduleMeetupReminderForLaterEvent}

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
}
