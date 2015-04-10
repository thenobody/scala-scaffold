package net.thenobody.scaffold.utils

import net.thenobody.scaffold.model.User
import spray.json.DefaultJsonProtocol._
import spray.json._

object JsonFormats {

  def userToJson(user: User): JsObject = new JsObject (
    Map(
      "id" -> JsString(user.id),
      "name" -> JsString(user.name),
      "password" -> JsString(user.password),
      "email" -> JsString(user.email)
    )
  )

  val mapUserModel = new RootJsonFormat[User] {
    override def write(obj: User): JsObject = userToJson(obj)

    // not implementing this method as we only serialise this type
    override def read(json: JsValue): User = {
      ???
    }
  }

  val mapUserListModel = new RootJsonFormat[Iterable[User]] {
    override def write(obj: Iterable[User]): JsObject = new JsObject(
      Map(
        "users" -> JsArray(obj.map(userToJson).toVector)
      )
    )

    override def read(json: JsValue): Iterable[User] = ???
  }

  implicit def mapUserToJsonString = mapUserModel
  implicit def mapUserListToJsonString = mapUserListModel
}
