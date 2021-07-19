package app

import org.jsoup._

object RssifierMain extends cask.MainRoutes{
  @cask.get("/hello")
  def hello() = {
    "Hello World!"
  }

  initialize()
}
