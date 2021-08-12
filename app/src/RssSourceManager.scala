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
            config = itemConf
          )
        )
      }
    }

  def getSource(name: String): Option[RssSource] = {
    return sources.get(name)
  }
}
