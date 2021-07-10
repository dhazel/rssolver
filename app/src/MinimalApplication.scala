package app

import org.jsoup._

object MinimalApplication extends cask.MainRoutes{
  @cask.get("/hello")
  def hello() = {
    "Hello World!"
  }

  initialize()
}
