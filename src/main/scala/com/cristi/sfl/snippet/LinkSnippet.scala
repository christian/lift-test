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
import net.liftweb.http.{SHtml, RequestVar, S}

class linksnippet {
  val categoryId = S.param("category_id").map(_.toInt) openOr 0

  def list = {
    Category.find(categoryId) match {
      case Full(category) =>
        ".url *" #> category.links.map { l =>
          "a *"      #> l.url &
          "a [href]" #> "/edit/%s".format(l.id.toString()) }
      case _ => ".links" #> "No links here"

    }
  }
}

}
}
