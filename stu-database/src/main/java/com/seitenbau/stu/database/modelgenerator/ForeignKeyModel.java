package com.seitenbau.stu.database.modelgenerator;

public class ForeignKeyModel
{
  TableModel pkTable;

  String pkColumnName;
  
  boolean isOptional;
  
  int localMinimum;
  
  int foreignMinimum;
  
  int foreignMaximum;

  public ForeignKeyModel(TableModel pkTable, String pkColumnName, String isNullable)
  {
    this.pkTable = pkTable;
    this.pkColumnName = pkColumnName;
    
    isOptional = "YES".equalsIgnoreCase(isNullable);

    localMinimum = (isOptional) ? 0 : 1;
    foreignMinimum = 0;
    foreignMaximum = -1;
  }

  public String getPkTableName()
  {
    return pkTable.getName();
  }

  public String getPkColumnName()
  {
    return pkColumnName;
  }

  @Override
  public String toString()
  {
    return getPkTableName() + ".ref(\"" + pkColumnName + "\")";
  }

  public TableModel getPkTable()
  {
    return pkTable;
  }
  
  public boolean isOptional()
  {
    return isOptional;
  }
  
  public int getLocalMaximum()
  {
    return 1;
  }
  
  public int getLocalMinimum()
  {
    return localMinimum;
  }
  
  public int getForeignMaximum()
  {
    return foreignMaximum;
  }
  
  public int getForeignMinimum()
  {
    return foreignMinimum;
  }

  public void setLocalMinimum(int minimum)
  {
    localMinimum = minimum;
  }

  public void setForeignMinimum(int minimum)
  {
    foreignMinimum = minimum;
  }

  public void setForeignMaximum(int maximum)
  {
    foreignMaximum = maximum;
  }

  public String getLocalString()
  {
    return String.valueOf(localMinimum) + "..1";
  }

  public String getForeignString()
  {
    StringBuilder result = new StringBuilder();
    result.append(String.valueOf(foreignMinimum));
    result.append("..");
    if (foreignMaximum < 0) {
      result.append("*");
    } else {
      result.append(String.valueOf(foreignMaximum));
    }
    return result.toString();
  }


}
