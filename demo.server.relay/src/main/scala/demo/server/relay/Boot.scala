package demo.server.relay

import akka.actor.{ ActorSystem, Props }
import akka.io.IO
import spray.can.Http

object Boot extends App {

  implicit val system = ActorSystem("relay-system")

  private val relayInterface = system.settings.config getString "relay.interface"

  private val relayPort = system.settings.config getInt "relay.port"

  private val service = system.actorOf(Props[RelayServiceActor], "relay-service")

  IO(Http) ! Http.Bind(service, interface = relayInterface, port = relayPort)

}