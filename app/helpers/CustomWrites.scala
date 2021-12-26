package helpers

import helpers.RoleImpl.Role
import helpers.Message.{ Error, Failure, Success }
import utils.Utils.enumerationFormatter
import models.{ User, UserProfile }
import play.api.data.{ Form, FormError }
import play.api.data.format.Formatter
import play.api.libs.json.{ JsString, JsValue, Json, Writes }

object CustomWrites {
  implicit val RoleFormatter: Formatter[Role] = enumerationFormatter(RoleImpl)

  implicit object FormErrorWrites extends Writes[FormError] {
    override def writes(o: FormError): JsValue = Json.obj(
      "field" -> Json.toJson(o.key),
      "errorCode" -> Json.toJson(o.message),
    )
  }

  implicit object UserFormWithErrorsWrites extends Writes[Form[User]] {
    override def writes(formWithErrors: Form[User]): JsValue = Json.obj(
      "error" -> Json.toJson(true),
      "message" -> JsString("Error processing request"),
      "reason" -> JsString("Bad or missing data in form"),
      "fields" -> Json.toJson(formWithErrors.errors),
    )
  }

  implicit object UserProfileFormWithErrorsWrites extends Writes[Form[UserProfile]] {
    override def writes(formWithErrors: Form[UserProfile]): JsValue = Json.obj(
      "error" -> Json.toJson(true),
      "message" -> JsString("Error processing request"),
      "reason" -> JsString("Bad or missing data in form"),
      "fields" -> Json.toJson(formWithErrors.errors),
    )
  }

  implicit object AuthFormWithErrorsWrites extends Writes[Form[(String, String)]] {
    override def writes(formWithErrors: Form[(String, String)]): JsValue = Json.obj(
      "error" -> Json.toJson(true),
      "message" -> JsString("Error processing request"),
      "reason" -> JsString("Bad or missing data in form"),
      "fields" -> Json.toJson(formWithErrors.errors),
    )
  }

  implicit object SuccessWrites extends Writes[Success] {
    override def writes(s: Success): JsValue = Json.obj(
      "success" -> Json.toJson(true),
      "message" -> Json.toJson(s.message),
    )
  }

  implicit object ErrorWrites extends Writes[Error] {
    override def writes(s: Error): JsValue = Json.obj(
      "error" -> Json.toJson(true),
      "message" -> Json.toJson(s.message),
    )
  }

  implicit object FailureWrites extends Writes[Failure] {
    override def writes(s: Failure): JsValue = Json.obj(
      "success" -> Json.toJson(false),
      "message" -> Json.toJson(s.message),
    )
  }

  implicit object ServerErrorWrites extends Writes[Throwable] {
    override def writes(s: Throwable): JsValue = Json.obj(
      "error" -> Json.toJson(true),
      "errCode" -> Json.toJson(s.getClass.getName.toLowerCase),
      "message" -> Json.toJson(s.getMessage),
    )
  }

  implicit object RoleWrites extends Writes[Role] {
    override def writes(s: Role): JsValue = JsString(s.toString)
  }

  implicit object UserWrites extends Writes[User] {
    override def writes(u: User): JsValue = Json.obj(
      "user" -> Json.obj(
        "user_id" -> Json.toJson(u.user_id),
        "email" -> Json.toJson(u.email),
        "country" -> Json.toJson(u.country),
        "dob" -> Json.toJson(u.dob),
        "phone" -> Json.toJson(u.phone),
        "Role" -> Json.toJson(u.role),
      )
    )
  }
}
