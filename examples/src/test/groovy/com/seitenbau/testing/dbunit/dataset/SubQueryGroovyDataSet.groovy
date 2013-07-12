package com.seitenbau.testing.dbunit.dataset

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*

import com.seitenbau.testing.dbunit.model.*


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
