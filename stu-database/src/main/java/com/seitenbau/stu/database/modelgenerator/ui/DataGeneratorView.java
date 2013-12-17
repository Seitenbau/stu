package com.seitenbau.stu.database.modelgenerator.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.SwingUtilities;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import com.seitenbau.stu.database.generator.ColumnBuilder;
import com.seitenbau.stu.database.generator.TableBuilder;
import com.seitenbau.stu.database.generator.TableBuilderCommon;
import com.seitenbau.stu.database.generator.data.DataGenerator;
import com.seitenbau.stu.database.generator.data.Entities;
import com.seitenbau.stu.database.generator.data.STUTableOutput;
import com.seitenbau.stu.database.modelgenerator.ColumnModel;
import com.seitenbau.stu.database.modelgenerator.DatabaseModel;
import com.seitenbau.stu.database.modelgenerator.ForeignKeyModel;
import com.seitenbau.stu.database.modelgenerator.TableModel;

public class DataGeneratorView extends CodeView
{

  private static final long serialVersionUID = 1L;

  private int lastModelHash = 0;
  
  private AtomicBoolean generating;

  public DataGeneratorView()
  {
    super(SyntaxConstants.SYNTAX_STYLE_GROOVY);
    generating = new AtomicBoolean(false);
  }

  public void setModel(DatabaseModel model)
  {
    if (!generating.compareAndSet(false, true)) {
      return; // error :-)
    }
    
    final GeneratorModel genModel = new GeneratorModel(model);
    
    if (genModel.hashCode() == lastModelHash) {
      //return;
    }
    
    lastModelHash = genModel.hashCode();
    
    setSource("// Please wait...");
    
    new Thread(new Runnable() {

      @Override
      public void run()
      {
        DataGenerator generator = new DataGenerator(genModel);

        StringBuilder summary = new StringBuilder();
        Entities entities = generator.generate(genModel);

        STUTableOutput output = new STUTableOutput();
        final String generatedDSL = output.create(entities);

        entities.printStats();
        System.out.println();

        summary.append("Loop Count: " + entities.getLoopCount() + "\t");
        System.out.println(summary.toString());        
        generating.set(false);            
        
        SwingUtilities.invokeLater(new Runnable()
        {
          public void run()
          {
            setSource(generatedDSL);
          }
        });
      }
      
    }).start();

  }
  
  private class GeneratorModel extends com.seitenbau.stu.database.generator.DatabaseModel {

    GeneratorModel(DatabaseModel model)
    {
      database("AnyDatabase");
      packageName("com.anydatabase.model"); 
      
      Map<TableModel, TableBuilder> builders = new HashMap<TableModel, TableBuilder>();
     
      for (TableModel tableModel : model.getTables()) {
        if (tableModel.isAssociative()) {
          builders.put(tableModel, associativeTable(tableModel.getName()));
        } else {
          builders.put(tableModel, table(tableModel.getName()));
        }
      }
      
      
      for (TableModel tableModel : model.getTables()) {
        TableBuilderCommon activeBuilder = builders.get(tableModel);
        
        for (ColumnModel columnModel : tableModel.getColumns()) {
          System.out.println("Adding builder " + columnModel.getName() + " with " + columnModel.getDataType());
          ColumnBuilder columnBuilder = activeBuilder.column(columnModel.getName(), columnModel.getDataType());
          
          activeBuilder = columnBuilder;
          
          if (columnModel.hasForeignKey()) {
            ForeignKeyModel fk = columnModel.getForeignKey();
            TableBuilder foreignTable = builders.get(fk.getPkTable());
            
            activeBuilder = columnBuilder
              .reference
                .local
                  .multiplicity(fk.getLocalString())
                .foreign(foreignTable.ref(fk.getPkColumnName()))
                  .multiplicity(fk.getForeignString());
          }
        }
        
        activeBuilder.build();
      }
      
    }

  }
  
}
