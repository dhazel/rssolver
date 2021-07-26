package app

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class XmlValidatorSpec extends AnyFlatSpec with Matchers {

  "XmlValidator" should "correctly validate xml" in {
    XmlValidator.validate(
      getClass.getResource("/testrss2xml.xml"),
      getClass.getResource("/rss2schema.xsd")
    ) should equal(true)

  }

}
