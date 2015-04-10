package net.thenobody.scaffold.restapi.service

import spray.http.MediaTypes._
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.routing._

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
trait PingService extends HttpService {
  val pingPath = path("ping")

  def handleGetPing = {
    get {
      respondWithMediaType(`application/json`) {
        complete {
          Map("ping" -> "ok")
        }
      }
    }
  }
}