name := "omicspath"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "com.novocode" % "junit-interface" % "0.8" % "test->default",
  "com.lihaoyi" %% "requests" % "0.1.4",
  "com.lihaoyi" %% "upickle" % "0.6.6",
  "net.liftweb" %% "lift-json" % "3.3.0",
  "io.circe" %% "circe-generic" % "0.1.0",
  "org.json4s" %% "json4s-native" % "3.6.1"
)

dependencyOverrides += "org.scala-lang" % "scala-compiler" % scalaVersion.value