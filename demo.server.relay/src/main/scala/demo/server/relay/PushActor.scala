package demo.server.relay

import akka.actor.{ Actor, Props }
import akka.actor.ActorLogging
import spray.routing.RequestContext
import spray.can.Http
import spray.http.MessageChunk
import spray.json.JsValue
import spray.http.MediaTypes._
import spray.http.HttpResponse
import spray.http.ChunkedResponseStart
import spray.http.HttpEntity
import spray.http.MediaType
import spray.http.HttpHeaders._
import spray.http.CacheDirectives._
import spray.json.JsObject
import spray.json.JsString

class PushActor(ctx: RequestContext) extends Actor with ActorLogging {

  import context._

  private val `application/json` = MediaType.custom("application/json")

  private val responseStartMessage = JsObject(List("subscription" -> JsString("active")))

  val responseStart = HttpResponse(entity = HttpEntity(`application/json`, responseStartMessage.prettyPrint))
    .withHeaders(List(`Cache-Control`(`no-cache`)))

  ctx.responder ! ChunkedResponseStart(responseStart)

  def receive = {
    case json: JsValue => {
      val nextChunk = MessageChunk(json.prettyPrint)
      ctx.responder ! nextChunk
    }
    case e: Http.ConnectionClosed =>
      log.warning("Stopping response streaming due to {}", e)
  }

}
