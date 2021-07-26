package app

import org.jsoup._

object RssifierMain extends cask.MainRoutes{
  @cask.get("/hello")
  def hello() = {
    "Hello World!"
  }

  @cask.get("/rss/:source")
  def rssSource(source: String) = {
    s"rss source, $source, almost here"
  }

  initialize()
}
