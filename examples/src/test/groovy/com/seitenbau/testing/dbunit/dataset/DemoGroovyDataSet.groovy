package com.seitenbau.testing.dbunit.dataset

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*
import com.seitenbau.testing.dbunit.model.*


class DemoGroovyDataSet extends PersonDatabaseBuilder
{
 
  def tables() {
    teamsTable.rows {
      REF         | title                 | description         | membersize
      QA          | "Quality Assurance"   | "Verifies software" | "QA Size"()
    }

    jobsTable.rows {
      REF         | title                 | description
      SWD         | "Software Developer"  | "Creating software"
      SWT         | "Software Tester"     | "Testing software"
      TM          | "Team Manager"        | "Makes the world go round"
    }

    personsTable.rows {
      REF         | first_name            | name
      KAULBERSCH  | "Dennis"              | "Kaulbersch"
      GUITTON     | "Julien"              | "Guitton"
      BARANOWSKI  | "Christian"           | "Baranowski"
    }
    
  }
  
  def relations() {
    QA.personsTo(KAULBERSCH, GUITTON, BARANOWSKI)
    KAULBERSCH.hasJob(SWD)
    GUITTON.hasJob(SWT)
    BARANOWSKI.hasJob(TM)
  }
  
  def "QA Size"() {
    return { personsTable.findWhere.teamId(QA).getRowCount() }
  }
}
