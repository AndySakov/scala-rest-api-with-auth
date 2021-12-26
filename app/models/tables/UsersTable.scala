package models.tables

import slick.lifted.Rep
import java.time.LocalDate
import java.util.UUID
import java.sql.Timestamp
import slick.jdbc.PostgresProfile.api._
import models.User
import helpers.RoleImpl.Role
import helpers.RoleImpl

class UsersTable(tag: Tag) extends Table[User](tag, "users") {
  implicit val RoleMapper =
    MappedColumnType.base[Role, String](_.toString, RoleImpl.withName)

  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def user_id: Rep[UUID] = column[UUID]("user_id", O.Unique)
  def email: Rep[String] = column[String]("email", O.Unique)
  def country: Rep[String] = column[String]("country")
  def name: Rep[String] = column[String]("name")
  def dob: Rep[LocalDate] = column[LocalDate]("dob")
  def phone: Rep[String] = column[String]("phone")
  def pass: Rep[String] = column[String]("pass")
  def role: Rep[Role] = column[Role]("Role")
  def createdAt: Rep[Timestamp] = column[Timestamp]("createdAt")

  def * = (id, user_id, email, country, name, dob, phone, pass, role, createdAt).mapTo[User]
}
