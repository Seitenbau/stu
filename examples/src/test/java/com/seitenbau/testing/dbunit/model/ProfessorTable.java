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
import com.seitenbau.testing.dbunit.model.AttendTable.RowGetters_Attend;
import com.seitenbau.testing.dbunit.model.ExamTable.RowGetters_Exam;
import com.seitenbau.testing.dbunit.model.GiveLectureTable.RowGetters_GiveLecture;
import com.seitenbau.testing.dbunit.model.IsTutorTable.RowGetters_IsTutor;
import com.seitenbau.testing.dbunit.model.LectureTable.RowGetters_Lecture;
import com.seitenbau.testing.dbunit.model.ParticipateTable.RowGetters_Participate;
import com.seitenbau.testing.dbunit.model.ProfessorTable.RowGetters_Professor;
import com.seitenbau.testing.dbunit.model.StudentTable.RowGetters_Student;
import com.seitenbau.testing.util.date.DateBuilder;


import static com.seitenbau.testing.util.DateUtil.*;

/* *******************************************************
  Generated via : codegeneration.GenerateDatabaseClasses
**********************************************************/
public class ProfessorTable implements ITable
{
  public final static String NAME = "PROFESSOR";

  public static class Columns
  {
    public static final String Id = "id";
    public static final String Name = "name";
    public static final String FirstName = "first_name";
    public static final String Title = "title";
    public static final String Faculty = "faculty";
  }

  // @formatter:off
  public final static Column[] COLUMNS = new Column[] {
    // idx = 0
    new Column(Columns.Id, DataType.BIGINT),
    // idx = 1
    new Column(Columns.Name, DataType.VARCHAR),
    // idx = 2
    new Column(Columns.FirstName, DataType.VARCHAR),
    // idx = 3
    new Column(Columns.Title, DataType.VARCHAR),
    // idx = 4
    new Column(Columns.Faculty, DataType.VARCHAR)
  };

  static Map<String, EnumSet<Flags>> GENERATOR_METADATA;
  static {
    GENERATOR_METADATA = new HashMap<String, EnumSet<Flags>>();
    GENERATOR_METADATA.put(Columns.Id,EnumSet.of( Flags.AutoInvokeNextIdMethod));
    GENERATOR_METADATA.put(Columns.Name,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.FirstName,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.Title,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.Faculty,EnumSet.noneOf( Flags.class ));
  }
  // @formatter:on

  ITableMetaData _metaData;
  
  STUExampleDBDataSet _dataSet;
  
  Iterator<RowBuilder_Professor> _iterator;
  
  public ProfessorTable()
  {
    _metaData=new DefaultTableMetaData(NAME, COLUMNS);
  }

  public void setDataset(STUExampleDBDataSet dataSet)
  {
    _dataSet=dataSet;
  }
  
  public STUExampleDBDataSet getDataset()
  {
    return _dataSet;
  }

  public List<RowBuilder_Professor> rows = new ArrayList<RowBuilder_Professor>();
  
  public interface RowSetters_Professor<T extends RowSetters_Professor>
  {
    T setId(Integer intValue);
    T setId(java.lang.Long value);
    T setIdRaw(Object value);
    T nextId();
    T setName(java.lang.String value);
    T setNameRaw(Object value);
    T setFirstName(java.lang.String value);
    T setFirstNameRaw(Object value);
    T setTitle(java.lang.String value);
    T setTitleRaw(Object value);
    T setFaculty(java.lang.String value);
    T setFacultyRaw(Object value);
     
  }
  
  public interface RowGetters_Professor<T extends RowGetters_Professor>
  {
    java.lang.Long getId();
    java.lang.String getName();
    java.lang.String getFirstName();
    java.lang.String getTitle();
    java.lang.String getFaculty();
     
  }

  public static class RowBuilder_Professor implements RowSetters_Professor<RowBuilder_Professor>, RowGetters_Professor<RowBuilder_Professor>
  {

    Object[] data;
    
    ProfessorTable table;
    
