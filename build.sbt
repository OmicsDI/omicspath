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
  "org.json4s" %% "json4s-native" % "3.6.1",
  "com.thoughtworks.xstream" % "xstream" % "1.4.3",
  "org.mongodb" %% "casbah" % "3.1.1",
  "org.slf4j" % "slf4j-simple" % "1.6.4",
  "com.novus" %% "salat-core" % "1.9.9" % Test
)

//dependencyOverrides += "org.scala-lang" % "scala-compiler" % scalaVersion.value