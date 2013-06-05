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


import com.seitenbau.testing.dbunit.model.PersonsTable.PersonsGetWhere;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowCollection_Persons;
import com.seitenbau.testing.dbunit.model.PersonsModel;
import com.seitenbau.testing.dbunit.model.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.model.JobsTable.RowGetters_Jobs;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowGetters_Teams;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowGetters_Persons;

public class PersonsTable {

  /**
   * Do not set a value. (To remove a value use <i>null</i>)
   */
  public final NoValue _ = new NoValue();

  /**
   * Column Header for Persons table.
   * <p>
   * Data Type: PersonsRef
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> REF = createREFBinding();

  /**
   * Column Header for Persons table.
   * <p>
   * Data Type: {@code java.lang.Long}
   * <br>   
   * Database Type: DataType.BIGINT
   * 
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> id = createIdBinding();

  /**
   * Column Header for Persons table.
   * <p>
   * Data Type: {@code java.lang.String}
   * <br>   
   * Database Type: DataType.VARCHAR
   * 
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> first_name = createFirstNameBinding();

  /**
   * Column Header for Persons table.
   * <p>
   * Actually this column represents the last name of a person
   * <p>
   * Data Type: {@code java.lang.String}
   * <br>   
   * Database Type: DataType.VARCHAR
   * 
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> name = createNameBinding();

  /**
   * Column Header for Persons table.
   * <p>
   * Data Type: {@code java.lang.Long}
   * or {@code JobsRef}  
   * <br>   
   * Database Type: DataType.BIGINT
   * 
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> job_id = createJobIdBinding();

  /**
   * Column Header for Persons table.
   * <p>
   * Data Type: {@code java.lang.Long}
   * or {@code JobsRef}  
   * <br>   
   * Database Type: DataType.BIGINT
   * 
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> job = job_id;

  /**
   * Column Header for Persons table.
   * <p>
   * Data Type: {@code java.lang.Long}
   * or {@code TeamsRef}  
   * <br>   
   * Database Type: DataType.BIGINT
   * 
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> team_id = createTeamIdBinding();

  /**
   * Column Header for Persons table.
   * <p>
   * Data Type: {@code java.lang.Long}
   * or {@code TeamsRef}  
   * <br>   
   * Database Type: DataType.BIGINT
   * 
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> team = team_id;

  private final PersonDatabaseBuilder _scope;

  private final com.seitenbau.testing.dbunit.model.PersonsTable _table;

  private final ITableAdapter<RowBuilder_Persons, PersonsGetWhere, PersonsRef> _adapter = new TableAdapter();
    
  PersonsTable(PersonDatabaseBuilder scope, com.seitenbau.testing.dbunit.model.PersonsTable table)
  {
    _scope = scope;
    _table = table;
  }
  
  com.seitenbau.testing.dbunit.model.PersonsTable getTableModel()
  {
    return _table;
  }
  
  /**
   * Parses the rows of a Persons table. Supported columns are:
   * <ul>
   *   <li><strong>{@code REF}</strong>: {@code PersonsRef}</li>
   *   <li> <strong>{@code id}</strong>: {@code java.lang.Long}
   *   <li> <strong>{@code first_name}</strong>: {@code java.lang.String}
   *   <li> <strong>{@code name}</strong>: {@code java.lang.String}
   *   <li> <strong>{@code job_id}</strong> (alias: <strong>{@code job}</strong>): {@code java.lang.Long}
   *   <li> <strong>{@code team_id}</strong> (alias: <strong>{@code team}</strong>): {@code java.lang.Long}
   * </ul>
   * @param rows The table data
   */
  public void rows(Closure<?> rows)
  {
    TableParserCallback<RowBuilder_Persons, PersonsGetWhere, PersonsRef> callback = 
        new TableParserCallback<RowBuilder_Persons, PersonsGetWhere, PersonsRef>(_adapter);
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
  
  private class TableAdapter implements ITableAdapter<RowBuilder_Persons, PersonsGetWhere, PersonsRef>
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
      reference.setBuilder(_scope, row);
    }

    public void handleReferences(PersonsRef reference, RowBuilder_Persons row)
    {
      _scope.replacePersonsRefWithId(reference, row);
    }    

    public RowBuilder_Persons getRowByReference(PersonsRef reference)
    {
      return reference.getBuilder(_scope);
    }

