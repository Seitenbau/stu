package com.seitenbau.stu.database.modelgenerator.ui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.seitenbau.stu.database.modelgenerator.DatabaseModel;
import com.seitenbau.stu.database.modelgenerator.STUModelWriter;

public class ModelSourceView extends JScrollPane
{

  private static final long serialVersionUID = 1L;

  private final STUModelWriter writer = new STUModelWriter();

  private final JTextArea source;

  public ModelSourceView()
  {
    source = new JTextArea();
    source.setEditable(false);
    setViewportView(source);
  }

  public void setModel(DatabaseModel model)
  {
    String text = writer.generate(model);
    source.setText(text);

    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        getHorizontalScrollBar().setValue(0);
        getVerticalScrollBar().setValue(0);
      }
    });
  }

  public void clear()
  {
    source.setText("");
  }

}
