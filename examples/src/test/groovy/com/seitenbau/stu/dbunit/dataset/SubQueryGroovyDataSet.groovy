package com.seitenbau.stu.dbunit.dataset

import static com.seitenbau.stu.dbunit.PersonDatabaseRefs.*

import com.seitenbau.stu.dbunit.model.*


class SubQueryGroovyDataSet extends PersonDatabaseBuilder
{

  def tables() {
    personsTable.rows {
      first_name | name
      "Hans"     | "Wurst"
      "Peter"    | "Wurst"
      "Paul"     | "Wurst"
      "Hans"     | "Mustermann"
      "Peter"    | "Mustermann"
    }
  }

}
