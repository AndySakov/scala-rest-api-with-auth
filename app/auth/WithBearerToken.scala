package auth

import com.mohiva.play.silhouette.api.{ Authenticator, Authorization }
import models.User
import play.api.mvc.Request

import scala.concurrent.Future
import com.mohiva.play.silhouette.impl.authenticators.BearerTokenAuthenticator

/**
 * Grants only access if a user has authenticated with the given provider.
 *
 * @param provider The provider ID the user must authenticated with.
 * @tparam A The type of the authenticator.
 */
case class WithBearerToken(provider: String) extends Authorization[User, BearerTokenAuthenticator] {

  /**
   * Indicates if a user is authorized to access an action.
   *
   * @param user The usr object.
   * @param authenticator The authenticator instance.
   * @param request The current request.
   * @tparam B The type of the request body.
   * @return True if the user is authorized, false otherwise.
   */
  override def isAuthorized[B](user: User, authenticator: BearerTokenAuthenticator)(implicit request: Request[B]): Future[Boolean] = {
    Future.successful(user.loginInfo.providerID == provider)
  }
}