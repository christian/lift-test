package com.cristi.sfl {
package model {

import _root_.java.math.MathContext
import _root_.net.liftweb.mapper._

// Create an Account class extending the LongKeyedMapper superclass
// (which is a "mapped" (to the database) trait that uses a Long primary key)
// and mixes in trait IdPK, which adds a primary key called "id".
class Link extends LongKeyedMapper[Link] with IdPK {
  // Define the singleton, as in the "User" class
  def getSingleton = Link

  // Define a many-to-one (foreign key) relationship to the Category class
  object category_id extends MappedLongForeignKey(this, Category) {
    override def dbIndexed_? = true
  }

  // fields in the database
  object url extends MappedString(this, 100)
}


object Link extends Link with LongKeyedMetaMapper[Link] {
  // define the DB table name
  override def dbTableName = "links"

  def findEveryLink() : List[Link] =
    Link.findAll()

  // def findByCategoryId(category : Category) : List[Link] =
  //   Link.findAll(By(Link.category_id, category.id.is))
}


}
}
