package io.fitcentive.scheduler.domain.events

import com.google.pubsub.v1.PubsubMessage
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import io.fitcentive.sdk.gcp.pubsub.PubSubMessageConverter
import io.fitcentive.sdk.utils.PubSubOps

import java.util.UUID

sealed trait EventMessage

object EventMessage {
  implicit val codec: Codec[EventMessage] = deriveCodec[EventMessage]
}

case class ScheduleMeetupReminderForLaterEvent(meetupId: UUID, scheduleReminderAtMillis: Long) extends EventMessage

object ScheduleMeetupReminderForLaterEvent extends PubSubOps {

  implicit val codec: Codec[ScheduleMeetupReminderForLaterEvent] = deriveCodec[ScheduleMeetupReminderForLaterEvent]

  implicit val converter: PubSubMessageConverter[ScheduleMeetupReminderForLaterEvent] =
    (message: PubsubMessage) => message.decodeUnsafe[ScheduleMeetupReminderForLaterEvent]
}

case class CancelScheduledMeetupForLaterEvent(meetupId: UUID) extends EventMessage

object CancelScheduledMeetupForLaterEvent extends PubSubOps {

  implicit val codec: Codec[CancelScheduledMeetupForLaterEvent] = deriveCodec[CancelScheduledMeetupForLaterEvent]

  implicit val converter: PubSubMessageConverter[CancelScheduledMeetupForLaterEvent] =
    (message: PubsubMessage) => message.decodeUnsafe[CancelScheduledMeetupForLaterEvent]
}

//----------------------------------------

case class ScheduleMeetupStateTransitionTimeForLaterEvent(meetupId: UUID, scheduleTransitionAtMillis: Long)
  extends EventMessage

object ScheduleMeetupStateTransitionTimeForLaterEvent extends PubSubOps {

  implicit val codec: Codec[ScheduleMeetupStateTransitionTimeForLaterEvent] =
    deriveCodec[ScheduleMeetupStateTransitionTimeForLaterEvent]

  implicit val converter: PubSubMessageConverter[ScheduleMeetupStateTransitionTimeForLaterEvent] =
    (message: PubsubMessage) => message.decodeUnsafe[ScheduleMeetupStateTransitionTimeForLaterEvent]
}

case class ScheduledMeetupStateTransitionEvent(meetupId: UUID) extends EventMessage

object ScheduledMeetupStateTransitionEvent extends PubSubOps {
  implicit val codec: Codec[ScheduledMeetupStateTransitionEvent] = deriveCodec[ScheduledMeetupStateTransitionEvent]

  implicit val converter: PubSubMessageConverter[ScheduledMeetupStateTransitionEvent] =
    (message: PubsubMessage) => message.decodeUnsafe[ScheduledMeetupStateTransitionEvent]
}

case class CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent(meetupId: UUID) extends EventMessage

object CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent extends PubSubOps {
  implicit val codec: Codec[CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent] =
    deriveCodec[CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent]

  implicit val converter: PubSubMessageConverter[CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent] =
    (message: PubsubMessage) => message.decodeUnsafe[CancelPreviouslyScheduledMeetupStateTransitionForLaterEvent]
}
