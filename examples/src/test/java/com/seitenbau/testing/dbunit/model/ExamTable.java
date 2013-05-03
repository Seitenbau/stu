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
public class ExamTable implements ITable
{
  public final static String NAME = "exam";

  public static class Columns
  {
    public static final String Id = "id";
    public static final String LectureId = "lecture_id";
    public static final String LectureType = "lecture_type";
    public static final String PointInTime = "point_in_time";
  }

  // @formatter:off
  public final static Column[] COLUMNS = new Column[] {
    // idx = 0
    new Column(Columns.Id, DataType.BIGINT),
    // idx = 1
    new Column(Columns.LectureId, DataType.BIGINT),
    // idx = 2
    new Column(Columns.LectureType, DataType.VARCHAR),
    // idx = 3
    new Column(Columns.PointInTime, DataType.DATE)
  };

  static Map<String, EnumSet<Flags>> GENERATOR_METADATA;
  static {
    GENERATOR_METADATA = new HashMap<String, EnumSet<Flags>>();
    GENERATOR_METADATA.put(Columns.Id,EnumSet.of( Flags.AutoInvokeNextIdMethod));
    GENERATOR_METADATA.put(Columns.LectureId,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.LectureType,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.PointInTime,EnumSet.noneOf( Flags.class ));
  }
  // @formatter:on

  ITableMetaData _metaData;
  
  STUExampleDBDataSet _dataSet;
  
  Iterator<RowBuilder_Exam> _iterator;
  
  public ExamTable()
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

  public List<RowBuilder_Exam> rows = new ArrayList<RowBuilder_Exam>();
  
  public interface RowSetters_Exam<T extends RowSetters_Exam>
  {
    T setId(Integer intValue);
    T setId(java.lang.Long value);
    T setIdRaw(Object value);
    T nextId();
    T setLectureId(Integer intValue);
    T setLectureId(java.lang.Long value);
    T setLectureIdRaw(Object value);
    T setLectureType(java.lang.String value);
    T setLectureTypeRaw(Object value);
    T setPointInTime(String dateString);
    T setPointInTime(DateBuilder datum);
    T setPointInTime(java.util.Date value);
    T setPointInTimeRaw(Object value);
     
  }
  
  public interface RowGetters_Exam<T extends RowGetters_Exam>
  {
    java.lang.Long getId();
    java.lang.Long getLectureId();
    java.lang.String getLectureType();
    java.util.Date getPointInTime();
     
  }

  public static class RowBuilder_Exam implements RowSetters_Exam<RowBuilder_Exam>, RowGetters_Exam<RowBuilder_Exam>
  {

    Object[] data;
    
    ExamTable table;
    
    RowBuilder_Exam(ExamTable tableDelegate) {
      data=new Object[COLUMNS.length];
      table = tableDelegate;
    }
    

