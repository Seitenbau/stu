package com.seitenbau.testing.dbunit.dataset

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*

class ExtendedDemoGroovyDataSet extends DemoGroovyDataSet
{
  ExtendedDemoGroovyDataSet(){
    tables {
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
}
