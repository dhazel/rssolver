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
