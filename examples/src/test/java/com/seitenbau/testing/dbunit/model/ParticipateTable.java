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
public class ParticipateTable implements ITable
{
  public final static String NAME = "participate";

  public static class Columns
  {
    public static final String StudentId = "student_id";
    public static final String LectureId = "lecture_id";
  }

  // @formatter:off
  public final static Column[] COLUMNS = new Column[] {
    // idx = 0
    new Column(Columns.StudentId, DataType.BIGINT),
    // idx = 1
    new Column(Columns.LectureId, DataType.BIGINT)
  };

  static Map<String, EnumSet<Flags>> GENERATOR_METADATA;
  static {
    GENERATOR_METADATA = new HashMap<String, EnumSet<Flags>>();
    GENERATOR_METADATA.put(Columns.StudentId,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.LectureId,EnumSet.noneOf( Flags.class ));
  }
  // @formatter:on

  ITableMetaData _metaData;
  
  STUExampleDBDataSet _dataSet;
  
  Iterator<RowBuilder_Participate> _iterator;
  
  public ParticipateTable()
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

  public List<RowBuilder_Participate> rows = new ArrayList<RowBuilder_Participate>();
  
  public interface RowSetters_Participate<T extends RowSetters_Participate>
  {
    T setStudentId(Integer intValue);
    T setStudentId(java.lang.Long value);
    T setStudentIdRaw(Object value);
    T setLectureId(Integer intValue);
    T setLectureId(java.lang.Long value);
    T setLectureIdRaw(Object value);
     
  }
  
  public interface RowGetters_Participate<T extends RowGetters_Participate>
  {
    java.lang.Long getStudentId();
    java.lang.Long getLectureId();
     
  }

  public static class RowBuilder_Participate implements RowSetters_Participate<RowBuilder_Participate>, RowGetters_Participate<RowBuilder_Participate>
  {

    Object[] data;
    
    ParticipateTable table;
    
    RowBuilder_Participate(ParticipateTable tableDelegate) {
      data=new Object[COLUMNS.length];
      table = tableDelegate;
    }
    

