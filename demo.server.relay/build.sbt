
name := "demo.server.relay"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val akkaVersion = "2.2.3"
  val sprayVersion = "1.2-RC3"
  val sprayJsonVersion = "1.2.5"
  Seq(
    "io.spray"          %  "spray-can"     % sprayVersion,
    "io.spray"          %  "spray-routing" % sprayVersion,
    "io.spray"          %  "spray-testkit" % sprayVersion,
    "io.spray"          %% "spray-json"    % sprayJsonVersion,
    "com.typesafe.akka" %% "akka-actor"    % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit"  % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j"    % akkaVersion,
    "org.specs2"        %% "specs2"        % "2.2.3"        % "test"
  )
}

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.13"

seq(Revolver.settings: _*)

atmosSettings

