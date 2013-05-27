package com.seitenbau.testing.dbunit.demomodel.dsl;

import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.demomodel.STUDataSet;
import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowBuilder_Persons;

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
    setReferencesContext();
    table.setDelegate(this);
    table.setResolveStrategy(Closure.DELEGATE_FIRST);
    table.call();
    unsetReferencesContext();
  }
  
  public void relations(Closure<?> relations)
  {
    setReferencesContext();
    relations.call();
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