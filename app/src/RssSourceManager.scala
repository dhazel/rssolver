package app

import java.net.URL
import scala.io.Source
import com.typesafe.config.Config
import scala.jdk.CollectionConverters._
import java.util.HashMap
import scala.util.control.Exception.allCatch

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
            userAgent = allCatch.opt(itemConf("userAgent")),
            itemContainer = itemConf("itemContainer"),
            itemTitle = itemConf("itemTitle"),
            itemLink = itemConf("itemLink"),
            itemDescription = itemConf("itemDescription")
          )
        )
      }
    }

  def getSource(name: String): Option[RssSource] = {
    return sources.get(name)
  }
}
