package io.fitcentive.scheduler.infrastructure.utils

import io.fitcentive.scheduler.services.SettingsService
import play.api.libs.ws.WSRequest

trait ServiceSecretSupport {

  implicit class ServiceSecretHeaders(wsRequest: WSRequest) {
    def addServiceSecret(settingsService: SettingsService): WSRequest =
      wsRequest.addHttpHeaders("Service-Secret" -> settingsService.secretConfig.serviceSecret)
  }
}
