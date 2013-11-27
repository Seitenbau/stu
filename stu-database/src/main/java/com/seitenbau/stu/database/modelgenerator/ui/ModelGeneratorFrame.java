package com.seitenbau.stu.database.modelgenerator.ui;

import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.zookeeper.proto.SetWatches;

import net.miginfocom.swing.MigLayout;

import com.google.common.base.Optional;
import com.seitenbau.stu.database.modelgenerator.ColumnModel;
import com.seitenbau.stu.database.modelgenerator.DatabaseModel;
import com.seitenbau.stu.database.modelgenerator.ModelReader;
import com.seitenbau.stu.database.modelgenerator.STUModelWriter;
import com.seitenbau.stu.database.modelgenerator.TableModel;

public class ModelGeneratorFrame extends JFrame
{

  private static final long serialVersionUID = 1L;

  private final DefaultMutableTreeNode rootNode;

  private final JTree databaseTree;

  private final ColumnView columnView;

  private final JTabbedPane tabbedPane;

  private final JTextArea source;

  private final STUModelWriter writer = new STUModelWriter();
  
  private Optional<ColumnModel> activeColumn = Optional.absent();

  public ModelGeneratorFrame()
  {
    setTitle("STU Database Tool");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JPanel tabDatabase = new JPanel();
    //JPanel tabSTUModel = new JPanel();

    source = new JTextArea();
    source.setEditable(false);
    JScrollPane souceView = new JScrollPane(source);

    tabbedPane = new JTabbedPane();


    rootNode = new DefaultMutableTreeNode("Database");
    databaseTree = new JTree(rootNode);
    JScrollPane treeView = new JScrollPane(databaseTree);

    columnView = new ColumnView();

    tabDatabase.setLayout(new MigLayout("", "[200::, grow, fill][250::]", "[250::, grow, fill]"));
    tabDatabase.add(treeView);
    tabDatabase.add(columnView);

    tabbedPane.addTab("Database", tabDatabase);
    tabbedPane.addTab("Source", souceView);
    //tabbedPane.addTab("Data Generator", new JPanel());
    add(tabbedPane);

    pack();

    databaseTree.addTreeSelectionListener(new ColumnSelectionListener());
  }

  public void readScheme(Connection connection)
  {
    rootNode.removeAllChildren();

    final Connection finalConnection = connection;

    new Thread(new Runnable() {

      @Override
      public void run()
      {
        try {
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
    for (TableModel table : model.getTables()) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(table.getName());
      rootNode.add(node);

      for (ColumnModel column : table.getColumns())
      {
        DefaultMutableTreeNode columnNode = new DefaultMutableTreeNode(column);
        node.add(columnNode);
      }
    }

    databaseTree.expandRow(0);

    source.setText(writer.generate(model));
  }

  private class ColumnSelectionListener implements TreeSelectionListener
  {

    @Override
    public void valueChanged(TreeSelectionEvent event)
    {
      ColumnModel column = getSelectedColumnModel(event);
      if (columnChanged(column)) {
        if (column != null) {
          columnView.updateView(column);
          activeColumn = Optional.of(column);
        } else {
          columnView.clearView();
          activeColumn = Optional.absent();
        }
      }
    }
    
    private ColumnModel getSelectedColumnModel(TreeSelectionEvent event)
    {
      if (event.getNewLeadSelectionPath() == null) {
        return null;
      }
      
      if (!(event.getNewLeadSelectionPath().getLastPathComponent() instanceof DefaultMutableTreeNode)) {
        return null;
      }
      
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getNewLeadSelectionPath().getLastPathComponent();
      if ((node.getUserObject() instanceof ColumnModel)) {
        return (ColumnModel)node.getUserObject();
      }
      
      return null;
    }
  }
  
  private boolean columnChanged(ColumnModel newModel)
  {
    boolean isPresent = newModel != null;
    if (activeColumn.isPresent() != isPresent) {
      return true;
    }
    
    return !(isPresent && activeColumn.get() == newModel);
  }
}
