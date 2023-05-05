package io.fitcentive.scheduler.domain.events

import com.google.pubsub.v1.PubsubMessage
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import io.fitcentive.sdk.gcp.pubsub.PubSubMessageConverter
import io.fitcentive.sdk.utils.PubSubOps

import java.util.UUID

case class ScheduleMeetupReminderForLaterEvent(meetupId: UUID, scheduleReminderAtMillis: Long) extends EventMessage

object ScheduleMeetupReminderForLater extends PubSubOps {

  implicit val codec: Codec[ScheduleMeetupReminderForLaterEvent] = deriveCodec[ScheduleMeetupReminderForLaterEvent]

  implicit val converter: PubSubMessageConverter[ScheduleMeetupReminderForLaterEvent] =
    (message: PubsubMessage) => message.decodeUnsafe[ScheduleMeetupReminderForLaterEvent]
}
