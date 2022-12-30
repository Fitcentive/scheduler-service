package app.place2meet.places.domain.foursquare

import play.api.libs.json.{Json, OFormat}

import java.time.Instant

case class RefinedFourSquareSearchResult(
  fsqId: String,
  categories: Seq[RefinedFourSquareSearchCategory],
  distance: Long,
  geocodes: FourSquareGeoCodes,
  link: String,
  name: String,
  price: Option[Long],
  rating: Option[Double],
  tel: Option[String],
  website: Option[String],
  socialMedia: Option[RefinedFourSquareSocialMedia],
  hours: RefinedFourSquareHours,
  photos: Option[Seq[RefinedFourSquarePhoto]]
)
object RefinedFourSquareSearchResult {
  implicit lazy val format: OFormat[RefinedFourSquareSearchResult] = Json.format[RefinedFourSquareSearchResult]
}

case class RefinedFourSquareSearchCategory(id: Long, name: String, iconUrl: String)
object RefinedFourSquareSearchCategory {
  implicit lazy val format: OFormat[RefinedFourSquareSearchCategory] = Json.format[RefinedFourSquareSearchCategory]
}

case class RefinedFourSquarePhoto(id: String, createdAt: Instant, url: String, width: Long, height: Long)
object RefinedFourSquarePhoto {
  implicit lazy val format: OFormat[RefinedFourSquarePhoto] = Json.format[RefinedFourSquarePhoto]
}

case class RefinedFourSquareHours(
  display: String,
  isLocalHoliday: Boolean,
  openNow: Boolean,
  regular: Seq[RefinedFourSquareRegularHour],
  seasonal: Seq[RefinedFourSquareRegularHour]
)
object RefinedFourSquareHours {
  implicit lazy val format: OFormat[RefinedFourSquareHours] = Json.format[RefinedFourSquareHours]
}

case class RefinedFourSquareRegularHour(close: String, day: String, open: String)
object RefinedFourSquareRegularHour {
  implicit lazy val format: OFormat[RefinedFourSquareRegularHour] = Json.format[RefinedFourSquareRegularHour]
}

case class RefinedFourSquareSocialMedia(
  facebookUrl: Option[String],
  instagramUrl: Option[String],
  twitterUrl: Option[String]
)
object RefinedFourSquareSocialMedia {
  implicit lazy val format: OFormat[RefinedFourSquareSocialMedia] = Json.format[RefinedFourSquareSocialMedia]
}
