package app.place2meet.places.controllers

import app.place2meet.places.api.PlacesApi
import app.place2meet.places.domain.payloads.DiscoverPlacesPayload
import app.place2meet.places.infrastructure.utils.ServerErrorHandler
import app.place2meet.sdk.play.UserAuthAction
import app.place2meet.sdk.utils.PlayControllerOps
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class PlacesController @Inject() (placesApi: PlacesApi, userAuthAction: UserAuthAction, cc: ControllerComponents)(
  implicit exec: ExecutionContext
) extends AbstractController(cc)
  with PlayControllerOps
  with ServerErrorHandler {

  // todo - must be blocked by user auth
  // or maybe we just need 2 routes? One fo
  def fetchLocations: Action[AnyContent] =
    Action.async { implicit request =>
      validateJson[DiscoverPlacesPayload](request.body.asJson) { payload =>
        placesApi
          .generateLocationRecommendations(payload.userLocations)
          .map(handleEitherResult(_)(locations => Ok(Json.toJson(locations))))
          .recover(resultErrorAsyncHandler)
      }
    }

}
