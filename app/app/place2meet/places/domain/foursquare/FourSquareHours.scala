package app.place2meet.places.domain.foursquare

import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration, OFormat}

case class FourSquareRegularHour(close: String, day: Long, open: String) {

  final val dayMap: Map[Long, String] = Map(
    1L -> "Monday",
    2L -> "Tuesday",
    3L -> "Wednesday",
    4L -> "Thursday",
    5L -> "Friday",
    6L -> "Saturday",
    7L -> "Sunday",
  )

  def toRefined: RefinedFourSquareRegularHour =
    RefinedFourSquareRegularHour(close = close.patch(2, ":", 0), day = dayMap(day), open = open.patch(2, ":", 0))
}

object FourSquareRegularHour {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit lazy val format: OFormat[FourSquareRegularHour] = Json.format[FourSquareRegularHour]
}

case class FourSquareSeasonalHour(
  closed: Boolean,
  endDate: String,
  startDate: String,
  hours: Seq[FourSquareRegularHour]
)

object FourSquareSeasonalHour {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit lazy val format: OFormat[FourSquareSeasonalHour] = Json.format[FourSquareSeasonalHour]
}

case class FourSquareHours(
  display: String,
  isLocalHoliday: Boolean,
  openNow: Boolean,
  regular: Seq[FourSquareRegularHour],
  seasonal: Seq[FourSquareSeasonalHour]
) {
  def toRefined: RefinedFourSquareHours =
    RefinedFourSquareHours(
      display = display,
      isLocalHoliday = isLocalHoliday,
      openNow = openNow,
      regular = regular.map(_.toRefined),
      seasonal = regular.map(_.toRefined)
    )
}

object FourSquareHours {
  implicit val config = JsonConfiguration(SnakeCase)
  implicit lazy val format: OFormat[FourSquareHours] = Json.format[FourSquareHours]
}
