package com.seitenbau.testing.dbunit.referencesexample

import static com.seitenbau.testing.dbunit.referencesexample.SampleRefs.*;
import com.seitenbau.testing.dbunit.model.dsl.JobsRef
import com.seitenbau.testing.dbunit.model.dsl.PersonsRef
import com.seitenbau.testing.dbunit.model.dsl.STUDSL;
import com.seitenbau.testing.dbunit.model.dsl.TeamsRef

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
  
  personsTable.rows {
    REF           | id  | first_name              | name                | job_id    | team_id
    KAULBERSCH    | 1   | "Dennis"                | "Kaulbersch"        | SWD       | QA
    GUITTON       | 2   | "Julien"                | "Guitton"           | SWT       | QA
    BARANOWSKI    | 3   | "Christian"             | "Baranowski"        | TM        | QA
  }
  
  teamsTable.rows {
    REF           | id  | title                   | description         | membersize
    QA            | 1   | "Quality Assurance"     | "Verifies software" | 3
  }
}

println("Jobtitle for SWD = " + sampleData.jobsTable.findWhere.id(SWD).getTitle())
println("Jobid for SWD = " + sampleData.jobsTable.findWhere.title(SWD).getId())
println("Teamtitle for id QA = " + sampleData.teamsTable.findWhere.id(QA).getTitle())
println("Foreign Jobid for Dennis = " + sampleData.personsTable.findWhere.firstName("Dennis").getJobId())
println("Foreign Teamid for Dennis = " + sampleData.personsTable.findWhere.firstName("Dennis").getTeamId())
println("Job SWD member count = " + sampleData.personsTable.findWhere.jobId(SWD).getRowCount())
println("Team QA member count = " + sampleData.personsTable.findWhere.teamId(QA).getRowCount())
