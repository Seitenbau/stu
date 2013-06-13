package com.seitenbau.testing.dbunit.dataset;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*;

import com.seitenbau.testing.dbunit.dsl.LazyValue;
import com.seitenbau.testing.dbunit.model.PersonDatabaseDataSet;

public class DemoClassicAPIDataSet extends PersonDatabaseDataSet
{

  @Override
  protected void initDataSet()
  {
    QA.hasMembers(KAULBERSCH, GUITTON, BARANOWSKI);

    table_Teams
      .insertRow()
        .bindRef(QA)
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
        .setTitle("Software Developer")
        .setDescription("Creating software")
      .insertRow()
        .bindRef(SWT)
        .setTitle("Software Tester")
        .setDescription("Testing software")
      .insertRow()
        .bindRef(TM)
        .setTitle("Team Manager")
        .setDescription("Makes the world go round");

    table_Persons
      .insertRow()
        .bindRef(KAULBERSCH)
        .setFirstName("Dennis")
        .setName("Kaulbersch")
      .insertRow()
        .bindRef(GUITTON)
        .setFirstName("Julien")
        .setName("Guitton")
      .insertRow()
        .bindRef(BARANOWSKI)
        .setFirstName("Christian")
        .setName("Baranowski");
    
    table_PersonJob
      .insertRow()
        .setPersonId(KAULBERSCH.getId())
        .setJobId(SWD.getId())
      .insertRow()
        .setPersonId(GUITTON.getId())
        .setJobId(SWT.getId())
      .insertRow()
        .setPersonId(BARANOWSKI.getId())
        .setJobId(TM.getId());
  }

}
