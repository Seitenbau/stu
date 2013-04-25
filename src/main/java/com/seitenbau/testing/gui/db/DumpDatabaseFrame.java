package com.seitenbau.testing.gui.db;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import com.seitenbau.testing.dbunit.DatabaseTester;
import com.seitenbau.testing.dbunit.tester.DataSetUtil;
import com.seitenbau.testing.dbunit.tester.DatabaseTesterBase;

public class DumpDatabaseFrame extends JDialog
{
  private static final long serialVersionUID = 1L;

  private static final Object[] ITEMS = new Object[] {"CLEAN INSERT", "INSERT"};

  private JTextField fieldDriver;

  private JTextField fieldURL;

  private JTextField fieldUserName;

  private JTextField fieldPassword;

  private JTextField fieldTargetFile;

  private JButton btnSave;

  private JTextField fieldSchema;

  private JButton btnLoad;

  private JComboBox cboxRestorOp;

  private DefaultComboBoxModel combo;

  public DumpDatabaseFrame()
  {
    createControls(getContentPane());
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setAlwaysOnTop(true);
    setTitle("sb-testing-db : dump Database");
  }

  private void createControls(Container contentPane)
  {
    contentPane.setLayout(new BorderLayout());
    Panel connectionPanel = new Panel(new GridLayout(8, 2));
    contentPane.add(connectionPanel, BorderLayout.NORTH);

    fieldDriver = new JTextField("org.gjt.mm.mysql.Driver");
    fieldURL = new JTextField("jdbc:mysql://192.168.0.42:3306/my_database_name");
    fieldUserName = new JTextField("user");
    fieldPassword = new JTextField("password");
    fieldTargetFile = new JTextField("./dump.xml");
    fieldSchema = new JTextField("");
    btnSave = new JButton("dump complete db to file");
    btnLoad = new JButton("restore dump file");
    combo = new DefaultComboBoxModel(ITEMS);
    cboxRestorOp = new JComboBox(combo);

    Dimension minimal = new Dimension(120, 10);
    fieldDriver.setMinimumSize(minimal);
    fieldURL.setMinimumSize(minimal);
    fieldUserName.setMinimumSize(minimal);
    fieldPassword.setMinimumSize(minimal);
    fieldTargetFile.setMinimumSize(minimal);
    fieldSchema.setMinimumSize(minimal);

    loadDefaultConfig();

    connectionPanel.add(new JLabel("driver : "));
    connectionPanel.add(fieldDriver);
    connectionPanel.add(new JLabel("URL : "));
    connectionPanel.add(fieldURL);
    connectionPanel.add(new JLabel("username : "));
    connectionPanel.add(fieldUserName);
    connectionPanel.add(new JLabel("password : "));
    connectionPanel.add(fieldPassword);
    connectionPanel.add(new JLabel("output filename : "));
    connectionPanel.add(fieldTargetFile);
    connectionPanel.add(new JLabel("db schema (optional) : "));
    connectionPanel.add(fieldSchema);
    connectionPanel.add(new JLabel("restore Operation : "));
    connectionPanel.add(cboxRestorOp);

    Panel ctrlPanel = new Panel(new GridLayout(1, 2));
    contentPane.add(ctrlPanel, BorderLayout.SOUTH);
    ctrlPanel.add(btnSave);
    ctrlPanel.add(btnLoad);

    addActionListeners();

    pack();
  }

  private void loadDefaultConfig()
  {
    if (new File("./database-dumper-default.properties").exists())
    {
      try
      {
        FileInputStream fin = new FileInputStream(new File(
            "./database-dumper-default.properties"));
        Properties properties = new Properties();
        properties.load(fin);
        fin.close();

        fieldDriver.setText(properties.getProperty("db.driver"));
        fieldURL.setText(properties.getProperty("db.url"));
        fieldUserName.setText(properties.getProperty("db.username"));
        fieldPassword.setText(properties.getProperty("db.password"));
        fieldTargetFile.setText(properties.getProperty("dump.file.name"));
        fieldSchema.setText(properties.getProperty("db.schema"));

      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  protected void dumpDatabase()
  {
    DumpDatabaseLog dumpDatabaseLog = new DumpDatabaseLog();
    dumpDatabaseLog.setVisible(true);
    DatabaseTesterBase<DatabaseTester> tester = getDatabaseTester();

    String filename = fieldTargetFile.getText();

    dumpDatabaseLog.append("Dumping ...\n");
    try
    {
      tester.dumpDatabase(filename);
    } 
    catch (Throwable t)
    {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      t.printStackTrace(new PrintStream(stream));
      dumpDatabaseLog.append(stream.toString());
    }
    dumpDatabaseLog.append("done");

    saveProperties();
    dumpDatabaseLog.finish();
  }

  private DatabaseTesterBase<DatabaseTester> getDatabaseTester()
  {
    String password = fieldPassword.getText();
    String driverName = fieldDriver.getText();
    String url = fieldURL.getText();
    String username = fieldUserName.getText();
    String schema = fieldSchema.getText();

    DatabaseTesterBase<DatabaseTester> tester = new DatabaseTester(driverName, url, username,
        password);
    if (schema != null && schema.length() > 0)
    {
      tester.setSchema(schema);
    }
    return tester;
  }

  private void saveProperties()
  {
    try
    {
      FileOutputStream fin = new FileOutputStream(new File(
          "./database-dumper-default.properties"));
      Properties properties = new Properties();

      properties.setProperty("db.driver", fieldDriver.getText());
      properties.setProperty("db.url", fieldURL.getText());
      properties.setProperty("db.username", fieldUserName.getText());
      properties.setProperty("db.password", fieldPassword.getText());
      properties.setProperty("db.schema", fieldSchema.getText());
      properties.setProperty("dump.file.name", fieldTargetFile.getText());

      properties.store(fin, "written");
      fin.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  protected void restoreDatabase()
  {
    IDataSet dataset;
    try
    {
      DatabaseTesterBase<DatabaseTester> tester = getDatabaseTester();

      String filename = fieldTargetFile.getText();

      System.out.println("Restoring Tables : ");
      dataset = DataSetUtil.getDataSetFromFile(filename);
      for (String name : dataset.getTableNames())
      {
        System.out.println("    " + name);
      }
      System.out.print("Restoring [" + filename + "] ...");
      DatabaseOperation operation = DatabaseOperation.CLEAN_INSERT;
      if (combo.getSelectedItem().equals("INSERT"))
      {
        operation = DatabaseOperation.INSERT;
      }
      tester.prepare(dataset, operation);
      System.out.println(" done");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    saveProperties();
  }

  private void addActionListeners()
  {
    btnSave.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        dumpDatabase();
      }
    });
    btnLoad.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        restoreDatabase();
      }
    });
  }
}
