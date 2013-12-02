package com.seitenbau.stu.database.modelgenerator.ui;

import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.seitenbau.stu.database.modelgenerator.DatabaseModel;
import com.seitenbau.stu.database.modelgenerator.ModelReader;

public class ModelGeneratorFrame extends JFrame
{

  private static final long serialVersionUID = 1L;

  private final JTabbedPane tabbedPane;

  private final ModelSourceView sourceView;

  private final DatabaseModelView databaseView;
  
  private final DataGeneratorView dataGeneratorView;
  
  private DatabaseModel model;

  public ModelGeneratorFrame()
  {
    setTitle("STU Database Tool");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    tabbedPane = new JTabbedPane();
    databaseView = new DatabaseModelView();
    sourceView = new ModelSourceView();
    dataGeneratorView = new DataGeneratorView();

    tabbedPane.addTab("Database Model", databaseView);
    tabbedPane.addTab("STU Model Source", sourceView);
    tabbedPane.addTab("Data Generator", dataGeneratorView);
    add(tabbedPane);

    tabbedPane.addChangeListener(new ChangeListener()
    {
      public void stateChanged(ChangeEvent e)
      {
        if (sourceView.equals(tabbedPane.getSelectedComponent())) {
          if (model != null) {
            sourceView.setModel(model);
          }
        }

        if (dataGeneratorView.equals(tabbedPane.getSelectedComponent())) {
          if (model != null) {
            dataGeneratorView.setModel(model);
          }
        }
      }
    });

    pack();
  }

  public void readScheme(Connection connection)
  {
    databaseView.clear();

    final Connection finalConnection = connection;

    new Thread(new Runnable()
    {

      @Override
      public void run()
      {
        try
        {
          final DatabaseModel model = ModelReader.readModel(finalConnection);
          SwingUtilities.invokeLater(new Runnable()
          {

            @Override
            public void run()
            {
              applyScheme(model);
            }
          });
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }

    }).start();
  }

  private void applyScheme(DatabaseModel model)
  {
    this.model = model;
    databaseView.applyScheme(model);
  }

}
