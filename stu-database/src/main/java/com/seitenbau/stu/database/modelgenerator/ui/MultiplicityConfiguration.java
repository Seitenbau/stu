package com.seitenbau.stu.database.modelgenerator.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class MultiplicityConfiguration extends JPanel
{
  private static final long serialVersionUID = 1L;
  private final JTextField minimum;
  private final JTextField maximum;
  private final boolean isForeign;
  
  private MultiplicityConfiguration(boolean isForeign)
  {
    this.isForeign = isForeign;
    setLayout(new MigLayout("", "0[50]0[]0[50]0", "0[]0"));
    
    minimum = new JTextField();
    maximum = new JTextField();
    
    add(minimum, "grow");
    add(new JLabel(".."));
    add(maximum, "grow");
  }
  
  public void setEnabled(boolean enabled)
  {
    if (!enabled) {
      minimum.setText("");
      maximum.setText("");
    }
    minimum.setEnabled(enabled);
    maximum.setEnabled(enabled && isForeign);
  }

  public void setMaximum(int i)
  {
    if (i < 0) {
      maximum.setText("*");
    } else {
      maximum.setText(String.valueOf(i));
    }
  }

  public void setMinimum(int i)
  {
    minimum.setText(String.valueOf(i));
  }
  
  public int getMinimum()
  {
    return Integer.valueOf(minimum.getText());
  }

  public int getMaximum()
  {
    if ("*".equals(maximum.getText())) {
      return -1;
    }
    return Integer.valueOf(maximum.getText());
  }

  public static MultiplicityConfiguration createLocal()
  {
    return new MultiplicityConfiguration(false);
  }
  
  public static MultiplicityConfiguration createForeign()
  {
    return new MultiplicityConfiguration(true);
  }

 
}
