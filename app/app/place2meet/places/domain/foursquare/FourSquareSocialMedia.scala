package app.place2meet.places.domain.foursquare

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration, OFormat}

case class FourSquareSocialMedia(facebookId: Option[String], instagram: Option[String], twitter: Option[String]) {
  def toRefined: RefinedFourSquareSocialMedia =
    RefinedFourSquareSocialMedia(
      facebookUrl = facebookId.map(id => s"https://facebook.com/$id"),
      instagramUrl = instagram.map(handle => s"https://instagram.com/$handle"),
      twitterUrl = twitter.map(handle => s"https://twitter.com/$handle")
    )
}

object FourSquareSocialMedia {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit lazy val format: OFormat[FourSquareSocialMedia] = Json.format[FourSquareSocialMedia]
}
