package com.seitenbau.testing.dbunit.dataset;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*;

import com.seitenbau.testing.dbunit.dsl.LazyValue;
import com.seitenbau.testing.dbunit.model.PersonDatabaseDataSet;

public class DemoClassicAPIDataSet extends PersonDatabaseDataSet
{

  @Override
  protected void initDataSet()
  {
    table_Teams
      .insertRow()
        .bindRef(QA)
        .setId(1)
        .setTitle("Quality Assurance")
        .setDescription("Verifies software")
        .setMembersize(new LazyValue() {
  
          @Override
          public Object getValue()
          {
            return table_Persons.findWhere.teamId(QA).getRowCount();
          }
          
        });
    
    table_Jobs
      .insertRow()
        .bindRef(SWD)
        .setId(1)
        .setTitle("Software Developer")
        .setDescription("Creating software")
      .insertRow()
        .bindRef(SWT)
        .setId(2)
        .setTitle("Software Tester")
        .setDescription("Testing software")
      .insertRow()
        .bindRef(TM)
        .setId(3)
        .setTitle("Team Manager")
        .setDescription("Makes the world go round");

    table_Persons
      .insertRow()
        .bindRef(KAULBERSCH)
        .setId(1)
        .setFirstName("Dennis")
        .setName("Kaulbersch")
        .setJobId(SWD)
        .setTeamId(QA)
      .insertRow()
        .bindRef(GUITTON)
        .setId(2)
        .setFirstName("Julien")
        .setName("Guitton")
        .setJobId(SWT)
        .setTeamId(QA)
      .insertRow()
        .bindRef(BARANOWSKI)
        .setId(3)
        .setFirstName("Christian")
        .setName("Baranowski")
        .setJobId(TM)
        .setTeamId(QA);
  }

}
