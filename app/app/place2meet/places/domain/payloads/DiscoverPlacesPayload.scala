package app.place2meet.places.domain.payloads

import app.place2meet.places.domain.spatial.GeoCircle
import play.api.libs.json.{Json, OFormat}

case class DiscoverPlacesPayload(userLocations: Seq[GeoCircle])

object DiscoverPlacesPayload {
  implicit lazy val format: OFormat[DiscoverPlacesPayload] = Json.format[DiscoverPlacesPayload]
}
