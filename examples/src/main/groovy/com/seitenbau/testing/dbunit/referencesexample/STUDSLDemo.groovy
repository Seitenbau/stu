package com.seitenbau.testing.dbunit.referencesexample

import static com.seitenbau.testing.dbunit.referencesexample.SampleRefs.*;
import com.seitenbau.testing.dbunit.datasets.SampleDataSet;
import com.seitenbau.testing.dbunit.dsl.ColumnBinding;
import com.seitenbau.testing.dbunit.model.JobsTable.JobsWhere;
import com.seitenbau.testing.dbunit.model.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.model.dsl.JobsRef
import com.seitenbau.testing.dbunit.model.dsl.JobsTable;
import com.seitenbau.testing.dbunit.model.dsl.JobsTableBinding;
import com.seitenbau.testing.dbunit.model.dsl.PersonsRef
import com.seitenbau.testing.dbunit.model.dsl.STUDSL;
import com.seitenbau.testing.dbunit.model.dsl.TeamsRef
import com.seitenbau.testing.dbunit.model.dsl.TeamsTable;

class SampleRefs{
  public static JobsRef SWD = new JobsRef();
  public static JobsRef SWT = new JobsRef();
  public static JobsRef TM = new JobsRef();
  
  public static TeamsRef QA = new TeamsRef();
  
  public static PersonsRef KAULBERSCH = new PersonsRef();
  public static PersonsRef GUITTON = new PersonsRef();
  public static PersonsRef BARANOWSKI = new PersonsRef();
}
    STUDSL sampleData = new STUDSL();
    sampleData.tables {
      jobsTable.rows {
        REF           | id  | title                   | description
        SWD           | 1   | "Software Developer"    | "Creating software"
        SWT           | 2   | "Software Tester"       | "Testing software"
        TM            | 3   | "Team Manager"          | "Makes the world go round"
      }
      
      teamsTable.rows {
        REF           | id  | title                   | description         | membersize
        QA            | 1   | "Quality Assurance"     | "Verifies software" | 3
      }
      
      personsTable.rows {
        REF           | id  | first_name              | name                | job_id    | team_id
        KAULBERSCH    | 1   | "Dennis"                | "Kaulbersch"        | SWD       | QA
        GUITTON       | 2   | "Julien"                | "Guitton"           | SWT       | QA
        BARANOWSKI    | 3   | "Christian"             | "Baranowski"        | TM        | QA
      }
      
    }
    
    sampleData.createDataSet()
    
    println("Jobtitle for SWD = " + sampleData.dataset.table_Jobs.findWhere.id(1).getTitle())
    println("Jobid for SWD = " + sampleData.dataset.table_Jobs.findWhere.title("Software Developer").getId())
    println("Teamtitle for id QA = " + sampleData.dataset.table_Teams.findWhere.id(1).getTitle())
//    println("Foreign Jobid for Dennis = " + sampleData.dataset.table_Persons.findWhere.firstName("Dennis").getJobId())
