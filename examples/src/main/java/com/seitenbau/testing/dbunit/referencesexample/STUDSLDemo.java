package com.seitenbau.testing.dbunit.referencesexample;

import static com.seitenbau.testing.dbunit.referencesexample.STUDSLRefs.QA;
import static com.seitenbau.testing.dbunit.referencesexample.STUDSLRefs.SWD;

public class STUDSLDemo
{

  public static void main(String[] args) 
  {
    STUDSLDataSet sampleData = new STUDSLDataSet();
    println("Jobtitle for SWD", sampleData.getJobsTable().findWhere.id(SWD).getTitle());
    println("Jobid for SWD", sampleData.getJobsTable().findWhere.title(SWD).getId());
    println("Teamtitle for id QA", sampleData.getTeamsTable().findWhere.id(QA).getTitle());
    println("Foreign Jobid for Dennis", sampleData.getPersonsTable().findWhere.firstName("Dennis").getJobId());
    println("Foreign Teamid for Dennis", sampleData.getPersonsTable().findWhere.firstName("Dennis").getTeamId());
    println("Job SWD member count", sampleData.getPersonsTable().findWhere.jobId(SWD).getRowCount());
    println("Team QA member count", sampleData.getPersonsTable().findWhere.teamId(QA).getRowCount());
  }
  
  private static void println(String message, Object value) 
  {
    System.out.println(message + " = " + value);
  }
}
