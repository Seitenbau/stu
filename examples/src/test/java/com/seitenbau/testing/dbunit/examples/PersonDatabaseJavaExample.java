package com.seitenbau.testing.dbunit.examples;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*;

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet;
import com.seitenbau.testing.dbunit.dsl.ScopeRegistry;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.util.Action;
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

    ScopeRegistry.use(dataSet);
    println("Dennis' last name", KAULBERSCH.getName());
    println("TM title", TM.getTitle());
    println("Persons Row Count", dataSet.personsTable.getRowCount());
    
    println("Persons filtered by name length", dataSet.personsTable.find(LEN_FILTER).getRowCount());
    
    System.out.println("List of first names");
    dataSet.personsTable.foreach(FIRST_NAME_PRINTER);
    System.out.println("(end of list)");    
  }
  
  private static final Filter<RowBuilder_Persons> LEN_FILTER = new Filter<RowBuilder_Persons>() 
      {

        @Override
        public boolean accept(RowBuilder_Persons value)
        {
          return (value.getFirstName().length() < 8);
        }
        
      };

  private static final Action<RowBuilder_Persons> FIRST_NAME_PRINTER = new Action<RowBuilder_Persons>()
      {

        @Override
        public void call(RowBuilder_Persons value)
        {
          System.out.println("- " + value.getFirstName());
        }
    
      };

  
  private static void println(String message, Object value)
  {
    System.out.println(message + " = " + value);
  }
}
