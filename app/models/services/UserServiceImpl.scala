package models.services

import com.mohiva.play.silhouette.api.LoginInfo
import javax.inject.Inject
import models.User
import models.daos.UserDAO

import scala.concurrent.{ ExecutionContext, Future }
import services.UserService
import helpers.ResultSet
import models.UserProfile

/**
 * Handles actions to users.
 *
 * @param userDAO The user DAO implementation.
 * @param ex      The execution context.
 */
class UserServiceImpl @Inject() (userDAO: UserDAO)(implicit ex: ExecutionContext) extends UserService {

  /**
   * Retrieves a user that matches the specified login info.
   *
   * @param loginInfo The login info to retrieve a user.
   * @return The retrieved user or None if no user could be retrieved for the given login info.
   */
  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = Future.successful(userDAO.find(loginInfo).result.data)

  /**
   * Saves a user.
   *
   * @param user The user to save.
   * @return The saved user.
   */
  override def save(user: User): ResultSet[User] = userDAO.save(user)

  /**
   * Updates a user.
   *
   * @param user The user to update.
   * @return The updated user.
   */
  override def update(user: User, newProfile: UserProfile): ResultSet[User] = userDAO.update(user, newProfile)
}