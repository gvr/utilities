package demo.server.relay

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import NotificationJsonSupport._
import akka.actor.Props
import akka.actor.ActorSystem
import akka.actor.PoisonPill
import scala.concurrent.duration._
import spray.json.JsObject
import scala.language.postfixOps

class RelayServiceActor() extends Actor with RelayService {

  def actorRefFactory = context

  def receive = runRoute(relayRoute)

}

trait RelayService extends HttpService {

  implicit def executionContext = actorRefFactory.dispatcher

  // the single relay actor that manages subscriptions
  // and relays messages to a requesting user/action
  private val relay = actorRefFactory.actorOf(Props(classOf[RelayActor]))

  private val staticRoute =
    path("") {
      getFromResource("www/index.html")
    } ~
      getFromResourceDirectory("www")

  val relayRoute = staticRoute ~
    path("relay") {
      post {
        handleWith((message: Notification) => {
          relay ! message
          s"message[id=${message.id}] sent\n"
        })
      }
    } ~
    path("api" / Segment / Segment) {
      (user: String, action: String) =>
        get {
          ctx => relay ! Subscription(user, action, ctx)
        }
    } ~
    path("shutdown") {
      post {
        complete {
          Boot.system.scheduler.scheduleOnce(1 second) {
            Boot.system.shutdown
          }
          "Shutting down..."
        }
      }
    }

}
