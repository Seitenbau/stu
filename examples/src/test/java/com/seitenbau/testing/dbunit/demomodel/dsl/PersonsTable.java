package com.seitenbau.testing.dbunit.demomodel.dsl;

import com.google.common.base.Optional;

import groovy.lang.Closure;

import java.util.HashSet;
import java.util.Set;

import com.seitenbau.testing.dbunit.demomodel.PersonsTable.PersonsGetWhere;
import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.dsl.CastUtil;
import com.seitenbau.testing.dbunit.dsl.ColumnBinding;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;
import com.seitenbau.testing.dbunit.dsl.TableParser;
import com.seitenbau.testing.dbunit.dsl.GeneralTableRowCallback;
import com.seitenbau.testing.dbunit.dsl.ITableAdapter;
import com.seitenbau.testing.dbunit.util.NullCompatibleEquivalence;
import com.seitenbau.testing.dbunit.modifier.IDataSetModifier;
import com.seitenbau.testing.dbunit.generator.DataType;


public class PersonsTable {

  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> REF = createREFBinding();

  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> id = createIdBinding();

  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> first_name = createFirstNameBinding();

  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> name = createNameBinding();

  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> job_id = createJobIdBinding();

  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> job = job_id;

  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> team_id = createTeamIdBinding();

  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> team = team_id;

  private final STUDSL _scope;

  private final com.seitenbau.testing.dbunit.demomodel.PersonsTable _table;

  private final Set<PersonsRef> _usedRefs;

  private final ITableAdapter<RowBuilder_Persons, PersonsGetWhere, PersonsRef> _adapter = new ITableAdapter<RowBuilder_Persons, PersonsGetWhere, PersonsRef>()
  {
    public RowBuilder_Persons insertRow() 
    {
      return _table.insertRow();
    }
  
    public PersonsGetWhere getWhere()
    {
      return _table.getWhere;
    }
    
    public void bindToScope(PersonsRef reference, RowBuilder_Persons row)
    {
      if (row != null) {
        reference.setBuilder(_scope, row);
        _usedRefs.add(reference);
      }
    }

    public void handleReferences(PersonsRef reference, RowBuilder_Persons row)
    {
      _scope.replacePersonsRefWithId(reference, row);
    }    

    public RowBuilder_Persons getRowByReference(PersonsRef reference)
    {
      return reference.getBuilder(_scope);
    }

    public String getTableName() {
      return "Persons";
    }
    
  };
    
  PersonsTable(STUDSL scope, com.seitenbau.testing.dbunit.demomodel.PersonsTable table)
  {
    _scope = scope;
    _table = table;
    _usedRefs = new HashSet<PersonsRef>();
  }
  
  Set<PersonsRef> getUsedRefs()
  {
    return _usedRefs;
  }
  
  com.seitenbau.testing.dbunit.demomodel.PersonsTable getTableModel()
  {
    return _table;
  }
  
  public void rows(Closure<?> rows) {
    GeneralTableRowCallback<RowBuilder_Persons, PersonsGetWhere, PersonsRef> callback = 
        new GeneralTableRowCallback<RowBuilder_Persons, PersonsGetWhere, PersonsRef>(_adapter);
    TableParser.parseTable(rows, this, callback);
  }

  private boolean valueMustBeSetRaw(Object value)
  {
    return (value instanceof DatabaseReference) || (value instanceof IDataSetModifier);
  }

  private ColumnBinding<RowBuilder_Persons, PersonsGetWhere> createREFBinding()
  {
    return new ColumnBinding<RowBuilder_Persons, PersonsGetWhere>()
    { 
      @Override
      public void set(RowBuilder_Persons row, Object value)
      {
        throw new RuntimeException("Setting on REF is not allowed");
      }
    
      @Override
      public boolean isRefColumn() {
        return true;
      }

      @Override
      public DataType getDataType() {
        return null;
      }
    };
  }

