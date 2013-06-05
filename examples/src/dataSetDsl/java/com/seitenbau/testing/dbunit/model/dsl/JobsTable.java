package com.seitenbau.testing.dbunit.model.dsl;

import com.google.common.base.Optional;

import com.seitenbau.testing.dbunit.dsl.CastUtil;
import com.seitenbau.testing.dbunit.dsl.ColumnBinding;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;
import com.seitenbau.testing.dbunit.dsl.TableParserCallback;
import com.seitenbau.testing.dbunit.dsl.ITableAdapter;
import com.seitenbau.testing.dbunit.dsl.LazyValue;
import com.seitenbau.testing.dbunit.dsl.NoValue;
import com.seitenbau.testing.dbunit.dsl.TableParser;
import com.seitenbau.testing.dbunit.generator.DataType;
import com.seitenbau.testing.dbunit.modifier.IDataSetModifier;
import com.seitenbau.testing.dbunit.util.NullCompatibleEquivalence;
import com.seitenbau.testing.util.date.DateBuilder;
import com.seitenbau.testing.util.Action;
import com.seitenbau.testing.util.Filter;

import groovy.lang.Closure;

import java.util.HashSet;
import java.util.Set;


import com.seitenbau.testing.dbunit.model.JobsTable.JobsGetWhere;
import com.seitenbau.testing.dbunit.model.JobsTable.RowCollection_Jobs;
import com.seitenbau.testing.dbunit.model.JobsModel;
import com.seitenbau.testing.dbunit.model.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.model.JobsTable.RowGetters_Jobs;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowGetters_Teams;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowGetters_Persons;

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
   * The job title
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
   * The description of the job
   * <p>
   * Data Type: {@code java.lang.String}
   * <br>   
   * Database Type: DataType.VARCHAR
   * 
   */
  public final ColumnBinding<RowBuilder_Jobs, JobsGetWhere> description = createDescriptionBinding();

  private final PersonDatabaseBuilder _scope;

  private final com.seitenbau.testing.dbunit.model.JobsTable _table;

  private final ITableAdapter<RowBuilder_Jobs, JobsGetWhere, JobsRef> _adapter = new TableAdapter();
    
  JobsTable(PersonDatabaseBuilder scope, com.seitenbau.testing.dbunit.model.JobsTable table)
  {
    _scope = scope;
    _table = table;
  }
  
  com.seitenbau.testing.dbunit.model.JobsTable getTableModel()
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
  public void rows(Closure<?> rows)
  {
    TableParserCallback<RowBuilder_Jobs, JobsGetWhere, JobsRef> callback = 
        new TableParserCallback<RowBuilder_Jobs, JobsGetWhere, JobsRef>(_adapter);
    TableParser.parseTable(rows, this, callback);
  }
  
  public int getRowCount() 
  {
    return _table.getRowCount();
  }
  

  private boolean valueMustBeSetRaw(Object value)
  {
    return (value instanceof DatabaseReference) || (value instanceof IDataSetModifier);
  }
  
  private class TableAdapter implements ITableAdapter<RowBuilder_Jobs, JobsGetWhere, JobsRef>
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
      reference.setBuilder(_scope, row);
    }

    public void handleReferences(JobsRef reference, RowBuilder_Jobs row)
    {
      _scope.replaceJobsRefWithId(reference, row);
    }    

    public RowBuilder_Jobs getRowByReference(JobsRef reference)
    {
      return reference.getBuilder(_scope);
    }

    public String getTableName()
    {
      return "Jobs";
    }
    
  };  

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
      public boolean isRefColumn() 
      {
        return true;
      }

      @Override
      public DataType getDataType() 
      {
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
        if (valueMustBeSetRaw(value)) 
        {
          row.setIdRaw(value);
        } else {
          row.setId((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
        }
      }

      @Override
      public Optional<RowBuilder_Jobs> getWhere(JobsGetWhere getWhere, Object value)
      {
        return getWhere.id((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
      }

      @Override
      public boolean isIdentifierColumn()
      {
        return true;
      }
    
      private Set<RowBuilder_Jobs> definedIdsSet = new HashSet<RowBuilder_Jobs>();
      
      @Override
      public DataType getDataType()
      {
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
        if (valueMustBeSetRaw(value)) 
        {
          row.setTitleRaw(value);
        } else {
          row.setTitle((java.lang.String)CastUtil.cast(value, DataType.VARCHAR));
        }
      }

      @Override
      public void setLazyValue(RowBuilder_Jobs row, LazyValue lazyValue)
      {
        row.setTitleRaw(lazyValue);
      }
      
      @Override
      public DataType getDataType()
      {
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
        if (valueMustBeSetRaw(value)) 
        {
          row.setDescriptionRaw(value);
        } else {
          row.setDescription((java.lang.String)CastUtil.cast(value, DataType.VARCHAR));
        }
      }

      @Override
      public void setLazyValue(RowBuilder_Jobs row, LazyValue lazyValue)
      {
        row.setDescriptionRaw(lazyValue);
      }
      
      @Override
      public DataType getDataType()
      {
        return DataType.VARCHAR;
      }

    };
  }

  public ExtendedRowBuilder_Jobs insertRow()
  {
    return new ExtendedRowBuilder_Jobs(this, _table.insertRow());
  }

  public ExtendedRowBuilder_Jobs insertRow(JobsModel row)
  {
    return new ExtendedRowBuilder_Jobs(this, _table.insertRow(row));
  }

  public ExtendedRowBuilder_Jobs insertRowAt(int index)
  {
    return new ExtendedRowBuilder_Jobs(this, _table.insertRowAt(index));
  }

  public ExtendedRowBuilder_Jobs insertRow(RowBuilder_Jobs theRow)
  {
    return new ExtendedRowBuilder_Jobs(this, _table.insertRow(theRow));
  }
  
  public static class ExtendedRowBuilder_Jobs implements RowBuilder_Jobs
  {
  
    private final JobsTable _parent;
    
    private final RowBuilder_Jobs _delegate;
  
    ExtendedRowBuilder_Jobs(JobsTable parent, RowBuilder_Jobs delegate)
    {
      _parent = parent;
      _delegate = delegate;
    }


    public ExtendedRowBuilder_Jobs setId(Integer intValue)
    {
      _delegate.setId(intValue);
      return this;
    }

    public ExtendedRowBuilder_Jobs setId(java.lang.Long value)
    {
      _delegate.setId(value);
      return this;
    }

    public ExtendedRowBuilder_Jobs setIdRaw(Object value)
    {
      _delegate.setIdRaw(value);
      return this;
    }
    
    public ExtendedRowBuilder_Jobs nextId()
    {
      _delegate.nextId();
      return this;
    }

    public java.lang.Long getId()
    {
      return _delegate.getId();
    }

    public Object getIdRaw()
    {
      return _delegate.getIdRaw();
    }

    public ExtendedRowBuilder_Jobs setTitle(java.lang.String value)
    {
      _delegate.setTitle(value);
      return this;
    }

    public ExtendedRowBuilder_Jobs setTitleRaw(Object value)
    {
      _delegate.setTitleRaw(value);
      return this;
    }
    
    /**
     * The job title
     * @return The value
     */
    public java.lang.String getTitle()
    {
      return _delegate.getTitle();
    }

    public Object getTitleRaw()
    {
      return _delegate.getTitleRaw();
    }

    public ExtendedRowBuilder_Jobs setDescription(java.lang.String value)
    {
      _delegate.setDescription(value);
      return this;
    }

    public ExtendedRowBuilder_Jobs setDescriptionRaw(Object value)
    {
      _delegate.setDescriptionRaw(value);
      return this;
    }
    
    /**
     * The description of the job
     * @return The value
     */
    public java.lang.String getDescription()
    {
      return _delegate.getDescription();
    }

    public Object getDescriptionRaw()
    {
      return _delegate.getDescriptionRaw();
    }
  

    public ExtendedRowBuilder_Jobs insertRow()
    {
      return _parent.insertRow();
    }
    
    public ExtendedRowBuilder_Jobs insertRow(JobsModel row)
    {
      return _parent.insertRow(row); 
    }

    public ExtendedRowBuilder_Jobs insertRowAt(int index)
    {
      return _parent.insertRowAt(index);
    }

    public ExtendedRowBuilder_Jobs insertRow(RowBuilder_Jobs theRow)
    {
      return _parent.insertRow(theRow);
    }
    
    public Object getValue(String column) throws RuntimeException {
      return _delegate.getValue(column);
    }

    
    @Override
    public ExtendedRowBuilder_Jobs clone() {
      RowBuilder_Jobs cloneDelegate = _delegate.clone();
      ExtendedRowBuilder_Jobs clone = new ExtendedRowBuilder_Jobs(_parent, cloneDelegate);
      return clone;
    }

  }

  /**
   * Delivers a collection of rows matching to a filter.
   * @param filter The used filter
   * @return The collection of rows, may return an empty list
   * @see #find(Closure)
   * @see #findWhere
   * @see #getWhere
   */
  public RowCollection_Jobs find(Filter<RowBuilder_Jobs> filter)
  {
    return _table.find(filter);
  }

  /**
   * Delivers a collection of rows matching to a filter.
   * @param filter A Groovy Closure with an argument of type RowBuilder_Jobs, returning a boolean
   * @return The collection of rows, may return an empty list
   * @see #find(Filter)
   * @see #findWhere
   * @see #getWhere
   */
  public RowCollection_Jobs find(Closure<Boolean> filter)
  {
    final Closure<Boolean> localFilter = filter;
    return _table.find(new Filter<RowBuilder_Jobs>()
      {
        @Override
        public boolean accept(RowBuilder_Jobs row)
        {
          return localFilter.call(row);
        }
      });
  }
  
  public void foreach(Action<RowBuilder_Jobs> action)
  {
    _table.foreach(action);
  }
  
  public void foreach(Closure<?> action)
  {
    final Closure<?> localAction = action;
    _table.foreach(new Action<RowBuilder_Jobs>()
      {
        @Override
        public void call(RowBuilder_Jobs row)
        {
          localAction.call(row);
        }
      });
  }
  
  
  /**
   * Allows searching for one or more rows specified by a column value.
   * findWhere assumes that it is used to search for existing rows. An
   * exception will be thrown if no matching row was found. Use getWhere
   * or find to avoid this behavior.
   * @see #getWhere
   * @see #find(Closure)
   * @see #find(Filter)
   */
  public final JobsTableFindWhere findWhere = new JobsTableFindWhere(this);

  public static class JobsTableFindWhere
  {
   
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
        throw new IllegalArgumentException("Reference is not defined in this scope");
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
        throw new IllegalArgumentException("Reference is not defined in this scope");
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
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.description(builder.getDescription());
    }
    
    public RowCollection_Jobs description(java.lang.String toSearch)
    {
      return table.getTableModel().findWhere.description(toSearch);
    }
  }
  
  /**
   * Allows searching for a row specified by a column value.
   * The first matching row is returned. Does not throw an
   * exception, if no element is found.
   * @see #findWhere
   * @see #find(Closure)
   * @see #find(Filter)
   */
  public final JobsTableGetWhere getWhere = new JobsTableGetWhere(this);

  public static class JobsTableGetWhere
  {

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
        throw new IllegalArgumentException("Reference is not defined in this scope");
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
        throw new IllegalArgumentException("Reference is not defined in this scope");
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
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.description(builder.getDescription());
    }

    public Optional<RowBuilder_Jobs> description(java.lang.String toSearch)
    {
      return table.getTableModel().getWhere.description(toSearch);
    }
  }
}

