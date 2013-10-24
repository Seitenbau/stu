package com.seitenbau.stu.database.dataset

import static com.seitenbau.stu.database.PersonDatabaseRefs.*

import com.seitenbau.stu.database.model.*


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
