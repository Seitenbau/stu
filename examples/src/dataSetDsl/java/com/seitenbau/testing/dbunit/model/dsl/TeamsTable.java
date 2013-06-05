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


import com.seitenbau.testing.dbunit.model.TeamsTable.TeamsGetWhere;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowCollection_Teams;
import com.seitenbau.testing.dbunit.model.TeamsModel;
import com.seitenbau.testing.dbunit.model.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.model.JobsTable.RowGetters_Jobs;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowGetters_Teams;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowGetters_Persons;

public class TeamsTable {

  /**
   * Do not set a value. (To remove a value use <i>null</i>)
   */
  public final NoValue _ = new NoValue();

  /**
   * Column Header for Teams table.
   * <p>
   * Data Type: TeamsRef
   */
  public final ColumnBinding<RowBuilder_Teams, TeamsGetWhere> REF = createREFBinding();

  /**
   * Column Header for Teams table.
   * <p>
   * Data Type: {@code java.lang.Long}
   * <br>   
   * Database Type: DataType.BIGINT
   * 
   */
  public final ColumnBinding<RowBuilder_Teams, TeamsGetWhere> id = createIdBinding();

  /**
   * Column Header for Teams table.
   * <p>
   * Data Type: {@code java.lang.String}
   * <br>   
   * Database Type: DataType.VARCHAR
   * 
   */
  public final ColumnBinding<RowBuilder_Teams, TeamsGetWhere> title = createTitleBinding();

  /**
   * Column Header for Teams table.
   * <p>
   * Data Type: {@code java.lang.String}
   * <br>   
   * Database Type: DataType.VARCHAR
   * 
   */
  public final ColumnBinding<RowBuilder_Teams, TeamsGetWhere> description = createDescriptionBinding();

  /**
   * Column Header for Teams table.
   * <p>
   * Data Type: {@code java.lang.Long}
   * <br>   
   * Database Type: DataType.BIGINT
   * 
   */
  public final ColumnBinding<RowBuilder_Teams, TeamsGetWhere> membersize = createMembersizeBinding();

  private final PersonDatabaseBuilder _scope;

  private final com.seitenbau.testing.dbunit.model.TeamsTable _table;

  private final ITableAdapter<RowBuilder_Teams, TeamsGetWhere, TeamsRef> _adapter = new TableAdapter();
    
  TeamsTable(PersonDatabaseBuilder scope, com.seitenbau.testing.dbunit.model.TeamsTable table)
  {
    _scope = scope;
    _table = table;
  }
  
  com.seitenbau.testing.dbunit.model.TeamsTable getTableModel()
  {
    return _table;
  }
  
  /**
   * Parses the rows of a Teams table. Supported columns are:
   * <ul>
   *   <li><strong>{@code REF}</strong>: {@code TeamsRef}</li>
   *   <li> <strong>{@code id}</strong>: {@code java.lang.Long}
   *   <li> <strong>{@code title}</strong>: {@code java.lang.String}
   *   <li> <strong>{@code description}</strong>: {@code java.lang.String}
   *   <li> <strong>{@code membersize}</strong>: {@code java.lang.Long}
   * </ul>
   * @param rows The table data
   */
  public void rows(Closure<?> rows)
  {
    TableParserCallback<RowBuilder_Teams, TeamsGetWhere, TeamsRef> callback = 
        new TableParserCallback<RowBuilder_Teams, TeamsGetWhere, TeamsRef>(_adapter);
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
  
  private class TableAdapter implements ITableAdapter<RowBuilder_Teams, TeamsGetWhere, TeamsRef>
  {
    public RowBuilder_Teams insertRow() 
    {
      return _table.insertRow();
    }
  
    public TeamsGetWhere getWhere()
    {
      return _table.getWhere;
    }
    
    public void bindToScope(TeamsRef reference, RowBuilder_Teams row)
    {
      reference.setBuilder(_scope, row);
    }

    public void handleReferences(TeamsRef reference, RowBuilder_Teams row)
    {
      _scope.replaceTeamsRefWithId(reference, row);
    }    

    public RowBuilder_Teams getRowByReference(TeamsRef reference)
    {
      return reference.getBuilder(_scope);
    }

    public String getTableName()
    {
      return "Teams";
    }
    
  };  

  private ColumnBinding<RowBuilder_Teams, TeamsGetWhere> createREFBinding()
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsGetWhere>()
    { 
      @Override
      public void set(RowBuilder_Teams row, Object value)
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

  private ColumnBinding<RowBuilder_Teams, TeamsGetWhere> createIdBinding() 
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Teams row, Object value)
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
      public Optional<RowBuilder_Teams> getWhere(TeamsGetWhere getWhere, Object value)
      {
        return getWhere.id((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
      }

      @Override
      public boolean isIdentifierColumn()
      {
        return true;
      }
    
      private Set<RowBuilder_Teams> definedIdsSet = new HashSet<RowBuilder_Teams>();
      
      @Override
      public DataType getDataType()
      {
        return DataType.BIGINT;
      }

    };
  }

  private ColumnBinding<RowBuilder_Teams, TeamsGetWhere> createTitleBinding() 
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Teams row, Object value)
      {
        if (valueMustBeSetRaw(value)) 
        {
          row.setTitleRaw(value);
        } else {
          row.setTitle((java.lang.String)CastUtil.cast(value, DataType.VARCHAR));
        }
      }

