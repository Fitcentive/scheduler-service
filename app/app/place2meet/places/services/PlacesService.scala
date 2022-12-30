package app.place2meet.places.services

import app.place2meet.places.domain.foursquare.{FourSquareSearchQuery, FourSquareSearchResults}
import app.place2meet.places.infrastructure.rest.FourSquarePlacesService
import app.place2meet.sdk.error.DomainError
import com.google.inject.ImplementedBy

import scala.concurrent.Future

@ImplementedBy(classOf[FourSquarePlacesService])
trait PlacesService {
  def findPlacesNear(query: FourSquareSearchQuery): Future[Either[DomainError, FourSquareSearchResults]]
}
