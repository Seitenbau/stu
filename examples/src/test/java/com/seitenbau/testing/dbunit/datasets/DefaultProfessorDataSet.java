package com.seitenbau.testing.dbunit.datasets;

public class DefaultProfessorDataSet extends EmptyDataset
{
  @Override
  protected void initDataSet()
  {
    table_Professor.insertRow() //
        .setFirstName("Hansi") //
        .setName("Krankl") //
        .setFaculty("Media") //
        .setTitle("Dipl.-Med.-Sys.-Wiss.");

    table_Professor.insertRow() //
        .setFirstName("Paul") //
        .setName("Breitner") //
        .setFaculty("Architecture") //
        .setTitle("Dr.");

    table_Professor.insertRow() //
        .setFirstName("Marco") //
        .setName("Polo") //
        .setFaculty("Business Management") //
        .setTitle("Dr. Dr.");
  }
}
