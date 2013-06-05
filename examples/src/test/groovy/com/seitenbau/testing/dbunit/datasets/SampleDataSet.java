package com.seitenbau.testing.dbunit.datasets;

import com.seitenbau.testing.dbunit.model.JobsTable.RowGetters_Jobs;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowGetters_Teams;

public class SampleDataSet extends EmptyDataSet
{
  @Override
  protected void initDataSet()
  {
    RowGetters_Jobs bakerman = //
    table_Jobs.insertRow() //
        .setTitle("Bakerman") //
        .setDescription("Baking bread");

    RowGetters_Teams bakercrew = //
    table_Teams.insertRow() //
        .setTitle("Butter & Bread") //
        .setDescription("Creating good bread").setMembersize(3);

    table_Persons.insertRow() //
        .setFirstName("Hansi") //
        .setName("Krankl") //
        .refJobId(bakerman) //
        .refTeamId(bakercrew);

  }
}
