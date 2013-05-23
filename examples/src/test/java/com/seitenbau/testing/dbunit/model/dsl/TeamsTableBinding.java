package com.seitenbau.testing.dbunit.model.dsl;


import java.util.HashSet;
import java.util.Set;

import com.seitenbau.testing.dbunit.model.TeamsTable.TeamsWhere;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.util.NullCompatibleEquivalence;
import com.seitenbau.testing.dbunit.dsl.CastUtil;
import com.seitenbau.testing.dbunit.dsl.ColumnBinding;
import com.seitenbau.testing.dbunit.generator.DataType;

public class TeamsTableBinding {

  public static final ColumnBinding<RowBuilder_Teams, TeamsWhere> REF = new ColumnBinding<RowBuilder_Teams, TeamsWhere>() 
  {
    @Override
    public void set(RowBuilder_Teams row, Object value)
    {
      // TODO NM think about it :-)
      // Setting reference does not make sense on REF column...
      throw new RuntimeException("Setting on REF is not allowed");
    }
    
    @Override
    public boolean isRefColumn() {
      return true;
    }

    public DataType getDataType() {
      return null;
    }
  };

  public static final ColumnBinding<RowBuilder_Teams, TeamsWhere> id = new ColumnBinding<RowBuilder_Teams, TeamsWhere>() 
  {
    @Override
    public void set(RowBuilder_Teams row, Object value)
    {
      if (definedIdsSet.contains(row) && !NullCompatibleEquivalence.equals(row.getId(), value)) {
        throw new IllegalStateException("Cannot reset column id [" + row.getId() + " vs " + value + "]");
      }
      definedIdsSet.add(row);
      row.setIdRaw(value);
    }

    @Override
    public RowBuilder_Teams query(TeamsWhere findWhere, Object value) {
      return findWhere.id((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
    }

    @Override
    public boolean isIdColumn() {
      return true;
    }
    
    private Set<RowBuilder_Teams> definedIdsSet = new HashSet<RowBuilder_Teams>();

    public DataType getDataType() {
      return DataType.BIGINT;
    }
  };

  public static final ColumnBinding<RowBuilder_Teams, TeamsWhere> title = new ColumnBinding<RowBuilder_Teams, TeamsWhere>() 
  {
    @Override
    public void set(RowBuilder_Teams row, Object value)
    {
      row.setTitleRaw(value);
    }

    public DataType getDataType() {
      return DataType.VARCHAR;
    }
  };

  public static final ColumnBinding<RowBuilder_Teams, TeamsWhere> description = new ColumnBinding<RowBuilder_Teams, TeamsWhere>() 
  {
    @Override
    public void set(RowBuilder_Teams row, Object value)
    {
      row.setDescriptionRaw(value);
    }

    public DataType getDataType() {
      return DataType.VARCHAR;
    }
  };

  public static final ColumnBinding<RowBuilder_Teams, TeamsWhere> membersize = new ColumnBinding<RowBuilder_Teams, TeamsWhere>() 
  {
    @Override
    public void set(RowBuilder_Teams row, Object value)
    {
      row.setMembersizeRaw(value);
    }

    public DataType getDataType() {
      return DataType.BIGINT;
    }
  };

}

