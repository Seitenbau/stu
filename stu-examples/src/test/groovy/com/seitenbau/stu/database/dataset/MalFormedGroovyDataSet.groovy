package com.seitenbau.stu.database.dataset

import static com.seitenbau.stu.database.PersonDatabaseRefs.*

import com.seitenbau.stu.database.model.*


class MalFormedGroovyDataSet extends PersonDatabaseBuilder
{

  def tables() {
    teamsTable.rows {
      REF         | title                 | description         | membersize
      QA          | "Quality Assurance"   | "Verifies software" | "1"
    }
  }

}
