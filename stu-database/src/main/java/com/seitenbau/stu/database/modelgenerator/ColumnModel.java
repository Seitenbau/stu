package com.seitenbau.stu.database.modelgenerator;

import com.google.common.base.Optional;
import com.seitenbau.stu.database.generator.DataType;

public class ColumnModel
{
  private Optional<ForeignKeyModel> foreignKey;
  
  private String name;
  
  private DataType dataType;

  private Integer columnSize;
  
  private Integer decimalDigits;

  private Integer charOctedLength;

  private String columnDefault;

  private String isAutoIncrement;
  
  private String isGeneratedColumn;

  private String isNullable;

  private Integer nullable;
  
  public ColumnModel(ColumnMetaData metaData)
  {
    name = metaData.getColumnName();
    dataType = metaData.getDataType().getDataType();
    columnSize = metaData.getColumnSize();
    decimalDigits = metaData.getDecimalDigits();
    charOctedLength = metaData.getCharOctedLength();
    columnDefault = metaData.getColumnDefault();
    isAutoIncrement = metaData.isAutoIncrement();
    isGeneratedColumn = metaData.isGeneratedColumn();
    isNullable = metaData.isNullable();
    nullable = metaData.getNullable();
    foreignKey = Optional.absent();
  }

  public void addForeignKey(TableModel pkTable, String pkColumnName)
  {
    foreignKey = Optional.of(new ForeignKeyModel(pkTable, pkColumnName, isNullable));
  }

  @Override
  public String toString()
  {
    return name;
  }

  public String getJavaCode()
  {
    StringBuilder result = new StringBuilder();
    result.append("        .column(\"");
    result.append(getName());
    result.append("\", DataType.");
    result.append(getDataType().toString());
    result.append(") //\n");
    // result.append("          // .description(\"\") //\n");
    if (foreignKey.isPresent())
    {
      result.append("          .reference //\n");
      result.append("            .local //\n");
      // result.append("              // .name(\"\") //\n");
      // result.append("              // .description(\"\") //\n");
      result.append("              .multiplicity(\"" + getForeignKey().getLocalString() + "\") //\n");
      result.append("            .foreign(" + foreignKey.get() + ") //\n");
      // result.append("              // .name(\"\") //\n");
      // result.append("              // .description(\"\") //\n");
      result.append("              .multiplicity(\"" + getForeignKey().getForeignString() + "\") //\n");
    }
    return result.toString();
  }

  public boolean hasForeignKey()
  {
    return foreignKey.isPresent();
  }

  public ForeignKeyModel getForeignKey()
  {
    if (!hasForeignKey())
    {
      throw new RuntimeException("No foreign key");
    }

    return foreignKey.get();
  }

  public String getName()
  {
    return name;
  }
  
  public DataType getDataType()
  {
    return dataType;
  }

  public int getColumnSize()
  {
    return columnSize;
  }
  
  public int getDecimalDigits()
  {
    return decimalDigits;
  }

  public int getCharOctedLength()
  {
    return charOctedLength;
  }

  public String getColumnDefault()
  {
    return columnDefault;
  }

  public String isAutoIncrement()
  {
    return isAutoIncrement;
  }

  public String isGeneratedColumn()
  {
    return isGeneratedColumn;
  }

  public String isNullable()
  {
    return isNullable;
  }

  public int getNullable()
  {
    return nullable;
  }
  
}
