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
 * Reference to an Jobs table row
 * <p>
 * Available fields:
 * <ul>
 *   <li>getId() - java.lang.Long
 *   </li>
 *   <li>getTitle() - java.lang.String
   *    <br>The job title
 *   </li>
 *   <li>getDescription() - java.lang.String
   *    <br>The description of the job
 *   </li>
 * </ul>
 */
public class JobsRef extends DatabaseReference
{

  private final Map<PersonDatabaseBuilder, RowBuilder_Jobs> builders;
  
  public JobsRef()
  {
    builders = new HashMap<PersonDatabaseBuilder, RowBuilder_Jobs>();
  }

  public java.lang.Long getId()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Jobs row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query Id in JobsRef");
    }
    return row.getId();
  } 

  /**
   * The job title
   * @return The value
   */
  public java.lang.String getTitle()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Jobs row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query Title in JobsRef");
    }
    return row.getTitle();
  } 

  /**
   * The description of the job
   * @return The value
   */
  public java.lang.String getDescription()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Jobs row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query Description in JobsRef");
    }
    return row.getDescription();
  } 
  
  void setBuilder(PersonDatabaseBuilder scope, RowBuilder_Jobs builder)
  {
    RowBuilder_Jobs lastBuilder = builders.put(scope, builder);
    if (lastBuilder != null && lastBuilder != builder)
    {
      throw new RuntimeException("Builder cannot be redefined");
    }

    List<PersonsRef> personsToListScoped = getOrCreateList(private_personsToMap, getScope());
    for (Iterator<PersonsRef> it = personsToListScoped.iterator(); it.hasNext(); )
    {
      PersonsRef ref = it.next();
      it.remove();	// remove iterator because if it will not resolve now, it will never
      RowBuilder_Persons otherBuilder = ref.getBuilder(scope);
      if (otherBuilder == null) 
      {
        continue;
      }
      otherBuilder.setJobId(builder.getId());
      List<JobsRef> otherList = getOrCreateList(ref.private_jobIdToMap, getScope());
      if (otherList.contains(this)) 
      {
        otherList.remove(this);
      }
    }
  }
  
  RowBuilder_Jobs getBuilder(PersonDatabaseBuilder scope)
  {
    return builders.get(scope);
  }
  
  Map<PersonDatabaseBuilder, List<PersonsRef>> private_personsToMap = new HashMap<PersonDatabaseBuilder, List<PersonsRef>>();

  // depending on relation type with or without ellipse (...)
  /**
   * Assigns a job to the person
   * @param refs The references to associate with this one
   */
  public void personsTo(PersonsRef ... refs) 
  {
    PersonDatabaseBuilder scope = getScope();
    RowBuilder_Jobs thisBuilder = getBuilder(scope);
    
    for (PersonsRef ref : refs) 
    {
      RowBuilder_Persons otherBuilder = ref.getBuilder(scope);
      
      // check if both row builders exist to resolve relation
      if (thisBuilder != null && otherBuilder != null) 
      {
        // foreign reference
        otherBuilder.setJobId(thisBuilder.getId());
      } else {
        // at least one builder does not exist... add relation information
        // to the Refs where no builder exist.
        if (thisBuilder == null)
        {
          getOrCreateList(private_personsToMap, getScope()).add(ref);
        }
        if (otherBuilder == null) 
        {
          getOrCreateList(ref.private_jobIdToMap, getScope()).add(this);
        }
      }
    }
  }


  private PersonDatabaseBuilder getScope() 
  {
    IScope scope = ScopeRegistry.getCurrentScope("com.seitenbau.testing.dbunit.model.PersonDatabase");
    if (scope == null)
    {
      throw new IllegalStateException("No active context set in JobsRef");
    }
    return (PersonDatabaseBuilder)scope;
  }

}
