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
public class GiveLectureTable implements ITable
{
  public final static String NAME = "give_lecture";

  public static class Columns
  {
    public static final String ProfessorId = "professor_id";
    public static final String ExamId = "exam_id";
  }

  // @formatter:off
  public final static Column[] COLUMNS = new Column[] {
    // idx = 0
    new Column(Columns.ProfessorId, DataType.BIGINT),
    // idx = 1
    new Column(Columns.ExamId, DataType.BIGINT)
  };

  static Map<String, EnumSet<Flags>> GENERATOR_METADATA;
  static {
    GENERATOR_METADATA = new HashMap<String, EnumSet<Flags>>();
    GENERATOR_METADATA.put(Columns.ProfessorId,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.ExamId,EnumSet.noneOf( Flags.class ));
  }
  // @formatter:on

  ITableMetaData _metaData;
  
  STUExampleDBDataSet _dataSet;
  
  Iterator<RowBuilder_GiveLecture> _iterator;
  
  public GiveLectureTable()
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

  public List<RowBuilder_GiveLecture> rows = new ArrayList<RowBuilder_GiveLecture>();
  
  public interface RowSetters_GiveLecture<T extends RowSetters_GiveLecture>
  {
    T setProfessorId(Integer intValue);
    T setProfessorId(java.lang.Long value);
    T setProfessorIdRaw(Object value);
    T setExamId(Integer intValue);
    T setExamId(java.lang.Long value);
    T setExamIdRaw(Object value);
     
  }
  
  public interface RowGetters_GiveLecture<T extends RowGetters_GiveLecture>
  {
    java.lang.Long getProfessorId();
    java.lang.Long getExamId();
     
  }

  public static class RowBuilder_GiveLecture implements RowSetters_GiveLecture<RowBuilder_GiveLecture>, RowGetters_GiveLecture<RowBuilder_GiveLecture>
  {

    Object[] data;
    
    GiveLectureTable table;
    
    RowBuilder_GiveLecture(GiveLectureTable tableDelegate) {
      data=new Object[COLUMNS.length];
      table = tableDelegate;
    }
    

