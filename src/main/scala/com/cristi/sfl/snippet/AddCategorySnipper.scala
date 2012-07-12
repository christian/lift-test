package com.cristi.sfl {
package snippet {

import scala.xml.{NodeSeq, Text}
import net.liftweb._
import net.liftweb.http.StatefulSnippet
import util._
import common._
import java.util.Date
import com.cristi.sfl.lib._
import com.cristi.sfl.model.Category
import Helpers._
import net.liftweb.http.{SHtml, RequestVar, S}

class AddCategorySnippet extends StatefulSnippet {
  // This maps the "add" XML element to the "add" method below. Is this needed ?
  def dispatch = {
    case "add" => add _
  }

  object name extends RequestVar[String]("") // this should be used in the stateless case ...

  object Log extends Logger

  // form variables
  // var url2 = S.param("url") openOr ""

  def add(xhtml : NodeSeq) : NodeSeq = {

    def processEntryAdd () = {

      if (name.isEmpty()) {
        S.error("invalid name")
      } else {
        val category = Category.create.name(name)
        category.save
        S.redirectTo("/")

        unregisterThisSnippet()
      }
    }

    bind("entry", xhtml,
         "name"   -> SHtml.text(name.is, name(_), "id" -> "nameField"),
         "submit" -> SHtml.submit("Add", processEntryAdd, "id" -> "submitButton"))
  }
}

}
}
