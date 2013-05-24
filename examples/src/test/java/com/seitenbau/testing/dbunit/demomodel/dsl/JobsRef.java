package com.seitenbau.testing.dbunit.demomodel.dsl;


import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;

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
    if (lastBuilder != null) {
      System.out.println("Reference has been associated to scope");
    }
  }
  
  RowBuilder_Jobs getBuilder(STUDSL scope)
  {
    return builders.get(scope);
  }
  
  Map<STUDSL, List<PersonsRef>> personsToList = new HashMap<STUDSL, List<PersonsRef>>();

  // depending on relation type with or without ellipse (...)
  void personsTo(PersonsRef ... refs) {
    final List<PersonsRef> list = getOrCreateList(personsToList, threadScope);
    list.addAll(Arrays.asList(refs));
  }


}

