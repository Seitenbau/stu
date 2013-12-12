package com.seitenbau.stu.database.modelgenerator;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.seitenbau.stu.database.modelgenerator.ui.ModelGeneratorFrame;
import com.seitenbau.stu.logger.LogManager;
import com.seitenbau.stu.logger.LogManager.Levels;

public class ModelGeneratorUI
{

  public static void main(String[] args)
  {
    LogManager.setLevel(Levels.INFO);
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e)
    {
    }

    SwingUtilities.invokeLater(new Runnable()
    {
        @Override
        public void run()
        {
          ModelGeneratorFrame ui = new ModelGeneratorFrame();
          ui.setVisible(true);
        }
    });

  }

}