    public RowBuilder_GiveLecture setProfessorId(Integer intValue)
    {
      data[ 0 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_GiveLecture setProfessorId(java.lang.Long value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_GiveLecture setProfessorIdRaw(Object value)
    {
      data[ 0 ] = value;
      return this;
    }

    public java.lang.Long getProfessorId()
    {
      return (java.lang.Long) data[0];
    }

    public RowBuilder_GiveLecture setExamId(Integer intValue)
    {
      data[ 1 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_GiveLecture setExamId(java.lang.Long value)
    {
      data[ 1 ] = value;
      return this;
    }
    public RowBuilder_GiveLecture setExamIdRaw(Object value)
    {
      data[ 1 ] = value;
      return this;
    }

    public java.lang.Long getExamId()
    {
      return (java.lang.Long) data[1];
    }
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_GiveLecture.insertRow()
    *   .setProfessorId( null )
    *   .setExamId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_GiveLecture insertRow()
    {
      return table.insertRow();
    }
    
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_GiveLecture.insertRow()
    *   .setProfessorId( null )
    *   .setExamId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_GiveLecture insertRow(GiveLectureModel row)
    {
      return table.insertRow(row);
    }
    
    /**
    * Insert a new Row at the given position
    * <code><pre>
    * ds.table_GiveLecture.this.insertRowAt(2)
    *   .setProfessorId( null )
    *   .setExamId( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_GiveLecture insertRowAt(int index)
    {
      return table.insertRowAt(index);
    }

    /**
    * Insert a row at the end of the table
    * <code><pre>
    * ds.table_GiveLecture.insertRow(baseUser);
    * </pre></code>
    */
    public RowBuilder_GiveLecture insertRow(RowBuilder_GiveLecture theRow)
    {
      return table.insertRow(theRow);
    }
    
    public Object getValue(String column) throws RuntimeException {
      if(column.equalsIgnoreCase(Columns.ProfessorId) ) {
        return data[0];
      }
      if(column.equalsIgnoreCase(Columns.ExamId) ) {
        return data[1];
      }
      throw new RuntimeException(NAME + " col = " + column);
    }
    

    public RowBuilder_GiveLecture refProfessorId(RowGetters_Professor reference)
    {
      setProfessorId(reference.getId());
      return this;
    }

    public RowBuilder_GiveLecture refExamId(RowGetters_Exam reference)
    {
      setExamId(reference.getId());
      return this;
    }
    
    @Override
    public RowBuilder_GiveLecture clone() {
      RowBuilder_GiveLecture clone = new RowBuilder_GiveLecture(table);
      clone.setProfessorId(getProfessorId());
      clone.setExamId(getExamId());
      return clone;
    }
  }
  
  public GiveLectureWhere findWhere = new GiveLectureWhere(this);

  public static class GiveLectureWhere
  {
    public List<RowBuilder_GiveLecture> rows;
    GiveLectureTable table;
    
    public GiveLectureWhere(GiveLectureTable theTable) {
       rows = theTable.rows;
       table = theTable;
    }
    
    public RowCollection_GiveLecture rowComparesTo(Comparable<RowBuilder_GiveLecture> toSearch) {
      RowCollection_GiveLecture modifiers = new RowCollection_GiveLecture(table);
      for (RowBuilder_GiveLecture row : rows) 
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
    public RowCollection_GiveLecture professorId(java.lang.Long toSearch) {
      RowCollection_GiveLecture modifiers = new RowCollection_GiveLecture(table);
      for (RowBuilder_GiveLecture row : rows) 
      {
        if (row.getProfessorId().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with professor_id = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_GiveLecture professorId(Integer toSearch) 
    {
      return professorId( Long.valueOf(toSearch) );
    }
    public RowCollection_GiveLecture examId(java.lang.Long toSearch) {
      RowCollection_GiveLecture modifiers = new RowCollection_GiveLecture(table);
      for (RowBuilder_GiveLecture row : rows) 
      {
        if (row.getExamId().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with exam_id = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_GiveLecture examId(Integer toSearch) 
    {
      return examId( Long.valueOf(toSearch) );
    }
  }
  
  /** Inner class! Use RowCollection_GiveLecture in your code ! */
  public static class RowModify_GiveLecture extends RowBuilder_GiveLecture 
  {
      List<RowBuilder_GiveLecture> _rows;

      public RowModify_GiveLecture(GiveLectureTable theTable) {
        super(theTable);
        _rows = new ArrayList<RowBuilder_GiveLecture>();
      }
      
      public void add(RowBuilder_GiveLecture row) {
          _rows.add(row);
      }
      
      public boolean isEmpty() {
          return _rows.isEmpty();
      }

      public RowModify_GiveLecture delete() {
          for(RowBuilder_GiveLecture row : _rows) {
            table.rows.remove(row);
          }
          return this;
      }
        

      public RowModify_GiveLecture setProfessorId(Integer intValue)
      {
        for(RowBuilder_GiveLecture row : _rows) {
          row.setProfessorId(intValue);
        }
        return this;
      }

      public RowModify_GiveLecture setProfessorId(java.lang.Long value)
      {
        for(RowBuilder_GiveLecture row : _rows) {
          row.setProfessorId(value);
        }
        return this;
      }
      public RowModify_GiveLecture setProfessorIdRaw(Object value)
      {
        for(RowBuilder_GiveLecture row : _rows) {
          row.setProfessorIdRaw(value);
        }
        return this;
      }
      public java.lang.Long getProfessorId()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getProfessorId();
      }

      public RowModify_GiveLecture setExamId(Integer intValue)
      {
        for(RowBuilder_GiveLecture row : _rows) {
          row.setExamId(intValue);
        }
        return this;
      }

      public RowModify_GiveLecture setExamId(java.lang.Long value)
      {
        for(RowBuilder_GiveLecture row : _rows) {
          row.setExamId(value);
        }
        return this;
      }
      public RowModify_GiveLecture setExamIdRaw(Object value)
      {
        for(RowBuilder_GiveLecture row : _rows) {
          row.setExamIdRaw(value);
        }
        return this;
      }
      public java.lang.Long getExamId()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getExamId();
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
      public RowModify_GiveLecture clone() {
        RowModify_GiveLecture clone = new RowModify_GiveLecture(table);
        for(RowBuilder_GiveLecture row:_rows) {
          clone._rows.add(row.clone());
        }
        return clone;
      }

  }
  
  public static class RowCollection_GiveLecture extends RowModify_GiveLecture {
    public GiveLectureWhere where = new GiveLectureWhere(table);
    
    public RowCollection_GiveLecture(GiveLectureTable theTable)
    {
      super(theTable);
    }
    
  }

  /**
  * Insert a new empty Row.
  * <code><pre>
  * ds.table_GiveLecture.insertRow()
  *   .setProfessorId( null )
  *   .setExamId( null )
  *   ;
  * </pre></code>
  */
  public RowBuilder_GiveLecture insertRow()
  {
    RowBuilder_GiveLecture row = new RowBuilder_GiveLecture(this);
    rows.add(row);
    return row;
  }
  
  /**
  * <code><pre>
  * ds.table_GiveLecture.insertRow()
  * </pre></code>
  */
  public RowBuilder_GiveLecture insertRow(GiveLectureModel rowToAdd)
  {
    RowBuilder_GiveLecture row = new RowBuilder_GiveLecture(this);
    row.setProfessorIdRaw( rowToAdd.getProfessorIdRaw() );
    row.setExamIdRaw( rowToAdd.getExamIdRaw() );
    rows.add(row);
    return row;
  }

  /**
  * <code><pre>
  * ds.table_GiveLecture.insertRow(data);
  * </pre></code>
  */
  public RowBuilder_GiveLecture insertRow(RowBuilder_GiveLecture theRow)
  {
    rows.add(theRow);
    return theRow;
  }
  
  /**
  * <code><pre>
  * ds.table_GiveLecture.insertRows(data);
  * </pre></code>
  */
  public void insertRows(RowBuilder_GiveLecture...theRows)
  {
    rows.addAll(Arrays.asList(theRows));
  }
  
  /**
  * Insert new row at the given index
  * <code><pre>
  * ds.table_GiveLecture.insertRowAt(3)
  *   ;
  * </pre></code>
  */
  public RowBuilder_GiveLecture insertRowAt(int index)
  {
    RowBuilder_GiveLecture row = new RowBuilder_GiveLecture(this);
    rows.add(index, row);
    return row;
  }
  
  /**
  * Insert new row Object at the given index
  * <code><pre>
  * ds.table_GiveLecture.insertRowAt(3)
  * </pre></code>
  */
  public RowBuilder_GiveLecture insertRowAt(int index,RowBuilder_GiveLecture theRow)
  {
    rows.add(index, theRow);
    return theRow;
  }
  
  /**
   * Remove a row from the builder by the given index.
   *
   * @return the deleted row
   */ 
  public RowBuilder_GiveLecture deleteRow(int index)
  {
    RowBuilder_GiveLecture rowBuilder = rows.get(index);
    rows.remove(rowBuilder);
    return rowBuilder;
  }
  
  /**
   * Remove a row from the builder
   */ 
  public RowBuilder_GiveLecture deleteRow(RowBuilder_GiveLecture rowToDelete)
  {
    rows.remove(rowToDelete);
    return rowToDelete;
  }

  /**
  * Creates a new row but does not add it to the table
  */
  public RowBuilder_GiveLecture newRow()
  {
    RowBuilder_GiveLecture row = new RowBuilder_GiveLecture(this);
    return row;
  }
  
  /**
  * Returns the next Object. The internal iterator is started at 
  * the first call.
  */
  public RowBuilder_GiveLecture next()
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
