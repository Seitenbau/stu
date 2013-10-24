package com.seitenbau.stu.dbunit.datasets;

import com.seitenbau.stu.dbunit.model.JobsTable.RowGetters_Jobs;
import com.seitenbau.stu.dbunit.model.PersonsTable.RowGetters_Persons;
import com.seitenbau.stu.dbunit.model.TeamsTable.RowGetters_Teams;

public class SampleDataSet extends EmptyDataSet
{
  @Override
  protected void initDataSet()
  {
    RowGetters_Jobs<?> bakerman = //
    table_Jobs.insertRow() //
        .setTitle("Bakerman") //
        .setDescription("Baking bread");

    RowGetters_Teams<?> bakercrew = //
    table_Teams.insertRow() //
        .setTitle("Butter & Bread") //
        .setDescription("Creating good bread").setMembersize(3);

    RowGetters_Persons<?> krankl = table_Persons.insertRow() //
        .setFirstName("Hansi") //
        .setName("Krankl") //
        .refTeamId(bakercrew);

    //table_PersonJob.insertRow() //
    //    .refPersonId(krankl) //
    //    .refJobId(bakerman);

  }
}
