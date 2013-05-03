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
public class AttendTable implements ITable
{
  public final static String NAME = "attend";

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
  
  Iterator<RowBuilder_Attend> _iterator;
  
  public AttendTable()
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

  public List<RowBuilder_Attend> rows = new ArrayList<RowBuilder_Attend>();
  
  public interface RowSetters_Attend<T extends RowSetters_Attend>
  {
    T setStudentId(Integer intValue);
    T setStudentId(java.lang.Long value);
    T setStudentIdRaw(Object value);
    T setLectureId(Integer intValue);
    T setLectureId(java.lang.Long value);
    T setLectureIdRaw(Object value);
     
  }
  
  public interface RowGetters_Attend<T extends RowGetters_Attend>
  {
    java.lang.Long getStudentId();
    java.lang.Long getLectureId();
     
  }

  public static class RowBuilder_Attend implements RowSetters_Attend<RowBuilder_Attend>, RowGetters_Attend<RowBuilder_Attend>
  {

    Object[] data;
    
    AttendTable table;
    
    RowBuilder_Attend(AttendTable tableDelegate) {
      data=new Object[COLUMNS.length];
      table = tableDelegate;
    }
    

    public RowBuilder_Attend setStudentId(Integer intValue)
    {
      data[ 0 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Attend setStudentId(java.lang.Long value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_Attend setStudentIdRaw(Object value)
    {
      data[ 0 ] = value;
      return this;
    }

    public java.lang.Long getStudentId()
    {
      return (java.lang.Long) data[0];
    }

    public RowBuilder_Attend setLectureId(Integer intValue)
    {
      data[ 1 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Attend setLectureId(java.lang.Long value)
    {
      data[ 1 ] = value;
      return this;
    }
    public RowBuilder_Attend setLectureIdRaw(Object value)
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
    * ds.table_Attend.insertRow()
    *   .setStudentId( null )
    *   .setLectureId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Attend insertRow()
    {
      return table.insertRow();
    }
    
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Attend.insertRow()
    *   .setStudentId( null )
    *   .setLectureId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Attend insertRow(AttendModel row)
    {
      return table.insertRow(row);
    }
    
    /**
    * Insert a new Row at the given position
    * <code><pre>
    * ds.table_Attend.this.insertRowAt(2)
    *   .setStudentId( null )
    *   .setLectureId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Attend insertRowAt(int index)
    {
      return table.insertRowAt(index);
    }

    /**
    * Insert a row at the end of the table
    * <code><pre>
    * ds.table_Attend.insertRow(baseUser);
    * </pre></code>
    */
    public RowBuilder_Attend insertRow(RowBuilder_Attend theRow)
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
    

    public RowBuilder_Attend refStudentId(RowGetters_Student reference)
    {
      setStudentId(reference.getStudentNumber());
      return this;
    }

    public RowBuilder_Attend refLectureId(RowGetters_Exam reference)
    {
      setLectureId(reference.getId());
      return this;
    }
    
    @Override
    public RowBuilder_Attend clone() {
      RowBuilder_Attend clone = new RowBuilder_Attend(table);
      clone.setStudentId(getStudentId());
      clone.setLectureId(getLectureId());
      return clone;
    }
  }
  
  public AttendWhere findWhere = new AttendWhere(this);

  public static class AttendWhere
  {
    public List<RowBuilder_Attend> rows;
    AttendTable table;
    
    public AttendWhere(AttendTable theTable) {
       rows = theTable.rows;
       table = theTable;
    }
    
    public RowCollection_Attend rowComparesTo(Comparable<RowBuilder_Attend> toSearch) {
      RowCollection_Attend modifiers = new RowCollection_Attend(table);
      for (RowBuilder_Attend row : rows) 
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
    public RowCollection_Attend studentId(java.lang.Long toSearch) {
      RowCollection_Attend modifiers = new RowCollection_Attend(table);
      for (RowBuilder_Attend row : rows) 
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
    public RowCollection_Attend studentId(Integer toSearch) 
    {
      return studentId( Long.valueOf(toSearch) );
    }
    public RowCollection_Attend lectureId(java.lang.Long toSearch) {
      RowCollection_Attend modifiers = new RowCollection_Attend(table);
      for (RowBuilder_Attend row : rows) 
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
    public RowCollection_Attend lectureId(Integer toSearch) 
    {
      return lectureId( Long.valueOf(toSearch) );
    }
  }
  
  /** Inner class! Use RowCollection_Attend in your code ! */
  public static class RowModify_Attend extends RowBuilder_Attend 
  {
      List<RowBuilder_Attend> _rows;

      public RowModify_Attend(AttendTable theTable) {
        super(theTable);
        _rows = new ArrayList<RowBuilder_Attend>();
      }
      
      public void add(RowBuilder_Attend row) {
          _rows.add(row);
      }
      
      public boolean isEmpty() {
          return _rows.isEmpty();
      }

      public RowModify_Attend delete() {
          for(RowBuilder_Attend row : _rows) {
            table.rows.remove(row);
          }
          return this;
      }
        

      public RowModify_Attend setStudentId(Integer intValue)
      {
        for(RowBuilder_Attend row : _rows) {
          row.setStudentId(intValue);
        }
        return this;
      }

      public RowModify_Attend setStudentId(java.lang.Long value)
      {
        for(RowBuilder_Attend row : _rows) {
          row.setStudentId(value);
        }
        return this;
      }
      public RowModify_Attend setStudentIdRaw(Object value)
      {
        for(RowBuilder_Attend row : _rows) {
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

      public RowModify_Attend setLectureId(Integer intValue)
      {
        for(RowBuilder_Attend row : _rows) {
          row.setLectureId(intValue);
        }
        return this;
      }

      public RowModify_Attend setLectureId(java.lang.Long value)
      {
        for(RowBuilder_Attend row : _rows) {
          row.setLectureId(value);
        }
        return this;
      }
      public RowModify_Attend setLectureIdRaw(Object value)
      {
        for(RowBuilder_Attend row : _rows) {
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
      public RowModify_Attend clone() {
        RowModify_Attend clone = new RowModify_Attend(table);
        for(RowBuilder_Attend row:_rows) {
          clone._rows.add(row.clone());
        }
        return clone;
      }

  }
  
  public static class RowCollection_Attend extends RowModify_Attend {
    public AttendWhere where = new AttendWhere(table);
    
    public RowCollection_Attend(AttendTable theTable)
    {
      super(theTable);
    }
    
  }

  /**
  * Insert a new empty Row.
  * <code><pre>
  * ds.table_Attend.insertRow()
  *   .setStudentId( null )
  *   .setLectureId( null )
  *   ;
  * </pre></code>
  */
  public RowBuilder_Attend insertRow()
  {
    RowBuilder_Attend row = new RowBuilder_Attend(this);
    rows.add(row);
    return row;
  }
  
  /**
  * <code><pre>
  * ds.table_Attend.insertRow()
  * </pre></code>
  */
  public RowBuilder_Attend insertRow(AttendModel rowToAdd)
  {
    RowBuilder_Attend row = new RowBuilder_Attend(this);
    row.setStudentIdRaw( rowToAdd.getStudentIdRaw() );
    row.setLectureIdRaw( rowToAdd.getLectureIdRaw() );
    rows.add(row);
    return row;
  }

  /**
  * <code><pre>
  * ds.table_Attend.insertRow(data);
  * </pre></code>
  */
  public RowBuilder_Attend insertRow(RowBuilder_Attend theRow)
  {
    rows.add(theRow);
    return theRow;
  }
  
  /**
  * <code><pre>
  * ds.table_Attend.insertRows(data);
  * </pre></code>
  */
  public void insertRows(RowBuilder_Attend...theRows)
  {
    rows.addAll(Arrays.asList(theRows));
  }
  
  /**
  * Insert new row at the given index
  * <code><pre>
  * ds.table_Attend.insertRowAt(3)
  *   ;
  * </pre></code>
  */
  public RowBuilder_Attend insertRowAt(int index)
  {
    RowBuilder_Attend row = new RowBuilder_Attend(this);
    rows.add(index, row);
    return row;
  }
  
  /**
  * Insert new row Object at the given index
  * <code><pre>
  * ds.table_Attend.insertRowAt(3)
  * </pre></code>
  */
  public RowBuilder_Attend insertRowAt(int index,RowBuilder_Attend theRow)
  {
    rows.add(index, theRow);
    return theRow;
  }
  
  /**
   * Remove a row from the builder by the given index.
   *
   * @return the deleted row
   */ 
  public RowBuilder_Attend deleteRow(int index)
  {
    RowBuilder_Attend rowBuilder = rows.get(index);
    rows.remove(rowBuilder);
    return rowBuilder;
  }
  
  /**
   * Remove a row from the builder
   */ 
  public RowBuilder_Attend deleteRow(RowBuilder_Attend rowToDelete)
  {
    rows.remove(rowToDelete);
    return rowToDelete;
  }

  /**
  * Creates a new row but does not add it to the table
  */
  public RowBuilder_Attend newRow()
  {
    RowBuilder_Attend row = new RowBuilder_Attend(this);
    return row;
  }
  
  /**
  * Returns the next Object. The internal iterator is started at 
  * the first call.
  */
  public RowBuilder_Attend next()
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
