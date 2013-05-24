package com.seitenbau.testing.dbunit.generator;

public class DBGeneratorSample
{
  public static void main(String[] args) throws Exception
  {
    DatabaseModel db = new DatabaseModel()
    {
      {
        database("RW");
        packageName("com.example");
      }
    };

    db.table("Users").column("ID", DataType.INTEGER).column("Name", DataType.VARCHAR);

    db.generate();
  }
}
