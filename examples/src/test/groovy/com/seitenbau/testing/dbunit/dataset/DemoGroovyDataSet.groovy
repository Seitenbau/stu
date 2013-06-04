package com.seitenbau.testing.dbunit.dataset

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*

import com.seitenbau.testing.dbunit.model.dsl.*

class DemoGroovyDataSet extends EmptyGroovyDataSet
{
  DemoGroovyDataSet() {
    tables {
      
      teamsTable.rows {
        REF           | id  | title                   | description         | membersize
        QA            | 1   | "Quality Assurance"     | "Verifies software" | { personsTable.findWhere.teamId(QA).getRowCount() } 
      }

      jobsTable.rows {
        REF           | id  | title                   | description
        SWD           | 1   | "Software Developer"    | "Creating software"
        SWT           | 2   | "Software Tester"       | "Testing software"
        TM            | 3   | "Team Manager"          | "Makes the world go round"
      }
      
      personsTable.rows {
        REF           | id  | first_name              | name                | job | team
        KAULBERSCH    | 1   | "Dennis"                | "Kaulbersch"        | SWD | QA
        GUITTON       | 2   | "Julien"                | "Guitton"           | SWT | QA
        BARANOWSKI    | 3   | "Christian"             | "Baranowski"        | TM  | QA
      }
      
    }
  }
}
