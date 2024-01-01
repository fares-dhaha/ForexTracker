ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.14"

lazy val root = (project in file("."))
  .settings(
    name := "ForexTracker",
    libraryDependencies += "org.apache.flink" %% "flink-streaming-scala" % "1.13.2",
    libraryDependencies += "org.apache.flink" %% "flink-connector-kafka" % "1.13.2",
    libraryDependencies += "org.json4s" %% "json4s-native" % "4.0.3",
    libraryDependencies += "org.apache.flink" %% "flink-clients" % "1.13.2"
  )
