package com.seitenbau.testing.dbunit.model;

import com.google.common.base.Optional;

import com.seitenbau.testing.dbunit.dsl.LazyValue;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;
import com.seitenbau.testing.dbunit.dsl.CastUtil;
import com.seitenbau.testing.util.Action;
import com.seitenbau.testing.util.Filter;
import com.seitenbau.testing.util.date.DateBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.Map;
import java.util.HashMap;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.RowOutOfBoundsException;
import org.dbunit.dataset.NoSuchColumnException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.datatype.DataType;


import com.seitenbau.testing.dbunit.model.JobsTable.RowGetters_Jobs;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowGetters_Teams;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowGetters_Persons;

import static com.seitenbau.testing.util.DateUtil.*;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class JobsTable implements ITable
{
  public final static String NAME = "jobs";

  public static class Columns
  {
    public static final String Id = "id";
    public static final String Title = "title";
    public static final String Description = "description";
  }

  // @formatter:off
  public final static Column[] COLUMNS = new Column[] {
    // idx = 0
    new Column(Columns.Id, DataType.BIGINT),
    // idx = 1
    new Column(Columns.Title, DataType.VARCHAR),
    // idx = 2
    new Column(Columns.Description, DataType.VARCHAR)
  };

  // @formatter:on

  ITableMetaData _metaData;
  
  PersonDatabaseDataSet _dataSet;
  
  Iterator<RowBuilder_Jobs> _iterator;
  
  public JobsTable()
  {
    _metaData = new DefaultTableMetaData(NAME, COLUMNS);
  }

  public void setDataset(PersonDatabaseDataSet dataSet)
  {
    _dataSet = dataSet;
  }
  
  public PersonDatabaseDataSet getDataset()
  {
    return _dataSet;
  }

  public List<RowBuilder_Jobs> rows = new ArrayList<RowBuilder_Jobs>();
  
  public interface RowSetters_Jobs<T extends RowSetters_Jobs>
  {
    T setId(Integer intValue);
    T setId(java.lang.Long value);
    T setIdRaw(Object value);
    T nextId();
    T setTitle(java.lang.String value);
    T setTitleRaw(Object value);
    T setDescription(java.lang.String value);
    T setDescriptionRaw(Object value);
  }
  
  public interface RowGetters_Jobs<T extends RowGetters_Jobs>
  {
    java.lang.Long getId();

    /**
     * The job title
     * @return The value
     */
    java.lang.String getTitle();

    /**
     * The description of the job
     * @return The value
     */
    java.lang.String getDescription();

  }

  public interface RowBuilder_Jobs extends RowSetters_Jobs<RowBuilder_Jobs>, RowGetters_Jobs<RowBuilder_Jobs>
  {

    /**
     * Gets the value of a specified column.
     * <p>
     * Throws a RuntimeException, if row count is not equal to 1.
     * @param column The column name (not case sensitive)
     * @return The value
     */
    Object getValue(String column) throws RuntimeException;

    RowBuilder_Jobs clone();

    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Jobs.insertRow()
    *   .setId( null )
    *   .setTitle( null )
    *   .setDescription( null )
    *   ;
    * </pre></code>
    */
    RowBuilder_Jobs insertRow();

    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Jobs.insertRow()
    *   .setId( null )
    *   .setTitle( null )
    *   .setDescription( null )
    *   ;
    * </pre></code>
    */
    RowBuilder_Jobs insertRow(JobsModel row);
    
    /**
    * Insert a new Row at the given position
    * <code><pre>
    * ds.table_Jobs.this.insertRowAt(2)
    *   .setId( null )
    *   .setTitle( null )
    *   .setDescription( null )
    *   ;
    * </pre></code>
    */
    RowBuilder_Jobs insertRowAt(int index);
    
    /**
    * Insert a row at the end of the table
    * <code><pre>
    * ds.table_Jobs.insertRow(someRow);
    * </pre></code>
    */
    RowBuilder_Jobs insertRow(RowBuilder_Jobs theRow);


    Object getIdRaw();

    /**
     * The job title
     * @return The value as raw object
     */
    Object getTitleRaw();

    /**
     * The description of the job
     * @return The value as raw object
     */
    Object getDescriptionRaw();
  }

  public static class RowBuilder_JobsImpl implements RowBuilder_Jobs
  {

    Object[] data;
    
    JobsTable table;
    
    RowBuilder_JobsImpl(JobsTable tableDelegate) 
    {
      data = new Object[COLUMNS.length];
      table = tableDelegate;
    }
    

    public RowBuilder_Jobs setId(Integer intValue)
    {
      data[0] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Jobs setId(java.lang.Long value)
    {
      data[0] = value;
      return this;
    }
    public RowBuilder_Jobs setIdRaw(Object value)
    {
      data[0] = value;
      return this;
    }
    public RowBuilder_Jobs nextId()
    {
      DatasetIdGenerator generator = table.getDataset().getIdGenerator();
      Long nextId = generator.nextId(JobsTable.NAME,"id");
      setId(nextId);
      return this;
    }

    public java.lang.Long getId()
    {
      Object value = data[0];
      if (value instanceof LazyValue) {
        LazyValue lazyValue = (LazyValue)value;
        return (java.lang.Long)CastUtil.cast(lazyValue.getValue(), 
          com.seitenbau.testing.dbunit.generator.DataType.BIGINT);
      } 
      return (java.lang.Long)value;
    }

    public Object getIdRaw()
    {
      return data[0];
    }

    public RowBuilder_Jobs setTitle(java.lang.String value)
    {
      data[1] = value;
      return this;
    }
    public RowBuilder_Jobs setTitleRaw(Object value)
    {
      data[1] = value;
      return this;
    }

    /**
     * The job title
     * @return The value
     */
    public java.lang.String getTitle()
    {
      Object value = data[1];
      if (value instanceof LazyValue) {
        LazyValue lazyValue = (LazyValue)value;
        return (java.lang.String)CastUtil.cast(lazyValue.getValue(), 
          com.seitenbau.testing.dbunit.generator.DataType.VARCHAR);
      } 
      return (java.lang.String)value;
    }

    /**
     * The job title
     * @return The value as raw object
     */
    public Object getTitleRaw()
    {
      return data[1];
    }

    public RowBuilder_Jobs setDescription(java.lang.String value)
    {
      data[2] = value;
      return this;
    }
    public RowBuilder_Jobs setDescriptionRaw(Object value)
    {
      data[2] = value;
      return this;
    }

    /**
     * The description of the job
     * @return The value
     */
    public java.lang.String getDescription()
    {
      Object value = data[2];
      if (value instanceof LazyValue) {
        LazyValue lazyValue = (LazyValue)value;
        return (java.lang.String)CastUtil.cast(lazyValue.getValue(), 
          com.seitenbau.testing.dbunit.generator.DataType.VARCHAR);
      } 
      return (java.lang.String)value;
    }

    /**
     * The description of the job
     * @return The value as raw object
     */
    public Object getDescriptionRaw()
    {
      return data[2];
    }

    public RowBuilder_Jobs insertRow()
    {
      return table.insertRow();
    }
    
    public RowBuilder_Jobs insertRow(JobsModel row)
    {
      return table.insertRow(row);
    }
    
    public RowBuilder_Jobs insertRowAt(int index)
    {
      return table.insertRowAt(index);
    }

    public RowBuilder_Jobs insertRow(RowBuilder_Jobs theRow)
    {
      return table.insertRow(theRow);
    }
    
    /**
     * Gets the value of a specified column.
     * <p>
     * Throws a RuntimeException, if row count is not equal to 1.
     * @param column The column name (not case sensitive)
     * @return The value
     */
    public Object getValue(String column) throws RuntimeException 
    {
      if(column.equalsIgnoreCase(Columns.Id) )
      {
        Object value = data[0];
        if (value instanceof LazyValue)
        {
          LazyValue lazyValue = (LazyValue)value;
          return lazyValue.getValue();
        } 
        return value;
      }
      if(column.equalsIgnoreCase(Columns.Title) )
      {
        Object value = data[1];
        if (value instanceof LazyValue)
        {
          LazyValue lazyValue = (LazyValue)value;
          return lazyValue.getValue();
        } 
        return value;
      }
      if(column.equalsIgnoreCase(Columns.Description) )
      {
        Object value = data[2];
        if (value instanceof LazyValue)
        {
          LazyValue lazyValue = (LazyValue)value;
          return lazyValue.getValue();
        } 
        return value;
      }
      throw new RuntimeException(NAME + " col = " + column);
    }
    
    @Override
    public RowBuilder_Jobs clone()
    {
      RowBuilder_Jobs clone = new RowBuilder_JobsImpl(table);
      clone.setId(getId());
      clone.setTitle(getTitle());
      clone.setDescription(getDescription());
      return clone;
    }
  }
 
  /**
   * Delivers a collection of rows matching to a filter.
   * @param filter The used filter
   * @return The collection of rows, may return an empty list
   * @see #findWhere
   * @see #getWhere
   */
  public RowCollection_Jobs find(Filter<RowBuilder_Jobs> filter)
  {
    RowCollection_Jobs modifiers = new RowCollection_Jobs(this);
    for (RowBuilder_Jobs row : rows)
    {
      if (filter.accept(row))
      {
        modifiers.add(row);
      }
    }
    return modifiers;
  }

  public void foreach(Action<RowBuilder_Jobs> action)
  {
    for (RowBuilder_Jobs row : rows)
    {
      action.call(row);
    }
  }

  /**
   * Allows searching for one or more rows specified by a column value.
   * findWhere assumes that it is used to search for existing rows. An
   * exception will be thrown if no matching row was found. Use getWhere
   * or find to avoid this behavior.
   * @see #getWhere
   * @see #find(Filter)
   */
  public JobsFindWhere findWhere = new JobsFindWhere(this);

  public static class JobsFindWhere
  {
    private final List<RowBuilder_Jobs> rows;
    private final JobsTable table;
    
    public JobsFindWhere(JobsTable theTable)
    {
       rows = theTable.rows;
       table = theTable;
    }
    
    public RowCollection_Jobs rowComparesTo(Comparable<RowBuilder_Jobs> toSearch) 
    {
      RowCollection_Jobs modifiers = new RowCollection_Jobs(table);
      for (RowBuilder_Jobs row : rows) 
      {
        if (toSearch.compareTo(row) == 0) 
        {
          modifiers.add(row);
        }
      }
      assertModifiersNotEmpty(modifiers, "No Row with ${column.name} = " + toSearch);
      return modifiers;
    }

    public RowCollection_Jobs id(java.lang.Long toSearch) 
    {
      RowCollection_Jobs modifiers = new RowCollection_Jobs(table);
      for (RowBuilder_Jobs row : rows) 
      {
        if (row.getId().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      assertModifiersNotEmpty(modifiers, "No Row with id = " + toSearch);
      return modifiers;
    }

    public RowCollection_Jobs id(Integer toSearch) 
    {
      return id( Long.valueOf(toSearch) );
    }

    public RowCollection_Jobs title(java.lang.String toSearch) 
    {
      RowCollection_Jobs modifiers = new RowCollection_Jobs(table);
      for (RowBuilder_Jobs row : rows) 
      {
        if (row.getTitle().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      assertModifiersNotEmpty(modifiers, "No Row with title = " + toSearch);
      return modifiers;
    }

    public RowCollection_Jobs description(java.lang.String toSearch) 
    {
      RowCollection_Jobs modifiers = new RowCollection_Jobs(table);
      for (RowBuilder_Jobs row : rows) 
      {
        if (row.getDescription().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      assertModifiersNotEmpty(modifiers, "No Row with description = " + toSearch);
      return modifiers;
    }

    private void assertModifiersNotEmpty(RowCollection_Jobs modifiers, String message)
    {
      if (modifiers.isEmpty()) 
      {
        throw new RuntimeException(message);
      }
    }    
  }

  /**
   * Allows searching for a row specified by a column value.
   * The first matching row is returned. Does not throw an
   * exception, if no element is found.
   * @see #findWhere
   * @see #find(Filter)
   */
  public JobsGetWhere getWhere = new JobsGetWhere(this);

  public static class JobsGetWhere
  {
    private final List<RowBuilder_Jobs> rows;
    private final JobsTable table;
    
    public JobsGetWhere(JobsTable theTable) 
    {
       rows = theTable.rows;
       table = theTable;
    }
    
    public Optional<RowBuilder_Jobs> rowComparesTo(Comparable<RowBuilder_Jobs> toSearch) 
    {
      for (RowBuilder_Jobs row : rows) 
      {
        if (toSearch.compareTo(row) == 0) 
        {
          return Optional.of(row);
        }
      }
      return Optional.<RowBuilder_Jobs> absent();
    }

    public Optional<RowBuilder_Jobs> id(java.lang.Long toSearch) 
    {
      for (RowBuilder_Jobs row : rows) 
      {
        if (row.getId().equals(toSearch)) 
        {
          return Optional.of(row);
        }
      }
      return Optional.<RowBuilder_Jobs> absent();
    }
    
    public Optional<RowBuilder_Jobs> id(Integer toSearch) 
    {
      return id( Long.valueOf(toSearch) );
    }

    public Optional<RowBuilder_Jobs> title(java.lang.String toSearch) 
    {
      for (RowBuilder_Jobs row : rows) 
      {
        if (row.getTitle().equals(toSearch)) 
        {
          return Optional.of(row);
        }
      }
      return Optional.<RowBuilder_Jobs> absent();
    }

    public Optional<RowBuilder_Jobs> description(java.lang.String toSearch) 
    {
      for (RowBuilder_Jobs row : rows) 
      {
        if (row.getDescription().equals(toSearch)) 
        {
          return Optional.of(row);
        }
      }
      return Optional.<RowBuilder_Jobs> absent();
    }
  }
  
  /** Inner class! Use RowCollection_Jobs in your code ! */
  public static class RowModify_Jobs extends RowBuilder_JobsImpl 
  {
    
    List<RowBuilder_Jobs> _rows;

    public RowModify_Jobs(JobsTable theTable) 
    {
      super(theTable);
      _rows = new ArrayList<RowBuilder_Jobs>();
    }
      
    public void add(RowBuilder_Jobs row) 
    {
      _rows.add(row);
    }
      
    public boolean isEmpty() 
    {
      return _rows.isEmpty();
    }

    public RowModify_Jobs delete()
    {
      for(RowBuilder_Jobs row : _rows) 
      {
        table.rows.remove(row);
      }
      return this;
    }
        

      
    public RowModify_Jobs setId(Integer intValue)
    {
      for(RowBuilder_Jobs row : _rows) 
      {
        row.setId(intValue);
      }
      return this;
    }

    public RowModify_Jobs setId(java.lang.Long value)
    {
      for(RowBuilder_Jobs row : _rows) 
      {
        row.setId(value);
      }
      return this;
    }
      
    public RowModify_Jobs setIdRaw(Object value)
    {
      for(RowBuilder_Jobs row : _rows)
      {
        row.setIdRaw(value);
      }
      return this;
    }
    
    public java.lang.Long getId()
    {
      assertSingleResult(); 
      return _rows.get(0).getId();
    }


    public RowModify_Jobs setTitle(java.lang.String value)
    {
      for(RowBuilder_Jobs row : _rows) 
      {
        row.setTitle(value);
      }
      return this;
    }
      
    public RowModify_Jobs setTitleRaw(Object value)
    {
      for(RowBuilder_Jobs row : _rows)
      {
        row.setTitleRaw(value);
      }
      return this;
    }
    
    /**
     * The job title
     * <p>
     * Throws a RuntimeException, if row count is not equal to 1
     * @return The value
     */
    public java.lang.String getTitle()
    {
      assertSingleResult(); 
      return _rows.get(0).getTitle();
    }


    public RowModify_Jobs setDescription(java.lang.String value)
    {
      for(RowBuilder_Jobs row : _rows) 
      {
        row.setDescription(value);
      }
      return this;
    }
      
    public RowModify_Jobs setDescriptionRaw(Object value)
    {
      for(RowBuilder_Jobs row : _rows)
      {
        row.setDescriptionRaw(value);
      }
      return this;
    }
    
    /**
     * The description of the job
     * <p>
     * Throws a RuntimeException, if row count is not equal to 1
     * @return The value
     */
    public java.lang.String getDescription()
    {
      assertSingleResult(); 
      return _rows.get(0).getDescription();
    }

    /**
     * Gets the value of a specified column.
     * <p>
     * Throws a RuntimeException, if row count is not equal to 1.
     * @param column The column name (not case sensitive)
     * @return The value
     */
    public Object getValue(String column)
    {
      assertSingleResult();
      return _rows.get(0).getValue(column);
    }
    
    private void assertSingleResult()
    {
      if (_rows.size() != 1) 
      {
        throw new RuntimeException("There where multiple Row in the result! " + _rows.size());
      }
    }    
      
    /** 
     * Return the count of rows contained in this collection
     */
    public int getRowCount()
    {
      return _rows.size();
    }
      
    @Override
    public RowModify_Jobs clone()
    {
      RowModify_Jobs clone = new RowModify_Jobs(table);
      for(RowBuilder_Jobs row:_rows) 
      {
        clone._rows.add(row.clone());
      }
      return clone;
    }

  }
  
  public static class RowCollection_Jobs extends RowModify_Jobs 
  {
    
    public JobsFindWhere where = new JobsFindWhere(table);
    
    public RowCollection_Jobs(JobsTable theTable)
    {
      super(theTable);
    }
    
  }

  /**
  * Insert a new empty Row.
  * <code><pre>
  * ds.table_Jobs.insertRow()
  *   .setId( null )
  *   .setTitle( null )
  *   .setDescription( null )
  *   ;
  * </pre></code>
  */
  public RowBuilder_Jobs insertRow()
  {
    RowBuilder_Jobs row = new RowBuilder_JobsImpl(this);
    row.nextId();
    rows.add(row);
    return row;
  }
  
  /**
  * <code><pre>
  * ds.table_Jobs.insertRow()
  * </pre></code>
  */
  public RowBuilder_Jobs insertRow(JobsModel rowToAdd)
  {
    RowBuilder_Jobs row = new RowBuilder_JobsImpl(this);
    row.setIdRaw( rowToAdd.getIdRaw() );
    row.setTitleRaw( rowToAdd.getTitleRaw() );
    row.setDescriptionRaw( rowToAdd.getDescriptionRaw() );
    rows.add(row);
    return row;
  }

  /**
  * <code><pre>
  * ds.table_Jobs.insertRow(data);
  * </pre></code>
  */
  public RowBuilder_Jobs insertRow(RowBuilder_Jobs theRow)
  {
    rows.add(theRow);
    return theRow;
  }
  
  /**
  * <code><pre>
  * ds.table_Jobs.insertRows(data);
  * </pre></code>
  */
  public void insertRows(RowBuilder_Jobs...theRows)
  {
    rows.addAll(Arrays.asList(theRows));
  }
  
  /**
  * Insert new row at the given index
  * <code><pre>
  * ds.table_Jobs.insertRowAt(3)
  *   ;
  * </pre></code>
  */
  public RowBuilder_Jobs insertRowAt(int index)
  {
    RowBuilder_Jobs row = new RowBuilder_JobsImpl(this);
    rows.add(index, row);
    return row;
  }
  
  /**
  * Insert new row Object at the given index
  * <code><pre>
  * ds.table_Jobs.insertRowAt(3)
  * </pre></code>
  */
  public RowBuilder_Jobs insertRowAt(int index,RowBuilder_Jobs theRow)
  {
    rows.add(index, theRow);
    return theRow;
  }
  
  /**
   * Remove a row from the builder by the given index.
   *
   * @return the deleted row
   */ 
  public RowBuilder_Jobs deleteRow(int index)
  {
    RowBuilder_Jobs rowBuilder = rows.get(index);
    rows.remove(rowBuilder);
    return rowBuilder;
  }
  
  /**
   * Remove a row from the builder
   */ 
  public RowBuilder_Jobs deleteRow(RowBuilder_Jobs rowToDelete)
  {
    rows.remove(rowToDelete);
    return rowToDelete;
  }

  /**
  * Creates a new row but does not add it to the table
  */
  public RowBuilder_Jobs newRow()
  {
    RowBuilder_Jobs row = new RowBuilder_JobsImpl(this);
    return row;
  }
  
  /**
  * Returns the next Object. The internal iterator is started at 
  * the first call.
  */
  public RowBuilder_Jobs next()
  {
    if(_iterator == null) 
    {
      _iterator = rows.iterator();
    }
    return _iterator.next();
  }
  
  public void resetIterator() 
  {
    _iterator = null;
  }

  public ITableMetaData getTableMetaData() 
  {
    return _metaData;
  }
  
  public int getRowCount() 
  {
    return rows.size();
  }
   
  /**
   * Gets the value of a specified table cell.
   * <p>
   * @param row The table row
   * @param column The column name (not case sensitive)
   * @return The value
   */
  public Object getValue(int row, String column) throws DataSetException 
  {
    if (row >= rows.size() || row < 0) 
    {
      throw new RowOutOfBoundsException();
    }
    return rows.get(row).getValue(column);
  }

  static Date toDate(String dateString)
  {
    return asDate(dateString);
  }
  
  static Date toDate(DateBuilder date)
  {
    return date.asDate();
  }
 
}
