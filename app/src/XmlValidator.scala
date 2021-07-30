package app

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.{Schema, SchemaFactory, Validator}
import org.xml.sax.{ErrorHandler, SAXParseException}
import java.net.URL
import java.io.InputStream
import java.io.ByteArrayInputStream

object XmlValidator {

  def validate(xmlFile: URL, xsdFile: URL): Boolean = {
    validate(xmlFile.openStream(), xsdFile)
  }

  def validate(xmlString: String, xsdFile: URL): Boolean = {
    validate(
      new ByteArrayInputStream(xmlString.getBytes),
      xsdFile
    )
  }

  def validate(xmlStream: InputStream, xsdFile: URL): Boolean = {
    var exceptions = List[String]()

    val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val schema: Schema = schemaFactory.newSchema(new StreamSource(xsdFile.openStream()))

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

    validator.validate(new StreamSource(xmlStream))
    exceptions.foreach(e=>{
      println(e)
    })

    return exceptions.length == 0
  }
}
