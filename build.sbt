name := "ist440w"
 
version := "1.0" 
      
lazy val `ist440w` = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

libraryDependencies ++= Seq( javaJdbc , javaWs , guice, "org.mindrot" % "jbcrypt" % "0.3m")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

