package app

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.{Schema, SchemaFactory, Validator}
import scala.jdk.CollectionConverters._
import org.xml.sax.{ErrorHandler, SAXParseException}
import java.net.URL
import org.jsoup.Jsoup
import java.io.File
import org.jsoup.nodes.Document
import us.codecraft.xsoup.Xsoup

object RssSource {

}

case class RssSource(
  name: String,
  title: String,
  description: String,
  url: String,
  itemContainer: String,
  itemTitle: String,
  itemLink: String
) {

  def getDocument(): Document = {
    val urlObj = new URL(url)

    return if ( urlObj.getProtocol() == "file" ) {
      Jsoup.parse(new File(
        getClass.getResource(
          s"${urlObj.getPath()}")
        .toURI()), null)
    }
    else {
      new Document("http://example.org")
    }
  }

  def getRss(): String = {
    val document = getDocument()
    val containers = Xsoup.compile(itemContainer)
                      .evaluate(document)
                      .list().asScala
    val rssXml =
      <rss version="2.0">
        <channel>
          <title>{title}</title>
          <link>{url}</link>
          <description>{description}</description>
          {for (parse <- containers.map(c =>
                  (exp: String) => Xsoup.compile(exp).evaluate(Jsoup.parse(c))
                )) yield
            <item>
              <title>{parse(itemTitle)}</title>
              <link>{parse(itemLink)}</link>
              <description>mydescription</description>
            </item>
          }
        </channel>
      </rss>

    return rssXml.toString()
  }
}
