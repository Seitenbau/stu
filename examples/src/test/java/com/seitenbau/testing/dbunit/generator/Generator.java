package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.dbunit.generator.DataType;
import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.Flags;
import com.seitenbau.testing.dbunit.generator.Table;

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

    db.generate();
  }
}
