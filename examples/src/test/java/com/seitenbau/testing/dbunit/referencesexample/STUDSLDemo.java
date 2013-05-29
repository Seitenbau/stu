package com.seitenbau.testing.dbunit.referencesexample;

import static com.seitenbau.testing.dbunit.referencesexample.STUDSLRefs.*;

import static com.seitenbau.testing.dbunit.dsl.ScopeRegistry.use;


public class STUDSLDemo
{

  public static void main(String[] args) 
  {
    STUDSLDataSet sampleData = new STUDSLDataSet();
    println("Jobtitle for SWD", sampleData.jobsTable.findWhere.id(SWD).getTitle());
    println("Jobid for SWD", sampleData.jobsTable.findWhere.title(SWD).getId());
    println("Teamtitle for id QA", sampleData.teamsTable.findWhere.id(QA).getTitle());
    println("Foreign Jobid for Dennis", sampleData.personsTable.findWhere.firstName("Dennis").getJobId());
    println("Foreign Teamid for Dennis", sampleData.personsTable.findWhere.firstName("Dennis").getTeamId());
    println("Job SWD member count", sampleData.personsTable.findWhere.jobId(SWD).getRowCount());
    println("Team QA member size", sampleData.teamsTable.findWhere.id(QA).getMembersize());
    println("Team QA member count", sampleData.personsTable.findWhere.teamId(QA).getRowCount());
    
    use(sampleData);
    println("Dennis' last name", KAULBERSCH.getName());
  }
  
  private static void println(String message, Object value) 
  {
    System.out.println(message + " = " + value);
  }
}
