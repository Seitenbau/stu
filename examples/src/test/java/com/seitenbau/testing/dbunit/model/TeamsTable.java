package com.seitenbau.testing.dbunit.model;

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

import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;
import com.seitenbau.testing.dbunit.generator.Flags;
import com.seitenbau.testing.dbunit.model.JobsTable.RowGetters_Jobs;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowGetters_Persons;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowGetters_Teams;
import com.seitenbau.testing.util.date.DateBuilder;


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

  static Map<String, EnumSet<Flags>> GENERATOR_METADATA;
  static {
    GENERATOR_METADATA = new HashMap<String, EnumSet<Flags>>();
    GENERATOR_METADATA.put(Columns.Id,EnumSet.of( Flags.AutoInvokeNextIdMethod));
    GENERATOR_METADATA.put(Columns.Title,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.Description,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.Membersize,EnumSet.noneOf( Flags.class ));
  }
  // @formatter:on

  ITableMetaData _metaData;
  
  STUDataSet _dataSet;
  
  Iterator<RowBuilder_Teams> _iterator;
  
  public TeamsTable()
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

  public static class RowBuilder_Teams implements RowSetters_Teams<RowBuilder_Teams>, RowGetters_Teams<RowBuilder_Teams>
  {

    Object[] data;
    
    TeamsTable table;
    
    RowBuilder_Teams(TeamsTable tableDelegate) {
      data=new Object[COLUMNS.length];
      table = tableDelegate;
    }
    

    public RowBuilder_Teams setId(Integer intValue)
    {
      data[ 0 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Teams setId(java.lang.Long value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_Teams setIdRaw(Object value)
    {
      data[ 0 ] = value;
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
      return (java.lang.Long) data[0];
    }

    public RowBuilder_Teams setTitle(java.lang.String value)
    {
      data[ 1 ] = value;
      return this;
    }
    public RowBuilder_Teams setTitleRaw(Object value)
    {
      data[ 1 ] = value;
      return this;
    }

    public java.lang.String getTitle()
    {
      return (java.lang.String) data[1];
    }

    public RowBuilder_Teams setDescription(java.lang.String value)
    {
      data[ 2 ] = value;
      return this;
    }
    public RowBuilder_Teams setDescriptionRaw(Object value)
    {
      data[ 2 ] = value;
      return this;
    }

    public java.lang.String getDescription()
    {
      return (java.lang.String) data[2];
    }

    public RowBuilder_Teams setMembersize(Integer intValue)
    {
      data[ 3 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Teams setMembersize(java.lang.Long value)
    {
      data[ 3 ] = value;
      return this;
    }
    public RowBuilder_Teams setMembersizeRaw(Object value)
    {
      data[ 3 ] = value;
      return this;
    }

    public java.lang.Long getMembersize()
    {
      return (java.lang.Long) data[3];
    }
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
    public RowBuilder_Teams insertRow()
    {
      return table.insertRow();
    }
    
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
    public RowBuilder_Teams insertRow(TeamsModel row)
    {
      return table.insertRow(row);
    }
    
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
    public RowBuilder_Teams insertRowAt(int index)
    {
      return table.insertRowAt(index);
    }

    /**
    * Insert a row at the end of the table
    * <code><pre>
    * ds.table_Teams.insertRow(baseUser);
    * </pre></code>
    */
    public RowBuilder_Teams insertRow(RowBuilder_Teams theRow)
    {
      return table.insertRow(theRow);
    }
    
    public Object getValue(String column) throws RuntimeException {
      if(column.equalsIgnoreCase(Columns.Id) ) {
        return data[0];
      }
      if(column.equalsIgnoreCase(Columns.Title) ) {
        return data[1];
      }
      if(column.equalsIgnoreCase(Columns.Description) ) {
        return data[2];
      }
      if(column.equalsIgnoreCase(Columns.Membersize) ) {
        return data[3];
      }
      throw new RuntimeException(NAME + " col = " + column);
    }
    
    
    @Override
    public RowBuilder_Teams clone() {
      RowBuilder_Teams clone = new RowBuilder_Teams(table);
      clone.setId(getId());
      clone.setTitle(getTitle());
      clone.setDescription(getDescription());
      clone.setMembersize(getMembersize());
      return clone;
    }
  }
  
  public TeamsWhere findWhere = new TeamsWhere(this);

  public static class TeamsWhere
  {
    public List<RowBuilder_Teams> rows;
    TeamsTable table;
    
    public TeamsWhere(TeamsTable theTable) {
       rows = theTable.rows;
       table = theTable;
    }
    
    public RowCollection_Teams rowComparesTo(Comparable<RowBuilder_Teams> toSearch) {
      RowCollection_Teams modifiers = new RowCollection_Teams(table);
      for (RowBuilder_Teams row : rows) 
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
    public RowCollection_Teams id(java.lang.Long toSearch) {
      RowCollection_Teams modifiers = new RowCollection_Teams(table);
      for (RowBuilder_Teams row : rows) 
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
    public RowCollection_Teams id(Integer toSearch) 
    {
      return id( Long.valueOf(toSearch) );
    }
    public RowCollection_Teams title(java.lang.String toSearch) {
      RowCollection_Teams modifiers = new RowCollection_Teams(table);
      for (RowBuilder_Teams row : rows) 
      {
        if (row.getTitle().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with title = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Teams description(java.lang.String toSearch) {
      RowCollection_Teams modifiers = new RowCollection_Teams(table);
      for (RowBuilder_Teams row : rows) 
      {
        if (row.getDescription().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with description = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Teams membersize(java.lang.Long toSearch) {
      RowCollection_Teams modifiers = new RowCollection_Teams(table);
      for (RowBuilder_Teams row : rows) 
      {
        if (row.getMembersize().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with membersize = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Teams membersize(Integer toSearch) 
    {
      return membersize( Long.valueOf(toSearch) );
    }
  }
  
  /** Inner class! Use RowCollection_Teams in your code ! */
  public static class RowModify_Teams extends RowBuilder_Teams 
  {
      List<RowBuilder_Teams> _rows;

      public RowModify_Teams(TeamsTable theTable) {
        super(theTable);
        _rows = new ArrayList<RowBuilder_Teams>();
      }
      
      public void add(RowBuilder_Teams row) {
          _rows.add(row);
      }
      
      public boolean isEmpty() {
          return _rows.isEmpty();
      }

      public RowModify_Teams delete() {
          for(RowBuilder_Teams row : _rows) {
            table.rows.remove(row);
          }
          return this;
      }
        

      public RowModify_Teams setId(Integer intValue)
      {
        for(RowBuilder_Teams row : _rows) {
          row.setId(intValue);
        }
        return this;
      }

      public RowModify_Teams setId(java.lang.Long value)
      {
        for(RowBuilder_Teams row : _rows) {
          row.setId(value);
        }
        return this;
      }
      public RowModify_Teams setIdRaw(Object value)
      {
        for(RowBuilder_Teams row : _rows) {
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

      public RowModify_Teams setTitle(java.lang.String value)
      {
        for(RowBuilder_Teams row : _rows) {
          row.setTitle(value);
        }
        return this;
      }
      public RowModify_Teams setTitleRaw(Object value)
      {
        for(RowBuilder_Teams row : _rows) {
          row.setTitleRaw(value);
        }
        return this;
      }
      public java.lang.String getTitle()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getTitle();
      }

      public RowModify_Teams setDescription(java.lang.String value)
      {
        for(RowBuilder_Teams row : _rows) {
          row.setDescription(value);
        }
        return this;
      }
      public RowModify_Teams setDescriptionRaw(Object value)
      {
        for(RowBuilder_Teams row : _rows) {
          row.setDescriptionRaw(value);
        }
        return this;
      }
      public java.lang.String getDescription()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getDescription();
      }

      public RowModify_Teams setMembersize(Integer intValue)
      {
        for(RowBuilder_Teams row : _rows) {
          row.setMembersize(intValue);
        }
        return this;
      }

      public RowModify_Teams setMembersize(java.lang.Long value)
      {
        for(RowBuilder_Teams row : _rows) {
          row.setMembersize(value);
        }
        return this;
      }
      public RowModify_Teams setMembersizeRaw(Object value)
      {
        for(RowBuilder_Teams row : _rows) {
          row.setMembersizeRaw(value);
        }
        return this;
      }
      public java.lang.Long getMembersize()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getMembersize();
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
      public RowModify_Teams clone() {
        RowModify_Teams clone = new RowModify_Teams(table);
        for(RowBuilder_Teams row:_rows) {
          clone._rows.add(row.clone());
        }
        return clone;
      }

  }
  
  public static class RowCollection_Teams extends RowModify_Teams {
    public TeamsWhere where = new TeamsWhere(table);
    
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
    RowBuilder_Teams row = new RowBuilder_Teams(this);
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
    RowBuilder_Teams row = new RowBuilder_Teams(this);
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
    RowBuilder_Teams row = new RowBuilder_Teams(this);
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
    RowBuilder_Teams row = new RowBuilder_Teams(this);
    return row;
  }
  
  /**
  * Returns the next Object. The internal iterator is started at 
  * the first call.
  */
  public RowBuilder_Teams next()
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
