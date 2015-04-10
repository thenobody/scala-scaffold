package net.thenobody.scaffold.restapi.service.metrics

import net.thenobody.scaffold.restapi.service.model.DetailUserRequest
import spray.routing.HttpService
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.routing._

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
trait DetailService extends HttpService {
  val detailPath = pathPrefix("detail" / Segment)

  def handleGetUserDetail(request: DetailUserRequest) = {
    get {
      complete {
        Map(
          "date" -> request.date
        )
      }
    }
  }
}
