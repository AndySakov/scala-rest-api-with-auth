package utils

import play.api.data.FormError
import play.api.data.format.Formatter

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ Await, Future }
import scala.language.postfixOps
import scala.util.Try

object Utils {
  def exec[A](action: Future[A]): A =
    Await.result(action, 10 seconds)

  def enumerationFormatter[E <: Enumeration](enum: E): Formatter[E#Value] = new Formatter[E#Value] {
    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], E#Value] =
      data
        .get(key)
        .map(s => Try(enum.withName(s)))
        .toRight(Seq(FormError(key, "error.required", Nil))) match {
        case Left(value) => Left(value)
        case Right(value) =>
          value match {
            case util.Failure(exception) => Left(Seq(FormError(key, "error.invalid_Role", Nil)))
            case util.Success(value) => Right(value)
          }
      }
    override def unbind(key: String, value: E#Value): Map[String, String] = Map(
      key -> value.toString
    )
  }

  def encrypt(plaintext: String): String =
    org.mindrot.jbcrypt.BCrypt.hashpw(plaintext, org.mindrot.jbcrypt.BCrypt.gensalt)

  def validate(plaintext: String, hashed: String): Boolean =
    org.mindrot.jbcrypt.BCrypt.checkpw(plaintext, hashed)
}
