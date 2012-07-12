package com.cristi.sfl {
  package snippet {

    import scala.xml.{NodeSeq, Text}
    import net.liftweb._
    import net.liftweb.http.StatefulSnippet
    import util._
    import common._
    import java.util.Date
    import com.cristi.sfl.lib._
    import com.cristi.sfl.model.{ User, Link, Category }
    import Helpers._
    import net.liftweb.http.{ RequestVar, S, SHtml }
    import net.liftweb.http.SHtml.{ text, select, submit }
    import org.slf4j.LoggerFactory
    import net.liftweb.http.js.JsCmds.FocusOnLoad

    class UserLogin {
      def render = User.login
    }

  }
}
