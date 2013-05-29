package com.seitenbau.testing.dbunit.demomodel.dsl;

import com.google.common.base.Optional;

import groovy.lang.Closure;

import java.util.HashSet;
import java.util.Set;

import com.seitenbau.testing.dbunit.demomodel.JobsTable.JobsGetWhere;
import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowCollection_Jobs;
import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.dsl.CastUtil;
import com.seitenbau.testing.dbunit.dsl.ColumnBinding;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;
import com.seitenbau.testing.dbunit.dsl.GeneralTableRowCallback;
import com.seitenbau.testing.dbunit.dsl.ITableAdapter;
import com.seitenbau.testing.dbunit.dsl.NoValue;
import com.seitenbau.testing.dbunit.dsl.TableParser;
import com.seitenbau.testing.dbunit.util.NullCompatibleEquivalence;
import com.seitenbau.testing.dbunit.modifier.IDataSetModifier;
import com.seitenbau.testing.dbunit.generator.DataType;

public class JobsTable {

  /**
   * Do not set a value. (To remove a value use <i>null</i>)
   */
  public final NoValue _ = new NoValue();

  /**
   * Column Header for Jobs table.
   * <p>
   * Data Type: JobsRef
   */
  public final ColumnBinding<RowBuilder_Jobs, JobsGetWhere> REF = createREFBinding();

  /**
   * Column Header for Jobs table.
   * <p>
   * Data Type: {@code java.lang.Long}
   * <br>   
   * Database Type: DataType.BIGINT
   * 
   */
  public final ColumnBinding<RowBuilder_Jobs, JobsGetWhere> id = createIdBinding();

  /**
   * Column Header for Jobs table.
   * <p>
   * Data Type: {@code java.lang.String}
   * <br>   
   * Database Type: DataType.VARCHAR
   * 
   */
  public final ColumnBinding<RowBuilder_Jobs, JobsGetWhere> title = createTitleBinding();

  /**
   * Column Header for Jobs table.
   * <p>
   * Data Type: {@code java.lang.String}
   * <br>   
   * Database Type: DataType.VARCHAR
   * 
   */
  public final ColumnBinding<RowBuilder_Jobs, JobsGetWhere> description = createDescriptionBinding();

  private final STUDSL _scope;

  private final com.seitenbau.testing.dbunit.demomodel.JobsTable _table;

  private final Set<RowBuilder_Jobs> _allRows;

  private final ITableAdapter<RowBuilder_Jobs, JobsGetWhere, JobsRef> _adapter = new ITableAdapter<RowBuilder_Jobs, JobsGetWhere, JobsRef>()
  {
    public RowBuilder_Jobs insertRow() 
    {
      RowBuilder_Jobs result = _table.insertRow();
      _allRows.add(result);
      return result;
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
    _allRows = new HashSet<RowBuilder_Jobs>();
  }
  
  Set<RowBuilder_Jobs> getAllRowBuilders()
  {
    return _allRows;
  }
  
  com.seitenbau.testing.dbunit.demomodel.JobsTable getTableModel()
  {
    return _table;
  }
  
  /**
   * Parses the rows of a Jobs table. Supported columns are:
   * <ul>
   *   <li><strong>{@code REF}</strong>: {@code JobsRef}</li>
   *   <li> <strong>{@code id}</strong>: {@code java.lang.Long}
   *   <li> <strong>{@code title}</strong>: {@code java.lang.String}
   *   <li> <strong>{@code description}</strong>: {@code java.lang.String}
   * </ul>
   * @param rows The table data
   */
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
      public Optional<RowBuilder_Jobs> getWhere(JobsGetWhere getWhere, Object value) {
        return getWhere.id((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
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
  
  public final JobsTableFindWhere findWhere = new JobsTableFindWhere(this);

  public static class JobsTableFindWhere {
    private final JobsTable table;
    
    JobsTableFindWhere(JobsTable table) 
    {
      this.table = table;
    }

    public RowCollection_Jobs id(JobsRef ref)
    {
      RowBuilder_Jobs builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        return new RowCollection_Jobs(table.getTableModel()); 
      }
      return table.getTableModel().findWhere.id(builder.getId());
    }
    
    public RowCollection_Jobs id(java.lang.Long toSearch)
    {
      return table.getTableModel().findWhere.id(toSearch);
    }

    public RowCollection_Jobs id(Integer toSearch) 
    {
      return id(Long.valueOf(toSearch));
    }

    public RowCollection_Jobs title(JobsRef ref)
    {
      RowBuilder_Jobs builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        return new RowCollection_Jobs(table.getTableModel()); 
      }
      return table.getTableModel().findWhere.title(builder.getTitle());
    }
    
    public RowCollection_Jobs title(java.lang.String toSearch)
    {
      return table.getTableModel().findWhere.title(toSearch);
    }

    public RowCollection_Jobs description(JobsRef ref)
    {
      RowBuilder_Jobs builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        return new RowCollection_Jobs(table.getTableModel()); 
      }
      return table.getTableModel().findWhere.description(builder.getDescription());
    }
    
    public RowCollection_Jobs description(java.lang.String toSearch)
    {
      return table.getTableModel().findWhere.description(toSearch);
    }
  }
  
  public final JobsTableGetWhere getWhere = new JobsTableGetWhere(this);

  public static class JobsTableGetWhere {
    private final JobsTable table;
    
    JobsTableGetWhere(JobsTable table) 
    {
      this.table = table;
    }

    public Optional<RowBuilder_Jobs> id(JobsRef ref)
    {
      RowBuilder_Jobs builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        return Optional.<RowBuilder_Jobs> absent();
      }
      return table.getTableModel().getWhere.id(builder.getId());
    }

    public Optional<RowBuilder_Jobs> id(java.lang.Long toSearch)
    {
      return table.getTableModel().getWhere.id(toSearch);
    }

    public Optional<RowBuilder_Jobs> id(Integer toSearch) 
    {
      return id(Long.valueOf(toSearch));
    }

    public Optional<RowBuilder_Jobs> title(JobsRef ref)
    {
      RowBuilder_Jobs builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        return Optional.<RowBuilder_Jobs> absent();
      }
      return table.getTableModel().getWhere.title(builder.getTitle());
    }

    public Optional<RowBuilder_Jobs> title(java.lang.String toSearch)
    {
      return table.getTableModel().getWhere.title(toSearch);
    }

    public Optional<RowBuilder_Jobs> description(JobsRef ref)
    {
      RowBuilder_Jobs builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        return Optional.<RowBuilder_Jobs> absent();
      }
      return table.getTableModel().getWhere.description(builder.getDescription());
    }

    public Optional<RowBuilder_Jobs> description(java.lang.String toSearch)
    {
      return table.getTableModel().getWhere.description(toSearch);
    }
  }
}

