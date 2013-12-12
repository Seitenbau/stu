package com.seitenbau.stu.database.modelgenerator.ui;

import groovy.ui.ConsoleTextEditor;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import com.seitenbau.stu.database.modelgenerator.DatabaseModel;
import com.seitenbau.stu.database.modelgenerator.STUModelWriter;

public class ModelSourceView extends JPanel
{

  private static final long serialVersionUID = 1L;

  private final STUModelWriter writer = new STUModelWriter();

  // private final JTextArea source;
  private final ConsoleTextEditor source;

  public ModelSourceView()
  {
    setLayout(new MigLayout("fill", "0[grow, fill]0", "0[grow, fill]0"));
    source = new ConsoleTextEditor();

    // source = new JTextArea();
    source.setEditable(false);
    // setViewportView(source);

    add(source, "grow");
  }

  public void setModel(DatabaseModel model)
  {
    source.getTextEditor().setText("// please wait...");
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
            source.getTextEditor().setText(text);
          }
        });
      }
    }).start();

  }

  public void clear()
  {
    source.getTextEditor().setText("");
  }

}
