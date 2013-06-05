package com.seitenbau.testing.dbunit.model.dsl;

import com.seitenbau.testing.dbunit.extend.DbUnitDatasetFactory;
import com.seitenbau.testing.dbunit.dsl.ScopeRegistry;
import com.seitenbau.testing.dbunit.dsl.IScope;
import org.dbunit.dataset.IDataSet;

import com.seitenbau.testing.dbunit.model.PersonDatabaseDataSet;
import com.seitenbau.testing.dbunit.model.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;

import groovy.lang.Closure;

/**
 * Represents a PersonDatabase Data Set Scope. Parses the DSL and builds
 * the datasets
 */
public class PersonDatabaseBuilder implements IScope, DbUnitDatasetFactory
{

  private final PersonDatabaseDataSet dataset;

  /**
   * The table containing the jobs of a great company
   */
  public final JobsTable jobsTable;
  
  // Getter for Groovy property access
  protected JobsTable getJobsTable()
  {
    return jobsTable;
  }
  /**
   * The table containing the teams of a great company
   */
  public final TeamsTable teamsTable;
  
  // Getter for Groovy property access
  protected TeamsTable getTeamsTable()
  {
    return teamsTable;
  }
  /**
   * The table containing the staff of a great company
   */
  public final PersonsTable personsTable;
  
  // Getter for Groovy property access
  protected PersonsTable getPersonsTable()
  {
    return personsTable;
  }
  public PersonDatabaseBuilder() {
    this(new PersonDatabaseDataSet() 
    {
      @Override
      public void initDataSet() { }
    });
  }
  
  public PersonDatabaseBuilder(PersonDatabaseDataSet dataset)
  {
    this.dataset = dataset;
    jobsTable = new JobsTable(this, dataset.table_Jobs);
    teamsTable = new TeamsTable(this, dataset.table_Teams);
    personsTable = new PersonsTable(this, dataset.table_Persons);
  }

  /**
   * Parses and integrates the tables to the data set
   * Supported tables:
   * <ul>
   *   <li>{@code jobsTable} - The table containing the jobs of a great company</li>
   *   <li>{@code teamsTable} - The table containing the teams of a great company</li>
   *   <li>{@code personsTable} - The table containing the staff of a great company</li>
   * </ul>
   *  
   * @param table Table data
   */
  public void tables(Closure<?> table)
  {
    ScopeRegistry.use(this);
    table.setDelegate(this);
    table.setResolveStrategy(Closure.DELEGATE_FIRST);
    table.call();
  }
  
  /**
   * Parses the defined relations and integrates them into the data set
   * @param relations The relations data
   */
  public void relations(Closure<?> relations)
  {
    ScopeRegistry.use(this);
    relations.call();
  }
 
  
  /**
   * Replaces all occurrences of a Jobs reference with the represented value
   * @param reference The reference, which can be resolved now
   * @param row The row containing the represented values 
   */
  void replaceJobsRefWithId(JobsRef reference, RowBuilder_Jobs row)
  {
    // Ref: Persons on JobId
    for (RowBuilder_Persons builder : personsTable.getTableModel().rows) {
      if (builder.getJobIdRaw() == reference)
      {
        builder.setJobId(row.getId());
      }
    }
    
  }
  
  /**
   * Replaces all occurrences of a Teams reference with the represented value
   * @param reference The reference, which can be resolved now
   * @param row The row containing the represented values 
   */
  void replaceTeamsRefWithId(TeamsRef reference, RowBuilder_Teams row)
  {
    // Ref: Persons on TeamId
    for (RowBuilder_Persons builder : personsTable.getTableModel().rows) {
      if (builder.getTeamIdRaw() == reference)
      {
        builder.setTeamId(row.getId());
      }
    }
    
  }
  
  /**
   * Replaces all occurrences of a Persons reference with the represented value
   * @param reference The reference, which can be resolved now
   * @param row The row containing the represented values 
   */
  void replacePersonsRefWithId(PersonsRef reference, RowBuilder_Persons row)
  {
  }


  /**
   * Allows Jobs table data access using references
   * @param ref The reference which represents tha data
   * @return The accessor object 
   */
  public RowBuilder_Jobs ref(JobsRef ref)
  {
    return ref.getBuilder(this);
  }

  /**
   * Allows Teams table data access using references
   * @param ref The reference which represents tha data
   * @return The accessor object 
   */
  public RowBuilder_Teams ref(TeamsRef ref)
  {
    return ref.getBuilder(this);
  }

  /**
   * Allows Persons table data access using references
   * @param ref The reference which represents tha data
   * @return The accessor object 
   */
  public RowBuilder_Persons ref(PersonsRef ref)
  {
    return ref.getBuilder(this);
  }

  @Override
  public String getScopeIdentifier()
  {
    return "com.seitenbau.testing.dbunit.model.PersonDatabase";
  }
  
  /**
   * Create a DBUnit dataset.
   * @return The Dbunit dataset
   */
  @Override
  public IDataSet createDBUnitDataSet() 
  {
    return dataset.createDBUnitDataSet();
  }
  
}