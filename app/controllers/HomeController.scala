package controllers

import java.time.{ LocalDate, LocalDateTime }

import helpers.Message
import models.daos.UserDAO
import javax.inject._
import models.User
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/** This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject() (
    users: UserDAO,
    val controllerComponents: ControllerComponents,
  ) extends BaseController {

  
}
