package com.seitenbau.stu.database.modelgenerator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.common.base.Optional;
import com.seitenbau.stu.database.modelgenerator.DatabaseModel;
import com.seitenbau.stu.database.modelgenerator.ModelReader;
import com.seitenbau.stu.database.modelgenerator.examples.CyclicExampleModel;
import com.seitenbau.stu.database.modelgenerator.examples.PersonDatabaseModel;
import com.seitenbau.stu.database.modelgenerator.examples.RecursiveExampleModel;

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

    setJMenuBar(createMenu());
    
    pack();
  }
  
  private JMenuBar createMenu()
  {
    JMenuBar result = new JMenuBar();
    
    JMenu menuFile = new JMenu("File");
    
    menuFile.add(createMenuItem("Query Database", new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        DatabaseConnectionDialog dialog = new DatabaseConnectionDialog(ModelGeneratorFrame.this);
        dialog.setVisible(true);
        if (dialog.hasResult()) {
          Optional<Connection> connection = dialog.getConnection();
          if (connection.isPresent()) {
            readScheme(connection.get());
          } else {
            JOptionPane.showMessageDialog(null, "Error connection to database.");
          }
        }
      }
      
    }));
    
    JMenu examplesMenu = new JMenu("Examples");
    examplesMenu.add(createMenuItem("Cyclic", new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        applyScheme(new CyclicExampleModel());
      }
      
    }));
    examplesMenu.add(createMenuItem("Recursive", new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        applyScheme(new RecursiveExampleModel());
      }
      
    }));
    examplesMenu.add(createMenuItem("Persons", new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        applyScheme(new PersonDatabaseModel());
      }
      
    }));
    
    menuFile.add(examplesMenu);
    result.add(menuFile);
    return result;
  }
  
  private JMenuItem createMenuItem(String caption, ActionListener listener)
  {
    JMenuItem result = new JMenuItem(caption);
    result.addActionListener(listener);
    return result;
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
          finalConnection.close();
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
