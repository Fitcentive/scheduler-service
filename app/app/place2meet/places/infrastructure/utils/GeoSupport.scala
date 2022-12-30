package app.place2meet.places.infrastructure.utils

import app.place2meet.places.domain.spatial.{GeoCircle, GeoCoordinate}

trait GeoSupport {

  // https://stackoverflow.com/questions/28542133/i-cannot-get-the-same-accuracy-as-google-maps-when-it-comes-to-distance/28543001#28543001
  def doesCoordinateWithinCircle(geoCoordinate: GeoCoordinate, geoCircle: GeoCircle): Boolean = {
    val dLat = scala.math.toRadians(geoCoordinate.latitude - geoCircle.center.latitude)
    val dLon = scala.math.toRadians(geoCoordinate.longitude - geoCircle.center.longitude)
    val a =
      0.5 - scala.math.cos(dLat) / 2 + scala.math.cos(scala.math.toRadians(geoCoordinate.latitude)) * scala.math.cos(
        scala.math.toRadians(geoCircle.center.latitude)
      ) * (1 - scala.math.cos(dLon)) / 2
    val dist = scala.math.round(GeoCircle.earthRadiusKm * 1000 * 2 * scala.math.asin(scala.math.sqrt(a)))
    dist <= geoCircle.radiusInMetres
  }

  def findCenterCoordinate(coordinates: Seq[GeoCoordinate]): GeoCoordinate = {
    val xTotal = coordinates
      .map(_.toRadians)
      .foldLeft(0d)((acc, coord) => acc + (scala.math.cos(coord.latitude) * scala.math.cos(coord.longitude)))
    val yTotal = coordinates
      .map(_.toRadians)
      .foldLeft(0d)((acc, coord) => acc + (scala.math.cos(coord.latitude) * scala.math.sin(coord.longitude)))
    val zTotal = coordinates.map(_.toRadians).foldLeft(0d)((acc, coord) => acc + scala.math.sin(coord.latitude))

    val x = xTotal / coordinates.length
    val y = yTotal / coordinates.length
    val z = zTotal / coordinates.length

    val centralLongitude = scala.math.atan2(y, x)
    val centralSquareRoot = scala.math.sqrt(x * x + y * y)
    val centralLatitude = scala.math.atan2(z, centralSquareRoot)

    GeoCoordinate(latitude = centralLatitude, longitude = centralLongitude, isRadians = true)
  }

  def distanceBetweenPoints(p1: GeoCoordinate, p2: GeoCoordinate): Double = { // The math module contains a function
    val p1r = p1.toRadians
    val p2r = p2.toRadians
    // Haversine formula
    val dlon = p2r.longitude - p1r.longitude
    val dlat = p2r.latitude - p1r.latitude
    val a = Math
      .pow(Math.sin(dlat / 2), 2) + Math.cos(p1r.latitude) * Math.cos(p2r.latitude) * Math.pow(Math.sin(dlon / 2), 2)
    val c = 2 * Math.asin(Math.sqrt(a))
    c * GeoCircle.earthRadiusKm
  }

  def findAverageDistanceInMetresFromPoints(center: GeoCoordinate, points: Seq[GeoCoordinate]): Long = {
    val distances = points.map(pt => distanceBetweenPoints(center, pt))
    ((distances.sum / points.length) * 1000).toLong
  }

  // Generates a circle of appropriate radius to approximate area covered by points described by `intersectionPoints`
  def getApproximateDiscoveryRadius(intersectionPoints: Seq[GeoCoordinate]): GeoCircle = {
    val y_min = intersectionPoints.minBy(_.latitude)
    val y_max = intersectionPoints.maxBy(_.latitude)
    val x_min = intersectionPoints.minBy(_.longitude)
    val x_max = intersectionPoints.maxBy(_.longitude)

    // Now find the centre of all the points
    // Note - this implementation does not account for 0/360 degree overlap
    val center = findCenterCoordinate(intersectionPoints).toDegrees
    val radius = findAverageDistanceInMetresFromPoints(center, Seq(x_min, x_max, y_min, y_max))
    GeoCircle(center = center, radiusInMetres = radius)
  }

  // Gets points of intersection between all provided GeoCircles
  def findGeoCoordinatesOfIntersection(circlesInQuestion: Seq[GeoCircle]): Seq[GeoCoordinate] = {
    var i = 0
    var intersectionPointsSuperSet: Seq[GeoCoordinate] = Seq.empty
    val max = circlesInQuestion.length
    while (i < max) {
      val currentCircle = circlesInQuestion(i)
      val otherCircles = circlesInQuestion.slice(0, i) ++ circlesInQuestion.slice(i + 1, max)
      // Now reduce list of points from every other circle
      val validPoints = otherCircles.foldLeft(Seq.empty[GeoCoordinate]) { (accumulator, geoCircle) =>
        accumulator ++ geoCircle.circleOuterCoordinates.foldLeft(Seq.empty[GeoCoordinate]) { (acc, coordinate) =>
          if (doesCoordinateWithinCircle(coordinate, currentCircle)) acc :+ coordinate
          else acc
        }
      }
      intersectionPointsSuperSet = intersectionPointsSuperSet ++ validPoints
      i += 1
    }

    // Now filter points once more, buy only getting points that exist in ALL circles
    intersectionPointsSuperSet.filter { point =>
      circlesInQuestion.forall { geoCircle =>
        doesCoordinateWithinCircle(point, geoCircle)
      }
    }

  }
}
