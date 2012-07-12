package bootstrap.liftweb

import net.liftweb._
import http.{LiftRules, NotFoundAsTemplate, ParsePath}
import sitemap.{SiteMap, Menu, Loc}
import util.{ NamedPF }
import net.liftweb.http.{ RewriteRequest, RewriteResponse }
import mapper.{Schemifier, DB, StandardDBVendor, DefaultConnectionIdentifier}
import util.{Props}
import common.{Full}
import http.{S}
import com.cristi.sfl.model.{User, Link, Category}
import _root_.net.liftweb.sitemap.Loc._


class Boot {
  def sitemap(): SiteMap =
    SiteMap(Menu.i("Home") / "index")

  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor =
        new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
                             Props.get("db.url") openOr "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
        		     Props.get("db.user"),
                             Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }

    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
    Schemifier.schemify(true, Schemifier.infoF _, User, Link, Category)

    // where to search snippet
    LiftRules.addToPackages("com.cristi.sfl")

    // build sitemap
    val entries = (List(
      Menu("Home")  / "index",
      Menu("About") / "about"
      ) ::: User.menus)

    // the User management menu items User.sitemap :::
    // List(Menu(Loc("Static", Link(List("static"), true, "/static/index"), "Static Content"))) :::

    LiftRules.setSiteMap(SiteMap(entries:_*))

    // Set up some rewrites
    LiftRules.statelessRewrite.append {
      case RewriteRequest(ParsePath(List("links"), _, _, _), _, _) =>
	      RewriteResponse("index" :: Nil)
      case RewriteRequest(ParsePath(List("links", category_id), _, _, _), _, _) =>
	      RewriteResponse("index" :: Nil, Map("category_id" -> category_id))

    } // todo urlEncode(category_id)



    LiftRules.uriNotFound.prepend(NamedPF("404handler"){
      case (req,failure) => NotFoundAsTemplate(
        ParsePath(List("exceptions", "404"), "html", false, false))
    })

    // set character encoding
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)



    // Make a transaction span the whole HTTP request
    S.addAround(DB.buildLoanWrapper)
    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

  }
}
