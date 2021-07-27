package app

import scala.None
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.OptionValues

class RssSourceManagerSpec extends AnyFlatSpec with Matchers with OptionValues {

  "RssSourceManager" should "receive a config file" in {
    val manager = new RssSourceManager(getClass.getResource("/testconfig.json"))
  }

  it should "get None for a nonexistent source" in {
    val manager = new RssSourceManager(getClass.getResource("/testconfig.json"))

    // check for a None return type
    manager.getSource("nonexistent") should not be Symbol("defined")
  }

  it should "get an RssSource for an existing source" in {
    val manager = new RssSourceManager(getClass.getResource("/testconfig.json"))

    manager.getSource("simple").value shouldBe a [RssSource]
  }

}
