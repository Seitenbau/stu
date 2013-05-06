package com.seitenbau.testing.dbunit.datasets;

import com.seitenbau.testing.dbunit.model.ProfessorTable.RowBuilder_Professor;

public class DefaultDataSet extends EmptyDataSet
{
  @Override
  protected void initDataSet()
  {
    RowBuilder_Professor hansi = table_Professor.insertRow() //
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

    table_Lecture.insertRow() //
        .setName("Semiotik Today") //
        .setSws(2) //
        .setEcts(10) //
        .refProfessorId(hansi);

    table_Lecture.insertRow() //
        .setName("Information Retrieval") //
        .setSws(5) //
        .setEcts(12) //
        .refProfessorId(hansi);
  }
}
