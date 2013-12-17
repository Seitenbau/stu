package com.seitenbau.stu.database.modelgenerator.examples;

import com.seitenbau.stu.database.generator.DataType;
import com.seitenbau.stu.database.modelgenerator.ColumnModel;
import com.seitenbau.stu.database.modelgenerator.DatabaseModel;
import com.seitenbau.stu.database.modelgenerator.TableModel;

public class RecursiveExampleModel extends DatabaseModel
{
  public RecursiveExampleModel()
  {
    TableModel tableA = new TableModel("Example");
    ColumnModel columnAId = new ColumnModel("id", DataType.BIGINT);
    columnAId.setIsNullable("NO");
    tableA.addColumn(columnAId);

    ColumnModel columnAFK = new ColumnModel("foreignid", DataType.BIGINT);
    columnAFK.setIsNullable("NO");
    tableA.addColumn(columnAFK);
    
    columnAFK.addForeignKey(tableA,  "id");
    columnAFK.getForeignKey().setForeignMinimum(1);
    columnAFK.getForeignKey().setForeignMaximum(1);

    add(tableA);
  }
  
  
}
