package jsxz.tools

import scala.util.matching.Regex

object sbt2pom {
  def main(args: Array[String]): Unit = {
    val str =
      """
        |      "org.scala-lang.modules" %% "scala-xml" % "1.0.5",
        |      "com.chuusai" %% "shapeless" % "2.3.2",
        |//      compilerPlugin("com.github.wheaties" %% "twotails" % "0.3.1" cross CrossVersion.full),
        |//      "com.github.wheaties" %% "twotails-annotations" % "0.3.1" cross CrossVersion.full,
        |      "org.specs2" %% "specs2-core" % "3.8.5" % "it,test",
        |      "net.sf.saxon" % "Saxon-HE" % "9.7.0-8" % "it,test"
        |""".stripMargin

    val pattern = "\\\"(.*)\\\"".r
    val items = (pattern findAllMatchIn str).toList.map(e => {
      val p1 = "\\\"(.*?)\\\"".r
      val value = p1.findAllMatchIn(e.toString).map(_.toString()).toList
      if (e.toString().contains("%%")) {
        List(value.head, value.tail.head + "_${scala.version}", value.tail.tail.head)
      } else {
        value
      }
    })
    println(items)
    items.foreach(value => {
      (value.head, value.tail.head, value.tail.tail.head)
      var arti = value.tail.head

      var result = s"<dependency><groupId>${value.head}</groupId><artifactId>$arti</artifactId><version>${value.tail.tail.head}</version></dependency>"

      println(result.replaceAll("\\\"", ""))
    })
  }
}
