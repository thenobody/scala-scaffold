package net.thenobody.scaffold.restapi.service

import akka.actor.Actor
import net.thenobody.scaffold.restapi.service.metrics._
import net.thenobody.scaffold.restapi.service.model._
import net.thenobody.scaffold.restapi.service.util.LoggingService
import net.thenobody.scaffold.restapi.service.util.users.UsersUtil
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import spray.http.MediaTypes._
import spray.httpx.encoding._
import spray.routing._

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */
class RouterServiceActor(val applicationContext: ApplicationContext) extends Actor with RouterService {
  override def receive: Receive = runRoute(route)
  override implicit def actorRefFactory = context

  override def usersUtil: UsersUtil =  applicationContext.getBean("usersUtil", classOf[UsersUtil])
}

trait RouterService extends HttpService
  with UsersService
  with DetailService
  with PingService
  with Conversions
  with LoggingService {

  val logger = LoggerFactory.getLogger(classOf[RouterService])

  val route = logHttp {
    compressResponse(Gzip) {
      respondWithMediaType(`application/json`) {
        handleRejections(RejectionHandler.Default) {
          users ~
          userIds ~
          userDetails ~
          ping
        }
      }
    }
  }

  /**
   * user details
   * /users/{userId}/detail
   */
  def userDetails = (userIdPath & detailPath).as(DetailUserRequest) {
    request => pathEnd(handleGetUserDetail(request))
  }

  /**
   * user entry
   * /users/{userId}
   */
  def userIds = userIdPath { user: String =>
    pathEnd(handleGetUser(user))
  }

  /**
   * user list and creation endpoint
   * /users
   */
  def users = usersPath {
    pathEnd {
      post {
        handlePostUser
      } ~
      get {
        handleGetUsers
      }
    }
  }

  /**
   * ping beacon
   * /ping
   */
  def ping = pingPath(handleGetPing)
}

