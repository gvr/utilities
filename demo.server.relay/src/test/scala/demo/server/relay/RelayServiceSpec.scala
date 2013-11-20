package demo.server.relay

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._
import spray.json.JsObject
import spray.json.JsString

import NotificationJsonSupport._

class RelayServiceSpec extends Specification with Specs2RouteTest with RelayService {
  def actorRefFactory = system

  "RelayService" should {

    "provide an html page with root path" in {
      Get("/") ~> relayRoute ~> check {
        responseAs[String] must contain("<!DOCTYPE html>")
        responseAs[String] must contain("<html>")
        responseAs[String] must contain("</html>")
      }
    }

    //"return from a relay POST" in {
    //  Post("/relay", RelayMessage(id="1", user="testUser", action="testAction", body=null)) ~> relayRoute ~> check {
    //    responseAs[String] must contain("OK")
    //  }
    //}

    "leave GET requests to other paths unhandled" in {
      Get("/kermit") ~> relayRoute ~> check {
        handled must beFalse
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      Put() ~> sealRoute(relayRoute) ~> check {
        status === MethodNotAllowed
        responseAs[String] === "HTTP method not allowed, supported methods: GET"
      }
    }
  }

}