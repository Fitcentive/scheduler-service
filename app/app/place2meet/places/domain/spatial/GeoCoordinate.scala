package app.place2meet.places.domain.spatial

import play.api.libs.json.{Json, OFormat}

case class GeoCoordinate(latitude: Double, longitude: Double, isRadians: Boolean = false) {
  def toRadians: GeoCoordinate =
    GeoCoordinate(
      latitude = scala.math.toRadians(latitude),
      longitude = scala.math.toRadians(longitude),
      isRadians = true
    )

  def toDegrees: GeoCoordinate =
    GeoCoordinate(latitude = scala.math.toDegrees(latitude), longitude = scala.math.toDegrees(longitude))

  override def toString: String = s"$latitude,$longitude"
}

object GeoCoordinate {
  implicit lazy val format: OFormat[GeoCoordinate] = Json.format[GeoCoordinate]
}
