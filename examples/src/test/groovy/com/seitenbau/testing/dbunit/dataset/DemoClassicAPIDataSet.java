package com.seitenbau.testing.dbunit.dataset;

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.BARANOWSKI;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.GUITTON;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.KAULBERSCH;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.QA;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.SWD;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.SWT;
import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.TM;

import com.seitenbau.testing.dbunit.model.PersonDatabaseDataSet;
import com.seitenbau.testing.dbunit.util.DateUtil;
import com.seitenbau.testing.util.Future;

public class DemoClassicAPIDataSet extends PersonDatabaseDataSet
{

  @Override
  protected void initDataSet()
  {
    QA.hasMembers(KAULBERSCH, GUITTON, BARANOWSKI);

    table_Teams
      .insertRow()
        .bind(QA)
        .setTitle("Quality Assurance")
        .setDescription("Verifies software")
        .setMembersize(new Future<Object>() {

          @Override
          public Object getFuture()
          {
            return table_Persons.findWhere.teamId(QA).getRowCount();
          }

        });

    table_Jobs
      .insertRow()
        .bind(SWD)
        .setTitle("Software Developer")
        .setDescription("Creating software")
      .insertRow()
        .bind(SWT)
        .setTitle("Software Tester")
        .setDescription("Testing software")
      .insertRow()
        .bind(TM)
        .setTitle("Team Manager")
        .setDescription("Makes the world go round");

    table_Persons
      .insertRow()
        .bind(KAULBERSCH)
        .setFirstName("Dennis")
        .setName("Kaulbersch")
      .insertRow()
        .bind(GUITTON)
        .setFirstName("Julien")
        .setName("Guitton")
      .insertRow()
        .bind(BARANOWSKI)
        .setFirstName("Christian")
        .setName("Baranowski");

    table_PersonJob
      .insertRow()
        .setPersonId(KAULBERSCH.getId())
        .setJobId(SWD.getId())
        .setEngagementStart(DateUtil.getDate(2013, 4, 1, 14, 0, 0))
      .insertRow()
        .setPersonId(GUITTON.getId())
        .setJobId(SWT.getId())
      .insertRow()
        .setPersonId(BARANOWSKI.getId())
        .setJobId(TM.getId());
  }

}
