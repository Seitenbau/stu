package com.seitenbau.stu.database.modelgenerator.ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.miginfocom.swing.MigLayout;

import com.seitenbau.stu.database.modelgenerator.ColumnModel;
import com.seitenbau.stu.database.modelgenerator.DatabaseModel;
import com.seitenbau.stu.database.modelgenerator.TableModel;

public class DatabaseModelView extends JPanel
{

  private static final long serialVersionUID = 1L;

  private final DefaultMutableTreeNode rootNode;

  private final JTree databaseTree;

  private final ModelView modelView;
  
  public DatabaseModelView()
  {
    setLayout(new MigLayout("", "[200::, grow, fill][250::]", "[250::, grow, fill]"));

    rootNode = new DefaultMutableTreeNode("Database");
    databaseTree = new JTree(rootNode);
    JScrollPane treeView = new JScrollPane(databaseTree);

    modelView = new ModelView();
  
    
    add(treeView);
    add(modelView);
    
    databaseTree.addTreeSelectionListener(new ColumnSelectionListener());
  }

  void applyScheme(DatabaseModel model)
  {
    clear();
    for (TableModel table : model.getTables()) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(table);
      rootNode.add(node);

      for (ColumnModel column : table.getColumns())
      {
        DefaultMutableTreeNode columnNode = new DefaultMutableTreeNode(column);
        node.add(columnNode);
      }
    }

    DefaultTreeModel treemodel = (DefaultTreeModel)databaseTree.getModel();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)treemodel.getRoot();
    treemodel.reload(root);
   
    databaseTree.expandRow(0);
    modelView.setView(rootNode.getUserObject());
  }
  
  private class ColumnSelectionListener implements TreeSelectionListener
  {

    @Override
    public void valueChanged(TreeSelectionEvent event)
    {
      Object model = getSelectedTreeObject(event);
      if (model != null) {
        modelView.setView(model);
      } else {
        modelView.clear();
      }
    }
    
    private Object getSelectedTreeObject(TreeSelectionEvent event)
    {
      if (event.getNewLeadSelectionPath() == null) {
        return null;
      }
      
      if (!(event.getNewLeadSelectionPath().getLastPathComponent() instanceof DefaultMutableTreeNode)) {
        return null;
      }
      
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getNewLeadSelectionPath().getLastPathComponent();
      return node.getUserObject();
    }
  }
  
  public void clear()
  {
    rootNode.removeAllChildren();
  }

}
