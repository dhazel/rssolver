package app

import java.net.URL
import scala.io.Source
import upickle.default._

object RssSourceManager {

}

class RssSourceManager(configFile: URL) {

  val config = ujson.read(Source.fromURL(configFile).mkString)
  val sources = config("sources").arr
    .map { source => new RssSource(source("name").str) }
    .foldLeft(Map[String,RssSource]()) { (acc, source) =>
      acc + ( source.name -> source )
    }

  def getSource(name: String): Option[RssSource] = {
    return sources.get(name)
  }
}
