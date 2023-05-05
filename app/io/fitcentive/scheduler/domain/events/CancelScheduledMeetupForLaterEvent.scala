package io.fitcentive.scheduler.domain.events

import com.google.pubsub.v1.PubsubMessage
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import io.fitcentive.sdk.gcp.pubsub.PubSubMessageConverter
import io.fitcentive.sdk.utils.PubSubOps

import java.util.UUID

case class CancelScheduledMeetupForLaterEvent(meetupId: UUID) extends EventMessage

object CancelScheduledMeetupForLaterEvent extends PubSubOps {

  implicit val codec: Codec[CancelScheduledMeetupForLaterEvent] = deriveCodec[CancelScheduledMeetupForLaterEvent]

  implicit val converter: PubSubMessageConverter[CancelScheduledMeetupForLaterEvent] =
    (message: PubsubMessage) => message.decodeUnsafe[CancelScheduledMeetupForLaterEvent]
}
