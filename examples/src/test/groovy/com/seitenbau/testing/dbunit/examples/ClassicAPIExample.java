package com.seitenbau.testing.dbunit.examples;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*;
import com.seitenbau.testing.dbunit.dataset.DemoClassicAPIDataSet;

public class ClassicAPIExample
{

  public static void main(String[] args)
  {
    DemoClassicAPIDataSet dataSet = new DemoClassicAPIDataSet();
    System.out.println(QA.getMembersize());
    System.out.println(dataSet.table_Persons.getWhere.id(BARANOWSKI).get().getFirstName());
    System.out.println(dataSet.table_Persons.getWhere.id(BARANOWSKI).get().getTeamId());
  }

}
