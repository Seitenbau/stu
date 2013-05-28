package com.seitenbau.testing.dbunit.demomodel.dsl;

import com.google.common.base.Optional;

import groovy.lang.Closure;

import java.util.HashSet;
import java.util.Set;

import com.seitenbau.testing.dbunit.demomodel.PersonsTable.PersonsGetWhere;
import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowCollection_Persons;
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


public class PersonsTable {

  /**
   * Do not set a value. (To remove a value use <i>null</i>)
   */
  public final NoValue _ = new NoValue();

  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> REF = createREFBinding();

  /**
   * Column Header for Persons table
   * 
   * Data Type: java.lang.Long
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> id = createIdBinding();

  /**
   * Column Header for Persons table
   * 
   * Data Type: java.lang.String
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> first_name = createFirstNameBinding();

  /**
   * Column Header for Persons table
   * 
   * Data Type: java.lang.String
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> name = createNameBinding();

  /**
   * Column Header for Persons table
   * 
   * Data Type: java.lang.Long
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> job_id = createJobIdBinding();

  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> job = job_id;

  /**
   * Column Header for Persons table
   * 
   * Data Type: java.lang.Long
   */
  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> team_id = createTeamIdBinding();

  public final ColumnBinding<RowBuilder_Persons, PersonsGetWhere> team = team_id;

  private final STUDSL _scope;

  private final com.seitenbau.testing.dbunit.demomodel.PersonsTable _table;

  private final Set<RowBuilder_Persons> _allRows;

  private final ITableAdapter<RowBuilder_Persons, PersonsGetWhere, PersonsRef> _adapter = new ITableAdapter<RowBuilder_Persons, PersonsGetWhere, PersonsRef>()
  {
    public RowBuilder_Persons insertRow() 
    {
      RowBuilder_Persons result = _table.insertRow();
      _allRows.add(result);
      return result;
    }
  
    public PersonsGetWhere getWhere()
    {
      return _table.getWhere;
    }
    
    public void bindToScope(PersonsRef reference, RowBuilder_Persons row)
    {
      if (row != null) {
        reference.setBuilder(_scope, row);
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
    _allRows = new HashSet<RowBuilder_Persons>();
  }
  
  Set<RowBuilder_Persons> getAllRowBuilders()
  {
    return _allRows;
  }
  
  com.seitenbau.testing.dbunit.demomodel.PersonsTable getTableModel()
  {
    return _table;
  }
  
  /**
   * Parses the rows of a Persons table. Supported columns are:
   * <ul>
   *   <li>REF</li>
   *   <li> id
   *   <li> first_name
   *   <li> name
   *   <li> job_id (alias: job)
   *   <li> team_id (alias: team)
   * </ul>
   * @param rows The table data
   */
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
  
  public final PersonsTableFindWhere findWhere = new PersonsTableFindWhere(this);

  public static class PersonsTableFindWhere {
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
        return new RowCollection_Persons(table.getTableModel()); 
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
        return new RowCollection_Persons(table.getTableModel()); 
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
        return new RowCollection_Persons(table.getTableModel()); 
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
        return new RowCollection_Persons(table.getTableModel()); 
      }
      return table.getTableModel().findWhere.jobId(builder.getJobId());
    }

    public RowCollection_Persons jobId(JobsRef ref)
    {
      RowBuilder_Jobs builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        return new RowCollection_Persons(table.getTableModel()); 
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
        return new RowCollection_Persons(table.getTableModel()); 
      }
      return table.getTableModel().findWhere.teamId(builder.getTeamId());
    }

    public RowCollection_Persons teamId(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        return new RowCollection_Persons(table.getTableModel()); 
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
  
  public final PersonsTableGetWhere getWhere = new PersonsTableGetWhere(this);

  public static class PersonsTableGetWhere {
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
        return Optional.<RowBuilder_Persons> absent();
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
        return Optional.<RowBuilder_Persons> absent();
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
        return Optional.<RowBuilder_Persons> absent();
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
        return Optional.<RowBuilder_Persons> absent();
      }
      return table.getTableModel().getWhere.jobId(builder.getJobId());
    }

    public Optional<RowBuilder_Persons> jobId(JobsRef ref)
    {
      RowBuilder_Jobs builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        return Optional.<RowBuilder_Persons> absent();
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
        return Optional.<RowBuilder_Persons> absent();
      }
      return table.getTableModel().getWhere.teamId(builder.getTeamId());
    }

    public Optional<RowBuilder_Persons> teamId(TeamsRef ref)
    {
      RowBuilder_Teams builder = ref.getBuilder(table._scope);
      if (builder == null) 
      {
        return Optional.<RowBuilder_Persons> absent();
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

