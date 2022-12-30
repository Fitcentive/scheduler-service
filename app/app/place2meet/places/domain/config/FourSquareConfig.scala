package app.place2meet.places.domain.config

import com.typesafe.config.Config

case class FourSquareConfig(baseUrl: String, apiKey: String)

object FourSquareConfig {
  def fromConfig(config: Config): FourSquareConfig =
    FourSquareConfig(baseUrl = config.getString("base-url"), apiKey = config.getString("api-key"))
}
