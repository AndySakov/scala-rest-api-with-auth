package models.daos

import helpers.RoleImpl._
import helpers.Message._
import helpers._
import utils.Utils._
import models.{ User, UserProfile }
import models.tables.UsersTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import java.sql.{ SQLIntegrityConstraintViolationException, Timestamp }
import java.time.LocalDate
import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.language.postfixOps
import scala.util.{ Failure, Success, Try }
import io.sentry.Sentry
import java.util.UUID
import com.mohiva.play.silhouette.api.LoginInfo

class UserDAOImpl @Inject() (
    val dbConfigProvider: DatabaseConfigProvider
  )(implicit
    executionContext: ExecutionContext
  ) extends UserDAO {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.profile.api._

  // noinspection TypeAnnotation
  implicit val RoleMapper =
    MappedColumnType.base[Role, String](_.toString, RoleImpl.withName)

  val Users = TableQuery[UsersTable]

  /** Function to create a new user
    * @param newbie
    *   the user to create
    * @return
    *   a future with the result of the operation
    */
  override def save(newbie: User): ResultSet[User] =
    Try(
      exec(db.run(Users += newbie.copy(password = encrypt(newbie.password))))
    ) match {
      case Failure(exception) =>
        val message: Message = exception match {
          case _: SQLIntegrityConstraintViolationException =>
            DuplicateUserEntry
          case other: Exception =>
            Sentry.captureException(other)
            UnknownFailure
        }
        ResultSet(ResultTypeImpl.FAILURE, Result(message, None))
      case Success(_) =>
        ResultSet(ResultTypeImpl.SUCCESS, Result(UserCreateSuccessful, Some(newbie)))
    }

  /** Function to update a users profile
    * @param user_id
    *   the unique id of the user to update
    * @param updated_profile
    *   the new detail
    * @return
    *   a future with unit
    */
  override def update(user: User, newProfile: UserProfile): ResultSet[User] =
    getUser(user.user_id) match {
      case None => ResultSet(ResultTypeImpl.FAILURE, Result(UserNotFound, None))
      case Some(user) =>
        val query = Users
          .filter(x => x.user_id === user.user_id)
          .map(user => (user.email, user.name, user.phone).mapTo[UserProfile])
          .update(newProfile)
        val result = exec(
          db.run(query andThen Users.filter(_.user_id === user.user_id).result)
        ).headOption
        ResultSet(ResultTypeImpl.SUCCESS, Result(UserUpdateSuccessful, result))
    }

  /** Function to select a user entry in the database
    * @param email
    *   the username of the user to select
    * @param pass
    *   the password of the user to select
    * @return
    *   a future with a sequence containing the user if it exists
    */
  override def find(loginInfo: LoginInfo): ResultSet[User] =
    exec(db.run(Users.filter(v => v.email === loginInfo.providerKey).result) map { result =>
      if (result.nonEmpty)
        ResultSet(ResultTypeImpl.SUCCESS, Result(UserAuthSuccessful, result.headOption))
      else
        ResultSet(ResultTypeImpl.FAILURE, Result(UserNotFound, None))
    })

  /** Function to select a user entry in the database
    * @param user_id
    *   the unique id of the user to select
    * @return
    *   a future containing either a boolean or a user
    */
  private def getUser(user_id: UUID): Option[User] =
    exec(db.run(Users.filter(v => v.user_id === user_id).result) map {
      case result: Seq[UsersTable#TableElementType] => result.headOption
      case _ => None
    })

  /** Function to delete a user entry from the database
    * @param user_id
    *   the unique id of the user to delete
    * @return
    *   a future with unit
    */
  def deleteUser(user_id: UUID): ResultSet[Boolean] =
    getUser(user_id) match {
      case Some(user) =>
        exec(db.run {
          Users.filter(_.user_id === user.user_id).delete
        } map {
          case 1 => ResultSet(ResultTypeImpl.SUCCESS, Result(UserAuthSuccessful, Some(true)))
          case _ => ResultSet(ResultTypeImpl.ERROR, Result(UnknownError, Some(false)))
        })
      case None =>
        ResultSet(ResultTypeImpl.FAILURE, Result(UserNotFound, None))
    }

  
}
