package com.seitenbau.testing.dbunit.referencesexample

import static com.seitenbau.testing.dbunit.referencesexample.STUDSLRefs.*;
import com.seitenbau.testing.dbunit.model.dsl.STUDSL
import com.seitenbau.testing.dbunit.model.dsl.JobsRef
import com.seitenbau.testing.dbunit.model.dsl.PersonsRef
import com.seitenbau.testing.dbunit.model.dsl.STUDSL;
import com.seitenbau.testing.dbunit.model.dsl.TeamsRef

class STUDSLDataSet extends STUDSL
{
  STUDSLDataSet() {
    tables {
      
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
      
      teamsTable.rows {
        REF           | id  | title                   | description         | membersize
        QA            | 1   | "Quality Assurance"     | "Verifies software" | 3
      }
      
    }
  }
}
