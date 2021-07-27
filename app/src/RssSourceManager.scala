package app

import java.net.URL
import scala.io.Source
import upickle.default._

object RssSourceManager {

}

class RssSourceManager(configFile: URL) {

  val config = ujson.read(Source.fromURL(configFile).mkString)
  val sources = config("sources").obj
    .map { case (key, source) => ( key -> new RssSource(key) ) }

  def getSource(name: String): Option[RssSource] = {
    return sources.get(name)
  }
}
