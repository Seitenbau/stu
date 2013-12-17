package com.seitenbau.stu.database.modelgenerator.examples;

import com.seitenbau.stu.database.generator.DataType;
import com.seitenbau.stu.database.modelgenerator.ColumnModel;
import com.seitenbau.stu.database.modelgenerator.DatabaseModel;
import com.seitenbau.stu.database.modelgenerator.TableModel;

public class CyclicExampleModel extends DatabaseModel
{
  public CyclicExampleModel()
  {
    TableModel tableA = new TableModel("A");
    ColumnModel columnAId = new ColumnModel("id", DataType.BIGINT);
    columnAId.setIsNullable("NO");
    tableA.addColumn(columnAId);

    ColumnModel columnAFK = new ColumnModel("foreignid", DataType.BIGINT);
    columnAFK.setIsNullable("NO");
    tableA.addColumn(columnAFK);
    
    TableModel tableB = new TableModel("B");
    ColumnModel columnBId = new ColumnModel("id", DataType.BIGINT);
    columnBId.setIsNullable("NO");
    tableB.addColumn(columnBId);
    
    ColumnModel columnBFK = new ColumnModel("foreignid", DataType.BIGINT);
    columnBFK.setIsNullable("NO");
    tableB.addColumn(columnBFK);
    
    columnAFK.addForeignKey(tableB,  "id");
    columnAFK.getForeignKey().setForeignMinimum(1);
    columnAFK.getForeignKey().setForeignMaximum(1);

    columnBFK.addForeignKey(tableA,  "id");
    columnBFK.getForeignKey().setForeignMinimum(1);
    columnBFK.getForeignKey().setForeignMaximum(1);
    
    add(tableA);
    add(tableB);
  }
  
  
}
