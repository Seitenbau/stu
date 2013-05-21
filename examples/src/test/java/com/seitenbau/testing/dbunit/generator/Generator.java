package com.seitenbau.testing.dbunit.generator;

public class Generator
{
  public static void main(String[] args) throws Exception
  {
    DatabaseModel db = new DatabaseModel()
    {
      {
        database("STU");
        packageName("com.seitenbau.testing.dbunit");
      }
    };
    
    Table jobs = db.addTable("jobs") //
        .addColumn("id", DataType.BIGINT, Flags.AutoInvokeNextIdMethod) //
        .addColumn("title", DataType.VARCHAR) //
        .addColumn("description", DataType.VARCHAR);

    Table teams = db.addTable("teams") //
        .addColumn("id", DataType.BIGINT, Flags.AutoInvokeNextIdMethod) //
        .addColumn("title", DataType.VARCHAR) //
        .addColumn("description", DataType.VARCHAR) //
        .addColumn("membersize", DataType.BIGINT);
    ; //

    Table persons = db.addTable("persons") //
        .addColumn("id", DataType.BIGINT, Flags.AutoInvokeNextIdMethod) //
        .addColumn("first_name", DataType.VARCHAR) //
        .addColumn("name", DataType.VARCHAR) //
        .addColumn("job_id", DataType.BIGINT, jobs.ref("id")) //
        .addColumn("team_id", DataType.BIGINT, teams.ref("id"));

    
    TableBuilder tableBuilder = new TableBuilder("sample");

    Table sample = tableBuilder //
        .column("id", DataType.BIGINT) //
          .identifierColumn() //
          .autoIdHandling() //
        .column("title", DataType.VARCHAR) //
        .column("foreign_id", DataType.BIGINT) //
          .references(teams.ref("id")) //
    .build();

    db.addTable(sample);
   
    db.generate();
  }
}
