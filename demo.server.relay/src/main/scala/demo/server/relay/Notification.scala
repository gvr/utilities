package demo.server.relay

import spray.json.JsValue

case class Notification(id: String, user: String, action: String, body: JsValue) {

  override def toString: String = s"RelayMessage(id=${id}, user=${user}, action=${action})"

}
