package com.seitenbau.stu.dbunit.dataset

import static com.seitenbau.stu.dbunit.PersonDatabaseRefs.*

import com.seitenbau.stu.dbunit.model.*


class MalFormedGroovyDataSet extends PersonDatabaseBuilder
{

  def tables() {
    teamsTable.rows {
      REF         | title                 | description         | membersize
      QA          | "Quality Assurance"   | "Verifies software" | "1"
    }
  }

}
