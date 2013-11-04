package com.seitenbau.stu.database.dataset

import static com.seitenbau.stu.database.PersonDatabaseRefs.*

import com.seitenbau.stu.database.model.PersonDatabaseBuilder

class ExtendedWithoutRelationsDemoGroovyDataSet extends PersonDatabaseBuilder
{

  def extendsDataSet() { DemoGroovyDataSet }

  def tables() {
    personsTable.rows {
      REF         | first_name            | name
      HOCHLEITER  | "Nicole"              | "Hochleiter"
    }

    teamsTable.rows {
      REF           | id  | title                   | description                           | membersize
      HR            | 2   | "Human Resources"       | "Make up workforce of an organzation" | 0
    }

    jobsTable.rows {
      REF           | id  | title                   | description
      SAT           | 4   | "Software Architect"    | "Developing software architecture"
    }
  }
}
