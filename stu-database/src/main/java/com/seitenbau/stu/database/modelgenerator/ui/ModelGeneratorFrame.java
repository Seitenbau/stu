package com.seitenbau.stu.database.modelgenerator.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

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

  private final JLabel columnName;

  private final JTabbedPane tabbedPane;

  private final JTextArea source;

  private final STUModelWriter writer = new STUModelWriter();

  public ModelGeneratorFrame()
  {
    setTitle("STU Database Tool");

    JPanel tabDatabase = new JPanel();
    //JPanel tabSTUModel = new JPanel();

    source = new JTextArea();
    source.setEditable(false);
    JScrollPane souceView = new JScrollPane(source);

    tabbedPane = new JTabbedPane();


    rootNode = new DefaultMutableTreeNode("Database");
    databaseTree = new JTree(rootNode);
    databaseTree.setMinimumSize(new Dimension(200, 200));
    databaseTree.setPreferredSize(new Dimension(200, 200));
    JScrollPane treeView = new JScrollPane(databaseTree);

    JPanel columnView = new JPanel();
    columnView.setMinimumSize(new Dimension(200, 200));
    columnView.setPreferredSize(new Dimension(200, 200));

    columnName = new JLabel();
    columnView.add(columnName);

    tabDatabase.setLayout(new BorderLayout());
    tabDatabase.add(treeView, BorderLayout.CENTER);
    tabDatabase.add(columnView, BorderLayout.EAST);

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
      columnName.setText("");
      if (!(event.getNewLeadSelectionPath().getLastPathComponent() instanceof DefaultMutableTreeNode))
      {
        return;
      }

      DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getNewLeadSelectionPath().getLastPathComponent();
      if (!(node.getUserObject() instanceof ColumnModel)) {
        return;
      }

      ColumnModel column = (ColumnModel)node.getUserObject();
      columnName.setText(column.getName());
    }

  }
}
