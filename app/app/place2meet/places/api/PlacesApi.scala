package app.place2meet.places.api

import app.place2meet.places.domain.foursquare.{FourSquareSearchQuery, RefinedFourSquareSearchResult}
import app.place2meet.places.domain.spatial.GeoCircle
import app.place2meet.places.infrastructure.utils.GeoSupport
import app.place2meet.places.services.PlacesService
import app.place2meet.sdk.error.DomainError

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PlacesApi @Inject() (placesService: PlacesService)(implicit ec: ExecutionContext) extends GeoSupport {

  def generateLocationRecommendations(
    userLocations: Seq[GeoCircle]
  ): Future[Either[DomainError, Seq[RefinedFourSquareSearchResult]]] = {
    val intersectionPoints = findGeoCoordinatesOfIntersection(userLocations)
    val discoveryCircle = getApproximateDiscoveryRadius(intersectionPoints)
    val searchQuery = FourSquareSearchQuery(center = discoveryCircle.center, radius = discoveryCircle.radiusInMetres)

    placesService.findPlacesNear(searchQuery).map(_.map(_.results.map(_.toRefined)))
  }

}
