package com.seitenbau.stu.database.modelgenerator.ui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.seitenbau.stu.database.modelgenerator.ColumnModel;
import com.seitenbau.stu.database.modelgenerator.ForeignKeyModel;

public class ColumnView extends JPanel
{

  private static final long serialVersionUID = 1L;
  
  private final JLabel columnName;
  private final DataTypeComboBox dataType;
  private final JLabel isNullable;
  private final JLabel isAutoIncrement;
  private final JLabel isGeneratedColumn;
  private final JLabel foreignKey;
  private final MultiplicityConfiguration local;
  private final MultiplicityConfiguration foreign;


  public ColumnView()
  {
    setLayout(new MigLayout("", "[][120::, grow, fill]"));
    
    columnName = addLabeledComponent("Name",  new JLabel());
    dataType = addLabeledComponent("Type", new DataTypeComboBox());
    isNullable = addLabeledComponent("isNullable", new JLabel());
    isAutoIncrement = addLabeledComponent("isAutoIncrement", new JLabel());
    isGeneratedColumn = addLabeledComponent("isGeneratedColumn", new JLabel());
    foreignKey = addLabeledComponent("Foreign Key to", new JLabel());
    local = addLabeledComponent("Local Multiplicity", MultiplicityConfiguration.createLocal());
    foreign = addLabeledComponent("Foreign Multiplicity", MultiplicityConfiguration.createForeign());
  }
  
  private <T extends Component> T addLabeledComponent(String text, T c)
  {
    JLabel label = new JLabel(text + ":");
    label.setLabelFor(c);
    add(label);
    add(c, "grow, wrap");
    return c;
  }
  
  public void updateView(ColumnModel column)
  {
    clearView();
    columnName.setText(column.getName());
    dataType.setDataType(column.getDataType());
    isAutoIncrement.setText(column.isAutoIncrement());
    isGeneratedColumn.setText(column.isGeneratedColumn());
    isNullable.setText(column.isNullable());
    
    if (column.hasForeignKey()) {
      ForeignKeyModel foreignKeyModel = column.getForeignKey();
      foreignKey.setText(foreignKeyModel.getPkTableName() + "." + foreignKeyModel.getPkColumnName());

      local.setEnabled(true);
      foreign.setEnabled(true);
      local.setMinimum(foreignKeyModel.getLocalMinimum());
      local.setMaximum(foreignKeyModel.getLocalMaximum());
      foreign.setMinimum(foreignKeyModel.getForeignMinimum());
      foreign.setMaximum(foreignKeyModel.getForeignMaximum());
    }
  }

  public void clearView()
  {
    dataType.setEnabled(false);

    columnName.setText("");
    foreignKey.setText("");
    isAutoIncrement.setText("");
    isGeneratedColumn.setText("");
    isNullable.setText("");
    
    local.setEnabled(false);
    foreign.setEnabled(false);
  }
  
  

}
