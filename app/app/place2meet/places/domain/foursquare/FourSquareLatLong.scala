package app.place2meet.places.domain.foursquare

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration, OFormat}

case class FourSquareLatLong(latitude: Double, longitude: Double)

object FourSquareLatLong {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit lazy val format: OFormat[FourSquareLatLong] = Json.format[FourSquareLatLong]
}
