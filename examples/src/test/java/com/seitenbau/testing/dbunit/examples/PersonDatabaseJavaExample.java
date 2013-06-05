package com.seitenbau.testing.dbunit.examples;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.KAULBERSCH;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.QA;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.SWD;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.TM;
import static com.seitenbau.testing.dbunit.dsl.ScopeRegistry.use;

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.util.Filter;

public class PersonDatabaseJavaExample
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
    println("Access Team with membersize",dataSet.teamsTable.findWhere.membersize(3).getTitle());

    use(dataSet);
    println("Dennis' last name", KAULBERSCH.getName());
    println("TM title", TM.getTitle());
    println("Persons Row Count", dataSet.personsTable.getRowCount());
    
    println("Persons filtered by name length", dataSet.personsTable.find(LEN_FILTER).getRowCount());
  }
  
  private static final Filter<RowBuilder_Persons> LEN_FILTER = new Filter<RowBuilder_Persons>() {

    @Override
    public boolean accept(RowBuilder_Persons value)
    {
      return (value.getFirstName().length() < 8);
    }
    
  };

  private static void println(String message, Object value)
  {
    System.out.println(message + " = " + value);
  }
}
