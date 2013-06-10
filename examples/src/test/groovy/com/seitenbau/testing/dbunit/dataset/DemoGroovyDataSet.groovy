package com.seitenbau.testing.dbunit.dataset

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*
import com.seitenbau.testing.dbunit.model.dsl.*

class DemoGroovyDataSet extends EmptyGroovyDataSet
{{
  relations {
    QA.personsTo(KAULBERSCH, GUITTON, BARANOWSKI);
  }

  tables {
      
    teamsTable.rows {
      REF         | title                 | description         | membersize
      QA          | "Quality Assurance"   | "Verifies software" | { personsTable.findWhere.teamId(QA).getRowCount() } 
    }

    jobsTable.rows {
      REF         | title                 | description
      SWD         | "Software Developer"  | "Creating software"
      SWT         | "Software Tester"     | "Testing software"
      TM          | "Team Manager"        | "Makes the world go round"
    }

    personsTable.rows {
      REF         | first_name            | name                | job
      KAULBERSCH  | "Dennis"              | "Kaulbersch"        | SWD
      GUITTON     | "Julien"              | "Guitton"           | SWT
      BARANOWSKI  | "Christian"           | "Baranowski"        | TM
    }

  }
}}
