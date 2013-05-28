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
    for (RowBuilder_Persons builder : personsTable.getAllRowBuilders()) {
      if (builder.getJobIdRaw() == reference)
      {
        builder.setJobId(row.getId());
      }
    }
    
  }

  void replaceTeamsRefWithId(TeamsRef reference, RowBuilder_Teams row)
  {
    // Ref: Persons on TeamId
    for (RowBuilder_Persons builder : personsTable.getAllRowBuilders()) {
      if (builder.getTeamIdRaw() == reference)
      {
        builder.setTeamId(row.getId());
      }
    }
    
  }

  void replacePersonsRefWithId(PersonsRef reference, RowBuilder_Persons row)
  {
  }


  public JobsRefAccess reference(JobsRef ref)
  {
    return new JobsRefAccess(ref, this);
  }
  
  public static class JobsRefAccess
  {
    private final JobsRef _ref;
    private final STUDSL _scope;
    
    private JobsRefAccess(JobsRef ref, STUDSL scope)
    {
      _ref = ref;
      _scope = scope;
    }

    public java.lang.Long id()
    {
      return _ref.getBuilder(_scope).getId();
    }

    public java.lang.String title()
    {
      return _ref.getBuilder(_scope).getTitle();
    }

    public java.lang.String description()
    {
      return _ref.getBuilder(_scope).getDescription();
    }
  }

  public TeamsRefAccess reference(TeamsRef ref)
  {
    return new TeamsRefAccess(ref, this);
  }
  
  public static class TeamsRefAccess
  {
    private final TeamsRef _ref;
    private final STUDSL _scope;
    
    private TeamsRefAccess(TeamsRef ref, STUDSL scope)
    {
      _ref = ref;
      _scope = scope;
    }

    public java.lang.Long id()
    {
      return _ref.getBuilder(_scope).getId();
    }

    public java.lang.String title()
    {
      return _ref.getBuilder(_scope).getTitle();
    }

    public java.lang.String description()
    {
      return _ref.getBuilder(_scope).getDescription();
    }

    public java.lang.Long membersize()
    {
      return _ref.getBuilder(_scope).getMembersize();
    }
  }

  public PersonsRefAccess reference(PersonsRef ref)
  {
    return new PersonsRefAccess(ref, this);
  }
  
  public static class PersonsRefAccess
  {
    private final PersonsRef _ref;
    private final STUDSL _scope;
    
    private PersonsRefAccess(PersonsRef ref, STUDSL scope)
    {
      _ref = ref;
      _scope = scope;
    }

    public java.lang.Long id()
    {
      return _ref.getBuilder(_scope).getId();
    }

    public java.lang.String firstName()
    {
      return _ref.getBuilder(_scope).getFirstName();
    }

    public java.lang.String name()
    {
      return _ref.getBuilder(_scope).getName();
    }

    public java.lang.Long jobId()
    {
      return _ref.getBuilder(_scope).getJobId();
    }

    public java.lang.Long teamId()
    {
      return _ref.getBuilder(_scope).getTeamId();
    }
  }
  
  public IDataSet createDataSet() 
  {
    return dataset.createDBUnitDataSet();
  }
}