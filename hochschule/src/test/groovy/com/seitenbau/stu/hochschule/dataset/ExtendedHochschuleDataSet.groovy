package com.seitenbau.stu.hochschule.dataset

import static com.seitenbau.stu.hochschule.HochschuleRefs.*

import com.seitenbau.stu.hochschule.model.HochschuleBuilder

class ExtendedHochschuleDataSet extends HochschuleBuilder
{

  def extendsDataSet() { HochschuleDataSet }

  def tables() {

   lehrveranstaltungTable.rows {
      REF       | id  | name                | sws | ects
      PROGR     | 3   | "Programmieren"     | 4   | 6.0
    }
  }

  def relations() {
    HAASE.leitet(PROGR)
  }

}
