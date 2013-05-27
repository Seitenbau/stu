package com.seitenbau.testing.dbunit.demomodel.dsl;

import groovy.lang.Closure;

import java.util.HashSet;
import java.util.Set;

import com.seitenbau.testing.dbunit.demomodel.JobsTable.JobsGetWhere;
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


public class JobsTable {

  public final ColumnBinding<RowBuilder_Jobs, JobsGetWhere> REF = createREFBinding();

  public final ColumnBinding<RowBuilder_Jobs, JobsGetWhere> id = createIdBinding();

  public final ColumnBinding<RowBuilder_Jobs, JobsGetWhere> title = createTitleBinding();

  public final ColumnBinding<RowBuilder_Jobs, JobsGetWhere> description = createDescriptionBinding();

  private final STUDSL _scope;

  private final com.seitenbau.testing.dbunit.demomodel.JobsTable _table;

  private final Set<JobsRef> _usedRefs;

  private final ITableAdapter<RowBuilder_Jobs, JobsGetWhere, JobsRef> _adapter = new ITableAdapter<RowBuilder_Jobs, JobsGetWhere, JobsRef>()
  {
    public RowBuilder_Jobs insertRow() 
    {
      return _table.insertRow();
    }
  
    public JobsGetWhere getWhere()
    {
      return _table.getWhere;
    }
    
    public void bindToScope(JobsRef reference, RowBuilder_Jobs row)
    {
      if (row != null) {
        reference.setBuilder(_scope, row);
      }
    }

    public void handleReferences(JobsRef reference, RowBuilder_Jobs row)
    {
      _scope.replaceJobsRefWithId(reference, row);
    }    

    public RowBuilder_Jobs getRowByReference(JobsRef reference)
    {
      return reference.getBuilder(_scope);
    }

    public String getTableName() {
      return "Jobs";
    }
    
  };
    
  JobsTable(STUDSL scope, com.seitenbau.testing.dbunit.demomodel.JobsTable table)
  {
    _scope = scope;
    _table = table;
    _usedRefs = new HashSet<JobsRef>();
  }
  
  Set<JobsRef> getUsedRefs()
  {
    return _usedRefs;
  }
  
  com.seitenbau.testing.dbunit.demomodel.JobsTable getTableModel()
  {
    return _table;
  }
  
  public void rows(Closure<?> rows) {
    GeneralTableRowCallback<RowBuilder_Jobs, JobsGetWhere, JobsRef> callback = 
        new GeneralTableRowCallback<RowBuilder_Jobs, JobsGetWhere, JobsRef>(_adapter);
    TableParser.parseTable(rows, this, callback);
  }

  private boolean valueMustBeSetRaw(Object value)
  {
    return (value instanceof DatabaseReference) || (value instanceof IDataSetModifier);
  }

  private ColumnBinding<RowBuilder_Jobs, JobsGetWhere> createREFBinding()
  {
    return new ColumnBinding<RowBuilder_Jobs, JobsGetWhere>()
    { 
      @Override
      public void set(RowBuilder_Jobs row, Object value)
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

  private ColumnBinding<RowBuilder_Jobs, JobsGetWhere> createIdBinding() 
  {
    return new ColumnBinding<RowBuilder_Jobs, JobsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Jobs row, Object value)
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
      public RowBuilder_Jobs query(JobsGetWhere findWhere, Object value) {
        return findWhere.id((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
      }

      @Override
      public boolean isIdentifierColumn() {
        return true;
      }
    
      private Set<RowBuilder_Jobs> definedIdsSet = new HashSet<RowBuilder_Jobs>();
      
      @Override
      public DataType getDataType() {
        return DataType.BIGINT;
      }
    };
  }
  private ColumnBinding<RowBuilder_Jobs, JobsGetWhere> createTitleBinding() 
  {
    return new ColumnBinding<RowBuilder_Jobs, JobsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Jobs row, Object value)
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
  private ColumnBinding<RowBuilder_Jobs, JobsGetWhere> createDescriptionBinding() 
  {
    return new ColumnBinding<RowBuilder_Jobs, JobsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Jobs row, Object value)
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
  
}

