import helpers.Message.PathNotFound
import helpers.Responses._
import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router

import java.util.concurrent.TimeoutException
import javax.inject._
import scala.concurrent._
import helpers.Message.ForbiddenError
import io.sentry.Sentry

@Singleton
class ErrorHandler @Inject() (
  env: Environment,
  config: Configuration,
  sourceMapper: OptionalSourceMapper,
  router: Provider[Router]
) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {
  override def onProdServerError(
    request: RequestHeader,
    exception: UsefulException
  ): Future[Result] = {
    Sentry.captureException(exception)
    Future.successful(
      InternalServerError("A server error occurred while we were processing your request")
    )
  }

  override def onForbidden(request: RequestHeader, message: String): Future[Result] = {
    Future.successful(
      Forbidden(errorResponse(ForbiddenError))
    )
  }

  // override def onNotFound(request: RequestHeader, message: String): Future[Result] = {
  //   Future.successful(
  //     NotFound(errorResponse(PathNotFound(request.path)))
  //   )
  // }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Sentry.captureException(exception)
    exception match {
      case tx: TimeoutException =>
        Future.successful(
          InternalServerError(serverErrorResponse(tx))
        )
      case _ => super.onServerError(request, exception)
    }
  }
}
