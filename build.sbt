name := """scala-rest-api-with-auth"""
organization := "com.example"

version := "1.0"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLogback)

scalaVersion := "2.13.6"

val playSilhouetteVersion = "7.0.2"
val slickVersion = "3.3.3"
val playSlickVersion = "5.0.0"
val circeVersion = "0.14.1"
val sentryVersion = "5.4.3"
val jwtLibVersion = "5.0.0"
val log4jVersion = "2.13.3"

resolvers += "Atlassian's Maven Public Repository" at "https://packages.atlassian.com/maven-public/"

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
  "io.github.honeycomb-cheesecake" %% "play-silhouette" % playSilhouetteVersion,
  "io.github.honeycomb-cheesecake" %% "play-silhouette-cas" % playSilhouetteVersion,
  "io.github.honeycomb-cheesecake" %% "play-silhouette-crypto-jca" % playSilhouetteVersion,
  "io.github.honeycomb-cheesecake" %% "play-silhouette-password-argon2" % playSilhouetteVersion,
  "io.github.honeycomb-cheesecake" %% "play-silhouette-password-bcrypt" % playSilhouetteVersion,
  "io.github.honeycomb-cheesecake" %% "play-silhouette-persistence" % playSilhouetteVersion,
  "io.github.honeycomb-cheesecake" %% "play-silhouette-totp" % playSilhouetteVersion,
  "io.github.honeycomb-cheesecake" %% "play-silhouette-testkit" % playSilhouetteVersion % Test,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "net.codingwell" %% "scala-guice" % "4.2.6",
  "com.typesafe.play" %% "play-slick" % playSlickVersion,
  "com.typesafe.play" %% "play-slick-evolutions" % playSlickVersion,
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "mysql" % "mysql-connector-java" % "8.0.25",
  "org.postgresql" % "postgresql" % "42.3.1",
  "com.github.daddykotex" %% "courier" % "3.0.1",
  "io.sentry" % "sentry" % sentryVersion,
  "io.sentry" % "sentry-log4j2" % sentryVersion,
  guice,
  ws
)

javaOptions += "-Dlog4j.configurationFile=conf/log4j2.xml"

coverageFailOnMinimum := true
coverageMinimumStmtTotal := 90
coverageMinimumBranchTotal := 90
coverageMinimumStmtPerPackage := 90
coverageMinimumBranchPerPackage := 85
coverageMinimumStmtPerFile := 85
coverageMinimumBranchPerFile := 80
