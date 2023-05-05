package io.fitcentive.scheduler.domain.availability

import play.api.libs.json.{Json, OFormat}

import java.time.Instant
import java.util.UUID

case class Availability(
  id: UUID,
  meetupId: UUID,
  userId: UUID,
  availabilityStart: Instant,
  availabilityEnd: Instant,
  createdAt: Instant,
  updatedAt: Instant
)

object Availability {
  implicit lazy val format: OFormat[Availability] = Json.format[Availability]

  // If id exists, then update - Else create
  case class Upsert(id: Option[UUID], availabilityStart: Instant, availabilityEnd: Instant)
  object Upsert {
    implicit lazy val format: OFormat[Upsert] = Json.format[Upsert]
  }
}