  private ColumnBinding<RowBuilder_Persons, PersonsGetWhere> createIdBinding() 
  {
    return new ColumnBinding<RowBuilder_Persons, PersonsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Persons row, Object value)
      {
        if (definedIdsSet.contains(row) && !NullCompatibleEquivalence.equals(row.getId(), value)) {
          throw new IllegalStateException("Cannot reset column id [" + row.getId() + " vs " + value + "]");
        }
        definedIdsSet.add(row);
        if (valueMustBeSetRaw(value)) {
          row.setIdRaw(value);
        }
        else {
          row.setId((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
        }
      }

      @Override
      public Optional<RowBuilder_Persons> getWhere(PersonsGetWhere getWhere, Object value) {
        return getWhere.id((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
      }

      @Override
      public boolean isIdentifierColumn() {
        return true;
      }
    
      private Set<RowBuilder_Persons> definedIdsSet = new HashSet<RowBuilder_Persons>();
      
      @Override
      public DataType getDataType() {
        return DataType.BIGINT;
      }
    };
  }
  private ColumnBinding<RowBuilder_Persons, PersonsGetWhere> createFirstNameBinding() 
  {
    return new ColumnBinding<RowBuilder_Persons, PersonsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Persons row, Object value)
      {
        if (valueMustBeSetRaw(value)) {
          row.setFirstNameRaw(value);
        }
        else {
          row.setFirstName((java.lang.String)CastUtil.cast(value, DataType.VARCHAR));
        }
      }
      
      @Override
      public DataType getDataType() {
        return DataType.VARCHAR;
      }
    };
  }
  private ColumnBinding<RowBuilder_Persons, PersonsGetWhere> createNameBinding() 
  {
    return new ColumnBinding<RowBuilder_Persons, PersonsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Persons row, Object value)
      {
        if (valueMustBeSetRaw(value)) {
          row.setNameRaw(value);
        }
        else {
          row.setName((java.lang.String)CastUtil.cast(value, DataType.VARCHAR));
        }
      }
      
      @Override
      public DataType getDataType() {
        return DataType.VARCHAR;
      }
    };
  }
  private ColumnBinding<RowBuilder_Persons, PersonsGetWhere> createJobIdBinding() 
  {
    return new ColumnBinding<RowBuilder_Persons, PersonsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Persons row, Object value)
      {
        if (valueMustBeSetRaw(value)) {
          row.setJobIdRaw(value);
        }
        else {
          row.setJobId((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
        }
      }

      @Override
      public void setReference(RowBuilder_Persons row, DatabaseReference ref)
      {
        JobsRef reference = (JobsRef)ref;
        RowBuilder_Jobs referenceBuilder = reference.getBuilder(_scope);
        if (referenceBuilder != null)
        {
          final java.lang.Long value = referenceBuilder.getId();
          row.setJobId(value);
        } else {
          row.setJobIdRaw(ref);
        }
      }
      
      @Override
      public DataType getDataType() {
        return DataType.BIGINT;
      }
    };
  }
  private ColumnBinding<RowBuilder_Persons, PersonsGetWhere> createTeamIdBinding() 
  {
    return new ColumnBinding<RowBuilder_Persons, PersonsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Persons row, Object value)
      {
        if (valueMustBeSetRaw(value)) {
          row.setTeamIdRaw(value);
        }
        else {
          row.setTeamId((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
        }
      }

      @Override
      public void setReference(RowBuilder_Persons row, DatabaseReference ref)
      {
        TeamsRef reference = (TeamsRef)ref;
        RowBuilder_Teams referenceBuilder = reference.getBuilder(_scope);
        if (referenceBuilder != null)
        {
          final java.lang.Long value = referenceBuilder.getId();
          row.setTeamId(value);
        } else {
          row.setTeamIdRaw(ref);
        }
      }
      
      @Override
      public DataType getDataType() {
        return DataType.BIGINT;
      }
    };
  }
  
}

