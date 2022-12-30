package app.place2meet.places.domain.spatial

import play.api.libs.json.{Json, OFormat}

case class GeoCircle(center: GeoCoordinate, radiusInMetres: Long) {
  import GeoCircle._

  val circleOuterCoordinates: Seq[GeoCoordinate] = {
    val bearings = Range.inclusive(0, 360 - (360 / numberOfPoints), 360 / numberOfPoints)
    bearings.map { bearing =>
      destinationPoint(center.latitude, center.longitude, bearing, radiusInMetres)
    }
  }
}

object GeoCircle {
  val numberOfPoints: Int = 360
  val earthRadiusKm: Int = 6371
  val pi: Double = java.lang.Math.PI

  // https://stackoverflow.com/questions/50133937/how-to-get-n-number-of-coordinates-around-center-coordinate-and-radius
  def destinationPoint(lat: Double, lng: Double, bearing: Double, distanceInMeters: Long): GeoCoordinate = {

    val dist = (distanceInMeters.toDouble / 1000) / earthRadiusKm.toDouble
    val brng = math.toRadians(bearing)
    val lat1 = math.toRadians(lat)
    val lon1 = math.toRadians(lng)

    val lat2 =
      math.asin((math.sin(lat1) * math.cos(dist)) + (math.cos(lat1) * math.sin(dist) * math.cos(brng)))

    val lon2 = lon1 + math.atan2(
      math.sin(brng) * math.sin(dist) * math.cos(lat1),
      math.cos(dist) - math.sin(lat1) * math.sin(lat2)
    )

    val lon2_2 = ((lon2 + 3 * pi) % (2 * pi)) - pi

    val finalLat2 = math.toDegrees(lat2)
    val finalLon2 = math.toDegrees(lon2_2)

    GeoCoordinate(finalLat2, finalLon2)
  }

  implicit lazy val format: OFormat[GeoCircle] = Json.format[GeoCircle]
}
