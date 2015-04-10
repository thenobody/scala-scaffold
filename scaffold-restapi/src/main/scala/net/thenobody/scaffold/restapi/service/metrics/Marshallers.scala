package net.thenobody.scaffold.restapi.service.metrics

import spray.http.{ContentType, ContentTypes, HttpEntity}
import spray.httpx.marshalling.Marshaller
import spray.json.DefaultJsonProtocol._
import spray.json._

object Marshallers {
  implicit val mapMarshaller = mapMarshallerMethod(ContentTypes.`application/json`)

  def mapMarshallerMethod(contentType: ContentType, more: ContentType*): Marshaller[Map[String, Map[String, String]]] = {
    Marshaller.of[Map[String, Map[String, String]]](contentType +: more: _*) { (value, contentType, ctx) ⇒
      ctx.marshalTo(HttpEntity(contentType, value.toJson.toString()))
    }
  }

  implicit val mapOfMapToDoubleMarshaller = mapOfMapToDouble(ContentTypes.`application/json`)

  def mapOfMapToDouble(contentType: ContentType, more: ContentType*): Marshaller[Map[String, Map[String, Double]]] = {
    Marshaller.of[Map[String, Map[String, Double]]](contentType +: more: _*) { (value, contentType, ctx) ⇒
      ctx.marshalTo(HttpEntity(contentType, value.toJson.toString()))
    }
  }

  implicit val listMarshaller = listOfMapsOfStringToString(ContentTypes.`application/json`)

  def listOfMapsOfStringToString(contentType: ContentType, more: ContentType*): Marshaller[Map[String, List[Map[String, String]]]] = {
    Marshaller.of[Map[String, List[Map[String, String]]]](contentType +: more: _*) { (value, contentType, ctx) ⇒
      ctx.marshalTo(HttpEntity(contentType, value.toJson.toString()))
    }
  }
  
  implicit val mapOfStringToDoubleMarshaller = mapOfStringToDouble(ContentTypes.`application/json`)
  
  def mapOfStringToDouble(contentType: ContentType, more: ContentType*): Marshaller[Map[String, Double]] = {
    Marshaller.of[Map[String, Double]](contentType +: more: _*) { (value, contentType, ctx) ⇒
      ctx.marshalTo(HttpEntity(contentType, value.toJson.toString()))
    }
  }
  
  implicit val mapOfStringToIntMarshaller = mapOfStringToInt(ContentTypes.`application/json`)
  
  def mapOfStringToInt(contentType: ContentType, more: ContentType*): Marshaller[Map[String, Long]] = {
    Marshaller.of[Map[String, Long]](contentType +: more: _*) { (value, contentType, ctx) ⇒
      ctx.marshalTo(HttpEntity(contentType, value.toJson.toString()))
    }
  }
}
