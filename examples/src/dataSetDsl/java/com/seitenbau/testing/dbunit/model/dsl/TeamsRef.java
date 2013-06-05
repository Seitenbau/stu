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
 * Reference to an Teams table row
 * <p>
 * Available fields:
 * <ul>
 *   <li>getId() - java.lang.Long
 *   </li>
 *   <li>getTitle() - java.lang.String
 *   </li>
 *   <li>getDescription() - java.lang.String
 *   </li>
 *   <li>getMembersize() - java.lang.Long
 *   </li>
 * </ul>
 */
public class TeamsRef extends DatabaseReference
{

  private final Map<PersonDatabaseBuilder, RowBuilder_Teams> builders;
  
  public TeamsRef()
  {
    builders = new HashMap<PersonDatabaseBuilder, RowBuilder_Teams>();
  }

  public java.lang.Long getId()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Teams row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query Id in TeamsRef");
    }
    return row.getId();
  } 

  public java.lang.String getTitle()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Teams row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query Title in TeamsRef");
    }
    return row.getTitle();
  } 

  public java.lang.String getDescription()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Teams row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query Description in TeamsRef");
    }
    return row.getDescription();
  } 

  public java.lang.Long getMembersize()
  {
    final PersonDatabaseBuilder scope = getScope();
    final RowBuilder_Teams row = builders.get(scope);
    if (row == null)
    {
      throw new IllegalStateException("No corresponding row to query Membersize in TeamsRef");
    }
    return row.getMembersize();
  } 
  
  void setBuilder(PersonDatabaseBuilder scope, RowBuilder_Teams builder)
  {
    RowBuilder_Teams lastBuilder = builders.put(scope, builder);
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
      otherBuilder.setTeamId(builder.getId());
      List<TeamsRef> otherList = getOrCreateList(ref.private_teamIdToMap, getScope());
      if (otherList.contains(this)) 
      {
        otherList.remove(this);
      }
    }
  }
  
  RowBuilder_Teams getBuilder(PersonDatabaseBuilder scope)
  {
    return builders.get(scope);
  }
  
  Map<PersonDatabaseBuilder, List<PersonsRef>> private_personsToMap = new HashMap<PersonDatabaseBuilder, List<PersonsRef>>();

  // depending on relation type with or without ellipse (...)
  /**
   * Assigns a team to the person
   * @param refs The references to associate with this one
   */
  public void personsTo(PersonsRef ... refs) 
  {
    PersonDatabaseBuilder scope = getScope();
    RowBuilder_Teams thisBuilder = getBuilder(scope);
    
    for (PersonsRef ref : refs) 
    {
      RowBuilder_Persons otherBuilder = ref.getBuilder(scope);
      
      // check if both row builders exist to resolve relation
      if (thisBuilder != null && otherBuilder != null) 
      {
        // foreign reference
        otherBuilder.setTeamId(thisBuilder.getId());
      } else {
        // at least one builder does not exist... add relation information
        // to the Refs where no builder exist.
        if (thisBuilder == null)
        {
          getOrCreateList(private_personsToMap, getScope()).add(ref);
        }
        if (otherBuilder == null) 
        {
          getOrCreateList(ref.private_teamIdToMap, getScope()).add(this);
        }
      }
    }
  }


  private PersonDatabaseBuilder getScope() 
  {
    IScope scope = ScopeRegistry.getCurrentScope("com.seitenbau.testing.dbunit.model.PersonDatabase");
    if (scope == null)
    {
      throw new IllegalStateException("No active context set in TeamsRef");
    }
    return (PersonDatabaseBuilder)scope;
  }

}
