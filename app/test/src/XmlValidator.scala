package app

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.{Schema, SchemaFactory, Validator}
import org.xml.sax.{ErrorHandler, SAXParseException}

object XmlValidator {

  def validate(xmlFile:String, xsdFile:String): Boolean ={
    var exceptions = List[String]()

    val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val url = getClass.getResource(xsdFile)
    val schema: Schema = schemaFactory.newSchema(new StreamSource(url.openStream()))

    val validator: Validator = schema.newValidator()
    validator.setErrorHandler(new ErrorHandler() {
      @Override
      def warning(exception:SAXParseException): Unit = {

        exceptions = exception.getMessage  :: exceptions
      }
      @Override
      def fatalError(exception:SAXParseException ): Unit = {
        exceptions = exception.getMessage  :: exceptions
      }
      @Override
      def error(exception:SAXParseException ): Unit = {
        exceptions = exception.getMessage  :: exceptions
      }
    });

    val xmlUrl = getClass.getResource(xmlFile)
    validator.validate(new StreamSource(xmlUrl.openStream()))
    exceptions.foreach(e=>{
      println(e)
    })

    return exceptions.length == 0
  }
}
