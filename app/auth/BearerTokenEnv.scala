package auth

import models.User
import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators.BearerTokenAuthenticator

trait BearerTokenEnv extends Env {
  type I = User
  type A = BearerTokenAuthenticator
}