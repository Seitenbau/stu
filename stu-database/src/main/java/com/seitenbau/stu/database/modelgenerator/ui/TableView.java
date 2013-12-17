package com.seitenbau.stu.database.modelgenerator.ui;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.seitenbau.stu.database.modelgenerator.TableModel;

public class TableView extends JPanel
{

  private static final long serialVersionUID = 1L;
  
  private final JLabel tableName;
  
  private final JCheckBox associative;;


  public TableView()
  {
    setLayout(new MigLayout("", "[][120::, grow, fill]"));
    
    tableName = addLabeledComponent("Name",  new JLabel());
    
    associative = addLabeledComponent("Associative Table", new JCheckBox());
  }
  
  private <T extends Component> T addLabeledComponent(String text, T c)
  {
    JLabel label = new JLabel(text + ":");
    label.setLabelFor(c);
    add(label);
    add(c, "grow, wrap");
    return c;
  }
  
  public void updateView(TableModel table)
  {
    clearView();
    tableName.setText(table.getName());
    associative.setSelected(table.isAssociative());
  }

  public void clearView()
  {
    tableName.setText("");
  }

}
