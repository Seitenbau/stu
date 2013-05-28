package com.seitenbau.testing.dbunit.demomodel.dsl;

import com.google.common.base.Optional;

import groovy.lang.Closure;

import java.util.HashSet;
import java.util.Set;

import com.seitenbau.testing.dbunit.demomodel.TeamsTable.TeamsGetWhere;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowCollection_Teams;
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
   * Data Type: java.lang.Long
   */
  public final ColumnBinding<RowBuilder_Teams, TeamsGetWhere> id = createIdBinding();

  /**
   * Column Header for Teams table.
   * <p>
   * Data Type: java.lang.String
   */
  public final ColumnBinding<RowBuilder_Teams, TeamsGetWhere> title = createTitleBinding();

  /**
   * Column Header for Teams table.
   * <p>
   * Data Type: java.lang.String
   */
  public final ColumnBinding<RowBuilder_Teams, TeamsGetWhere> description = createDescriptionBinding();

  /**
   * Column Header for Teams table.
   * <p>
   * Data Type: java.lang.Long
   */
  public final ColumnBinding<RowBuilder_Teams, TeamsGetWhere> membersize = createMembersizeBinding();

  private final STUDSL _scope;

  private final com.seitenbau.testing.dbunit.demomodel.TeamsTable _table;

  private final Set<RowBuilder_Teams> _allRows;

  private final ITableAdapter<RowBuilder_Teams, TeamsGetWhere, TeamsRef> _adapter = new ITableAdapter<RowBuilder_Teams, TeamsGetWhere, TeamsRef>()
  {
    public RowBuilder_Teams insertRow() 
    {
      RowBuilder_Teams result = _table.insertRow();
      _allRows.add(result);
      return result;
    }
  
    public TeamsGetWhere getWhere()
    {
      return _table.getWhere;
    }
    
    public void bindToScope(TeamsRef reference, RowBuilder_Teams row)
    {
      if (row != null) {
        reference.setBuilder(_scope, row);
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
    _allRows = new HashSet<RowBuilder_Teams>();
  }
  
  Set<RowBuilder_Teams> getAllRowBuilders()
  {
    return _allRows;
  }
  
  com.seitenbau.testing.dbunit.demomodel.TeamsTable getTableModel()
  {
    return _table;
  }
  
  /**
   * Parses the rows of a Teams table. Supported columns are:
   * <ul>
   *   <li>REF</li>
   *   <li> id
   *   <li> title
   *   <li> description
   *   <li> membersize
   * </ul>
   * @param rows The table data
   */
  public void rows(Closure<?> rows) {
    GeneralTableRowCallback<RowBuilder_Teams, TeamsGetWhere, TeamsRef> callback = 
        new GeneralTableRowCallback<RowBuilder_Teams, TeamsGetWhere, TeamsRef>(_adapter);
    TableParser.parseTable(rows, this, callback);
  }

  private boolean valueMustBeSetRaw(Object value)
  {
    return (value instanceof DatabaseReference) || (value instanceof IDataSetModifier);
  }

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
      public boolean isRefColumn() {
        return true;
      }

      @Override
      public DataType getDataType() {
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
        if (valueMustBeSetRaw(value)) {
          row.setIdRaw(value);
        }
        else {
          row.setId((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
        }
      }

      @Override
      public Optional<RowBuilder_Teams> getWhere(TeamsGetWhere getWhere, Object value) {
        return getWhere.id((java.lang.Long)CastUtil.cast(value, DataType.BIGINT));
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
  private ColumnBinding<RowBuilder_Teams, TeamsGetWhere> createTitleBinding() 
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsGetWhere>()
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
  private ColumnBinding<RowBuilder_Teams, TeamsGetWhere> createDescriptionBinding() 
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsGetWhere>()
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
  private ColumnBinding<RowBuilder_Teams, TeamsGetWhere> createMembersizeBinding() 
  {
    return new ColumnBinding<RowBuilder_Teams, TeamsGetWhere>()
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
  
  public final TeamsTableFindWhere findWhere = new TeamsTableFindWhere(this);

  public static class TeamsTableFindWhere {
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
        return new RowCollection_Teams(table.getTableModel()); 
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
        return new RowCollection_Teams(table.getTableModel()); 
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
        return new RowCollection_Teams(table.getTableModel()); 
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
        return new RowCollection_Teams(table.getTableModel()); 
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
  
  public final TeamsTableGetWhere getWhere = new TeamsTableGetWhere(this);

  public static class TeamsTableGetWhere {
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
        return Optional.<RowBuilder_Teams> absent();
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
        return Optional.<RowBuilder_Teams> absent();
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
        return Optional.<RowBuilder_Teams> absent();
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
        return Optional.<RowBuilder_Teams> absent();
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

