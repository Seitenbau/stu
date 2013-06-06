package com.seitenbau.testing.dbunit.examples;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*;
import com.seitenbau.testing.dbunit.dataset.DemoClassicAPIDataSet;
import com.seitenbau.testing.dbunit.dsl.ScopeRegistry;

public class ClassicAPIExample
{

  public static void main(String[] args)
  {
    DemoClassicAPIDataSet dataSet = new DemoClassicAPIDataSet();
    
    ScopeRegistry.use(dataSet);
    
    System.out.println(QA.getMembersize());
  }

}
