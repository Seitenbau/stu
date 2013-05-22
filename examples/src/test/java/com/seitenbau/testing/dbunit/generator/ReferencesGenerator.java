package com.seitenbau.testing.dbunit.generator;

public class ReferencesGenerator {
  
  public static void main(String[] args) throws Exception {
    DatabaseModel db = new DatabaseModel() {
      {
        database("DBUnitExamples");
        packageName("com.seitenbau.testdatadsl.dbunitdemo.sbtesting");
      }
    };
    
    Table professoren = db.table("professor")
        .column("id", DataType.BIGINT) 
          .identifierColumn() 
          .autoIdHandling()
        .column("name", DataType.VARCHAR)
        .column("vorname", DataType.VARCHAR)
        .column("titel", DataType.VARCHAR)
        .column("fakultaet", DataType.VARCHAR)
      .build();
    Table lehrveranstaltungen = db.table("lehrveranstaltung")
        .column("id", DataType.BIGINT)
          .identifierColumn() 
          .autoIdHandling()
        .column("professor_id", DataType.BIGINT)
          .references(professoren)
            .local("geleitetVon")
            .remote("leitet")
              .min(1)
              .max(1)
        .column("name", DataType.VARCHAR)
        .column("sws", DataType.INTEGER)
        .column("ects", DataType.DOUBLE)
      .build();
    Table pruefungen = db.table("pruefung")
        .column("id", DataType.BIGINT)
          .identifierColumn() 
          .autoIdHandling()
        .column("lehrveranstaltung_id", DataType.BIGINT)
          .references(lehrveranstaltungen)
            .local("stoffVon")
            .remote("hatPruefung")
              .min(1)
              .max(1)
        .column("typ", DataType.VARCHAR)
        .column("zeitpunkt", DataType.DATE)
      .build();
    Table studenten = db.table("student")
        .column("matrikelnummer", DataType.BIGINT)
          .identifierColumn() 
          .autoIdHandling()
        .column("name", DataType.VARCHAR)
        .column("vorname", DataType.VARCHAR)
        .column("studiengang", DataType.VARCHAR)
        .column("semester", DataType.INTEGER)
        .column("immatrikuliert_seit", DataType.DATE)
      .build();

    db.table("beaufsichtigt")
        .column("professor_id", DataType.BIGINT)
          .references(professoren)
            .remote("beaufsichtigt")
              .max(1)
        .column("pruefung_id", DataType.BIGINT)
          .references(pruefungen)
            .remote("beaufsichtigtVon")
              .max(1)
      .build();
    db.table("besucht")
        .column("student_id", DataType.BIGINT)
          .references(studenten)
            .remote("besucht")
              .max(1)
        .column("lehrveranstaltung_id", DataType.BIGINT)
          .references(lehrveranstaltungen)
            .remote("besuchtVon")
              .max(1)
      .build();
    db.table("isttutor")
        .column("student_id", DataType.BIGINT)
          .references(studenten)
            .remote("istTutor")
              .max(1)
        .column("lehrveranstaltung_id", DataType.BIGINT)
          .references(lehrveranstaltungen)
            .remote("hatTutor")
              .max(1)
      .build();
    db.table("schreibt")
        .column("student_id", DataType.BIGINT)
          .references(studenten)
            .remote("schreibt")
              .max(1)
        .column("pruefung_id", DataType.BIGINT)
          .references(pruefungen)
            .remote("geschriebenVon")
              .max(1)
      .build();
    
    db.generate();
  }
}
