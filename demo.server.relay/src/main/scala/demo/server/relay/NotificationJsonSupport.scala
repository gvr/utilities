package demo.server.relay

import spray.json.DefaultJsonProtocol
import spray.httpx.SprayJsonSupport

object NotificationJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val RelayMessageJsonFormat = jsonFormat4(Notification)

}
