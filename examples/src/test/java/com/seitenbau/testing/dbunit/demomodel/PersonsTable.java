package com.seitenbau.testing.dbunit.demomodel;

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

import com.seitenbau.testing.dbunit.demomodel.JobsTable.RowGetters_Jobs;
import com.seitenbau.testing.dbunit.demomodel.PersonsTable.RowGetters_Persons;
import com.seitenbau.testing.dbunit.demomodel.TeamsTable.RowGetters_Teams;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;
import com.seitenbau.testing.util.date.DateBuilder;


import static com.seitenbau.testing.util.DateUtil.*;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class PersonsTable implements ITable
{
  public final static String NAME = "persons";

  public static class Columns
  {
    public static final String Id = "id";
    public static final String FirstName = "first_name";
    public static final String Name = "name";
    public static final String JobId = "job_id";
    public static final String TeamId = "team_id";
  }

  // @formatter:off
  public final static Column[] COLUMNS = new Column[] {
    // idx = 0
    new Column(Columns.Id, DataType.BIGINT),
    // idx = 1
    new Column(Columns.FirstName, DataType.VARCHAR),
    // idx = 2
    new Column(Columns.Name, DataType.VARCHAR),
    // idx = 3
    new Column(Columns.JobId, DataType.BIGINT),
    // idx = 4
    new Column(Columns.TeamId, DataType.BIGINT)
  };

  // @formatter:on

  ITableMetaData _metaData;
  
  STUDataSet _dataSet;
  
  Iterator<RowBuilder_Persons> _iterator;
  
  public PersonsTable()
  {
    _metaData=new DefaultTableMetaData(NAME, COLUMNS);
  }

  public void setDataset(STUDataSet dataSet)
  {
    _dataSet=dataSet;
  }
  
  public STUDataSet getDataset()
  {
    return _dataSet;
  }

  public List<RowBuilder_Persons> rows = new ArrayList<RowBuilder_Persons>();
  
  public interface RowSetters_Persons<T extends RowSetters_Persons>
  {
    T setId(Integer intValue);
    T setId(java.lang.Long value);
    T setIdRaw(Object value);
    T nextId();
    T setFirstName(java.lang.String value);
    T setFirstNameRaw(Object value);
    T setName(java.lang.String value);
    T setNameRaw(Object value);
    T setJobId(Integer intValue);
    T setJobId(java.lang.Long value);
    T setJobIdRaw(Object value);
    T setTeamId(Integer intValue);
    T setTeamId(java.lang.Long value);
    T setTeamIdRaw(Object value);
     
  }
  
  public interface RowGetters_Persons<T extends RowGetters_Persons>
  {
    java.lang.Long getId();
    java.lang.String getFirstName();
    java.lang.String getName();
    java.lang.Long getJobId();
    java.lang.Long getTeamId();
     
  }

  public static class RowBuilder_Persons implements RowSetters_Persons<RowBuilder_Persons>, RowGetters_Persons<RowBuilder_Persons>
  {

    Object[] data;
    
    PersonsTable table;
    
    RowBuilder_Persons(PersonsTable tableDelegate) {
      data=new Object[COLUMNS.length];
      table = tableDelegate;
    }
    

    public RowBuilder_Persons setId(Integer intValue)
    {
      data[0] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Persons setId(java.lang.Long value)
    {
      data[0] = value;
      return this;
    }
    public RowBuilder_Persons setIdRaw(Object value)
    {
      data[0] = value;
      return this;
    }
    public RowBuilder_Persons nextId()
    {
      DatasetIdGenerator generator = table.getDataset().getIdGenerator();
      Long nextId = generator.nextId(PersonsTable.NAME,"id");
      setId(nextId);
      return this;
    }

    public java.lang.Long getId()
    {
      return (java.lang.Long) data[0];
    }

    public Object getIdRaw()
    {
      return data[0];
    }

    public RowBuilder_Persons setFirstName(java.lang.String value)
    {
      data[1] = value;
      return this;
    }
    public RowBuilder_Persons setFirstNameRaw(Object value)
    {
      data[1] = value;
      return this;
    }

    public java.lang.String getFirstName()
    {
      return (java.lang.String) data[1];
    }

    public Object getFirstNameRaw()
    {
      return data[1];
    }

    public RowBuilder_Persons setName(java.lang.String value)
    {
      data[2] = value;
      return this;
    }
    public RowBuilder_Persons setNameRaw(Object value)
    {
      data[2] = value;
      return this;
    }

    public java.lang.String getName()
    {
      return (java.lang.String) data[2];
    }

    public Object getNameRaw()
    {
      return data[2];
    }

    public RowBuilder_Persons setJobId(Integer intValue)
    {
      data[3] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Persons setJobId(java.lang.Long value)
    {
      data[3] = value;
      return this;
    }
    public RowBuilder_Persons setJobIdRaw(Object value)
    {
      data[3] = value;
      return this;
    }

    public java.lang.Long getJobId()
    {
      return (java.lang.Long) data[3];
    }

    public Object getJobIdRaw()
    {
      return data[3];
    }

    public RowBuilder_Persons setTeamId(Integer intValue)
    {
      data[4] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Persons setTeamId(java.lang.Long value)
    {
      data[4] = value;
      return this;
    }
    public RowBuilder_Persons setTeamIdRaw(Object value)
    {
      data[4] = value;
      return this;
    }

    public java.lang.Long getTeamId()
    {
      return (java.lang.Long) data[4];
    }

    public Object getTeamIdRaw()
    {
      return data[4];
    }
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Persons.insertRow()
    *   .setId( null )
    *   .setFirstName( null )
    *   .setName( null )
    *   .setJobId( null )
    *   .setTeamId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Persons insertRow()
    {
      return table.insertRow();
    }
    
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Persons.insertRow()
    *   .setId( null )
    *   .setFirstName( null )
    *   .setName( null )
    *   .setJobId( null )
    *   .setTeamId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Persons insertRow(PersonsModel row)
    {
      return table.insertRow(row);
    }
    
    /**
    * Insert a new Row at the given position
    * <code><pre>
    * ds.table_Persons.this.insertRowAt(2)
    *   .setId( null )
    *   .setFirstName( null )
    *   .setName( null )
    *   .setJobId( null )
    *   .setTeamId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Persons insertRowAt(int index)
    {
      return table.insertRowAt(index);
    }

    /**
    * Insert a row at the end of the table
    * <code><pre>
    * ds.table_Persons.insertRow(baseUser);
    * </pre></code>
    */
    public RowBuilder_Persons insertRow(RowBuilder_Persons theRow)
    {
      return table.insertRow(theRow);
    }
    
    public Object getValue(String column) throws RuntimeException {
      if(column.equalsIgnoreCase(Columns.Id) ) {
        return data[0];
      }
      if(column.equalsIgnoreCase(Columns.FirstName) ) {
        return data[1];
      }
      if(column.equalsIgnoreCase(Columns.Name) ) {
        return data[2];
      }
      if(column.equalsIgnoreCase(Columns.JobId) ) {
        return data[3];
      }
      if(column.equalsIgnoreCase(Columns.TeamId) ) {
        return data[4];
      }
      throw new RuntimeException(NAME + " col = " + column);
    }
    

    public RowBuilder_Persons refJobId(RowGetters_Jobs reference)
    {
      setJobId(reference.getId());
      return this;
    }

    public RowBuilder_Persons refTeamId(RowGetters_Teams reference)
    {
      setTeamId(reference.getId());
      return this;
    }
    
    @Override
    public RowBuilder_Persons clone() {
      RowBuilder_Persons clone = new RowBuilder_Persons(table);
      clone.setId(getId());
      clone.setFirstName(getFirstName());
      clone.setName(getName());
      clone.setJobId(getJobId());
      clone.setTeamId(getTeamId());
      return clone;
    }
  }
  
  public PersonsWhere findWhere = new PersonsWhere(this);

  public static class PersonsWhere
  {
    public List<RowBuilder_Persons> rows;
    PersonsTable table;
    
    public PersonsWhere(PersonsTable theTable) {
       rows = theTable.rows;
       table = theTable;
    }
    
    public RowCollection_Persons rowComparesTo(Comparable<RowBuilder_Persons> toSearch) {
      RowCollection_Persons modifiers = new RowCollection_Persons(table);
      for (RowBuilder_Persons row : rows) 
      {
        if (toSearch.compareTo( row ) == 0) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with ${column.name} = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Persons id(java.lang.Long toSearch) {
      RowCollection_Persons modifiers = new RowCollection_Persons(table);
      for (RowBuilder_Persons row : rows) 
      {
        if (row.getId().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with id = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Persons id(Integer toSearch) 
    {
      return id( Long.valueOf(toSearch) );
    }
    public RowCollection_Persons firstName(java.lang.String toSearch) {
      RowCollection_Persons modifiers = new RowCollection_Persons(table);
      for (RowBuilder_Persons row : rows) 
      {
        if (row.getFirstName().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with first_name = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Persons name(java.lang.String toSearch) {
      RowCollection_Persons modifiers = new RowCollection_Persons(table);
      for (RowBuilder_Persons row : rows) 
      {
        if (row.getName().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with name = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Persons jobId(java.lang.Long toSearch) {
      RowCollection_Persons modifiers = new RowCollection_Persons(table);
      for (RowBuilder_Persons row : rows) 
      {
        if (row.getJobId().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with job_id = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Persons jobId(Integer toSearch) 
    {
      return jobId( Long.valueOf(toSearch) );
    }
    public RowCollection_Persons teamId(java.lang.Long toSearch) {
      RowCollection_Persons modifiers = new RowCollection_Persons(table);
      for (RowBuilder_Persons row : rows) 
      {
        if (row.getTeamId().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with team_id = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Persons teamId(Integer toSearch) 
    {
      return teamId( Long.valueOf(toSearch) );
    }
  }
  
  /** Inner class! Use RowCollection_Persons in your code ! */
  public static class RowModify_Persons extends RowBuilder_Persons 
  {
      List<RowBuilder_Persons> _rows;

      public RowModify_Persons(PersonsTable theTable) {
        super(theTable);
        _rows = new ArrayList<RowBuilder_Persons>();
      }
      
      public void add(RowBuilder_Persons row) {
          _rows.add(row);
      }
      
      public boolean isEmpty() {
          return _rows.isEmpty();
      }

      public RowModify_Persons delete() {
          for(RowBuilder_Persons row : _rows) {
            table.rows.remove(row);
          }
          return this;
      }
        

      public RowModify_Persons setId(Integer intValue)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setId(intValue);
        }
        return this;
      }

      public RowModify_Persons setId(java.lang.Long value)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setId(value);
        }
        return this;
      }
      public RowModify_Persons setIdRaw(Object value)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setIdRaw(value);
        }
        return this;
      }
      public java.lang.Long getId()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getId();
      }

      public RowModify_Persons setFirstName(java.lang.String value)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setFirstName(value);
        }
        return this;
      }
      public RowModify_Persons setFirstNameRaw(Object value)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setFirstNameRaw(value);
        }
        return this;
      }
      public java.lang.String getFirstName()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getFirstName();
      }

      public RowModify_Persons setName(java.lang.String value)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setName(value);
        }
        return this;
      }
      public RowModify_Persons setNameRaw(Object value)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setNameRaw(value);
        }
        return this;
      }
      public java.lang.String getName()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getName();
      }

      public RowModify_Persons setJobId(Integer intValue)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setJobId(intValue);
        }
        return this;
      }

      public RowModify_Persons setJobId(java.lang.Long value)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setJobId(value);
        }
        return this;
      }
      public RowModify_Persons setJobIdRaw(Object value)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setJobIdRaw(value);
        }
        return this;
      }
      public java.lang.Long getJobId()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getJobId();
      }

      public RowModify_Persons setTeamId(Integer intValue)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setTeamId(intValue);
        }
        return this;
      }

      public RowModify_Persons setTeamId(java.lang.Long value)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setTeamId(value);
        }
        return this;
      }
      public RowModify_Persons setTeamIdRaw(Object value)
      {
        for(RowBuilder_Persons row : _rows) {
          row.setTeamIdRaw(value);
        }
        return this;
      }
      public java.lang.Long getTeamId()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getTeamId();
      }
      public Object getValue(String column)
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getValue(column);
      }
      
      /** 
       * Return the count of rows contained in this collection
       */
      public int getRowCount()
      {
        return _rows.size();
      }
      
      @Override
      public RowModify_Persons clone() {
        RowModify_Persons clone = new RowModify_Persons(table);
        for(RowBuilder_Persons row:_rows) {
          clone._rows.add(row.clone());
        }
        return clone;
      }

  }
  
  public static class RowCollection_Persons extends RowModify_Persons {
    public PersonsWhere where = new PersonsWhere(table);
    
    public RowCollection_Persons(PersonsTable theTable)
    {
      super(theTable);
    }
    
  }

  /**
  * Insert a new empty Row.
  * <code><pre>
  * ds.table_Persons.insertRow()
  *   .setId( null )
  *   .setFirstName( null )
  *   .setName( null )
  *   .setJobId( null )
  *   .setTeamId( null )
  *   ;
  * </pre></code>
  */
  public RowBuilder_Persons insertRow()
  {
    RowBuilder_Persons row = new RowBuilder_Persons(this);
    row.nextId();
    rows.add(row);
    return row;
  }
  
  /**
  * <code><pre>
  * ds.table_Persons.insertRow()
  * </pre></code>
  */
  public RowBuilder_Persons insertRow(PersonsModel rowToAdd)
  {
    RowBuilder_Persons row = new RowBuilder_Persons(this);
    row.setIdRaw( rowToAdd.getIdRaw() );
    row.setFirstNameRaw( rowToAdd.getFirstNameRaw() );
    row.setNameRaw( rowToAdd.getNameRaw() );
    row.setJobIdRaw( rowToAdd.getJobIdRaw() );
    row.setTeamIdRaw( rowToAdd.getTeamIdRaw() );
    rows.add(row);
    return row;
  }

  /**
  * <code><pre>
  * ds.table_Persons.insertRow(data);
  * </pre></code>
  */
  public RowBuilder_Persons insertRow(RowBuilder_Persons theRow)
  {
    rows.add(theRow);
    return theRow;
  }
  
  /**
  * <code><pre>
  * ds.table_Persons.insertRows(data);
  * </pre></code>
  */
  public void insertRows(RowBuilder_Persons...theRows)
  {
    rows.addAll(Arrays.asList(theRows));
  }
  
  /**
  * Insert new row at the given index
  * <code><pre>
  * ds.table_Persons.insertRowAt(3)
  *   ;
  * </pre></code>
  */
  public RowBuilder_Persons insertRowAt(int index)
  {
    RowBuilder_Persons row = new RowBuilder_Persons(this);
    rows.add(index, row);
    return row;
  }
  
  /**
  * Insert new row Object at the given index
  * <code><pre>
  * ds.table_Persons.insertRowAt(3)
  * </pre></code>
  */
  public RowBuilder_Persons insertRowAt(int index,RowBuilder_Persons theRow)
  {
    rows.add(index, theRow);
    return theRow;
  }
  
  /**
   * Remove a row from the builder by the given index.
   *
   * @return the deleted row
   */ 
  public RowBuilder_Persons deleteRow(int index)
  {
    RowBuilder_Persons rowBuilder = rows.get(index);
    rows.remove(rowBuilder);
    return rowBuilder;
  }
  
  /**
   * Remove a row from the builder
   */ 
  public RowBuilder_Persons deleteRow(RowBuilder_Persons rowToDelete)
  {
    rows.remove(rowToDelete);
    return rowToDelete;
  }

  /**
  * Creates a new row but does not add it to the table
  */
  public RowBuilder_Persons newRow()
  {
    RowBuilder_Persons row = new RowBuilder_Persons(this);
    return row;
  }
  
  /**
  * Returns the next Object. The internal iterator is started at 
  * the first call.
  */
  public RowBuilder_Persons next()
  {
    if(_iterator == null) {
      _iterator = rows.iterator();
    }
    return _iterator.next();
  }
  
  public void resetIterator() {
    _iterator = null;
  }

  public ITableMetaData getTableMetaData() {
      return _metaData;
  }
  
  public int getRowCount() {
      return rows.size();
  }
   
  public Object getValue(int row, String column) throws DataSetException {
      if (row >= rows.size() || row < 0) {
        throw new RowOutOfBoundsException();
      }
      return rows.get(row).getValue(column);
  }

  static Date toDate(String dateString)
  {
    return asDate(dateString);
  }
  
  static Date toDate(DateBuilder datum)
  {
    return datum.asDate();
  }
 
}
