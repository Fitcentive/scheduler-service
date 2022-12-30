package app.place2meet.places.infrastructure.utils

import app.place2meet.places.domain.errors.FourSquareServiceError
import app.place2meet.sdk.error.DomainError
import app.place2meet.sdk.logging.AppLogger
import app.place2meet.sdk.utils.DomainErrorHandler
import play.api.mvc.Result
import play.api.mvc.Results.InternalServerError

trait ServerErrorHandler extends DomainErrorHandler with AppLogger {

  override def resultErrorAsyncHandler: PartialFunction[Throwable, Result] = {
    case e: Exception =>
      logError(s"${e.getMessage}", e)
      InternalServerError(e.getMessage)
  }

  override def domainErrorHandler: PartialFunction[DomainError, Result] = {
    case FourSquareServiceError(reason) => InternalServerError(s"FourSquare API error: $reason")
    case _                              => InternalServerError("Unexpected error occurred ")
  }

}
