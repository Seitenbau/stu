package model;

import com.seitenbau.testing.dbunit.generator.DataType;
import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.Table;

public class HochschuleModel extends DatabaseModel
{
  public HochschuleModel()
  {
    database("Hochschule");
    packageName("com.seitenbau.testing.hochschule.model");
    enableTableModelClassesGeneration();

    Table raum = table("raum")
        .description("Die Tabelle mit den Räumen der Hochschule")
        .column("id", DataType.BIGINT)
          .defaultIdentifier()
          .autoInvokeNext()
        .column("gebaude", DataType.VARCHAR)
        .column("nummer", DataType.VARCHAR)
      .build();

    Table professoren = table("professor")
        .description("Die Tabelle mit den Professoren der Hochschule")
        .column("id", DataType.BIGINT)
          .defaultIdentifier()
          .autoInvokeNext()
        .column("name", DataType.VARCHAR)
        .column("vorname", DataType.VARCHAR)
        .column("titel", DataType.VARCHAR)
        .column("fakultaet", DataType.VARCHAR)
        .column("raum_id", DataType.BIGINT)
          .reference
            .local
              .name("hatRaum")
            .foreign(raum)
              .name("gehoert")
              .multiplicity("0..1")
      .build();

    Table raum2 = table("raum2")
        .column("id", DataType.BIGINT)
          .defaultIdentifier()
          .autoInvokeNext()
        .column("professor_id", DataType.BIGINT)
          .reference
            .local
              .name("hatRaum2")
              .multiplicity("0..1")
            .foreign(professoren)
              .name("gehoert2")
              .multiplicity("1..1")
      .build();

    Table lehrveranstaltungen = table("lehrveranstaltung")
        .description("Die Tabelle mit den Lehrveranstaltungen der Hochschule")
        .column("id", DataType.BIGINT)
          .defaultIdentifier()
          .autoInvokeNext()
        .column("professor_id", DataType.BIGINT)
          .reference
            .local
              .name("geleitetVon")
              .description("Gibt an, von welchem Professor eine Lehrveranstaltung geleitet wird.")
            .foreign(professoren)
              .name("leitet")
              .description("Gibt an, welche Lehrveranstaltungen ein Professor leitet.")
              .multiplicity("0..*")
        .column("name", DataType.VARCHAR)
        .column("sws", DataType.INTEGER)
        .column("ects", DataType.DOUBLE)
      .build();

    Table pruefungen = table("pruefung")
        .description("Die Tabelle mit den Prüfungen der Hochschule")
        .column("id", DataType.BIGINT)
          .defaultIdentifier()
          .autoInvokeNext()
        .column("lehrveranstaltung_id", DataType.BIGINT)
          .reference
            .local
              .name("stoffVon")
              .multiplicity("1")
              .description("Gibt an, zu welcher Lehrvanstaltung eine Prüfung gehört.")
            .foreign(lehrveranstaltungen)
              .name("hatPruefung")
              .multiplicity("0..*")
              .description("Ordnet Prüfungen einer Lehrveranstaltung zu.")
        .column("typ", DataType.VARCHAR)
        .column("zeitpunkt", DataType.DATE)
      .build();

    Table studenten = table("student")
        .description("Die Tabelle mit den immatrikulierten Studenten der Hochschule")
        .column("matrikelnummer", DataType.BIGINT)
          .defaultIdentifier()
          .autoInvokeNext()
        .column("name", DataType.VARCHAR)
        .column("vorname", DataType.VARCHAR)
        .column("studiengang", DataType.VARCHAR)
        .column("semester", DataType.INTEGER)
        .column("immatrikuliert_seit", DataType.DATE)
      .build();

    associativeTable("beaufsichtigt")
        .column("professor_id", DataType.BIGINT)
          .reference
            .foreign(professoren)
              .name("beaufsichtigt")
              .description("Gibt an, welche Prüfungen ein Professor beaufsichtigt.")
              .multiplicity("0..*")
        .column("pruefung_id", DataType.BIGINT)
          .reference
            .foreign(pruefungen)
              .name("beaufsichtigtVon")
              .description("Gibt an, welche Professoren eine Prüfung beaufsichtigen.")
              .multiplicity("0..*")
      .build();

    associativeTable("besucht")
        .column("student_id", DataType.BIGINT)
          .reference
            .foreign(studenten)
              .name("besucht")
              .multiplicity("0..*")
              .description("Gibt an, welche Lehrveranstaltungen ein Student besucht.")
        .column("lehrveranstaltung_id", DataType.BIGINT)
          .reference
            .foreign(lehrveranstaltungen)
              .name("besuchtVon")
              .multiplicity("3..10")
              .description("Gibt an, welche Studenten eine Lehrveranstaltung besuchen.")
      .build();

    associativeTable("isttutor")
        .column("student_id", DataType.BIGINT)
          .reference
            .foreign(studenten)
              .name("istTutor")
              .multiplicity("0..*")
              .description("Gibt an, bei welchen Lehrveranstaltungen ein Student Tutor ist.")
        .column("lehrveranstaltung_id", DataType.BIGINT)
          .reference
            .foreign(lehrveranstaltungen)
              .name("hatTutor")
              .multiplicity("0..*")
              .description("Gibt an, welche Tutoren eine Lehrveranstaltung hat.")
      .build();

    associativeTable("schreibt")
        .column("student_id", DataType.BIGINT)
          .reference
            .foreign(studenten)
              .name("schreibt")
              .multiplicity("0..*")
              .description("Gibt an, welche Prüfungen ein Student schreibt.")
        .column("pruefung_id", DataType.BIGINT)
          .reference
            .foreign(pruefungen)
              .name("geschriebenVon")
              .multiplicity("0..*")
              .description("Gibt an, welche Studenten eine Prüfung schreiben.")
        .column("versuch", DataType.INTEGER)
      .build();
  }

}
