package net.thenobody.scaffold.restapi

import akka.actor._
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.Config
import net.thenobody.scaffold.restapi.service.RouterServiceActor
import org.springframework.context.support.ClassPathXmlApplicationContext
import spray.can.Http

import scala.concurrent.duration._

/**
 * @author Anton Vanco <anton.vanco@imagini.net>
 */

object Boot {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()

    val applicationContext = new ClassPathXmlApplicationContext("scaffold-restapi.xml")
    val configuration = applicationContext.getBean("configuration", classOf[Config])
    lazy val routerService = system.actorOf(Props { new RouterServiceActor(applicationContext) })

    val port = configuration.getInt("scaffold.server.port")

    implicit val timeout = Timeout(5.seconds)
    IO(Http) ? Http.Bind(routerService, interface = "0.0.0.0", port = port)
  }
}
