package app.place2meet.places.domain.foursquare

import app.place2meet.places.domain.spatial.GeoCoordinate

case class FourSquareSearchQuery(
  center: GeoCoordinate,
  radius: Long,
  query: String = "",
  fieldsToReturn: String = FourSquareSearchQuery.defaultFieldsToReturn,
  sort: String = FourSquareSearchQuery.defaultSort,
  limit: Int = FourSquareSearchQuery.defaultLimit,
  sessionToken: Option[String] = None
)

object FourSquareSearchQuery {
  val defaultSort: String = "DISTANCE"
  val defaultLimit: Int = 50
  val defaultFieldsToReturn: String =
    "fsq_id,name,geocodes,categories,distance,link,description,tel,fax,email,website,social_media,hours,rating,price,photos,tastes,features"
}
