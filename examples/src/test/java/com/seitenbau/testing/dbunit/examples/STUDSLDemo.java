package com.seitenbau.testing.dbunit.examples;


import com.seitenbau.testing.dbunit.dataset.STUDataSet;

import static com.seitenbau.testing.dbunit.dsl.ScopeRegistry.use;
import static com.seitenbau.testing.dbunit.examples.STUDSLRefs.*;


public class STUDSLDemo
{

  public static void main(String[] args) 
  {
    STUDataSet dataSet = new STUDataSet();
    println("Jobtitle for SWD", dataSet.jobsTable.findWhere.id(SWD).getTitle());
    println("Jobid for SWD", dataSet.jobsTable.findWhere.title(SWD).getId());
    println("Teamtitle for id QA", dataSet.teamsTable.findWhere.id(QA).getTitle());
    println("Foreign Jobid for Dennis", dataSet.personsTable.findWhere.firstName("Dennis").getJobId());
    println("Foreign Teamid for Dennis", dataSet.personsTable.findWhere.firstName("Dennis").getTeamId());
    println("Job SWD member count", dataSet.personsTable.findWhere.jobId(SWD).getRowCount());
    println("Team QA member size", dataSet.teamsTable.findWhere.id(QA).getMembersize());
    println("Team QA member count", dataSet.personsTable.findWhere.teamId(QA).getRowCount());
    
    use(dataSet);
    println("Dennis' last name", KAULBERSCH.getName());
  }
  
  private static void println(String message, Object value) 
  {
    System.out.println(message + " = " + value);
  }
}
