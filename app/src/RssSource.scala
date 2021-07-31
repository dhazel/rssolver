package app

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.{Schema, SchemaFactory, Validator}
import org.xml.sax.{ErrorHandler, SAXParseException}
import java.net.URL

object RssSource {

}

case class RssSource(
  name: String,
  title: String,
  description: String,
  url: URL
) {
  def getRss(): String = {
    val rssXml =
      <rss version="2.0">
        <channel>
          <title>{title}</title>
          <link>{url.toString()}</link>
          <description>{description}</description>
        </channel>
      </rss>

    return rssXml.toString()
  }
}
