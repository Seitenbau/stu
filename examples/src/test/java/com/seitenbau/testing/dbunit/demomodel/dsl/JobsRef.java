package com.seitenbau.testing.dbunit.demomodel.dsl;

import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;
import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowBuilder_Persons;

public class JobsRef extends DatabaseReference {

  private final Map<STUDSL, RowBuilder_Jobs> builders;
  
  public JobsRef()
  {
    builders = new HashMap<STUDSL, RowBuilder_Jobs>();
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
  
  void setBuilder(STUDSL scope, RowBuilder_Jobs builder)
  {
    RowBuilder_Jobs lastBuilder = builders.put(scope, builder);
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
      
      otherBuilder.setJobId(builder.getId());
      List<JobsRef> otherList = getOrCreateList(ref.private_jobIdToMap, threadScope);
      if (otherList.contains(this)) 
      {
        otherList.remove(this);
      }
    }
  }
  
  RowBuilder_Jobs getBuilder(STUDSL scope)
  {
    return builders.get(scope);
  }
  
  Map<STUDSL, List<PersonsRef>> private_personsToMap = new HashMap<STUDSL, List<PersonsRef>>();

  // depending on relation type with or without ellipse (...)
  void personsTo(PersonsRef ... refs) {
    STUDSL scope = threadScope.get();
    RowBuilder_Jobs thisBuilder = getBuilder(scope);
    
    for (PersonsRef ref : refs) 
    {
      RowBuilder_Persons otherBuilder = ref.getBuilder(scope);
      
      // check if both row builders exist to resolve relation
      if (thisBuilder != null && otherBuilder != null) 
      {
        // foreign reference
        otherBuilder.setJobId(thisBuilder.getId());
      }
      else {
        // at least one builder does not exist... at relation information
        // to the Refs where no builder exist.
        if (thisBuilder == null)
        {
          getOrCreateList(private_personsToMap, threadScope).add(ref);
        }
        if (otherBuilder == null) {
          getOrCreateList(ref.private_jobIdToMap, threadScope).add(this);
        }
      }
    }
  }


}

