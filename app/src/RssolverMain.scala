package app

import org.jsoup._
import scala.util.control.Exception.allCatch

object RssolverMain extends cask.MainRoutes with ConfigAble {

  override def host: String = getConfig().getString("host")
  override def port: Int = getConfig().getInt("port")

  @cask.get("/hello")
  def hello() = {
    "Hello World!"
  }

  @cask.get("/rss/:sourceName")
  def rssSource(sourceName: String) = {
    val manager = new RssSourceManager(getConfig())
    val source = manager.getSource(sourceName)
    source match {
      case None => cask.Response("", statusCode = 404)
      case Some(value) => cask.Response(value.getRss(), statusCode = 200)
    }
  }

  initialize()
}
