package com.seitenbau.stu.database.modelgenerator;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.seitenbau.stu.database.modelgenerator.ui.ModelGeneratorFrame;
import com.seitenbau.stu.logger.LogManager;
import com.seitenbau.stu.logger.LogManager.Levels;

public class ModelGeneratorUI
{

  public static void main(String[] args)
  {
    try
    {
      LogManager.setLevel(Levels.INFO);

      Class.forName("com.mysql.jdbc.Driver");
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      //final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/tests?user=root&password=");
      final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/bigdb?user=root&password=");
      SwingUtilities.invokeLater(new Runnable()
      {
          @Override
          public void run()
          {
            ModelGeneratorFrame ui = new ModelGeneratorFrame();
            ui.setVisible(true);


            ui.readScheme(connection);
          }
      });
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return;
    }

  }

}
