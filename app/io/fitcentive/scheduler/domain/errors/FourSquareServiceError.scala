package io.fitcentive.scheduler.domain.errors

import io.fitcentive.sdk.error.DomainError

import java.util.UUID

case class FourSquareServiceError(reason: String) extends DomainError {
  override def code: UUID = UUID.fromString("eee55ea8-6431-47e0-a1de-f35f82f310e4")
}
