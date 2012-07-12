package com.cristi.sfl {
package model {

import _root_.java.math.MathContext
import _root_.net.liftweb.mapper._

class Category extends LongKeyedMapper[Category] with IdPK {

  def getSingleton = Category

  object name extends MappedString(this, 100)

  def links = Link.findAll(By(Link.category_id, this.id))

}


object Category  extends Category with LongKeyedMetaMapper[Category] {
  // define the DB table name
  override def dbTableName = "categories"

}

}
}
