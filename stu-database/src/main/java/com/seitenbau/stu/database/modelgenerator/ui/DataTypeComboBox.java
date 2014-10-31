package com.seitenbau.stu.database.modelgenerator.ui;

import javax.swing.JComboBox;

import com.seitenbau.stu.database.generator.DataType;

public class DataTypeComboBox extends JComboBox
{

  private static final long serialVersionUID = 1L;
  
  public DataTypeComboBox()
  {
    super(DataType.values());
  }
  
  public void setDataType(DataType type)
  {
    setEnabled(true);
    setSelectedItem(type);
  }
  
  public DataType getDataType()
  {
    return (DataType)getSelectedItem();
  }
  
  public void setEnabled(boolean enabled)
  {
    super.setEnabled(enabled);
    if (!enabled) {
      setSelectedIndex(0);
    }
  }

}
