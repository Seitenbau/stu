package com.seitenbau.stu.database.modelgenerator.ui;

import javax.swing.SwingUtilities;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import com.seitenbau.stu.database.modelgenerator.DatabaseModel;
import com.seitenbau.stu.database.modelgenerator.STUModelWriter;

public class ModelSourceView extends CodeView
{

  private static final long serialVersionUID = 1L;
  
  private final STUModelWriter writer = new STUModelWriter();

  public ModelSourceView()
  {
    super(SyntaxConstants.SYNTAX_STYLE_JAVA);
  }

  public void setModel(DatabaseModel model)
  {
    setSource("// please wait...");
    final String text = writer.generate(model);
    new Thread(new Runnable()
    {

      @Override
      public void run()
      {
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            setSource(text);
          }
        });
      }
    }).start();
  }
 
}
