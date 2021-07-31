package app

import java.net.URL
import scala.io.Source
import com.typesafe.config.Config
import scala.jdk.CollectionConverters._

object RssSourceManager {

}

class RssSourceManager(config: Config) {

  val sources = config.getObject("sources").asScala
    .map { case (key, source) => {
        var itemConf = source.asInstanceOf[Config]
        ( key -> new RssSource(
            name = key,
            //title = itemConf.getString("title"),
            //description = itemConf.getString("description"),
            //url = new URL(itemConf.getString("url"))
          )
        )
      }
    }

  def getSource(name: String): Option[RssSource] = {
    return sources.get(name)
  }
}
