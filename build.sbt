name := """mongo_timeout_test"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice

//Database
libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.6.4"
libraryDependencies += "org.mongodb.morphia" % "morphia" % "1.3.2"

//Jackson
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

sources in (Compile, doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false

packageName in Universal := {
  val name = (packageName in Universal).value
  def timestamp = new java.text.SimpleDateFormat("yyyy.MM.dd.HH.mm.ss") format new java.util.Date()
  if (isSnapshot.value) s"$name-$timestamp" else name
}