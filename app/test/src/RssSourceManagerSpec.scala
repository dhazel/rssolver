package app

import scala.None
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.OptionValues
import com.typesafe.config.{ConfigFactory, Config}
import org.scalatest.matchers.BePropertyMatcher
import org.scalatest.matchers.BePropertyMatchResult
import scala.xml.XML

class RssSourceManagerSpec extends AnyFlatSpec
with Matchers with OptionValues with ConfigAble {

  "RssSourceManager" should "receive a config" in {
    val manager = new RssSourceManager(getConfig())
  }

  it should "get None for a nonexistent source" in {
    val manager = new RssSourceManager(getConfig())

    // check for a None return type
    manager.getSource("nonexistent") should not be Symbol("defined")
  }

  it should "get an RssSource for an existing source" in {
    val manager = new RssSourceManager(getConfig())

    manager.getSource("simple").value shouldBe a [RssSource]
  }

  it should "transform a valid source to rss2" in {
    val manager = new RssSourceManager(getConfig())

    manager.getSource("simple").value.getRss() should be(rss2)
  }

  it should "contain the correct rss items" in {
    val manager = new RssSourceManager(getConfig())
    val rss = XML.loadString(manager.getSource("simple").value.getRss())

    (rss \ "channel" \ "title").text should equal("Simple Source")
    (rss \ "channel" \ "link").text should equal("file:///test_simple_source.html")
    (rss \ "channel" \ "description").text should equal("A simple RSS2 source for testing")
    (rss \ "channel" \ "item").length should equal(4)

    (rss \ "channel" \ "item" \ "title")(0).text should equal("2005 Honda Civic EX")
    (rss \ "channel" \ "item" \ "title")(1).text should equal("2009 Honda Civic LX")
    (rss \ "channel" \ "item" \ "title")(2).text should equal("2004 Honda Civic Ex Manual Coupe")
    (rss \ "channel" \ "item" \ "title")(3).text should equal("2009 Honda civic")

    (rss \ "channel" \ "item" \ "link")(0).text should equal("https://test.example.org/2005-honda-civic-ex/test1.html")
    (rss \ "channel" \ "item" \ "link")(1).text should equal("https://test.example.org/2009-honda-civic-lx/test2.html")
    (rss \ "channel" \ "item" \ "link")(2).text should equal("https://test.example.org/2004-honda-civic-ex-manual-coupe/test3.html")
    (rss \ "channel" \ "item" \ "link")(3).text should equal("https://test.example.org/2009-honda-civic/test4.html")

    (rss \ "channel" \ "item" \ "description")(0).text should fullyMatch regex """\$2,800 +\(neighborhood1\) +3\.1mi"""
    (rss \ "channel" \ "item" \ "description")(1).text should fullyMatch regex """\$7,000 +\(neighborhood2\) +5\.5mi"""
    (rss \ "channel" \ "item" \ "description")(2).text should fullyMatch regex """\$2,900 +\(neighborhood2\) +5\.6mi"""
    (rss \ "channel" \ "item" \ "description")(3).text should fullyMatch regex """\$3,500 +5\.9mi"""
  }

  it should "pull sources from the internet" in {
    val manager = new RssSourceManager(getConfig())
    //val rss = XML.loadString(manager.getSource("exampleorg").value.getRss())

    manager.getSource("exampleorg").value.getRss() should be(rss2)
  }

}

trait ConfigAble {
  def getConfig(): Config = {
    return ConfigFactory.load()
  }
}

object rss2 extends BePropertyMatcher[String] {
  def apply(left : String) = BePropertyMatchResult(
    XmlValidator.validate(left, getClass.getResource("/rss2schema.xsd")),
    "valid rss2")
}
