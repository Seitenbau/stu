package com.seitenbau.stu.database.modelgenerator.ui;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.google.common.base.Optional;

public class DatabaseConnectionDialog extends JDialog
{

  private static final long serialVersionUID = 1L;
  
  private boolean dialogResult;
  
  private final JTextField host;
  private final JTextField database;
  private final JTextField user;
  private final JTextField password;
  
  public DatabaseConnectionDialog(Frame owner)
  {
    super(owner);
    setModal(true);
    dialogResult = false;

    setLayout(new MigLayout("", "[][120::, grow, fill]"));
    addLabeledComponent("DBMS", new JLabel("MYSQL"));
    host = addLabeledComponent("Host", new JTextField("localhost"));
    database = addLabeledComponent("Database", new JTextField("bigdb"));
    user = addLabeledComponent("User", new JTextField("root"));
    password = addLabeledComponent("Password", new JPasswordField(""));
    
    
    final JButton okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        dialogResult = true;
        DatabaseConnectionDialog.this.setVisible(false);
      }
      
    });
    add(okButton);
    
    pack();
  }
  
  private <T extends Component> T addLabeledComponent(String text, T c)
  {
    JLabel label = new JLabel(text + ":");
    label.setLabelFor(c);
    add(label);
    add(c, "grow, wrap");
    return c;
  }
  
  public Optional<Connection> getConnection()
  {
    if (!dialogResult) {
      return Optional.absent();
    }
    
    try
    {
      String dbms = "mysql";

      Properties connectionProps = new Properties();
      connectionProps.put("user", user.getText());
      connectionProps.put("password", password.getText());      
      
      String connectionString = "jdbc:" + dbms + "://" + host.getText() + "/" + database.getText();
      
      //final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/tests);
      Class.forName("com.mysql.jdbc.Driver");
      Connection connection = DriverManager.getConnection(connectionString, connectionProps);
      return Optional.of(connection);
    }
    catch (Exception e)
    {
      return Optional.absent();
    }
  }

  public boolean hasResult()
  {
    return dialogResult;
  }

}