    public RowBuilder_Participate setStudentId(Integer intValue)
    {
      data[ 0 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Participate setStudentId(java.lang.Long value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_Participate setStudentIdRaw(Object value)
    {
      data[ 0 ] = value;
      return this;
    }

    public java.lang.Long getStudentId()
    {
      return (java.lang.Long) data[0];
    }

    public RowBuilder_Participate setLectureId(Integer intValue)
    {
      data[ 1 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Participate setLectureId(java.lang.Long value)
    {
      data[ 1 ] = value;
      return this;
    }
    public RowBuilder_Participate setLectureIdRaw(Object value)
    {
      data[ 1 ] = value;
      return this;
    }

    public java.lang.Long getLectureId()
    {
      return (java.lang.Long) data[1];
    }
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Participate.insertRow()
    *   .setStudentId( null )
    *   .setLectureId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Participate insertRow()
    {
      return table.insertRow();
    }
    
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Participate.insertRow()
    *   .setStudentId( null )
    *   .setLectureId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Participate insertRow(ParticipateModel row)
    {
      return table.insertRow(row);
    }
    
    /**
    * Insert a new Row at the given position
    * <code><pre>
    * ds.table_Participate.this.insertRowAt(2)
    *   .setStudentId( null )
    *   .setLectureId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Participate insertRowAt(int index)
    {
      return table.insertRowAt(index);
    }

    /**
    * Insert a row at the end of the table
    * <code><pre>
    * ds.table_Participate.insertRow(baseUser);
    * </pre></code>
    */
    public RowBuilder_Participate insertRow(RowBuilder_Participate theRow)
    {
      return table.insertRow(theRow);
    }
    
    public Object getValue(String column) throws RuntimeException {
      if(column.equalsIgnoreCase(Columns.StudentId) ) {
        return data[0];
      }
      if(column.equalsIgnoreCase(Columns.LectureId) ) {
        return data[1];
      }
      throw new RuntimeException(NAME + " col = " + column);
    }
    

    public RowBuilder_Participate refStudentId(RowGetters_Student reference)
    {
      setStudentId(reference.getStudentNumber());
      return this;
    }

    public RowBuilder_Participate refLectureId(RowGetters_Lecture reference)
    {
      setLectureId(reference.getId());
      return this;
    }
    
    @Override
    public RowBuilder_Participate clone() {
      RowBuilder_Participate clone = new RowBuilder_Participate(table);
      clone.setStudentId(getStudentId());
      clone.setLectureId(getLectureId());
      return clone;
    }
  }
  
  public ParticipateWhere findWhere = new ParticipateWhere(this);

  public static class ParticipateWhere
  {
    public List<RowBuilder_Participate> rows;
    ParticipateTable table;
    
    public ParticipateWhere(ParticipateTable theTable) {
       rows = theTable.rows;
       table = theTable;
    }
    
    public RowCollection_Participate rowComparesTo(Comparable<RowBuilder_Participate> toSearch) {
      RowCollection_Participate modifiers = new RowCollection_Participate(table);
      for (RowBuilder_Participate row : rows) 
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
    public RowCollection_Participate studentId(java.lang.Long toSearch) {
      RowCollection_Participate modifiers = new RowCollection_Participate(table);
      for (RowBuilder_Participate row : rows) 
      {
        if (row.getStudentId().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with student_id = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Participate studentId(Integer toSearch) 
    {
      return studentId( Long.valueOf(toSearch) );
    }
    public RowCollection_Participate lectureId(java.lang.Long toSearch) {
      RowCollection_Participate modifiers = new RowCollection_Participate(table);
      for (RowBuilder_Participate row : rows) 
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
    public RowCollection_Participate lectureId(Integer toSearch) 
    {
      return lectureId( Long.valueOf(toSearch) );
    }
  }
  
  /** Inner class! Use RowCollection_Participate in your code ! */
  public static class RowModify_Participate extends RowBuilder_Participate 
  {
      List<RowBuilder_Participate> _rows;

      public RowModify_Participate(ParticipateTable theTable) {
        super(theTable);
        _rows = new ArrayList<RowBuilder_Participate>();
      }
      
      public void add(RowBuilder_Participate row) {
          _rows.add(row);
      }
      
      public boolean isEmpty() {
          return _rows.isEmpty();
      }

      public RowModify_Participate delete() {
          for(RowBuilder_Participate row : _rows) {
            table.rows.remove(row);
          }
          return this;
      }
        

      public RowModify_Participate setStudentId(Integer intValue)
      {
        for(RowBuilder_Participate row : _rows) {
          row.setStudentId(intValue);
        }
        return this;
      }

      public RowModify_Participate setStudentId(java.lang.Long value)
      {
        for(RowBuilder_Participate row : _rows) {
          row.setStudentId(value);
        }
        return this;
      }
      public RowModify_Participate setStudentIdRaw(Object value)
      {
        for(RowBuilder_Participate row : _rows) {
          row.setStudentIdRaw(value);
        }
        return this;
      }
      public java.lang.Long getStudentId()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getStudentId();
      }

      public RowModify_Participate setLectureId(Integer intValue)
      {
        for(RowBuilder_Participate row : _rows) {
          row.setLectureId(intValue);
        }
        return this;
      }

      public RowModify_Participate setLectureId(java.lang.Long value)
      {
        for(RowBuilder_Participate row : _rows) {
          row.setLectureId(value);
        }
        return this;
      }
      public RowModify_Participate setLectureIdRaw(Object value)
      {
        for(RowBuilder_Participate row : _rows) {
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
      public RowModify_Participate clone() {
        RowModify_Participate clone = new RowModify_Participate(table);
        for(RowBuilder_Participate row:_rows) {
          clone._rows.add(row.clone());
        }
        return clone;
      }

  }
  
  public static class RowCollection_Participate extends RowModify_Participate {
    public ParticipateWhere where = new ParticipateWhere(table);
    
    public RowCollection_Participate(ParticipateTable theTable)
    {
      super(theTable);
    }
    
  }

  /**
  * Insert a new empty Row.
  * <code><pre>
  * ds.table_Participate.insertRow()
  *   .setStudentId( null )
  *   .setLectureId( null )
  *   ;
  * </pre></code>
  */
  public RowBuilder_Participate insertRow()
  {
    RowBuilder_Participate row = new RowBuilder_Participate(this);
    rows.add(row);
    return row;
  }
  
  /**
  * <code><pre>
  * ds.table_Participate.insertRow()
  * </pre></code>
  */
  public RowBuilder_Participate insertRow(ParticipateModel rowToAdd)
  {
    RowBuilder_Participate row = new RowBuilder_Participate(this);
    row.setStudentIdRaw( rowToAdd.getStudentIdRaw() );
    row.setLectureIdRaw( rowToAdd.getLectureIdRaw() );
    rows.add(row);
    return row;
  }

  /**
  * <code><pre>
  * ds.table_Participate.insertRow(data);
  * </pre></code>
  */
  public RowBuilder_Participate insertRow(RowBuilder_Participate theRow)
  {
    rows.add(theRow);
    return theRow;
  }
  
  /**
  * <code><pre>
  * ds.table_Participate.insertRows(data);
  * </pre></code>
  */
  public void insertRows(RowBuilder_Participate...theRows)
  {
    rows.addAll(Arrays.asList(theRows));
  }
  
  /**
  * Insert new row at the given index
  * <code><pre>
  * ds.table_Participate.insertRowAt(3)
  *   ;
  * </pre></code>
  */
  public RowBuilder_Participate insertRowAt(int index)
  {
    RowBuilder_Participate row = new RowBuilder_Participate(this);
    rows.add(index, row);
    return row;
  }
  
  /**
  * Insert new row Object at the given index
  * <code><pre>
  * ds.table_Participate.insertRowAt(3)
  * </pre></code>
  */
  public RowBuilder_Participate insertRowAt(int index,RowBuilder_Participate theRow)
  {
    rows.add(index, theRow);
    return theRow;
  }
  
  /**
   * Remove a row from the builder by the given index.
   *
   * @return the deleted row
   */ 
  public RowBuilder_Participate deleteRow(int index)
  {
    RowBuilder_Participate rowBuilder = rows.get(index);
    rows.remove(rowBuilder);
    return rowBuilder;
  }
  
  /**
   * Remove a row from the builder
   */ 
  public RowBuilder_Participate deleteRow(RowBuilder_Participate rowToDelete)
  {
    rows.remove(rowToDelete);
    return rowToDelete;
  }

  /**
  * Creates a new row but does not add it to the table
  */
  public RowBuilder_Participate newRow()
  {
    RowBuilder_Participate row = new RowBuilder_Participate(this);
    return row;
  }
  
  /**
  * Returns the next Object. The internal iterator is started at 
  * the first call.
  */
  public RowBuilder_Participate next()
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
