package auth

import helpers.RoleImpl.Role
import models.User
import com.mohiva.play.silhouette.api.Authorization
import play.api.mvc.Request
import scala.concurrent.Future

case class WithRole(role: Role) extends Authorization[User, BearerTokenEnv#A] {
  override def isAuthorized[B](user: User, authenticator: BearerTokenEnv#A)(implicit request: Request[B]): Future[Boolean] =
    Future.successful(user.role == role)
}
