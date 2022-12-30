package app.place2meet.places.infrastructure.rest

import app.place2meet.places.domain.errors.FourSquareServiceError
import app.place2meet.places.domain.foursquare.{FourSquareSearchQuery, FourSquareSearchResults}
import app.place2meet.places.infrastructure.utils.FourSquareApiKeySupport
import app.place2meet.places.services.{PlacesService, SettingsService}
import app.place2meet.sdk.error.DomainError
import play.api.http.Status
import play.api.libs.ws.WSClient

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FourSquarePlacesService @Inject() (wsClient: WSClient, settingsService: SettingsService)(implicit
  ec: ExecutionContext
) extends PlacesService
  with FourSquareApiKeySupport {

  val baseUrl: String = settingsService.fourSquareConfig.baseUrl

  override def findPlacesNear(query: FourSquareSearchQuery): Future[Either[DomainError, FourSquareSearchResults]] = {
    wsClient
      .url(s"$baseUrl/places/search")
      .addQueryStringParameters(
        "ll" -> query.center.toString,
        "sort" -> query.sort,
        "radius" -> query.radius.toString,
        "fields" -> query.fieldsToReturn,
      )
      .addHttpHeaders("Content-Type" -> "application/json")
      .addAuthorizationHeader(settingsService)
      .get()
      .map { response =>
        response.status match {
          case Status.OK =>
            println(s"THE RESPONSE IS: ${response.json.toString()}")
            println("============================================")
            Right(response.json.as[FourSquareSearchResults])
          case status => Left(FourSquareServiceError(s"Bad response from fourSquare API: $status"))
        }
      }
  }
}
