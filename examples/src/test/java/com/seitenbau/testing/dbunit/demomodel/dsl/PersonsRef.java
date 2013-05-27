package com.seitenbau.testing.dbunit.demomodel.dsl;

import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;
import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowBuilder_Persons;

public class PersonsRef extends DatabaseReference {

  private final Map<STUDSL, RowBuilder_Persons> builders;
  
  public PersonsRef()
  {
    builders = new HashMap<STUDSL, RowBuilder_Persons>();
  }
  
  private static final ThreadLocal<STUDSL> threadScope = new ThreadLocal<STUDSL>();
  
  static void setThreadLocalScope(STUDSL scope) 
  {
    threadScope.set(scope);    
  }
  
  static void unsetThreadLocalScope() 
  {
    threadScope.remove();
  }
  
  void setBuilder(STUDSL scope, RowBuilder_Persons builder)
  {
    RowBuilder_Persons lastBuilder = builders.put(scope, builder);
    if (lastBuilder != null && lastBuilder != builder) {
      throw new RuntimeException("Builder cannot be redefined");
    }

    List<JobsRef> jobIdToListScoped = getOrCreateList(private_jobIdToMap, threadScope);
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
      List<PersonsRef> otherList = getOrCreateList(ref.private_personsToMap, threadScope);
      if (otherList.contains(this)) 
      {
        otherList.remove(this);
      }
    }

    List<TeamsRef> teamIdToListScoped = getOrCreateList(private_teamIdToMap, threadScope);
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
      List<PersonsRef> otherList = getOrCreateList(ref.private_personsToMap, threadScope);
      if (otherList.contains(this)) 
      {
        otherList.remove(this);
      }
    }
  }
  
  RowBuilder_Persons getBuilder(STUDSL scope)
  {
    return builders.get(scope);
  }
  
  Map<STUDSL, List<JobsRef>> private_jobIdToMap = new HashMap<STUDSL, List<JobsRef>>();

  // depending on relation type with or without ellipse (...)
  void jobIdTo(JobsRef ... refs) {
    STUDSL scope = threadScope.get();
    RowBuilder_Persons thisBuilder = getBuilder(scope);
    
    for (JobsRef ref : refs) 
    {
      RowBuilder_Jobs otherBuilder = ref.getBuilder(scope);
      
      // check if both row builders exist to resolve relation
      if (thisBuilder != null && otherBuilder != null) 
      {
        // simple reference
        thisBuilder.setJobId(otherBuilder.getId());
      }
      else {
        // at least one builder does not exist... at relation information
        // to the Refs where no builder exist.
        if (thisBuilder == null)
        {
          getOrCreateList(private_jobIdToMap, threadScope).add(ref);
        }
        if (otherBuilder == null) {
          getOrCreateList(ref.private_personsToMap, threadScope).add(this);
        }
      }
    }
  }

  Map<STUDSL, List<TeamsRef>> private_teamIdToMap = new HashMap<STUDSL, List<TeamsRef>>();

  // depending on relation type with or without ellipse (...)
  void teamIdTo(TeamsRef ... refs) {
    STUDSL scope = threadScope.get();
    RowBuilder_Persons thisBuilder = getBuilder(scope);
    
    for (TeamsRef ref : refs) 
    {
      RowBuilder_Teams otherBuilder = ref.getBuilder(scope);
      
      // check if both row builders exist to resolve relation
      if (thisBuilder != null && otherBuilder != null) 
      {
        // simple reference
        thisBuilder.setTeamId(otherBuilder.getId());
      }
      else {
        // at least one builder does not exist... at relation information
        // to the Refs where no builder exist.
        if (thisBuilder == null)
        {
          getOrCreateList(private_teamIdToMap, threadScope).add(ref);
        }
        if (otherBuilder == null) {
          getOrCreateList(ref.private_personsToMap, threadScope).add(this);
        }
      }
    }
  }


}

