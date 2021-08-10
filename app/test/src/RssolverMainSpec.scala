package app
import io.undertow.Undertow

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RssolverMainSpec extends AnyFlatSpec with Matchers with TestServer {

  "/hello" should "return successfully" in {
    usingServer(RssolverMain) { host =>
      val success = requests.get(s"$host/hello")

      success.text() shouldEqual "Hello World!"
      success.statusCode shouldEqual 200
    }
  }

  "/doesnt-exist" should "return 404" in {
    usingServer(RssolverMain) { host =>
      requests.get(s"$host/doesnt-exist", check = false).statusCode shouldEqual 404
    }
  }

  "/rss/simple" should "return correctly" in {
    usingServer(RssolverMain) { host =>
      requests.get(s"$host/rss/simple").statusCode shouldEqual 200
    }
  }

  "/rss/doesnt-exist" should "return 404" in {
    usingServer(RssolverMain) { host =>
      requests.get(s"$host/rss/doesnt-exist", check = false).statusCode shouldEqual 404
    }
  }

  "RssolverMain" should "set host by config file" in {
    RssolverMain.host should equal("testhost")
  }

  it should "set port by config file" in {
    RssolverMain.port should equal(8085)
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
