package com.seitenbau.stu.database.modelgenerator;

import com.google.common.base.Optional;
import com.seitenbau.stu.database.generator.DataType;

public class ColumnModel
{
  private final String name;
  private final DataType dataType;
  private Optional<ForeignKeyModel> foreignKey;


  public ColumnModel(ColumnMetaData metaData)
  {
    this.name = metaData.getColumnName();
    this.dataType = metaData.getDataType().getDataType();
    foreignKey = Optional.absent();
  }

  public void addForeignKey(TableModel pkTable, String pkColumnName)
  {
    foreignKey = Optional.of(new ForeignKeyModel(pkTable, pkColumnName));
  }

  public void printStats()
  {
    //System.out.println("  COLUMN: " + name);
    //System.out.println("    dataType:" + dataType);
    //System.out.println("    Column Size: " + metaData.getColumnSize());
    //System.out.println("    getDecimalDigits: " + metaData.getDecimalDigits());
    //System.out.println("    getCharOctedLength: " + metaData.getCharOctedLength());
    //System.out.println("    Column Default: " + metaData.getColumnDefault());
    //System.out.println("    isAutoIncrement: " + metaData.isAutoIncrement());
    //System.out.println("    isGeneradedColumn: " + metaData.isGeneradedColumn());
    //System.out.println("    isNullable: " + metaData.isNullable());
    //System.out.println("    getNullable: " + metaData.getNullable());
    if (foreignKey.isPresent()) {
      //System.out.println("    Foreign Key -> " + foreignKey.get());
    }
  }

  public String getJavaCode()
  {
    StringBuilder result = new StringBuilder();
    result.append("        .column(\"");
    result.append(name);
    result.append("\", DataType.");
    result.append(dataType.toString());
    result.append(") //\n");
    //result.append("          // .description(\"\") //\n");
    if (foreignKey.isPresent()) {
      result.append("          .reference //\n");
      result.append("            .local //\n");
      //result.append("              // .name(\"\") //\n");
      //result.append("              // .description(\"\") //\n");
      result.append("            .foreign(" + foreignKey.get() + ") //\n");
      //result.append("              // .name(\"\") //\n");
      //result.append("              // .description(\"\") //\n");
    }
    return result.toString();
  }

  public boolean hasForeignKey()
  {
    return foreignKey.isPresent();
  }

  public ForeignKeyModel getForeignKey()
  {
    if (!hasForeignKey()) {
      throw new RuntimeException("No foreign key");
    }

    return foreignKey.get();
  }

  public String getName()
  {
    return name;
  }



}
