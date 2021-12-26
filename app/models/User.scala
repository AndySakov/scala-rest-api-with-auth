package models

import java.sql.Timestamp
import java.time.LocalDate
import helpers.RoleImpl.Role
import java.util.UUID
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.password.BCryptSha256PasswordHasher
import com.mohiva.play.silhouette.api.Identity

/** This is the User model that defines the user structure in the database
  * @param user_id
  *   a unique ID that all users possess
  * @param email
  *   the email associated with this user
  * @param pass
  *   the password for this user account
  * @param name
  *   the full name of this user
  * @param dob
  *   the date of birth of the user
  * @param phone
  *   the phone number of the user
  * @param Role
  *   the user account class
  * @param country
  *   the country of origin
  * @param createdAt
  *   the time of creation of this user account accurate to milliseconds
  */
final case class User(
    id: Long = 0L,
    user_id: UUID,
    email: String,
    country: String,
    name: String,
    dob: LocalDate,
    phone: String,
    password: String,
    role: Role,
    createdAt: Timestamp,
  ) extends Serializable with Identity {

  /** Generates login info from email
    *
    * @return login info
    */
  def loginInfo = LoginInfo(CredentialsProvider.ID, email)

  /** Generates password info from password.
    *
    * @return password info
    */
  def passwordInfo = PasswordInfo(BCryptSha256PasswordHasher.ID, password)
}
