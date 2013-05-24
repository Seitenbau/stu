package com.seitenbau.testing.dbunit.demomodel.dsl;


import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;

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
    if (lastBuilder != null) {
      System.out.println("Reference has been associated to scope");
    }
  }
  
  RowBuilder_Persons getBuilder(STUDSL scope)
  {
    return builders.get(scope);
  }
  

}

