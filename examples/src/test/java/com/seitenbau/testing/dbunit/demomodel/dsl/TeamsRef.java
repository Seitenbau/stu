package com.seitenbau.testing.dbunit.demomodel.dsl;

import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;
import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowBuilder_Persons;

/**
 * Reference to an Teams table row
 */
public class TeamsRef extends DatabaseReference {

  private final Map<STUDSL, RowBuilder_Teams> builders;
  
  private static final ThreadLocal<STUDSL> threadScope = new ThreadLocal<STUDSL>();

  public TeamsRef()
  {
    builders = new HashMap<STUDSL, RowBuilder_Teams>();
  }

  public java.lang.Long getId()
  {
    final STUDSL scope = threadScope.get();
    if (scope == null)
    {
      throw new IllegalStateException("No active context to query Id in TeamsRef");
    } 
    final RowBuilder_Teams row = builders.get(scope);
    if (scope == null)
    {
      throw new IllegalStateException("No corresponding row to query Id in TeamsRef");
    }
    return row.getId();
  } 

  public java.lang.String getTitle()
  {
    final STUDSL scope = threadScope.get();
    if (scope == null)
    {
      throw new IllegalStateException("No active context to query Title in TeamsRef");
    } 
    final RowBuilder_Teams row = builders.get(scope);
    if (scope == null)
    {
      throw new IllegalStateException("No corresponding row to query Title in TeamsRef");
    }
    return row.getTitle();
  } 

  public java.lang.String getDescription()
  {
    final STUDSL scope = threadScope.get();
    if (scope == null)
    {
      throw new IllegalStateException("No active context to query Description in TeamsRef");
    } 
    final RowBuilder_Teams row = builders.get(scope);
    if (scope == null)
    {
      throw new IllegalStateException("No corresponding row to query Description in TeamsRef");
    }
    return row.getDescription();
  } 

  public java.lang.Long getMembersize()
  {
    final STUDSL scope = threadScope.get();
    if (scope == null)
    {
      throw new IllegalStateException("No active context to query Membersize in TeamsRef");
    } 
    final RowBuilder_Teams row = builders.get(scope);
    if (scope == null)
    {
      throw new IllegalStateException("No corresponding row to query Membersize in TeamsRef");
    }
    return row.getMembersize();
  } 
  
  static void setThreadLocalScope(STUDSL scope) 
  {
    threadScope.set(scope);    
  }
  
  static void unsetThreadLocalScope() 
  {
    threadScope.remove();
  }
  
  void setBuilder(STUDSL scope, RowBuilder_Teams builder)
  {
    RowBuilder_Teams lastBuilder = builders.put(scope, builder);
    if (lastBuilder != null && lastBuilder != builder) {
      throw new RuntimeException("Builder cannot be redefined");
    }

    List<PersonsRef> personsToListScoped = getOrCreateList(private_personsToMap, threadScope);
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
      List<TeamsRef> otherList = getOrCreateList(ref.private_teamIdToMap, threadScope);
      if (otherList.contains(this)) 
      {
        otherList.remove(this);
      }
    }
  }
  
  RowBuilder_Teams getBuilder(STUDSL scope)
  {
    return builders.get(scope);
  }
  
  Map<STUDSL, List<PersonsRef>> private_personsToMap = new HashMap<STUDSL, List<PersonsRef>>();

  // depending on relation type with or without ellipse (...)
  /**
   * Assigns a team to the person
   * @param refs The references to associate with this one
   */
  public void personsTo(PersonsRef ... refs) {
    STUDSL scope = threadScope.get();
    RowBuilder_Teams thisBuilder = getBuilder(scope);
    
    for (PersonsRef ref : refs) 
    {
      RowBuilder_Persons otherBuilder = ref.getBuilder(scope);
      
      // check if both row builders exist to resolve relation
      if (thisBuilder != null && otherBuilder != null) 
      {
        // foreign reference
        otherBuilder.setTeamId(thisBuilder.getId());
      }
      else {
        // at least one builder does not exist... add relation information
        // to the Refs where no builder exist.
        if (thisBuilder == null)
        {
          getOrCreateList(private_personsToMap, threadScope).add(ref);
        }
        if (otherBuilder == null) {
          getOrCreateList(ref.private_teamIdToMap, threadScope).add(this);
        }
      }
    }
  }

}
