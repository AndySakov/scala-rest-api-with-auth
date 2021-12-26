package helpers

sealed abstract class Message(text: String) {
  def message: String = text
}

object Message {
  sealed abstract class Success(text: String) extends Message(text)
  case object UserCreateSuccessful extends Success("User created successfully")
  case object UserUpdateSuccessful extends Success("User updated successfully")
  case object UserAuthSuccessful extends Success("Welcome back!")

  sealed abstract class Failure(text: String) extends Message(text)
  case object UnknownFailure
      extends Failure("The request could not be processed. Please contact the admin for help")
  case object DuplicateUserEntry extends Failure("A user already exists with this email")
  case object UserNotFound extends Failure("User not found")
  case object InvalidCredentials extends Failure("Your email or password is incorrect")
  case object SessionExpired extends Failure("Your session has expired. Please login again")
  case object SessionNotFound extends Failure("You need to login to access this resource")
  case class InvalidAuthToken(override val message: String) extends Failure(message)
  case object AuthTokenNotFound extends Failure("No auth token was sent")

  sealed abstract class Error(text: String) extends Message(text)
  case object UnknownError extends Error("An unknown error has occurred")
  case class PathNotFound(path: String) extends Error(s"Path `$path` not found!")
  case object ForbiddenError extends Error("You are not allowed to access this resource!")
  case object TimeoutError extends Error("Request Timed Out")
}
