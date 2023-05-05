package io.fitcentive.scheduler.domain.scheduler

import io.fitcentive.scheduler.domain.events.EventMessage

import scala.concurrent.Future

trait BackgroundScheduler {
  def schedule(eventMessage: EventMessage): Future[Unit]
}
