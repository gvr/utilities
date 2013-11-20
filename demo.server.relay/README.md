Transaction Relay
=================

A utility to facilitate json submission for a webapp demo application.
Input can be submitted with a post request. This input is then pushed to subscribed http connections.

Example subscription:
* http://localhost:8080/api/someUser/someAction

Example post with curl:
* curl -X POST -H "Content-Type: application/json" -d '{"id":"1","user":"someUser","action":"someAction","body":{"bla":"testjsonobject","zzz":[1,2,3]}}' http://localhost:8080/relay

When subscribing, the first message will be the immediate response json { subscription: "activated" }.
After this all messages with the user and action as specified in the subscription (which may be arbitrary), will be pushe to the connection.
Only one connection with the user/action combination will be served.
