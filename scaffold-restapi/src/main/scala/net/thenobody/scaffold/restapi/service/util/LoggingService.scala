package net.thenobody.scaffold.restapi.service.util

import net.thenobody.scaffold.restapi.service.exception.InvalidRequestException
import spray.http.{StatusCodes, HttpResponse, HttpRequest}
import spray.routing._
import spray.routing.directives.LoggingMagnet
import spray.util.LoggingContext

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
trait LoggingService extends HttpService
  with ResponseExceptionHandler {

  def logHttp = logRequestResponse(LoggingMagnet(buildLogEntry)) & handleExceptions(handleResponseExceptions)

  def buildLogEntry(req: HttpRequest)(res: Any): Unit = {
    val loggingContext = implicitly[LoggingContext]
    val method = req.method
    val url = req.uri
    val protocol = req.protocol
    val responseCode = res match {
      case rejected: Rejected => rejected.rejections.head match {
        case validation: ValidationRejection => validation.cause.get match {
          case invalidException: InvalidRequestException => invalidException.status
        }
        case transformation: TransformationRejection => StatusCodes.NotFound
      }
      case response: HttpResponse => response.status
    }

    loggingContext.info(Seq(method, url, protocol, responseCode).mkString(" "))
  }

}
