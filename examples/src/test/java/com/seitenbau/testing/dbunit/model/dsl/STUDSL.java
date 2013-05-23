package com.seitenbau.testing.dbunit.model.dsl;

import java.util.Map.Entry;

import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.model.STUDataSet;
import com.seitenbau.testing.dbunit.model.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;

import groovy.lang.Closure;

public class STUDSL {

  STUDataSet dataset = new STUDataSet() {
      @Override
      public void initDataSet() { }
    };

  JobsTable jobsTable = new JobsTable(dataset.table_Jobs);
  
  TeamsTable teamsTable = new TeamsTable(dataset.table_Teams);
  
  PersonsTable personsTable = new PersonsTable(dataset.table_Persons);
  
  public void tables(Closure<?> table) {
    table.setDelegate(this);
    table.setResolveStrategy(Closure.DELEGATE_FIRST);
    table.call();
  }
  
  public void relations(Closure<?> relations) {
    relations.call();
    
    updateRelationsJobsRef();
    updateRelationsTeamsRef();
    updateRelationsPersonsRef();
  }
 
  private void updateRelationsJobsRef() {
    for (Entry<JobsRef, RowBuilder_Jobs> entry : jobsTable.getUsedRefs().entrySet())
    {
      JobsRef jobsRef = entry.getKey(); 
      RowBuilder_Jobs jobs = entry.getValue();
      for (PersonsRef personsRef : jobsRef.isJobOfList)
      {
        System.out.println("RELATION: Updated Row in Persons");
        RowBuilder_Persons persons = personsTable.getUsedRefs().get(personsRef);
        java.lang.Long value = jobs.getId();
        persons.setJobId(value);
      }
      
      jobsRef.isJobOfList.clear(); 
    }
  }

  private void updateRelationsTeamsRef() {
    for (Entry<TeamsRef, RowBuilder_Teams> entry : teamsTable.getUsedRefs().entrySet())
    {
      TeamsRef teamsRef = entry.getKey(); 
      RowBuilder_Teams teams = entry.getValue();
      for (PersonsRef personsRef : teamsRef.hasMemberList)
      {
        System.out.println("RELATION: Updated Row in Persons");
        RowBuilder_Persons persons = personsTable.getUsedRefs().get(personsRef);
        java.lang.Long value = teams.getId();
        persons.setTeamId(value);
      }
      
      teamsRef.hasMemberList.clear(); 
    }
  }

  private void updateRelationsPersonsRef() {
    for (Entry<PersonsRef, RowBuilder_Persons> entry : personsTable.getUsedRefs().entrySet())
    {
      PersonsRef personsRef = entry.getKey();
      RowBuilder_Persons persons = entry.getValue();
      for (JobsRef jobsRef : personsRef.worksAsList)
      {
        System.out.println("RELATION: Updated Row in Persons");
        RowBuilder_Jobs jobs = jobsTable.getUsedRefs().get(jobsRef);
        java.lang.Long value = jobs.getId();
        persons.setJobId(value);
      }
      
      personsRef.worksAsList.clear();
    } 
    for (Entry<PersonsRef, RowBuilder_Persons> entry : personsTable.getUsedRefs().entrySet())
    {
      PersonsRef personsRef = entry.getKey();
      RowBuilder_Persons persons = entry.getValue();
      for (TeamsRef teamsRef : personsRef.worksInList)
      {
        System.out.println("RELATION: Updated Row in Persons");
        RowBuilder_Teams teams = teamsTable.getUsedRefs().get(teamsRef);
        java.lang.Long value = teams.getId();
        persons.setTeamId(value);
      }
      
      personsRef.worksInList.clear();
    } 
  }


  public IDataSet createDataSet() {
    return dataset.createDBUnitDataSet();
  }
}