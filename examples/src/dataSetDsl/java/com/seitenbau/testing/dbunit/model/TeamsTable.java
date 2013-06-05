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
public class TeamsTable implements ITable
{
  public final static String NAME = "teams";

  public static class Columns
  {
    public static final String Id = "id";
    public static final String Title = "title";
    public static final String Description = "description";
    public static final String Membersize = "membersize";
  }

  // @formatter:off
  public final static Column[] COLUMNS = new Column[] {
    // idx = 0
    new Column(Columns.Id, DataType.BIGINT),
    // idx = 1
    new Column(Columns.Title, DataType.VARCHAR),
    // idx = 2
    new Column(Columns.Description, DataType.VARCHAR),
    // idx = 3
    new Column(Columns.Membersize, DataType.BIGINT)
  };

  // @formatter:on

  ITableMetaData _metaData;
  
  PersonDatabaseDataSet _dataSet;
  
  Iterator<RowBuilder_Teams> _iterator;
  
  public TeamsTable()
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

  public List<RowBuilder_Teams> rows = new ArrayList<RowBuilder_Teams>();
  
  public interface RowSetters_Teams<T extends RowSetters_Teams>
  {
    T setId(Integer intValue);
    T setId(java.lang.Long value);
    T setIdRaw(Object value);
    T nextId();
    T setTitle(java.lang.String value);
    T setTitleRaw(Object value);
    T setDescription(java.lang.String value);
    T setDescriptionRaw(Object value);
    T setMembersize(Integer intValue);
    T setMembersize(java.lang.Long value);
    T setMembersizeRaw(Object value);
  }
  
  public interface RowGetters_Teams<T extends RowGetters_Teams>
  {
    java.lang.Long getId();
    java.lang.String getTitle();
    java.lang.String getDescription();
    java.lang.Long getMembersize();

  }

  public interface RowBuilder_Teams extends RowSetters_Teams<RowBuilder_Teams>, RowGetters_Teams<RowBuilder_Teams>
  {

    /**
     * Gets the value of a specified column.
     * <p>
     * Throws a RuntimeException, if row count is not equal to 1.
     * @param column The column name (not case sensitive)
     * @return The value
     */
    Object getValue(String column) throws RuntimeException;

    RowBuilder_Teams clone();

    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Teams.insertRow()
    *   .setId( null )
    *   .setTitle( null )
    *   .setDescription( null )
    *   .setMembersize( null )
    *   ;
    * </pre></code>
    */
    RowBuilder_Teams insertRow();

    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Teams.insertRow()
    *   .setId( null )
    *   .setTitle( null )
    *   .setDescription( null )
    *   .setMembersize( null )
    *   ;
    * </pre></code>
    */
    RowBuilder_Teams insertRow(TeamsModel row);
    
    /**
    * Insert a new Row at the given position
    * <code><pre>
    * ds.table_Teams.this.insertRowAt(2)
    *   .setId( null )
    *   .setTitle( null )
    *   .setDescription( null )
    *   .setMembersize( null )
    *   ;
    * </pre></code>
    */
    RowBuilder_Teams insertRowAt(int index);
    
    /**
    * Insert a row at the end of the table
    * <code><pre>
    * ds.table_Teams.insertRow(someRow);
    * </pre></code>
    */
    RowBuilder_Teams insertRow(RowBuilder_Teams theRow);


    Object getIdRaw();

    Object getTitleRaw();

    Object getDescriptionRaw();

    Object getMembersizeRaw();
  }

  public static class RowBuilder_TeamsImpl implements RowBuilder_Teams
  {

    Object[] data;
    
    TeamsTable table;
    
    RowBuilder_TeamsImpl(TeamsTable tableDelegate) 
    {
      data = new Object[COLUMNS.length];
      table = tableDelegate;
    }
    

