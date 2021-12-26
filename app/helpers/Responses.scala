package helpers

import CustomWrites._
import Message.{Error, Failure, SessionExpired, SessionNotFound, Success}
import models.User
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Result, Results}

object Responses {
  def successResponse[A <: Success](message: A, data: Option[User]): JsValue = {
    Json.obj(
      "res"  -> SuccessWrites.writes(message),
      "data" -> Json.toJson(data)
    )
  }

  def errorResponse[A <: Error](message: A): JsValue = {
    ErrorWrites.writes(message)
  }

  def failedResponse[A <: Failure](message: A): JsValue = {
    FailureWrites.writes(message)
  }

  def serverErrorResponse[A <: Throwable](error: A): JsValue = {
    ServerErrorWrites.writes(error)
  }

  def sessionExpired(): Result = Results.Unauthorized(failedResponse(SessionExpired))

  def sessionNotFound(): Result = Results.Unauthorized(failedResponse(SessionNotFound))
}
