package app.place2meet.places.domain.foursquare

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration, OFormat}

// features - includes more personalized metadata, come back to it later
case class FourSquareSearchResult(
  fsqId: String,
  categories: Seq[FourSquareSearchCategory],
  distance: Long,
  geocodes: FourSquareGeoCodes,
  link: String,
  name: String,
  price: Option[Long],
  rating: Option[Double],
  tel: Option[String],
  website: Option[String],
  socialMedia: Option[FourSquareSocialMedia],
  hours: FourSquareHours,
  photos: Option[Seq[FourSquarePhoto]]
) {
  def toRefined: RefinedFourSquareSearchResult =
    RefinedFourSquareSearchResult(
      fsqId = fsqId,
      categories = categories.map(_.toRefined),
      distance = distance,
      geocodes = geocodes,
      link = link,
      name = name,
      price = price,
      rating = rating,
      tel = tel,
      website = website,
      socialMedia = socialMedia.map(_.toRefined),
      hours = hours.toRefined,
      photos = photos.map(_.map(_.toRefined))
    )
}

object FourSquareSearchResult {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit lazy val format: OFormat[FourSquareSearchResult] = Json.format[FourSquareSearchResult]
}

case class FourSquareSearchResults(results: Seq[FourSquareSearchResult])

object FourSquareSearchResults {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit lazy val format: OFormat[FourSquareSearchResults] = Json.format[FourSquareSearchResults]
}
