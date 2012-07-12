package com.cristi.sfl {
package snippet {

import scala.xml.{NodeSeq, Text}
import net.liftweb._
import net.liftweb.http.StatefulSnippet
import util._
import common._
import java.util.Date
import com.cristi.sfl.lib._
import com.cristi.sfl.model.{ Link, Category }
import Helpers._
import net.liftweb.http.{ RequestVar, S}
import net.liftweb.http.SHtml.{ text, select, submit }
import org.slf4j.LoggerFactory

class AddLinkSnippet extends StatefulSnippet with Logger {
  // Use a different name for our logger
  override val _logger = LoggerFactory.getLogger("AddLinkSnippet")

  // This maps the "add" XML element to the "add" method below. Is this needed ?
  def dispatch = {
    case "add" => add _
  }

  var categoryQueryParam = S.param("category_id")

  val categories = Category.findAll.map(cat => (cat.id.toString, cat.name.toString))

  def add(xhtml : NodeSeq) : NodeSeq = {
    // form fields; need to perform validation on them; this is for the stateful case; can also be done in a stateless way
    var selectedCategory : Box[String] = Empty
    var url : String = ""

    _logger.info("-------------->>>>>>>>> Category from param %s".format(categoryQueryParam))

    def processEntryAdd () = {
      if (url.isEmpty()) {
        S.error("invalid url")
      } else {
        _logger.info("----------------->>>>>>> Url is %s".format(url))
        _logger.info("----------------->>>>>>> Category id is %s".format(selectedCategory))

        val cat = Category.find(selectedCategory)
        val u = Link.create.url(url).category_id(cat) // TODO category_id is a bit weird as a method name
        u.save

        S.notice("Url has been added: " + u.url)
        S.redirectTo(S.uri)

        unregisterThisSnippet() // because the snippet is stateful
      }
    }

    bind("entry", xhtml,
         "url"      -> text(url, url = _, "id" -> "urlField"),
         "category" -> select(categories, categoryQueryParam, v => selectedCategory = Full(v), "class" -> "myselect"),
         "submit"   -> submit("Add", processEntryAdd, "id" -> "submitButton"))
  }
}

}
}