    public String getTableName()
    {
      return "Persons";
    }
    
  };  

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
        if (valueMustBeSetRaw(value)) 
        {
          row.setIdRaw(value);
        } else {
          row.setId((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
        }
      }

      @Override
      public Optional<RowBuilder_Persons> getWhere(PersonsGetWhere getWhere, Object value)
      {
        return getWhere.id((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
      }

      @Override
      public boolean isIdentifierColumn()
      {
        return true;
      }
    
      private Set<RowBuilder_Persons> definedIdsSet = new HashSet<RowBuilder_Persons>();
      
      @Override
      public DataType getDataType()
      {
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
        if (valueMustBeSetRaw(value)) 
        {
          row.setFirstNameRaw(value);
        } else {
          row.setFirstName((java.lang.String)CastUtil.cast(value, DataType.VARCHAR));
        }
      }

      @Override
      public void setLazyValue(RowBuilder_Persons row, LazyValue lazyValue)
      {
        row.setFirstNameRaw(lazyValue);
      }
      
      @Override
      public DataType getDataType()
      {
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
        if (valueMustBeSetRaw(value)) 
        {
          row.setNameRaw(value);
        } else {
          row.setName((java.lang.String)CastUtil.cast(value, DataType.VARCHAR));
        }
      }

      @Override
      public void setLazyValue(RowBuilder_Persons row, LazyValue lazyValue)
      {
        row.setNameRaw(lazyValue);
      }
      
      @Override
      public DataType getDataType()
      {
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
        if (valueMustBeSetRaw(value)) 
        {
          row.setJobIdRaw(value);
        } else {
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
      public void setLazyValue(RowBuilder_Persons row, LazyValue lazyValue)
      {
        row.setJobIdRaw(lazyValue);
      }
      
      @Override
      public DataType getDataType()
      {
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
        if (valueMustBeSetRaw(value)) 
        {
          row.setTeamIdRaw(value);
        } else {
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
      public void setLazyValue(RowBuilder_Persons row, LazyValue lazyValue)
      {
        row.setTeamIdRaw(lazyValue);
      }
      
      @Override
      public DataType getDataType()
      {
        return DataType.BIGINT;
      }

    };
  }

  public ExtendedRowBuilder_Persons insertRow()
  {
    return new ExtendedRowBuilder_Persons(this, _table.insertRow());
  }

  public ExtendedRowBuilder_Persons insertRow(PersonsModel row)
  {
    return new ExtendedRowBuilder_Persons(this, _table.insertRow(row));
  }

  public ExtendedRowBuilder_Persons insertRowAt(int index)
  {
    return new ExtendedRowBuilder_Persons(this, _table.insertRowAt(index));
  }

  public ExtendedRowBuilder_Persons insertRow(RowBuilder_Persons theRow)
  {
    return new ExtendedRowBuilder_Persons(this, _table.insertRow(theRow));
  }
  
  public static class ExtendedRowBuilder_Persons implements RowBuilder_Persons
  {
  
    private final PersonsTable _parent;
    
    private final RowBuilder_Persons _delegate;
  
    ExtendedRowBuilder_Persons(PersonsTable parent, RowBuilder_Persons delegate)
    {
      _parent = parent;
      _delegate = delegate;
    }


    public ExtendedRowBuilder_Persons setId(Integer intValue)
    {
      _delegate.setId(intValue);
      return this;
    }

    public ExtendedRowBuilder_Persons setId(java.lang.Long value)
    {
      _delegate.setId(value);
      return this;
    }

    public ExtendedRowBuilder_Persons setIdRaw(Object value)
    {
      _delegate.setIdRaw(value);
      return this;
    }
    
    public ExtendedRowBuilder_Persons nextId()
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

    public ExtendedRowBuilder_Persons setFirstName(java.lang.String value)
    {
      _delegate.setFirstName(value);
      return this;
    }

    public ExtendedRowBuilder_Persons setFirstNameRaw(Object value)
    {
      _delegate.setFirstNameRaw(value);
      return this;
    }
    
    public java.lang.String getFirstName()
    {
      return _delegate.getFirstName();
    }

    public Object getFirstNameRaw()
    {
      return _delegate.getFirstNameRaw();
    }

    public ExtendedRowBuilder_Persons setName(java.lang.String value)
    {
      _delegate.setName(value);
      return this;
    }

    public ExtendedRowBuilder_Persons setNameRaw(Object value)
    {
      _delegate.setNameRaw(value);
      return this;
    }
    
    /**
     * Actually this column represents the last name of a person
     * @return The value
     */
    public java.lang.String getName()
    {
      return _delegate.getName();
    }

    public Object getNameRaw()
    {
      return _delegate.getNameRaw();
    }

    public ExtendedRowBuilder_Persons setJobId(Integer intValue)
    {
      _delegate.setJobId(intValue);
      return this;
    }

    public ExtendedRowBuilder_Persons setJobId(java.lang.Long value)
    {
      _delegate.setJobId(value);
      return this;
    }

    public ExtendedRowBuilder_Persons setJobIdRaw(Object value)
    {
      _delegate.setJobIdRaw(value);
      return this;
    }
    
    public java.lang.Long getJobId()
    {
      return _delegate.getJobId();
    }

    public Object getJobIdRaw()
    {
      return _delegate.getJobIdRaw();
    }

    public ExtendedRowBuilder_Persons setTeamId(Integer intValue)
    {
      _delegate.setTeamId(intValue);
      return this;
    }

    public ExtendedRowBuilder_Persons setTeamId(java.lang.Long value)
    {
      _delegate.setTeamId(value);
      return this;
    }

    public ExtendedRowBuilder_Persons setTeamIdRaw(Object value)
    {
      _delegate.setTeamIdRaw(value);
      return this;
    }
    
    public java.lang.Long getTeamId()
    {
      return _delegate.getTeamId();
    }

    public Object getTeamIdRaw()
    {
      return _delegate.getTeamIdRaw();
    }
  

    public ExtendedRowBuilder_Persons setJobId(JobsRef reference)
    {
      // TODO _delegate.setJobId( ... ) 
      RowBuilder_Jobs builder = reference.getBuilder(_parent._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      _delegate.refJobId(builder);
      return this;
    }

    public ExtendedRowBuilder_Persons setTeamId(TeamsRef reference)
    {
      // TODO _delegate.setTeamId( ... ) 
      RowBuilder_Teams builder = reference.getBuilder(_parent._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      _delegate.refTeamId(builder);
      return this;
    }

    public ExtendedRowBuilder_Persons insertRow()
    {
      return _parent.insertRow();
    }
    
    public ExtendedRowBuilder_Persons insertRow(PersonsModel row)
    {
      return _parent.insertRow(row); 
    }

    public ExtendedRowBuilder_Persons insertRowAt(int index)
    {
      return _parent.insertRowAt(index);
    }

    public ExtendedRowBuilder_Persons insertRow(RowBuilder_Persons theRow)
    {
      return _parent.insertRow(theRow);
    }
    
    public Object getValue(String column) throws RuntimeException {
      return _delegate.getValue(column);
    }


    public ExtendedRowBuilder_Persons refJobId(RowGetters_Jobs reference)
    {
      _delegate.refJobId(reference);
      return this;
    }

    public ExtendedRowBuilder_Persons refTeamId(RowGetters_Teams reference)
    {
      _delegate.refTeamId(reference);
      return this;
    }
    
    @Override
    public ExtendedRowBuilder_Persons clone() {
      RowBuilder_Persons cloneDelegate = _delegate.clone();
      ExtendedRowBuilder_Persons clone = new ExtendedRowBuilder_Persons(_parent, cloneDelegate);
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
  public RowCollection_Persons find(Filter<RowBuilder_Persons> filter)
  {
    return _table.find(filter);
  }

  /**
   * Delivers a collection of rows matching to a filter.
   * @param filter A Groovy Closure with an argument of type RowBuilder_Persons, returning a boolean
   * @return The collection of rows, may return an empty list
   * @see #find(Filter)
   * @see #findWhere
   * @see #getWhere
   */
  public RowCollection_Persons find(Closure<Boolean> filter)
  {
    final Closure<Boolean> localFilter = filter;
    return _table.find(new Filter<RowBuilder_Persons>()
      {
        @Override
        public boolean accept(RowBuilder_Persons row)
        {
          return localFilter.call(row);
        }
      });
  }
  
  public void foreach(Action<RowBuilder_Persons> action)
  {
    _table.foreach(action);
  }
  
  public void foreach(Closure<?> action)
  {
    final Closure<?> localAction = action;
    _table.foreach(new Action<RowBuilder_Persons>()
      {
        @Override
        public void call(RowBuilder_Persons row)
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
  public final PersonsTableFindWhere findWhere = new PersonsTableFindWhere(this);

  public static class PersonsTableFindWhere
  {
   
    private final PersonsTable table;
    
    PersonsTableFindWhere(PersonsTable table) 
    {
      this.table = table;
    }

    public RowCollection_Persons id(PersonsRef ref)
    {
      RowBuilder_Persons builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.id(builder.getId());
    }
    
    public RowCollection_Persons id(java.lang.Long toSearch)
    {
      return table.getTableModel().findWhere.id(toSearch);
    }

    public RowCollection_Persons id(Integer toSearch) 
    {
      return id(Long.valueOf(toSearch));
    }

    public RowCollection_Persons firstName(PersonsRef ref)
    {
      RowBuilder_Persons builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.firstName(builder.getFirstName());
    }
    
    public RowCollection_Persons firstName(java.lang.String toSearch)
    {
      return table.getTableModel().findWhere.firstName(toSearch);
    }

    public RowCollection_Persons name(PersonsRef ref)
    {
      RowBuilder_Persons builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.name(builder.getName());
    }
    
    public RowCollection_Persons name(java.lang.String toSearch)
    {
      return table.getTableModel().findWhere.name(toSearch);
    }

    public RowCollection_Persons jobId(PersonsRef ref)
    {
      RowBuilder_Persons builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.jobId(builder.getJobId());
    }

    public RowCollection_Persons jobId(JobsRef ref)
    {
      RowBuilder_Jobs builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.jobId(builder.getId());
    }
    
    public RowCollection_Persons jobId(java.lang.Long toSearch)
    {
      return table.getTableModel().findWhere.jobId(toSearch);
    }

    public RowCollection_Persons jobId(Integer toSearch) 
    {
      return jobId(Long.valueOf(toSearch));
    }

    public RowCollection_Persons teamId(PersonsRef ref)
    {
      RowBuilder_Persons builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.teamId(builder.getTeamId());
    }

    public RowCollection_Persons teamId(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.teamId(builder.getId());
    }
    
    public RowCollection_Persons teamId(java.lang.Long toSearch)
    {
      return table.getTableModel().findWhere.teamId(toSearch);
    }

    public RowCollection_Persons teamId(Integer toSearch) 
    {
      return teamId(Long.valueOf(toSearch));
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
  public final PersonsTableGetWhere getWhere = new PersonsTableGetWhere(this);

  public static class PersonsTableGetWhere
  {

    private final PersonsTable table;
    
    PersonsTableGetWhere(PersonsTable table) 
    {
      this.table = table;
    }

    public Optional<RowBuilder_Persons> id(PersonsRef ref)
    {
      RowBuilder_Persons builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.id(builder.getId());
    }

    public Optional<RowBuilder_Persons> id(java.lang.Long toSearch)
    {
      return table.getTableModel().getWhere.id(toSearch);
    }

    public Optional<RowBuilder_Persons> id(Integer toSearch) 
    {
      return id(Long.valueOf(toSearch));
    }

    public Optional<RowBuilder_Persons> firstName(PersonsRef ref)
    {
      RowBuilder_Persons builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.firstName(builder.getFirstName());
    }

    public Optional<RowBuilder_Persons> firstName(java.lang.String toSearch)
    {
      return table.getTableModel().getWhere.firstName(toSearch);
    }

    public Optional<RowBuilder_Persons> name(PersonsRef ref)
    {
      RowBuilder_Persons builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.name(builder.getName());
    }

    public Optional<RowBuilder_Persons> name(java.lang.String toSearch)
    {
      return table.getTableModel().getWhere.name(toSearch);
    }

    public Optional<RowBuilder_Persons> jobId(PersonsRef ref)
    {
      RowBuilder_Persons builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.jobId(builder.getJobId());
    }

    public Optional<RowBuilder_Persons> jobId(JobsRef ref)
    {
      RowBuilder_Jobs builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.jobId(builder.getId());
    }

    public Optional<RowBuilder_Persons> jobId(java.lang.Long toSearch)
    {
      return table.getTableModel().getWhere.jobId(toSearch);
    }

    public Optional<RowBuilder_Persons> jobId(Integer toSearch) 
    {
      return jobId(Long.valueOf(toSearch));
    }

    public Optional<RowBuilder_Persons> teamId(PersonsRef ref)
    {
      RowBuilder_Persons builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.teamId(builder.getTeamId());
    }

    public Optional<RowBuilder_Persons> teamId(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.teamId(builder.getId());
    }

    public Optional<RowBuilder_Persons> teamId(java.lang.Long toSearch)
    {
      return table.getTableModel().getWhere.teamId(toSearch);
    }

    public Optional<RowBuilder_Persons> teamId(Integer toSearch) 
    {
      return teamId(Long.valueOf(toSearch));
    }
  }
}