      @Override
      public void setLazyValue(RowBuilder_Teams row, LazyValue lazyValue)
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

  private ColumnBinding<RowBuilder_Teams, TeamsGetWhere> createDescriptionBinding() 
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Teams row, Object value)
      {
        if (valueMustBeSetRaw(value)) 
        {
          row.setDescriptionRaw(value);
        } else {
          row.setDescription((java.lang.String)CastUtil.cast(value, DataType.VARCHAR));
        }
      }

      @Override
      public void setLazyValue(RowBuilder_Teams row, LazyValue lazyValue)
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

  private ColumnBinding<RowBuilder_Teams, TeamsGetWhere> createMembersizeBinding() 
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsGetWhere>()
    {
      @Override
      public void set(RowBuilder_Teams row, Object value)
      {
        if (valueMustBeSetRaw(value)) 
        {
          row.setMembersizeRaw(value);
        } else {
          row.setMembersize((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
        }
      }

      @Override
      public void setLazyValue(RowBuilder_Teams row, LazyValue lazyValue)
      {
        row.setMembersizeRaw(lazyValue);
      }
      
      @Override
      public DataType getDataType()
      {
        return DataType.BIGINT;
      }

    };
  }

  public ExtendedRowBuilder_Teams insertRow()
  {
    return new ExtendedRowBuilder_Teams(this, _table.insertRow());
  }

  public ExtendedRowBuilder_Teams insertRow(TeamsModel row)
  {
    return new ExtendedRowBuilder_Teams(this, _table.insertRow(row));
  }

  public ExtendedRowBuilder_Teams insertRowAt(int index)
  {
    return new ExtendedRowBuilder_Teams(this, _table.insertRowAt(index));
  }

  public ExtendedRowBuilder_Teams insertRow(RowBuilder_Teams theRow)
  {
    return new ExtendedRowBuilder_Teams(this, _table.insertRow(theRow));
  }
  
  public static class ExtendedRowBuilder_Teams implements RowBuilder_Teams
  {
  
    private final TeamsTable _parent;
    
    private final RowBuilder_Teams _delegate;
  
    ExtendedRowBuilder_Teams(TeamsTable parent, RowBuilder_Teams delegate)
    {
      _parent = parent;
      _delegate = delegate;
    }


    public ExtendedRowBuilder_Teams setId(Integer intValue)
    {
      _delegate.setId(intValue);
      return this;
    }

    public ExtendedRowBuilder_Teams setId(java.lang.Long value)
    {
      _delegate.setId(value);
      return this;
    }

    public ExtendedRowBuilder_Teams setIdRaw(Object value)
    {
      _delegate.setIdRaw(value);
      return this;
    }
    
    public ExtendedRowBuilder_Teams nextId()
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

    public ExtendedRowBuilder_Teams setTitle(java.lang.String value)
    {
      _delegate.setTitle(value);
      return this;
    }

    public ExtendedRowBuilder_Teams setTitleRaw(Object value)
    {
      _delegate.setTitleRaw(value);
      return this;
    }
    
    public java.lang.String getTitle()
    {
      return _delegate.getTitle();
    }

    public Object getTitleRaw()
    {
      return _delegate.getTitleRaw();
    }

    public ExtendedRowBuilder_Teams setDescription(java.lang.String value)
    {
      _delegate.setDescription(value);
      return this;
    }

    public ExtendedRowBuilder_Teams setDescriptionRaw(Object value)
    {
      _delegate.setDescriptionRaw(value);
      return this;
    }
    
    public java.lang.String getDescription()
    {
      return _delegate.getDescription();
    }

    public Object getDescriptionRaw()
    {
      return _delegate.getDescriptionRaw();
    }

    public ExtendedRowBuilder_Teams setMembersize(Integer intValue)
    {
      _delegate.setMembersize(intValue);
      return this;
    }

    public ExtendedRowBuilder_Teams setMembersize(java.lang.Long value)
    {
      _delegate.setMembersize(value);
      return this;
    }

    public ExtendedRowBuilder_Teams setMembersizeRaw(Object value)
    {
      _delegate.setMembersizeRaw(value);
      return this;
    }
    
    public java.lang.Long getMembersize()
    {
      return _delegate.getMembersize();
    }

    public Object getMembersizeRaw()
    {
      return _delegate.getMembersizeRaw();
    }
  

    public ExtendedRowBuilder_Teams insertRow()
    {
      return _parent.insertRow();
    }
    
    public ExtendedRowBuilder_Teams insertRow(TeamsModel row)
    {
      return _parent.insertRow(row); 
    }

    public ExtendedRowBuilder_Teams insertRowAt(int index)
    {
      return _parent.insertRowAt(index);
    }

    public ExtendedRowBuilder_Teams insertRow(RowBuilder_Teams theRow)
    {
      return _parent.insertRow(theRow);
    }
    
    public Object getValue(String column) throws RuntimeException {
      return _delegate.getValue(column);
    }

    
    @Override
    public ExtendedRowBuilder_Teams clone() {
      RowBuilder_Teams cloneDelegate = _delegate.clone();
      ExtendedRowBuilder_Teams clone = new ExtendedRowBuilder_Teams(_parent, cloneDelegate);
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
  public RowCollection_Teams find(Filter<RowBuilder_Teams> filter)
  {
    return _table.find(filter);
  }

  /**
   * Delivers a collection of rows matching to a filter.
   * @param filter A Groovy Closure with an argument of type RowBuilder_Teams, returning a boolean
   * @return The collection of rows, may return an empty list
   * @see #find(Filter)
   * @see #findWhere
   * @see #getWhere
   */
  public RowCollection_Teams find(Closure<Boolean> filter)
  {
    final Closure<Boolean> localFilter = filter;
    return _table.find(new Filter<RowBuilder_Teams>()
      {
        @Override
        public boolean accept(RowBuilder_Teams row)
        {
          return localFilter.call(row);
        }
      });
  }
  
  public void foreach(Action<RowBuilder_Teams> action)
  {
    _table.foreach(action);
  }
  
  public void foreach(Closure<?> action)
  {
    final Closure<?> localAction = action;
    _table.foreach(new Action<RowBuilder_Teams>()
      {
        @Override
        public void call(RowBuilder_Teams row)
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
  public final TeamsTableFindWhere findWhere = new TeamsTableFindWhere(this);

  public static class TeamsTableFindWhere
  {
   
    private final TeamsTable table;
    
    TeamsTableFindWhere(TeamsTable table) 
    {
      this.table = table;
    }

    public RowCollection_Teams id(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.id(builder.getId());
    }
    
    public RowCollection_Teams id(java.lang.Long toSearch)
    {
      return table.getTableModel().findWhere.id(toSearch);
    }

    public RowCollection_Teams id(Integer toSearch) 
    {
      return id(Long.valueOf(toSearch));
    }

    public RowCollection_Teams title(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.title(builder.getTitle());
    }
    
    public RowCollection_Teams title(java.lang.String toSearch)
    {
      return table.getTableModel().findWhere.title(toSearch);
    }

    public RowCollection_Teams description(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.description(builder.getDescription());
    }
    
    public RowCollection_Teams description(java.lang.String toSearch)
    {
      return table.getTableModel().findWhere.description(toSearch);
    }

    public RowCollection_Teams membersize(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().findWhere.membersize(builder.getMembersize());
    }
    
    public RowCollection_Teams membersize(java.lang.Long toSearch)
    {
      return table.getTableModel().findWhere.membersize(toSearch);
    }

    public RowCollection_Teams membersize(Integer toSearch) 
    {
      return membersize(Long.valueOf(toSearch));
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
  public final TeamsTableGetWhere getWhere = new TeamsTableGetWhere(this);

  public static class TeamsTableGetWhere
  {

    private final TeamsTable table;
    
    TeamsTableGetWhere(TeamsTable table) 
    {
      this.table = table;
    }

    public Optional<RowBuilder_Teams> id(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.id(builder.getId());
    }

    public Optional<RowBuilder_Teams> id(java.lang.Long toSearch)
    {
      return table.getTableModel().getWhere.id(toSearch);
    }

    public Optional<RowBuilder_Teams> id(Integer toSearch) 
    {
      return id(Long.valueOf(toSearch));
    }

    public Optional<RowBuilder_Teams> title(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.title(builder.getTitle());
    }

    public Optional<RowBuilder_Teams> title(java.lang.String toSearch)
    {
      return table.getTableModel().getWhere.title(toSearch);
    }

    public Optional<RowBuilder_Teams> description(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.description(builder.getDescription());
    }

    public Optional<RowBuilder_Teams> description(java.lang.String toSearch)
    {
      return table.getTableModel().getWhere.description(toSearch);
    }

    public Optional<RowBuilder_Teams> membersize(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        throw new IllegalArgumentException("Reference is not defined in this scope");
      }
      return table.getTableModel().getWhere.membersize(builder.getMembersize());
    }

    public Optional<RowBuilder_Teams> membersize(java.lang.Long toSearch)
    {
      return table.getTableModel().getWhere.membersize(toSearch);
    }

    public Optional<RowBuilder_Teams> membersize(Integer toSearch) 
    {
      return membersize(Long.valueOf(toSearch));
    }
  }
}

