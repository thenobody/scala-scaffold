package net.thenobody.scaffold.restapi.service.util

import net.thenobody.scaffold.restapi.service.exception.InvalidRequestException
import spray.http.StatusCodes
import spray.routing.{HttpService, ExceptionHandler}
import spray.util.LoggingContext

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
trait ResponseExceptionHandler extends HttpService {

  def handleResponseExceptions(implicit log: LoggingContext) = ExceptionHandler {
    case e: InvalidRequestException =>
      requestUri { uri =>
        log.error(e.cause, "Exception occurred")
        complete(e.status, e.toString)
      }
    case e: Throwable => {
      log.error(e, "Error handling request")
      complete(StatusCodes.InternalServerError)
    }
  }
}