    public RowBuilder_Exam setId(Integer intValue)
    {
      data[ 0 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Exam setId(java.lang.Long value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_Exam setIdRaw(Object value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_Exam nextId()
    {
      DatasetIdGenerator generator = table.getDataset().getIdGenerator();
      Long nextId = generator.nextId(ExamTable.NAME,"id");
      setId(nextId);
      return this;
    }

    public java.lang.Long getId()
    {
      return (java.lang.Long) data[0];
    }

    public RowBuilder_Exam setLectureId(Integer intValue)
    {
      data[ 1 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Exam setLectureId(java.lang.Long value)
    {
      data[ 1 ] = value;
      return this;
    }
    public RowBuilder_Exam setLectureIdRaw(Object value)
    {
      data[ 1 ] = value;
      return this;
    }

    public java.lang.Long getLectureId()
    {
      return (java.lang.Long) data[1];
    }

    public RowBuilder_Exam setLectureType(java.lang.String value)
    {
      data[ 2 ] = value;
      return this;
    }
    public RowBuilder_Exam setLectureTypeRaw(Object value)
    {
      data[ 2 ] = value;
      return this;
    }

    public java.lang.String getLectureType()
    {
      return (java.lang.String) data[2];
    }

    public RowBuilder_Exam setPointInTime(String dateString)
    {
      data[ 3 ] = toDate(dateString);
      return this;
    }
    public RowBuilder_Exam setPointInTime(DateBuilder datum)
    {
      data[ 3 ] = toDate(datum);
      return this;
    }
    public RowBuilder_Exam setPointInTime(java.util.Date value)
    {
      data[ 3 ] = value;
      return this;
    }
    public RowBuilder_Exam setPointInTimeRaw(Object value)
    {
      data[ 3 ] = value;
      return this;
    }

    public java.util.Date getPointInTime()
    {
      return (java.util.Date) data[3];
    }
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Exam.insertRow()
    *   .setId( null )
    *   .setLectureId( null )
    *   .setLectureType( null )
    *   .setPointInTime( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Exam insertRow()
    {
      return table.insertRow();
    }
    
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Exam.insertRow()
    *   .setId( null )
    *   .setLectureId( null )
    *   .setLectureType( null )
    *   .setPointInTime( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Exam insertRow(ExamModel row)
    {
      return table.insertRow(row);
    }
    
    /**
    * Insert a new Row at the given position
    * <code><pre>
    * ds.table_Exam.this.insertRowAt(2)
    *   .setId( null )
    *   .setLectureId( null )
    *   .setLectureType( null )
    *   .setPointInTime( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Exam insertRowAt(int index)
    {
      return table.insertRowAt(index);
    }

    /**
    * Insert a row at the end of the table
    * <code><pre>
    * ds.table_Exam.insertRow(baseUser);
    * </pre></code>
    */
    public RowBuilder_Exam insertRow(RowBuilder_Exam theRow)
    {
      return table.insertRow(theRow);
    }
    
    public Object getValue(String column) throws RuntimeException {
      if(column.equalsIgnoreCase(Columns.Id) ) {
        return data[0];
      }
      if(column.equalsIgnoreCase(Columns.LectureId) ) {
        return data[1];
      }
      if(column.equalsIgnoreCase(Columns.LectureType) ) {
        return data[2];
      }
      if(column.equalsIgnoreCase(Columns.PointInTime) ) {
        return data[3];
      }
      throw new RuntimeException(NAME + " col = " + column);
    }
    

    public RowBuilder_Exam refLectureId(RowGetters_Lecture reference)
    {
      setLectureId(reference.getId());
      return this;
    }
    
    @Override
    public RowBuilder_Exam clone() {
      RowBuilder_Exam clone = new RowBuilder_Exam(table);
      clone.setId(getId());
      clone.setLectureId(getLectureId());
      clone.setLectureType(getLectureType());
      clone.setPointInTime(getPointInTime());
      return clone;
    }
  }
  
  public ExamWhere findWhere = new ExamWhere(this);

  public static class ExamWhere
  {
    public List<RowBuilder_Exam> rows;
    ExamTable table;
    
    public ExamWhere(ExamTable theTable) {
       rows = theTable.rows;
       table = theTable;
    }
    
    public RowCollection_Exam rowComparesTo(Comparable<RowBuilder_Exam> toSearch) {
      RowCollection_Exam modifiers = new RowCollection_Exam(table);
      for (RowBuilder_Exam row : rows) 
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
    public RowCollection_Exam id(java.lang.Long toSearch) {
      RowCollection_Exam modifiers = new RowCollection_Exam(table);
      for (RowBuilder_Exam row : rows) 
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
    public RowCollection_Exam id(Integer toSearch) 
    {
      return id( Long.valueOf(toSearch) );
    }
    public RowCollection_Exam lectureId(java.lang.Long toSearch) {
      RowCollection_Exam modifiers = new RowCollection_Exam(table);
      for (RowBuilder_Exam row : rows) 
      {
        if (row.getLectureId().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with lecture_id = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Exam lectureId(Integer toSearch) 
    {
      return lectureId( Long.valueOf(toSearch) );
    }
    public RowCollection_Exam lectureType(java.lang.String toSearch) {
      RowCollection_Exam modifiers = new RowCollection_Exam(table);
      for (RowBuilder_Exam row : rows) 
      {
        if (row.getLectureType().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with lecture_type = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Exam pointInTime(java.util.Date toSearch) {
      RowCollection_Exam modifiers = new RowCollection_Exam(table);
      for (RowBuilder_Exam row : rows) 
      {
        if (row.getPointInTime().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with point_in_time = " + toSearch );
      }
      return modifiers;
    }
  }
  
  /** Inner class! Use RowCollection_Exam in your code ! */
  public static class RowModify_Exam extends RowBuilder_Exam 
  {
      List<RowBuilder_Exam> _rows;

      public RowModify_Exam(ExamTable theTable) {
        super(theTable);
        _rows = new ArrayList<RowBuilder_Exam>();
      }
      
      public void add(RowBuilder_Exam row) {
          _rows.add(row);
      }
      
      public boolean isEmpty() {
          return _rows.isEmpty();
      }

      public RowModify_Exam delete() {
          for(RowBuilder_Exam row : _rows) {
            table.rows.remove(row);
          }
          return this;
      }
        

      public RowModify_Exam setId(Integer intValue)
      {
        for(RowBuilder_Exam row : _rows) {
          row.setId(intValue);
        }
        return this;
      }

      public RowModify_Exam setId(java.lang.Long value)
      {
        for(RowBuilder_Exam row : _rows) {
          row.setId(value);
        }
        return this;
      }
      public RowModify_Exam setIdRaw(Object value)
      {
        for(RowBuilder_Exam row : _rows) {
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

      public RowModify_Exam setLectureId(Integer intValue)
      {
        for(RowBuilder_Exam row : _rows) {
          row.setLectureId(intValue);
        }
        return this;
      }

      public RowModify_Exam setLectureId(java.lang.Long value)
      {
        for(RowBuilder_Exam row : _rows) {
          row.setLectureId(value);
        }
        return this;
      }
      public RowModify_Exam setLectureIdRaw(Object value)
      {
        for(RowBuilder_Exam row : _rows) {
          row.setLectureIdRaw(value);
        }
        return this;
      }
      public java.lang.Long getLectureId()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getLectureId();
      }

      public RowModify_Exam setLectureType(java.lang.String value)
      {
        for(RowBuilder_Exam row : _rows) {
          row.setLectureType(value);
        }
        return this;
      }
      public RowModify_Exam setLectureTypeRaw(Object value)
      {
        for(RowBuilder_Exam row : _rows) {
          row.setLectureTypeRaw(value);
        }
        return this;
      }
      public java.lang.String getLectureType()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getLectureType();
      }

      public RowModify_Exam setPointInTime(String dateString)
      {
        for(RowBuilder_Exam row : _rows) {
          row.setPointInTime(dateString);
        }
        return this;
      }
      public RowModify_Exam setPointInTime(DateBuilder datum)
      {
        for(RowBuilder_Exam row : _rows) {
          row.setPointInTime(datum);
        }
        return this;
      }
      public RowModify_Exam setPointInTime(java.util.Date value)
      {
        for(RowBuilder_Exam row : _rows) {
          row.setPointInTime(value);
        }
        return this;
      }
      public RowModify_Exam setPointInTimeRaw(Object value)
      {
        for(RowBuilder_Exam row : _rows) {
          row.setPointInTimeRaw(value);
        }
        return this;
      }
      public java.util.Date getPointInTime()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getPointInTime();
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
      public RowModify_Exam clone() {
        RowModify_Exam clone = new RowModify_Exam(table);
        for(RowBuilder_Exam row:_rows) {
          clone._rows.add(row.clone());
        }
        return clone;
      }

  }
  
  public static class RowCollection_Exam extends RowModify_Exam {
    public ExamWhere where = new ExamWhere(table);
    
    public RowCollection_Exam(ExamTable theTable)
    {
      super(theTable);
    }
    
  }

  /**
  * Insert a new empty Row.
  * <code><pre>
  * ds.table_Exam.insertRow()
  *   .setId( null )
  *   .setLectureId( null )
  *   .setLectureType( null )
  *   .setPointInTime( null )
  *   ;
  * </pre></code>
  */
  public RowBuilder_Exam insertRow()
  {
    RowBuilder_Exam row = new RowBuilder_Exam(this);
    row.nextId();
    rows.add(row);
    return row;
  }
  
  /**
  * <code><pre>
  * ds.table_Exam.insertRow()
  * </pre></code>
  */
  public RowBuilder_Exam insertRow(ExamModel rowToAdd)
  {
    RowBuilder_Exam row = new RowBuilder_Exam(this);
    row.setIdRaw( rowToAdd.getIdRaw() );
    row.setLectureIdRaw( rowToAdd.getLectureIdRaw() );
    row.setLectureTypeRaw( rowToAdd.getLectureTypeRaw() );
    row.setPointInTimeRaw( rowToAdd.getPointInTimeRaw() );
    rows.add(row);
    return row;
  }

  /**
  * <code><pre>
  * ds.table_Exam.insertRow(data);
  * </pre></code>
  */
  public RowBuilder_Exam insertRow(RowBuilder_Exam theRow)
  {
    rows.add(theRow);
    return theRow;
  }
  
  /**
  * <code><pre>
  * ds.table_Exam.insertRows(data);
  * </pre></code>
  */
  public void insertRows(RowBuilder_Exam...theRows)
  {
    rows.addAll(Arrays.asList(theRows));
  }
  
  /**
  * Insert new row at the given index
  * <code><pre>
  * ds.table_Exam.insertRowAt(3)
  *   ;
  * </pre></code>
  */
  public RowBuilder_Exam insertRowAt(int index)
  {
    RowBuilder_Exam row = new RowBuilder_Exam(this);
    rows.add(index, row);
    return row;
  }
  
  /**
  * Insert new row Object at the given index
  * <code><pre>
  * ds.table_Exam.insertRowAt(3)
  * </pre></code>
  */
  public RowBuilder_Exam insertRowAt(int index,RowBuilder_Exam theRow)
  {
    rows.add(index, theRow);
    return theRow;
  }
  
  /**
   * Remove a row from the builder by the given index.
   *
   * @return the deleted row
   */ 
  public RowBuilder_Exam deleteRow(int index)
  {
    RowBuilder_Exam rowBuilder = rows.get(index);
    rows.remove(rowBuilder);
    return rowBuilder;
  }
  
  /**
   * Remove a row from the builder
   */ 
  public RowBuilder_Exam deleteRow(RowBuilder_Exam rowToDelete)
  {
    rows.remove(rowToDelete);
    return rowToDelete;
  }

  /**
  * Creates a new row but does not add it to the table
  */
  public RowBuilder_Exam newRow()
  {
    RowBuilder_Exam row = new RowBuilder_Exam(this);
    return row;
  }
  
  /**
  * Returns the next Object. The internal iterator is started at 
  * the first call.
  */
  public RowBuilder_Exam next()
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
