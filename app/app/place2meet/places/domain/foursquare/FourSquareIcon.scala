package app.place2meet.places.domain.foursquare

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration, OFormat}

// To obtain an icon, the only acceptable size values are one of: 32, 44, 64, 88, or 120 (pixels)
// https://location.foursquare.com/developer/reference/places-photos-guide#category-icons
case class FourSquareIcon(prefix: String, suffix: String) {
  def url(size: Int): String = s"$prefix$size$suffix"
}

object FourSquareIcon {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit lazy val format: OFormat[FourSquareIcon] = Json.format[FourSquareIcon]
  val defaultIconSize = 64 // pixels
}
