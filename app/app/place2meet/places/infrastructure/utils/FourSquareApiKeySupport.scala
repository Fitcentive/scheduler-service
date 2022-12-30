package app.place2meet.places.infrastructure.utils

import app.place2meet.places.services.SettingsService
import play.api.libs.ws.WSRequest

trait FourSquareApiKeySupport {

  implicit class AuthorizationHeader(wsRequest: WSRequest) {
    def addAuthorizationHeader(settingsService: SettingsService): WSRequest =
      wsRequest.addHttpHeaders("Authorization" -> settingsService.fourSquareConfig.apiKey)
  }
}
