package demo.server.relay

import akka.actor.{ Actor, Props }
import spray.routing.RequestContext
import akka.actor.ActorLogging
import akka.actor.ActorRef
import spray.json.JsValue

class RelayActor() extends Actor with ActorLogging {

  private var subscriptions = scala.collection.mutable.Map.empty[String, ActorRef]

  private def actorRefFactory = context

  def receive = {
    case Subscription(user, action, ctx) => subscribe(user, action, ctx)
    case Notification(id, user, action, body) => push(id, user, action, body)
  }

  def subscribe(user: String, action: String, ctx: RequestContext) {
    log.debug(s"subscribe(user=${user}")
    val key = s"${user}/${action}"
    subscriptions.get(key) match {
      case Some(ref) => { // kill and start (possibly in a new request context)
        actorRefFactory.stop(ref)
      }
      case None => {
        // do nothing
      }
    }
    // simply update mapping
    val ref = actorRefFactory.actorOf(Props(classOf[PushActor], ctx))
    subscriptions.put(key, ref)
  }

  def push(id: String, user: String, action: String, body: JsValue) {
    log.debug(s"message(id=${id}, user=${user}, action=${action}")
    val key = s"${user}/${action}"
    subscriptions.get(key) match {
      case Some(ref) => {
        ref ! body
      }
      case None => {
        // ignore when there is no subscription
      }
    }
  }

}
