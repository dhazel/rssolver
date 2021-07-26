package app
import io.undertow.Undertow

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RssifierMainSpec extends AnyFlatSpec with Matchers with TestServer {

  "/hello" should "return successfully" in {
    usingServer(RssifierMain) { host =>
      val success = requests.get(s"$host/hello")

      success.text() shouldEqual "Hello World!"
      success.statusCode shouldEqual 200
    }
  }

  "/doesnt-exist" should "return 404" in {
    usingServer(RssifierMain) { host =>
      requests.get(s"$host/doesnt-exist", check = false).statusCode shouldEqual 404
    }
  }

}

trait TestServer {

  def usingServer(caskMain: cask.main.Main)(f: String => Unit): Unit = {
    val hostname = "localhost"
    val port = 8081

    val server = Undertow.builder
      .addHttpListener(port, hostname)
      .setHandler(caskMain.defaultHandler)
      .build
    server.start()

    try {
      f(s"http://${hostname}:${port}")
    }
    finally {
      server.stop()
    }

  }

}
