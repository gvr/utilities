package demo.server.relay

import spray.routing.RequestContext

case class Subscription(user: String, action: String, ctx: RequestContext) {

  override def toString: String = s"Subscription(user=${user}, action=${action})"

}