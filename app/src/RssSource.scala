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
import scala.util.control.Exception.allCatch

object RssSource {

}

class RssSource(
  name: String,
  config: scala.collection.mutable.Map[String, String],
) {

  val title = config("title")
  val description = config("description")
  val url = config("url")
  val userAgent = allCatch.opt(config("userAgent"))
  val itemContainer = config("itemContainer")
  val itemTitle = config("itemTitle")
  val itemLink = config("itemLink")
  val itemDescription = config("itemDescription")

  def getDocument(): Document = {
    val urlObj = new URL(url)

    return if ( urlObj.getProtocol() == "file" ) {
      Jsoup.parse(new File(
        getClass.getResource(
          s"${urlObj.getPath()}"
        ).toURI()), null)
    }
    else {
      userAgent match {
        case None => Jsoup.connect(url).get()
        case Some(value) => Jsoup.connect(url).userAgent(value).get()
      }
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
                                    .list().asScala.toList.mkString(" ")
                )) yield
            <item>
              <title>{parse(itemTitle)}</title>
              <link>{parse(itemLink)}</link>
              <guid>{parse(itemLink)}</guid>
              <description>{parse(itemDescription)}</description>
            </item>
          }
        </channel>
      </rss>

    return rssXml.toString()
  }
}
