package com.seitenbau.stu.database.dataset;

import static com.seitenbau.stu.database.PersonDatabaseRefs.BARANOWSKI;
import static com.seitenbau.stu.database.PersonDatabaseRefs.GUITTON;
import static com.seitenbau.stu.database.PersonDatabaseRefs.KAULBERSCH;
import static com.seitenbau.stu.database.PersonDatabaseRefs.QA;
import static com.seitenbau.stu.database.PersonDatabaseRefs.SWD;
import static com.seitenbau.stu.database.PersonDatabaseRefs.SWT;
import static com.seitenbau.stu.database.PersonDatabaseRefs.TM;

import com.seitenbau.stu.database.model.PersonDatabaseDataSet;
import com.seitenbau.stu.database.util.DateUtil;
import com.seitenbau.stu.util.Future;

public class DemoClassicAPIDataSet extends PersonDatabaseDataSet
{

  @Override
  protected void initDataSet()
  {
    QA.hasMembers_belongsTo(KAULBERSCH, GUITTON, BARANOWSKI);

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
