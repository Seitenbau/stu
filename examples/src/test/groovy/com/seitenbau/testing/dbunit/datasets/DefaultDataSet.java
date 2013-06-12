package com.seitenbau.testing.dbunit.datasets;

import com.seitenbau.testing.dbunit.model.JobsTable.RowGetters_Jobs;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowGetters_Teams;

public class DefaultDataSet extends EmptyDataSet
{
  @Override
  protected void initDataSet()
  {
    RowGetters_Jobs<?> softwareDeveloper = //
    table_Jobs.insertRow() //
        .setTitle("Software Developer") //
        .setDescription("Creating software");

    RowGetters_Jobs<?> softwareTester = //
    table_Jobs.insertRow() //
        .setTitle("Software Tester") //
        .setDescription("Testing software");

    RowGetters_Jobs<?> teamManager = //
    table_Jobs.insertRow() //
        .setTitle("Team Manager") //
        .setDescription("Makes the world go round");

    RowGetters_Teams<?> qualityAssurance = //
    table_Teams.insertRow() //
        .setTitle("Quality Assurance") //
        .setDescription("Verifies that requirements for a product are fulfilled").setMembersize(3);

    RowBuilder_Persons dennis = table_Persons.insertRow() //
        .setFirstName("Dennis") //
        .setName("Kaulbersch") //
        .refTeamId(qualityAssurance);

    RowBuilder_Persons julien = table_Persons.insertRow() //
        .setFirstName("Julien") //
        .setName("Guitton") //
        .refTeamId(qualityAssurance);

    RowBuilder_Persons christian = table_Persons.insertRow() //
        .setFirstName("Christian") //
        .setName("Baranowski") //
        .refTeamId(qualityAssurance);
    
    table_PersonJob.insertRow() //
        .setPersonId(dennis.getId()) //
        .setJobId(softwareDeveloper.getId());
    
    table_PersonJob.insertRow() //
    .setPersonId(julien.getId()) //
    .setJobId(softwareTester.getId());
    
    table_PersonJob.insertRow() //
    .setPersonId(christian.getId()) //
    .setJobId(teamManager.getId());

  }
}
