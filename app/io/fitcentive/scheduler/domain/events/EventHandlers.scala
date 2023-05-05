package io.fitcentive.scheduler.domain.events

import scala.concurrent.Future

trait EventHandlers {
  def handleEvent(event: EventMessage): Future[Unit]
}
