package com.seitenbau.testing.dbunit.examples;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*;
import static com.seitenbau.testing.dbunit.dsl.ScopeRegistry.use;

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;

public class PersonTableDataSetDemo
{

  public static void main(String[] args)
  {
    DemoGroovyDataSet dataSet = new DemoGroovyDataSet();
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
    println("TM title", TM.getTitle());
    println("Persons Row Count", dataSet.personsTable.getRowCount());
  }

  private static void println(String message, Object value)
  {
    System.out.println(message + " = " + value);
  }
}