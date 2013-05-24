package com.seitenbau.testing.dbunit.demomodel.dsl;

import groovy.lang.Closure;

import java.util.HashSet;
import java.util.Set;

import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.TeamsWhere;
import com.seitenbau.testing.dbunit.dsl.CastUtil;
import com.seitenbau.testing.dbunit.dsl.ColumnBinding;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;
import com.seitenbau.testing.dbunit.dsl.TableParser;
import com.seitenbau.testing.dbunit.dsl.GeneralTableRowCallback;
import com.seitenbau.testing.dbunit.dsl.ITableAdapter;
import com.seitenbau.testing.dbunit.util.NullCompatibleEquivalence;
import com.seitenbau.testing.dbunit.modifier.IDataSetModifier;
import com.seitenbau.testing.dbunit.generator.DataType;


public class TeamsTable {

  public final ColumnBinding<RowBuilder_Teams, TeamsWhere> REF = createREFBinding();

  public final ColumnBinding<RowBuilder_Teams, TeamsWhere> id = createIdBinding();

  public final ColumnBinding<RowBuilder_Teams, TeamsWhere> title = createTitleBinding();

  public final ColumnBinding<RowBuilder_Teams, TeamsWhere> description = createDescriptionBinding();

  public final ColumnBinding<RowBuilder_Teams, TeamsWhere> membersize = createMembersizeBinding();

  private final STUDSL _scope;

  private final com.seitenbau.testing.dbunit.demomodel.TeamsTable _table;

  private final Set<TeamsRef> _usedRefs;

  private final ITableAdapter<RowBuilder_Teams, TeamsWhere, TeamsRef> _adapter = new ITableAdapter<RowBuilder_Teams, TeamsWhere, TeamsRef>()
  {
    public RowBuilder_Teams insertRow() 
    {
      return _table.insertRow();
    }
  
    public TeamsWhere getFindWhere()
    {
      return _table.findWhere;
    }
    
    public void referenceUsed(TeamsRef reference, RowBuilder_Teams row)
    {
      if (row != null) {
        reference.setBuilder(_scope, row);
        _usedRefs.add(reference);
      }
    }

    public void handleReferences(TeamsRef reference, RowBuilder_Teams row)
    {
      _scope.replaceTeamsRefWithId(reference, row);
    }    

    public RowBuilder_Teams getRowByReference(TeamsRef reference)
    {
      return reference.getBuilder(_scope);
    }

    public String getTableName() {
      return "Teams";
    }
    
  };
    
  TeamsTable(STUDSL scope, com.seitenbau.testing.dbunit.demomodel.TeamsTable table)
  {
    _scope = scope;
    _table = table;
    _usedRefs = new HashSet<TeamsRef>();
  }
  
  Set<TeamsRef> getUsedRefs()
  {
    return _usedRefs;
  }
  
  com.seitenbau.testing.dbunit.demomodel.TeamsTable getTableModel()
  {
    return _table;
  }
  
  public void rows(Closure<?> rows) {
    GeneralTableRowCallback<RowBuilder_Teams, TeamsWhere, TeamsRef> callback = 
        new GeneralTableRowCallback<RowBuilder_Teams, TeamsWhere, TeamsRef>(_adapter);
    TableParser.parseTable(rows, this, callback);
  }

  private boolean valueMustBeSetRaw(Object value)
  {
    return (value instanceof DatabaseReference) || (value instanceof IDataSetModifier);
  }

  private ColumnBinding<RowBuilder_Teams, TeamsWhere> createREFBinding()
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsWhere>()
    { 
      @Override
      public void set(RowBuilder_Teams row, Object value)
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

  private ColumnBinding<RowBuilder_Teams, TeamsWhere> createIdBinding() 
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsWhere>()
    {
      @Override
      public void set(RowBuilder_Teams row, Object value)
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
      public RowBuilder_Teams query(TeamsWhere findWhere, Object value) {
        return findWhere.id((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
      }

      @Override
      public boolean isIdentifierColumn() {
        return true;
      }
    
      private Set<RowBuilder_Teams> definedIdsSet = new HashSet<RowBuilder_Teams>();
      
      @Override
      public DataType getDataType() {
        return DataType.BIGINT;
      }
    };
  }
  private ColumnBinding<RowBuilder_Teams, TeamsWhere> createTitleBinding() 
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsWhere>()
    {
      @Override
      public void set(RowBuilder_Teams row, Object value)
      {
        if (valueMustBeSetRaw(value)) {
          row.setTitleRaw(value);
        }
        else {
          row.setTitle((java.lang.String)CastUtil.cast(value, DataType.VARCHAR));
        }
      }
      
      @Override
      public DataType getDataType() {
        return DataType.VARCHAR;
      }
    };
  }
  private ColumnBinding<RowBuilder_Teams, TeamsWhere> createDescriptionBinding() 
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsWhere>()
    {
      @Override
      public void set(RowBuilder_Teams row, Object value)
      {
        if (valueMustBeSetRaw(value)) {
          row.setDescriptionRaw(value);
        }
        else {
          row.setDescription((java.lang.String)CastUtil.cast(value, DataType.VARCHAR));
        }
      }
      
      @Override
      public DataType getDataType() {
        return DataType.VARCHAR;
      }
    };
  }
  private ColumnBinding<RowBuilder_Teams, TeamsWhere> createMembersizeBinding() 
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsWhere>()
    {
      @Override
      public void set(RowBuilder_Teams row, Object value)
      {
        if (valueMustBeSetRaw(value)) {
          row.setMembersizeRaw(value);
        }
        else {
          row.setMembersize((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
        }
      }
      
      @Override
      public DataType getDataType() {
        return DataType.BIGINT;
      }
    };
  }
  
}

