package services

import com.mohiva.play.silhouette.api.services.IdentityService
import scala.concurrent.Future
import models.User
import helpers.ResultSet
import models.UserProfile

/**
* Handles actions to users.
*/
trait UserService extends IdentityService[User] {

 /**
  * Saves a user.
  *
  * @param user The user to save.
  * @return The saved user.
  */
 def save(user: User): ResultSet[User]

 /**
  * Updates a user.
  *
  * @param user The user to update.
  * @return The updated user.
  */
 def update(user: User, newProfile: UserProfile): ResultSet[User]
}