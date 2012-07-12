package com.cristi.sfl.model {

import net.liftweb._
import mapper._
import util._
import common._

/**
 * The singleton that has methods for accessing the database
 */
object User extends User with MetaMegaProtoUser[User] {

  override def dbTableName = "users" // define the DB table name
  //override def screenWrap = Full(<lift:surround with="default" at="content">
  //           <lift:bind /></lift:surround>)

  override def skipEmailValidation = true

  // Provide our own login page template.
  // override def loginXhtml =
  //   <lift:surround with="default" at="content">
  //     { super.loginXhtml }
  //   </lift:surround>

  // // Provide our own signup page template.

  override def signupXhtml(user: User) =
    <lift:surround with="default" at="content">
      { super.signupXhtml(user) }
    </lift:surround>

  // define the order fields will appear in forms and output
  //override def fieldOrder = List(id, firstName, lastName, email,
  // locale, timezone, password, textArea)
}

/**
 * An O-R mapped "User" class that includes first name, last name, password and we add a "Personal Essay" to it
 */
class User extends MegaProtoUser[User] {
  def getSingleton = User // what's the "meta" server

  // define an additional field for a personal essay
  object textArea extends MappedTextarea(this, 2048) {
    override def textareaRows  = 10
    override def textareaCols = 50
    override def displayName = "Personal Essay"
  }
}

}
