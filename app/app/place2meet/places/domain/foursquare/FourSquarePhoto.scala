package app.place2meet.places.domain.foursquare

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration, OFormat}

import java.time.Instant

case class FourSquarePhoto(
  id: String,
  createdAt: Instant,
  prefix: String,
  suffix: String,
  width: Long,
  height: Long,
  classifications: Option[Seq[String]]
) {
  def toRefined: RefinedFourSquarePhoto =
    RefinedFourSquarePhoto(
      id = id,
      createdAt = createdAt,
      url = s"${prefix}original$suffix",
      width = width,
      height = height
    )
}

object FourSquarePhoto {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit lazy val format: OFormat[FourSquarePhoto] = Json.format[FourSquarePhoto]
}
