package com.seitenbau.testing.hochschule.dataset

import static com.seitenbau.testing.hochschule.HochschuleRefs.*

import com.seitenbau.testing.hochschule.model.HochschuleBuilder

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
