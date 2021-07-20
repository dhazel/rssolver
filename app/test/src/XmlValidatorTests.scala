package app

import utest._

object XmlValidatorTests extends TestSuite{

  val tests = Tests {
    test("XmlValidator") {

      "validates correct xml" - {
        XmlValidator.validate(
          getClass.getResource("/testrss2xml.xml"),
          getClass.getResource("/rss2schema.xsd")
        ) ==> true
      }

    }
  }

}
