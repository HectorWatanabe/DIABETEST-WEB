name := "Diabe_test"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "nz.ac.waikato.cms.weka" % "weka-dev" % "3.9.1"
)     

play.Project.playJavaSettings

