package app.place2meet.places.domain.foursquare

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration, OFormat}

case class FourSquareSearchCategory(id: Long, name: String, icon: FourSquareIcon) {
  def toRefined: RefinedFourSquareSearchCategory =
    RefinedFourSquareSearchCategory(id = id, name = name, iconUrl = s"${icon.url(FourSquareIcon.defaultIconSize)}")
}

object FourSquareSearchCategory {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit lazy val format: OFormat[FourSquareSearchCategory] = Json.format[FourSquareSearchCategory]
}
