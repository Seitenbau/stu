package com.seitenbau.testing.dbunit.model.dsl;

import com.seitenbau.testing.dbunit.dsl.IScope;
import com.seitenbau.testing.dbunit.dsl.ScopeRegistry;

import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;
import com.seitenbau.testing.dbunit.model.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;

/**
 * Reference to an Persons table row
 * <p>
 * Available fields:
 * <ul>
 *   <li>getId() - java.lang.Long
 *   </li>
 *   <li>getFirstName() - java.lang.String
 *   </li>
 *   <li>getName() - java.lang.String
   *    <br>Actually this column represents the last name of a person
 *   </li>
 *   <li>getJobId() - java.lang.Long
 *   </li>
 *   <li>getTeamId() - java.lang.Long
 *   </li>
 * </ul>
 */
public class PersonsRef extends DatabaseReference
{

  private final Map<PersonDatabaseBuilder, RowBuilder_Persons> builders;
  
  public PersonsRef()
  {
    builders = new HashMap<PersonDatabaseBuilder, RowBuilder_Persons>();
  }

  public java.lang.Long getId()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Persons row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query Id in PersonsRef");
    }
    return row.getId();
  } 

  public java.lang.String getFirstName()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Persons row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query FirstName in PersonsRef");
    }
    return row.getFirstName();
  } 

  /**
   * Actually this column represents the last name of a person
   * @return The value
   */
  public java.lang.String getName()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Persons row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query Name in PersonsRef");
    }
    return row.getName();
  } 

  public java.lang.Long getJobId()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Persons row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query JobId in PersonsRef");
    }
    return row.getJobId();
  } 

  public java.lang.Long getTeamId()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Persons row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query TeamId in PersonsRef");
    }
    return row.getTeamId();
  } 
  
  void setBuilder(PersonDatabaseBuilder scope, RowBuilder_Persons builder)
  {
    RowBuilder_Persons lastBuilder = builders.put(scope, builder);
    if (lastBuilder != null && lastBuilder != builder)
    {
      throw new RuntimeException("Builder cannot be redefined");
    }

    List<JobsRef> jobIdToListScoped = getOrCreateList(private_jobIdToMap, getScope());
    for (Iterator<JobsRef> it = jobIdToListScoped.iterator(); it.hasNext(); )
    {
      JobsRef ref = it.next();
      it.remove();	// remove iterator because if it will not resolve now, it will never
      RowBuilder_Jobs otherBuilder = ref.getBuilder(scope);
      if (otherBuilder == null) 
      {
        continue;
      }
      builder.setJobId(otherBuilder.getId());
      List<PersonsRef> otherList = getOrCreateList(ref.private_personsToMap, getScope());
      if (otherList.contains(this)) 
      {
        otherList.remove(this);
      }
    }

    List<TeamsRef> teamIdToListScoped = getOrCreateList(private_teamIdToMap, getScope());
    for (Iterator<TeamsRef> it = teamIdToListScoped.iterator(); it.hasNext(); )
    {
      TeamsRef ref = it.next();
      it.remove();	// remove iterator because if it will not resolve now, it will never
      RowBuilder_Teams otherBuilder = ref.getBuilder(scope);
      if (otherBuilder == null) 
      {
        continue;
      }
      builder.setTeamId(otherBuilder.getId());
      List<PersonsRef> otherList = getOrCreateList(ref.private_personsToMap, getScope());
      if (otherList.contains(this)) 
      {
        otherList.remove(this);
      }
    }
  }
  
  RowBuilder_Persons getBuilder(PersonDatabaseBuilder scope)
  {
    return builders.get(scope);
  }
  
  Map<PersonDatabaseBuilder, List<JobsRef>> private_jobIdToMap = new HashMap<PersonDatabaseBuilder, List<JobsRef>>();

  // depending on relation type with or without ellipse (...)
  /**
   * 
   * @param refs The references to associate with this one
   */
  public void jobIdTo(JobsRef ... refs) 
  {
    PersonDatabaseBuilder scope = getScope();
    RowBuilder_Persons thisBuilder = getBuilder(scope);
    
    for (JobsRef ref : refs) 
    {
      RowBuilder_Jobs otherBuilder = ref.getBuilder(scope);
      
      // check if both row builders exist to resolve relation
      if (thisBuilder != null && otherBuilder != null) 
      {
        thisBuilder.setJobId(otherBuilder.getId());
      } else {
        // at least one builder does not exist... add relation information
        // to the Refs where no builder exist.
        if (thisBuilder == null)
        {
          getOrCreateList(private_jobIdToMap, getScope()).add(ref);
        }
        if (otherBuilder == null) 
        {
          getOrCreateList(ref.private_personsToMap, getScope()).add(this);
        }
      }
    }
  }

  Map<PersonDatabaseBuilder, List<TeamsRef>> private_teamIdToMap = new HashMap<PersonDatabaseBuilder, List<TeamsRef>>();

  // depending on relation type with or without ellipse (...)
  /**
   * 
   * @param refs The references to associate with this one
   */
  public void teamIdTo(TeamsRef ... refs) 
  {
    PersonDatabaseBuilder scope = getScope();
    RowBuilder_Persons thisBuilder = getBuilder(scope);
    
    for (TeamsRef ref : refs) 
    {
      RowBuilder_Teams otherBuilder = ref.getBuilder(scope);
      
      // check if both row builders exist to resolve relation
      if (thisBuilder != null && otherBuilder != null) 
      {
        thisBuilder.setTeamId(otherBuilder.getId());
      } else {
        // at least one builder does not exist... add relation information
        // to the Refs where no builder exist.
        if (thisBuilder == null)
        {
          getOrCreateList(private_teamIdToMap, getScope()).add(ref);
        }
        if (otherBuilder == null) 
        {
          getOrCreateList(ref.private_personsToMap, getScope()).add(this);
        }
      }
    }
  }


  private PersonDatabaseBuilder getScope() 
  {
    IScope scope = ScopeRegistry.getCurrentScope("com.seitenbau.testing.dbunit.model.PersonDatabase");
    if (scope == null)
    {
      throw new IllegalStateException("No active context set in PersonsRef");
    }
    return (PersonDatabaseBuilder)scope;
  }

}