    RowBuilder_Professor(ProfessorTable tableDelegate) {
      data=new Object[COLUMNS.length];
      table = tableDelegate;
    }
    

    public RowBuilder_Professor setId(Integer intValue)
    {
      data[ 0 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Professor setId(java.lang.Long value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_Professor setIdRaw(Object value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_Professor nextId()
    {
      DatasetIdGenerator generator = table.getDataset().getIdGenerator();
      Long nextId = generator.nextId(ProfessorTable.NAME,"id");
      setId(nextId);
      return this;
    }

    public java.lang.Long getId()
    {
      return (java.lang.Long) data[0];
    }

    public RowBuilder_Professor setName(java.lang.String value)
    {
      data[ 1 ] = value;
      return this;
    }
    public RowBuilder_Professor setNameRaw(Object value)
    {
      data[ 1 ] = value;
      return this;
    }

    public java.lang.String getName()
    {
      return (java.lang.String) data[1];
    }

    public RowBuilder_Professor setFirstName(java.lang.String value)
    {
      data[ 2 ] = value;
      return this;
    }
    public RowBuilder_Professor setFirstNameRaw(Object value)
    {
      data[ 2 ] = value;
      return this;
    }

    public java.lang.String getFirstName()
    {
      return (java.lang.String) data[2];
    }

    public RowBuilder_Professor setTitle(java.lang.String value)
    {
      data[ 3 ] = value;
      return this;
    }
    public RowBuilder_Professor setTitleRaw(Object value)
    {
      data[ 3 ] = value;
      return this;
    }

    public java.lang.String getTitle()
    {
      return (java.lang.String) data[3];
    }

    public RowBuilder_Professor setFaculty(java.lang.String value)
    {
      data[ 4 ] = value;
      return this;
    }
    public RowBuilder_Professor setFacultyRaw(Object value)
    {
      data[ 4 ] = value;
      return this;
    }

    public java.lang.String getFaculty()
    {
      return (java.lang.String) data[4];
    }
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Professor.insertRow()
    *   .setId( null )
    *   .setName( null )
    *   .setFirstName( null )
    *   .setTitle( null )
    *   .setFaculty( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Professor insertRow()
    {
      return table.insertRow();
    }
    
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Professor.insertRow()
    *   .setId( null )
    *   .setName( null )
    *   .setFirstName( null )
    *   .setTitle( null )
    *   .setFaculty( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Professor insertRow(ProfessorModel row)
    {
      return table.insertRow(row);
    }
    
    /**
    * Insert a new Row at the given position
    * <code><pre>
    * ds.table_Professor.this.insertRowAt(2)
    *   .setId( null )
    *   .setName( null )
    *   .setFirstName( null )
    *   .setTitle( null )
    *   .setFaculty( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Professor insertRowAt(int index)
    {
      return table.insertRowAt(index);
    }

    /**
    * Insert a row at the end of the table
    * <code><pre>
    * ds.table_Professor.insertRow(baseUser);
    * </pre></code>
    */
    public RowBuilder_Professor insertRow(RowBuilder_Professor theRow)
    {
      return table.insertRow(theRow);
    }
    
    public Object getValue(String column) throws RuntimeException {
      if(column.equalsIgnoreCase(Columns.Id) ) {
        return data[0];
      }
      if(column.equalsIgnoreCase(Columns.Name) ) {
        return data[1];
      }
      if(column.equalsIgnoreCase(Columns.FirstName) ) {
        return data[2];
      }
      if(column.equalsIgnoreCase(Columns.Title) ) {
        return data[3];
      }
      if(column.equalsIgnoreCase(Columns.Faculty) ) {
        return data[4];
      }
      throw new RuntimeException(NAME + " col = " + column);
    }
    
    
    @Override
    public RowBuilder_Professor clone() {
      RowBuilder_Professor clone = new RowBuilder_Professor(table);
      clone.setId(getId());
      clone.setName(getName());
      clone.setFirstName(getFirstName());
      clone.setTitle(getTitle());
      clone.setFaculty(getFaculty());
      return clone;
    }
  }
  
  public ProfessorWhere findWhere = new ProfessorWhere(this);

  public static class ProfessorWhere
  {
    public List<RowBuilder_Professor> rows;
    ProfessorTable table;
    
    public ProfessorWhere(ProfessorTable theTable) {
       rows = theTable.rows;
       table = theTable;
    }
    
    public RowCollection_Professor rowComparesTo(Comparable<RowBuilder_Professor> toSearch) {
      RowCollection_Professor modifiers = new RowCollection_Professor(table);
      for (RowBuilder_Professor row : rows) 
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
    public RowCollection_Professor id(java.lang.Long toSearch) {
      RowCollection_Professor modifiers = new RowCollection_Professor(table);
      for (RowBuilder_Professor row : rows) 
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
    public RowCollection_Professor id(Integer toSearch) 
    {
      return id( Long.valueOf(toSearch) );
    }
    public RowCollection_Professor name(java.lang.String toSearch) {
      RowCollection_Professor modifiers = new RowCollection_Professor(table);
      for (RowBuilder_Professor row : rows) 
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
    public RowCollection_Professor firstName(java.lang.String toSearch) {
      RowCollection_Professor modifiers = new RowCollection_Professor(table);
      for (RowBuilder_Professor row : rows) 
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
    public RowCollection_Professor title(java.lang.String toSearch) {
      RowCollection_Professor modifiers = new RowCollection_Professor(table);
      for (RowBuilder_Professor row : rows) 
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
    public RowCollection_Professor faculty(java.lang.String toSearch) {
      RowCollection_Professor modifiers = new RowCollection_Professor(table);
      for (RowBuilder_Professor row : rows) 
      {
        if (row.getFaculty().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with faculty = " + toSearch );
      }
      return modifiers;
    }
  }
  
  /** Inner class! Use RowCollection_Professor in your code ! */
  public static class RowModify_Professor extends RowBuilder_Professor 
  {
      List<RowBuilder_Professor> _rows;

      public RowModify_Professor(ProfessorTable theTable) {
        super(theTable);
        _rows = new ArrayList<RowBuilder_Professor>();
      }
      
      public void add(RowBuilder_Professor row) {
          _rows.add(row);
      }
      
      public boolean isEmpty() {
          return _rows.isEmpty();
      }

      public RowModify_Professor delete() {
          for(RowBuilder_Professor row : _rows) {
            table.rows.remove(row);
          }
          return this;
      }
        

      public RowModify_Professor setId(Integer intValue)
      {
        for(RowBuilder_Professor row : _rows) {
          row.setId(intValue);
        }
        return this;
      }

      public RowModify_Professor setId(java.lang.Long value)
      {
        for(RowBuilder_Professor row : _rows) {
          row.setId(value);
        }
        return this;
      }
      public RowModify_Professor setIdRaw(Object value)
      {
        for(RowBuilder_Professor row : _rows) {
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

      public RowModify_Professor setName(java.lang.String value)
      {
        for(RowBuilder_Professor row : _rows) {
          row.setName(value);
        }
        return this;
      }
      public RowModify_Professor setNameRaw(Object value)
      {
        for(RowBuilder_Professor row : _rows) {
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

      public RowModify_Professor setFirstName(java.lang.String value)
      {
        for(RowBuilder_Professor row : _rows) {
          row.setFirstName(value);
        }
        return this;
      }
      public RowModify_Professor setFirstNameRaw(Object value)
      {
        for(RowBuilder_Professor row : _rows) {
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

      public RowModify_Professor setTitle(java.lang.String value)
      {
        for(RowBuilder_Professor row : _rows) {
          row.setTitle(value);
        }
        return this;
      }
      public RowModify_Professor setTitleRaw(Object value)
      {
        for(RowBuilder_Professor row : _rows) {
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

      public RowModify_Professor setFaculty(java.lang.String value)
      {
        for(RowBuilder_Professor row : _rows) {
          row.setFaculty(value);
        }
        return this;
      }
      public RowModify_Professor setFacultyRaw(Object value)
      {
        for(RowBuilder_Professor row : _rows) {
          row.setFacultyRaw(value);
        }
        return this;
      }
      public java.lang.String getFaculty()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getFaculty();
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
      public RowModify_Professor clone() {
        RowModify_Professor clone = new RowModify_Professor(table);
        for(RowBuilder_Professor row:_rows) {
          clone._rows.add(row.clone());
        }
        return clone;
      }

  }
  
  public static class RowCollection_Professor extends RowModify_Professor {
    public ProfessorWhere where = new ProfessorWhere(table);
    
    public RowCollection_Professor(ProfessorTable theTable)
    {
      super(theTable);
    }
    
  }

  /**
  * Insert a new empty Row.
  * <code><pre>
  * ds.table_Professor.insertRow()
  *   .setId( null )
  *   .setName( null )
  *   .setFirstName( null )
  *   .setTitle( null )
  *   .setFaculty( null )
  *   ;
  * </pre></code>
  */
  public RowBuilder_Professor insertRow()
  {
    RowBuilder_Professor row = new RowBuilder_Professor(this);
    row.nextId();
    rows.add(row);
    return row;
  }
  
  /**
  * <code><pre>
  * ds.table_Professor.insertRow()
  * </pre></code>
  */
  public RowBuilder_Professor insertRow(ProfessorModel rowToAdd)
  {
    RowBuilder_Professor row = new RowBuilder_Professor(this);
    row.setIdRaw( rowToAdd.getIdRaw() );
    row.setNameRaw( rowToAdd.getNameRaw() );
    row.setFirstNameRaw( rowToAdd.getFirstNameRaw() );
    row.setTitleRaw( rowToAdd.getTitleRaw() );
    row.setFacultyRaw( rowToAdd.getFacultyRaw() );
    rows.add(row);
    return row;
  }

  /**
  * <code><pre>
  * ds.table_Professor.insertRow(data);
  * </pre></code>
  */
  public RowBuilder_Professor insertRow(RowBuilder_Professor theRow)
  {
    rows.add(theRow);
    return theRow;
  }
  
  /**
  * <code><pre>
  * ds.table_Professor.insertRows(data);
  * </pre></code>
  */
  public void insertRows(RowBuilder_Professor...theRows)
  {
    rows.addAll(Arrays.asList(theRows));
  }
  
  /**
  * Insert new row at the given index
  * <code><pre>
  * ds.table_Professor.insertRowAt(3)
  *   ;
  * </pre></code>
  */
  public RowBuilder_Professor insertRowAt(int index)
  {
    RowBuilder_Professor row = new RowBuilder_Professor(this);
    rows.add(index, row);
    return row;
  }
  
  /**
  * Insert new row Object at the given index
  * <code><pre>
  * ds.table_Professor.insertRowAt(3)
  * </pre></code>
  */
  public RowBuilder_Professor insertRowAt(int index,RowBuilder_Professor theRow)
  {
    rows.add(index, theRow);
    return theRow;
  }
  
  /**
   * Remove a row from the builder by the given index.
   *
   * @return the deleted row
   */ 
  public RowBuilder_Professor deleteRow(int index)
  {
    RowBuilder_Professor rowBuilder = rows.get(index);
    rows.remove(rowBuilder);
    return rowBuilder;
  }
  
  /**
   * Remove a row from the builder
   */ 
  public RowBuilder_Professor deleteRow(RowBuilder_Professor rowToDelete)
  {
    rows.remove(rowToDelete);
    return rowToDelete;
  }

  /**
  * Creates a new row but does not add it to the table
  */
  public RowBuilder_Professor newRow()
  {
    RowBuilder_Professor row = new RowBuilder_Professor(this);
    return row;
  }
  
  /**
  * Returns the next Object. The internal iterator is started at 
  * the first call.
  */
  public RowBuilder_Professor next()
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
