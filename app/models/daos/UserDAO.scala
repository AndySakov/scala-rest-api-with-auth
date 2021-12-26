package models.daos

import helpers.ResultSet
import models.User
import com.mohiva.play.silhouette.api.LoginInfo
import models.UserProfile

trait UserDAO {

  /**
   * Finds a user by its login info.
   *
   * @param loginInfo The login info of the user to find.
   * @return The found user or None if no user for the given login info could be found.
   */
  def find(loginInfo: LoginInfo): ResultSet[User]

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
   * @return The saved user.
   */
  def update(user: User, newProfile: UserProfile): ResultSet[User]
}