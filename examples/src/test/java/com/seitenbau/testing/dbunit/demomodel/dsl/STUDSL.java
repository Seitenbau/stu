package com.seitenbau.testing.dbunit.demomodel.dsl;

import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.demomodel.STUDataSet;
import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.demomodel.dsl.JobsRef;
import com.seitenbau.testing.dbunit.demomodel.dsl.JobsTable;
import com.seitenbau.testing.dbunit.demomodel.dsl.PersonsRef;
import com.seitenbau.testing.dbunit.demomodel.dsl.PersonsTable;
import com.seitenbau.testing.dbunit.demomodel.dsl.TeamsRef;
import com.seitenbau.testing.dbunit.demomodel.dsl.TeamsTable;

import groovy.lang.Closure;

public class STUDSL 
{

  STUDataSet dataset = new STUDataSet() 
  {
    @Override
    public void initDataSet() { }
  };

  JobsTable jobsTable = new JobsTable(this, dataset.table_Jobs);
  
  TeamsTable teamsTable = new TeamsTable(this, dataset.table_Teams);
  
  PersonsTable personsTable = new PersonsTable(this, dataset.table_Persons);
  
  public void tables(Closure<?> table)
  {
    table.setDelegate(this);
    table.setResolveStrategy(Closure.DELEGATE_FIRST);
    table.call();
  }
  
  public void relations(Closure<?> relations)
  {
    setReferencesContext();
    relations.call();
    resolveRelations();
    unsetReferencesContext();
  }
  
  private void setReferencesContext()
  {
    JobsRef.setThreadLocalScope(this);
    TeamsRef.setThreadLocalScope(this);
    PersonsRef.setThreadLocalScope(this);
  }
  
  private void unsetReferencesContext()
  {
    JobsRef.unsetThreadLocalScope();
    TeamsRef.unsetThreadLocalScope();
    PersonsRef.unsetThreadLocalScope();
  }

  private void resolveRelations()
  {
    updateRelationsJobsRef();
    updateRelationsTeamsRef();
    updateRelationsPersonsRef();
  }
 
  private void updateRelationsJobsRef()
  {
  

    for (JobsRef jobsRef : jobsTable.getUsedRefs())
    {
      RowBuilder_Jobs jobs = jobsRef.getBuilder(this);
      if (jobsRef.personsToList.get(this) != null)
      {
        for (PersonsRef personsRef : jobsRef.personsToList.get(this))
        {
          System.out.println("RELATION: Updated Row in Persons");
          RowBuilder_Persons persons = personsRef.getBuilder(this);
          java.lang.Long value = jobs.getId();
          persons.setJobId(value);
        }
      }

      jobsRef.personsToList.clear(); 
    }
  }
  private void updateRelationsTeamsRef()
  {
  

    for (TeamsRef teamsRef : teamsTable.getUsedRefs())
    {
      RowBuilder_Teams teams = teamsRef.getBuilder(this);
      if (teamsRef.personsToList.get(this) != null)
      {
        for (PersonsRef personsRef : teamsRef.personsToList.get(this))
        {
          System.out.println("RELATION: Updated Row in Persons");
          RowBuilder_Persons persons = personsRef.getBuilder(this);
          java.lang.Long value = teams.getId();
          persons.setTeamId(value);
        }
      }

      teamsRef.personsToList.clear(); 
    }
  }
  private void updateRelationsPersonsRef()
  {
  

  }

  void replaceJobsRefWithId(JobsRef reference, RowBuilder_Jobs row)
  {
    // Ref: Persons on JobId
    for (PersonsRef personsRef : personsTable.getUsedRefs()) {
      RowBuilder_Persons builder = personsRef.getBuilder(this);
      builder.setJobId(row.getId());
    }
    
  }

  void replaceTeamsRefWithId(TeamsRef reference, RowBuilder_Teams row)
  {
    // Ref: Persons on TeamId
    for (PersonsRef personsRef : personsTable.getUsedRefs()) {
      RowBuilder_Persons builder = personsRef.getBuilder(this);
      builder.setTeamId(row.getId());
    }
    
  }

  void replacePersonsRefWithId(PersonsRef reference, RowBuilder_Persons row)
  {
  }

  public IDataSet createDataSet() 
  {
    return dataset.createDBUnitDataSet();
  }
}