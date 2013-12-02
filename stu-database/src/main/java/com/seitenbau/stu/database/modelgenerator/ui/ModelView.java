package com.seitenbau.stu.database.modelgenerator.ui;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.google.common.base.Optional;
import com.seitenbau.stu.database.modelgenerator.ColumnModel;
import com.seitenbau.stu.database.modelgenerator.TableModel;


public class ModelView extends JPanel
{

  private static final long serialVersionUID = 1L;

  private Optional<Object> activeModel = Optional.absent();
  

  private final ColumnView columnView;
  private final TableView tableView;
  
  public ModelView()
  {
    setLayout(new MigLayout("hidemode 3, fill", "0[grow, fill]0", "0[grow, fill]0"));

    columnView = new ColumnView();
    columnView.setVisible(false);
    
    tableView = new TableView();
    tableView.setVisible(false);
    
    add(columnView);
    add(tableView);
  }

  public void setView(Object model)
  {
    if (!viewModelChanged(model)) {
      return;
    }
    
    activeModel = Optional.of(model);
    System.out.println("setting");
    
    columnView.applyValues();
    
    if (model instanceof ColumnModel) {
      tableView.setVisible(false);
      columnView.setVisible(true);
      columnView.updateView((ColumnModel) model);
      return;
    }
    if (model instanceof TableModel) {
      System.out.println("setting tableModel");
      columnView.setVisible(false);
      tableView.setVisible(true);
      tableView.updateView((TableModel) model);
      return;
    }

    columnView.setVisible(false);
    tableView.setVisible(false);
  }
  
  public void clear()
  {
    activeModel = Optional.absent();

    columnView.clearView();
    columnView.setVisible(false);

    tableView.clearView();
    tableView.setVisible(false);
  }
  
  private boolean viewModelChanged(Object newModel)
  {
    boolean isPresent = newModel != null;
    if (activeModel.isPresent() != isPresent) {
      return true;
    }
    
    return !(isPresent && activeModel.get() == newModel);
  }

}
