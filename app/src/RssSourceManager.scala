package app

import java.net.URL
import scala.io.Source
import com.typesafe.config.Config
import scala.jdk.CollectionConverters._

object RssSourceManager {

}

class RssSourceManager(config: Config) {

  val sources = config.getObject("sources").asScala
    .map { case (key, source) => ( key -> new RssSource(key) ) }

  def getSource(name: String): Option[RssSource] = {
    return sources.get(name)
  }
}
