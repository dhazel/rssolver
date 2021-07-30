package app

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.io.Source

class XmlValidatorSpec extends AnyFlatSpec with Matchers {

  "XmlValidator" should "correctly validate xml" in {
    XmlValidator.validate(
      getClass.getResource("/testrss2xml.xml"),
      getClass.getResource("/rss2schema.xsd")
    ) should equal(true)
  }

  it should "validate an xml stream" in {
    XmlValidator.validate(
      getClass.getResource("/testrss2xml.xml").openStream(),
      getClass.getResource("/rss2schema.xsd")
    ) should equal(true)
  }

  it should "validate an xml string" in {
    XmlValidator.validate(
      Source.fromURL(getClass.getResource("/testrss2xml.xml")).mkString,
      getClass.getResource("/rss2schema.xsd")
    ) should equal(true)
  }

}
