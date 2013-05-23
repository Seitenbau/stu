package com.seitenbau.testing.dbunit.model.dsl;


import java.util.HashSet;
import java.util.Set;

import com.seitenbau.testing.dbunit.model.PersonsTable.PersonsWhere;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.util.NullCompatibleEquivalence;
import com.seitenbau.testing.dbunit.dsl.CastUtil;
import com.seitenbau.testing.dbunit.dsl.ColumnBinding;
import com.seitenbau.testing.dbunit.generator.DataType;

public class PersonsTableBinding {

  public static final ColumnBinding<RowBuilder_Persons, PersonsWhere> REF = new ColumnBinding<RowBuilder_Persons, PersonsWhere>() 
  {
    @Override
    public void set(RowBuilder_Persons row, Object value)
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

  public static final ColumnBinding<RowBuilder_Persons, PersonsWhere> id = new ColumnBinding<RowBuilder_Persons, PersonsWhere>() 
  {
    @Override
    public void set(RowBuilder_Persons row, Object value)
    {
      if (definedIdsSet.contains(row) && !NullCompatibleEquivalence.equals(row.getId(), value)) {
        throw new IllegalStateException("Cannot reset column id [" + row.getId() + " vs " + value + "]");
      }
      definedIdsSet.add(row);
      row.setIdRaw(value);
    }

    @Override
    public RowBuilder_Persons query(PersonsWhere findWhere, Object value) {
      return findWhere.id((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
    }

    @Override
    public boolean isIdColumn() {
      return true;
    }
    
    private Set<RowBuilder_Persons> definedIdsSet = new HashSet<RowBuilder_Persons>();

    public DataType getDataType() {
      return DataType.BIGINT;
    }
  };

  public static final ColumnBinding<RowBuilder_Persons, PersonsWhere> first_name = new ColumnBinding<RowBuilder_Persons, PersonsWhere>() 
  {
    @Override
    public void set(RowBuilder_Persons row, Object value)
    {
      row.setFirstNameRaw(value);
    }

    public DataType getDataType() {
      return DataType.VARCHAR;
    }
  };

  public static final ColumnBinding<RowBuilder_Persons, PersonsWhere> name = new ColumnBinding<RowBuilder_Persons, PersonsWhere>() 
  {
    @Override
    public void set(RowBuilder_Persons row, Object value)
    {
      row.setNameRaw(value);
    }

    public DataType getDataType() {
      return DataType.VARCHAR;
    }
  };

  public static final ColumnBinding<RowBuilder_Persons, PersonsWhere> job_id = new ColumnBinding<RowBuilder_Persons, PersonsWhere>() 
  {
    @Override
    public void set(RowBuilder_Persons row, Object value)
    {
      row.setJobIdRaw(value);
    }

    public DataType getDataType() {
      return DataType.BIGINT;
    }
  };

  public static final ColumnBinding<RowBuilder_Persons, PersonsWhere> team_id = new ColumnBinding<RowBuilder_Persons, PersonsWhere>() 
  {
    @Override
    public void set(RowBuilder_Persons row, Object value)
    {
      row.setTeamIdRaw(value);
    }

    public DataType getDataType() {
      return DataType.BIGINT;
    }
  };

}

