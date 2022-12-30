package app.place2meet.places.domain.foursquare

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration, OFormat}

case class FourSquareGeoCodes(main: FourSquareLatLong)

object FourSquareGeoCodes {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit lazy val format: OFormat[FourSquareGeoCodes] = Json.format[FourSquareGeoCodes]
}
