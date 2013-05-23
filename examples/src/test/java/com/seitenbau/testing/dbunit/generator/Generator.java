package com.seitenbau.testing.dbunit.generator;


public class Generator
{
  public static void main(String[] args) throws Exception
  {
    DatabaseModel db = new DatabaseModel()
    {
      {
        database("STU");
        packageName("com.seitenbau.testing.dbunit.model");
      }
    };

    Table jobs = db.table("jobs") //
          .column("id", DataType.BIGINT) //
            .autoIdHandling() //
            .identifierColumn() //
          .column("title", DataType.VARCHAR) //
          .column("description", DataType.VARCHAR) //
        .build();

    Table teams = db.table("teams") //
          .column("id", DataType.BIGINT) //
            .autoIdHandling() //
            .identifierColumn() //
          .column("title", DataType.VARCHAR) //
          .column("description", DataType.VARCHAR) //
          .column("membersize", DataType.BIGINT) //
        .build();
    ; //

    Table persons = db.table("persons") //
          .column("id", DataType.BIGINT) //
            .autoIdHandling() //
            .identifierColumn() //
          .column("first_name", DataType.VARCHAR) //
          .column("name", DataType.VARCHAR) //
          .column("job_id", DataType.BIGINT) //
            .references(jobs.ref("id")) //
          .column("team_id", DataType.BIGINT) //
            .references(teams.ref("id")) //
        .build();

    db.generate();
  }
}
