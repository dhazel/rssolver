package app

import java.net.URL
import scala.io.Source
import com.typesafe.config.Config
import scala.jdk.CollectionConverters._
import java.util.HashMap

object RssSourceManager {

}

class RssSourceManager(config: Config) {

  val sources = config.getObject("sources").asScala
    .map { case (key, source) => {
        var itemConf = source.unwrapped()
                        .asInstanceOf[HashMap[String,String]]
                        .asScala
        ( key -> new RssSource(
            name = key,
            title = itemConf("title"),
            description = itemConf("description"),
            url = itemConf("url"),
            itemContainer = itemConf("itemContainer"),
            itemTitle = itemConf("itemTitle")
          )
        )
      }
    }

  def getSource(name: String): Option[RssSource] = {
    return sources.get(name)
  }
}
