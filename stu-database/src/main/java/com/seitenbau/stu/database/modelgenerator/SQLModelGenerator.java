package com.seitenbau.stu.database.modelgenerator;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLModelGenerator {

  public static void main(String[] args) {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/tests?user=root&password=");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/bigdb?user=root&password=");
      
      DatabaseModel model = ModelReader.readModel(connection);
      STUModelWriter writer = new STUModelWriter();
      System.out.println(writer.generate(model));
    }
    catch (Exception e)
    {
      throw new RuntimeException("Error creating model", e);
    }
  }

}