package helpers

case class Result[A](message: Message, data: Option[A])