    public RowBuilder_Teams setId(Integer intValue)
    {
      data[0] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Teams setId(java.lang.Long value)
    {
      data[0] = value;
      return this;
    }
    public RowBuilder_Teams setIdRaw(Object value)
    {
      data[0] = value;
      return this;
    }
    public RowBuilder_Teams nextId()
    {
      DatasetIdGenerator generator = table.getDataset().getIdGenerator();
      Long nextId = generator.nextId(TeamsTable.NAME,"id");
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

    public RowBuilder_Teams setTitle(java.lang.String value)
    {
      data[1] = value;
      return this;
    }
    public RowBuilder_Teams setTitleRaw(Object value)
    {
      data[1] = value;
      return this;
    }

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

    public Object getTitleRaw()
    {
      return data[1];
    }

    public RowBuilder_Teams setDescription(java.lang.String value)
    {
      data[2] = value;
      return this;
    }
    public RowBuilder_Teams setDescriptionRaw(Object value)
    {
      data[2] = value;
      return this;
    }

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

    public Object getDescriptionRaw()
    {
      return data[2];
    }

    public RowBuilder_Teams setMembersize(Integer intValue)
    {
      data[3] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Teams setMembersize(java.lang.Long value)
    {
      data[3] = value;
      return this;
    }
    public RowBuilder_Teams setMembersizeRaw(Object value)
    {
      data[3] = value;
      return this;
    }

    public java.lang.Long getMembersize()
    {
      Object value = data[3];
      if (value instanceof LazyValue) {
        LazyValue lazyValue = (LazyValue)value;
        return (java.lang.Long)CastUtil.cast(lazyValue.getValue(), 
          com.seitenbau.testing.dbunit.generator.DataType.BIGINT);
      } 
      return (java.lang.Long)value;
    }

    public Object getMembersizeRaw()
    {
      return data[3];
    }

    public RowBuilder_Teams insertRow()
    {
      return table.insertRow();
    }
    
    public RowBuilder_Teams insertRow(TeamsModel row)
    {
      return table.insertRow(row);
    }
    
    public RowBuilder_Teams insertRowAt(int index)
    {
      return table.insertRowAt(index);
    }

    public RowBuilder_Teams insertRow(RowBuilder_Teams theRow)
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
      if(column.equalsIgnoreCase(Columns.Membersize) )
      {
        Object value = data[3];
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
    public RowBuilder_Teams clone()
    {
      RowBuilder_Teams clone = new RowBuilder_TeamsImpl(table);
      clone.setId(getId());
      clone.setTitle(getTitle());
      clone.setDescription(getDescription());
      clone.setMembersize(getMembersize());
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
  public RowCollection_Teams find(Filter<RowBuilder_Teams> filter)
  {
    RowCollection_Teams modifiers = new RowCollection_Teams(this);
    for (RowBuilder_Teams row : rows)
    {
      if (filter.accept(row))
      {
        modifiers.add(row);
      }
    }
    return modifiers;
  }

  public void foreach(Action<RowBuilder_Teams> action)
  {
    for (RowBuilder_Teams row : rows)
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
  public TeamsFindWhere findWhere = new TeamsFindWhere(this);

  public static class TeamsFindWhere
  {
    private final List<RowBuilder_Teams> rows;
    private final TeamsTable table;
    
    public TeamsFindWhere(TeamsTable theTable)
    {
       rows = theTable.rows;
       table = theTable;
    }
    
    public RowCollection_Teams rowComparesTo(Comparable<RowBuilder_Teams> toSearch) 
    {
      RowCollection_Teams modifiers = new RowCollection_Teams(table);
      for (RowBuilder_Teams row : rows) 
      {
        if (toSearch.compareTo(row) == 0) 
        {
          modifiers.add(row);
        }
      }
      assertModifiersNotEmpty(modifiers, "No Row with ${column.name} = " + toSearch);
      return modifiers;
    }

    public RowCollection_Teams id(java.lang.Long toSearch) 
    {
      RowCollection_Teams modifiers = new RowCollection_Teams(table);
      for (RowBuilder_Teams row : rows) 
      {
        if (row.getId().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      assertModifiersNotEmpty(modifiers, "No Row with id = " + toSearch);
      return modifiers;
    }

    public RowCollection_Teams id(Integer toSearch) 
    {
      return id( Long.valueOf(toSearch) );
    }

    public RowCollection_Teams title(java.lang.String toSearch) 
    {
      RowCollection_Teams modifiers = new RowCollection_Teams(table);
      for (RowBuilder_Teams row : rows) 
      {
        if (row.getTitle().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      assertModifiersNotEmpty(modifiers, "No Row with title = " + toSearch);
      return modifiers;
    }

    public RowCollection_Teams description(java.lang.String toSearch) 
    {
      RowCollection_Teams modifiers = new RowCollection_Teams(table);
      for (RowBuilder_Teams row : rows) 
      {
        if (row.getDescription().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      assertModifiersNotEmpty(modifiers, "No Row with description = " + toSearch);
      return modifiers;
    }

    public RowCollection_Teams membersize(java.lang.Long toSearch) 
    {
      RowCollection_Teams modifiers = new RowCollection_Teams(table);
      for (RowBuilder_Teams row : rows) 
      {
        if (row.getMembersize().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      assertModifiersNotEmpty(modifiers, "No Row with membersize = " + toSearch);
      return modifiers;
    }

    public RowCollection_Teams membersize(Integer toSearch) 
    {
      return membersize( Long.valueOf(toSearch) );
    }

    private void assertModifiersNotEmpty(RowCollection_Teams modifiers, String message)
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
  public TeamsGetWhere getWhere = new TeamsGetWhere(this);

  public static class TeamsGetWhere
  {
    private final List<RowBuilder_Teams> rows;
    private final TeamsTable table;
    
    public TeamsGetWhere(TeamsTable theTable) 
    {
       rows = theTable.rows;
       table = theTable;
    }
    
    public Optional<RowBuilder_Teams> rowComparesTo(Comparable<RowBuilder_Teams> toSearch) 
    {
      for (RowBuilder_Teams row : rows) 
      {
        if (toSearch.compareTo(row) == 0) 
        {
          return Optional.of(row);
        }
      }
      return Optional.<RowBuilder_Teams> absent();
    }

    public Optional<RowBuilder_Teams> id(java.lang.Long toSearch) 
    {
      for (RowBuilder_Teams row : rows) 
      {
        if (row.getId().equals(toSearch)) 
        {
          return Optional.of(row);
        }
      }
      return Optional.<RowBuilder_Teams> absent();
    }
    
    public Optional<RowBuilder_Teams> id(Integer toSearch) 
    {
      return id( Long.valueOf(toSearch) );
    }

    public Optional<RowBuilder_Teams> title(java.lang.String toSearch) 
    {
      for (RowBuilder_Teams row : rows) 
      {
        if (row.getTitle().equals(toSearch)) 
        {
          return Optional.of(row);
        }
      }
      return Optional.<RowBuilder_Teams> absent();
    }

    public Optional<RowBuilder_Teams> description(java.lang.String toSearch) 
    {
      for (RowBuilder_Teams row : rows) 
      {
        if (row.getDescription().equals(toSearch)) 
        {
          return Optional.of(row);
        }
      }
      return Optional.<RowBuilder_Teams> absent();
    }

    public Optional<RowBuilder_Teams> membersize(java.lang.Long toSearch) 
    {
      for (RowBuilder_Teams row : rows) 
      {
        if (row.getMembersize().equals(toSearch)) 
        {
          return Optional.of(row);
        }
      }
      return Optional.<RowBuilder_Teams> absent();
    }
    
    public Optional<RowBuilder_Teams> membersize(Integer toSearch) 
    {
      return membersize( Long.valueOf(toSearch) );
    }
  }
  
  /** Inner class! Use RowCollection_Teams in your code ! */
  public static class RowModify_Teams extends RowBuilder_TeamsImpl 
  {
    
    List<RowBuilder_Teams> _rows;

    public RowModify_Teams(TeamsTable theTable) 
    {
      super(theTable);
      _rows = new ArrayList<RowBuilder_Teams>();
    }
      
    public void add(RowBuilder_Teams row) 
    {
      _rows.add(row);
    }
      
    public boolean isEmpty() 
    {
      return _rows.isEmpty();
    }

    public RowModify_Teams delete()
    {
      for(RowBuilder_Teams row : _rows) 
      {
        table.rows.remove(row);
      }
      return this;
    }
        

      
    public RowModify_Teams setId(Integer intValue)
    {
      for(RowBuilder_Teams row : _rows) 
      {
        row.setId(intValue);
      }
      return this;
    }

    public RowModify_Teams setId(java.lang.Long value)
    {
      for(RowBuilder_Teams row : _rows) 
      {
        row.setId(value);
      }
      return this;
    }
      
    public RowModify_Teams setIdRaw(Object value)
    {
      for(RowBuilder_Teams row : _rows)
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


    public RowModify_Teams setTitle(java.lang.String value)
    {
      for(RowBuilder_Teams row : _rows) 
      {
        row.setTitle(value);
      }
      return this;
    }
      
    public RowModify_Teams setTitleRaw(Object value)
    {
      for(RowBuilder_Teams row : _rows)
      {
        row.setTitleRaw(value);
      }
      return this;
    }
    
    public java.lang.String getTitle()
    {
      assertSingleResult(); 
      return _rows.get(0).getTitle();
    }


    public RowModify_Teams setDescription(java.lang.String value)
    {
      for(RowBuilder_Teams row : _rows) 
      {
        row.setDescription(value);
      }
      return this;
    }
      
    public RowModify_Teams setDescriptionRaw(Object value)
    {
      for(RowBuilder_Teams row : _rows)
      {
        row.setDescriptionRaw(value);
      }
      return this;
    }
    
    public java.lang.String getDescription()
    {
      assertSingleResult(); 
      return _rows.get(0).getDescription();
    }

      
    public RowModify_Teams setMembersize(Integer intValue)
    {
      for(RowBuilder_Teams row : _rows) 
      {
        row.setMembersize(intValue);
      }
      return this;
    }

    public RowModify_Teams setMembersize(java.lang.Long value)
    {
      for(RowBuilder_Teams row : _rows) 
      {
        row.setMembersize(value);
      }
      return this;
    }
      
    public RowModify_Teams setMembersizeRaw(Object value)
    {
      for(RowBuilder_Teams row : _rows)
      {
        row.setMembersizeRaw(value);
      }
      return this;
    }
    
    public java.lang.Long getMembersize()
    {
      assertSingleResult(); 
      return _rows.get(0).getMembersize();
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
    public RowModify_Teams clone()
    {
      RowModify_Teams clone = new RowModify_Teams(table);
      for(RowBuilder_Teams row:_rows) 
      {
        clone._rows.add(row.clone());
      }
      return clone;
    }

  }
  
  public static class RowCollection_Teams extends RowModify_Teams 
  {
    
    public TeamsFindWhere where = new TeamsFindWhere(table);
    
    public RowCollection_Teams(TeamsTable theTable)
    {
      super(theTable);
    }
    
  }

  /**
  * Insert a new empty Row.
  * <code><pre>
  * ds.table_Teams.insertRow()
  *   .setId( null )
  *   .setTitle( null )
  *   .setDescription( null )
  *   .setMembersize( null )
  *   ;
  * </pre></code>
  */
  public RowBuilder_Teams insertRow()
  {
    RowBuilder_Teams row = new RowBuilder_TeamsImpl(this);
    row.nextId();
    rows.add(row);
    return row;
  }
  
  /**
  * <code><pre>
  * ds.table_Teams.insertRow()
  * </pre></code>
  */
  public RowBuilder_Teams insertRow(TeamsModel rowToAdd)
  {
    RowBuilder_Teams row = new RowBuilder_TeamsImpl(this);
    row.setIdRaw( rowToAdd.getIdRaw() );
    row.setTitleRaw( rowToAdd.getTitleRaw() );
    row.setDescriptionRaw( rowToAdd.getDescriptionRaw() );
    row.setMembersizeRaw( rowToAdd.getMembersizeRaw() );
    rows.add(row);
    return row;
  }

  /**
  * <code><pre>
  * ds.table_Teams.insertRow(data);
  * </pre></code>
  */
  public RowBuilder_Teams insertRow(RowBuilder_Teams theRow)
  {
    rows.add(theRow);
    return theRow;
  }
  
  /**
  * <code><pre>
  * ds.table_Teams.insertRows(data);
  * </pre></code>
  */
  public void insertRows(RowBuilder_Teams...theRows)
  {
    rows.addAll(Arrays.asList(theRows));
  }
  
  /**
  * Insert new row at the given index
  * <code><pre>
  * ds.table_Teams.insertRowAt(3)
  *   ;
  * </pre></code>
  */
  public RowBuilder_Teams insertRowAt(int index)
  {
    RowBuilder_Teams row = new RowBuilder_TeamsImpl(this);
    rows.add(index, row);
    return row;
  }
  
  /**
  * Insert new row Object at the given index
  * <code><pre>
  * ds.table_Teams.insertRowAt(3)
  * </pre></code>
  */
  public RowBuilder_Teams insertRowAt(int index,RowBuilder_Teams theRow)
  {
    rows.add(index, theRow);
    return theRow;
  }
  
  /**
   * Remove a row from the builder by the given index.
   *
   * @return the deleted row
   */ 
  public RowBuilder_Teams deleteRow(int index)
  {
    RowBuilder_Teams rowBuilder = rows.get(index);
    rows.remove(rowBuilder);
    return rowBuilder;
  }
  
  /**
   * Remove a row from the builder
   */ 
  public RowBuilder_Teams deleteRow(RowBuilder_Teams rowToDelete)
  {
    rows.remove(rowToDelete);
    return rowToDelete;
  }

  /**
  * Creates a new row but does not add it to the table
  */
  public RowBuilder_Teams newRow()
  {
    RowBuilder_Teams row = new RowBuilder_TeamsImpl(this);
    return row;
  }
  
  /**
  * Returns the next Object. The internal iterator is started at 
  * the first call.
  */
  public RowBuilder_Teams next()
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
