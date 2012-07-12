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

class CategoriesSnippet {

  def list = {
    ".category *" #> Category.findAll.map { l =>
       "a *"      #> l.name &
       "a [href]" #> "/links/%s".format(l.id.toString()) }
  }
}

}
}
