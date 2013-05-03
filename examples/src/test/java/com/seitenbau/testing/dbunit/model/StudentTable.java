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
public class StudentTable implements ITable
{
  public final static String NAME = "student";

  public static class Columns
  {
    public static final String StudentNumber = "student_number";
    public static final String Name = "name";
    public static final String FirstName = "first_name";
    public static final String DegreeCourse = "degree_course";
    public static final String Semester = "semester";
    public static final String EnrolledSince = "enrolled_since";
  }

  // @formatter:off
  public final static Column[] COLUMNS = new Column[] {
    // idx = 0
    new Column(Columns.StudentNumber, DataType.BIGINT),
    // idx = 1
    new Column(Columns.Name, DataType.VARCHAR),
    // idx = 2
    new Column(Columns.FirstName, DataType.VARCHAR),
    // idx = 3
    new Column(Columns.DegreeCourse, DataType.VARCHAR),
    // idx = 4
    new Column(Columns.Semester, DataType.INTEGER),
    // idx = 5
    new Column(Columns.EnrolledSince, DataType.DATE)
  };

  static Map<String, EnumSet<Flags>> GENERATOR_METADATA;
  static {
    GENERATOR_METADATA = new HashMap<String, EnumSet<Flags>>();
    GENERATOR_METADATA.put(Columns.StudentNumber,EnumSet.of( Flags.AddNextIdMethod));
    GENERATOR_METADATA.put(Columns.Name,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.FirstName,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.DegreeCourse,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.Semester,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.EnrolledSince,EnumSet.noneOf( Flags.class ));
  }
  // @formatter:on

  ITableMetaData _metaData;
  
  STUExampleDBDataSet _dataSet;
  
  Iterator<RowBuilder_Student> _iterator;
  
  public StudentTable()
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

  public List<RowBuilder_Student> rows = new ArrayList<RowBuilder_Student>();
  
  public interface RowSetters_Student<T extends RowSetters_Student>
  {
    T setStudentNumber(Integer intValue);
    T setStudentNumber(java.lang.Long value);
    T setStudentNumberRaw(Object value);
    T nextStudentNumber();
    T setName(java.lang.String value);
    T setNameRaw(Object value);
    T setFirstName(java.lang.String value);
    T setFirstNameRaw(Object value);
    T setDegreeCourse(java.lang.String value);
    T setDegreeCourseRaw(Object value);
    T setSemester(java.lang.Integer value);
    T setSemesterRaw(Object value);
    T setEnrolledSince(String dateString);
    T setEnrolledSince(DateBuilder datum);
    T setEnrolledSince(java.util.Date value);
    T setEnrolledSinceRaw(Object value);
     
  }
  
  public interface RowGetters_Student<T extends RowGetters_Student>
  {
    java.lang.Long getStudentNumber();
    java.lang.String getName();
    java.lang.String getFirstName();
    java.lang.String getDegreeCourse();
    java.lang.Integer getSemester();
    java.util.Date getEnrolledSince();
     
  }

  public static class RowBuilder_Student implements RowSetters_Student<RowBuilder_Student>, RowGetters_Student<RowBuilder_Student>
  {

    Object[] data;
    
    StudentTable table;
    
    RowBuilder_Student(StudentTable tableDelegate) {
      data=new Object[COLUMNS.length];
      table = tableDelegate;
    }
    

    public RowBuilder_Student setStudentNumber(Integer intValue)
    {
      data[ 0 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Student setStudentNumber(java.lang.Long value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_Student setStudentNumberRaw(Object value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_Student nextStudentNumber()
    {
      DatasetIdGenerator generator = table.getDataset().getIdGenerator();
      Long nextId = generator.nextId(StudentTable.NAME,"student_number");
      setStudentNumber(nextId);
      return this;
    }

    public java.lang.Long getStudentNumber()
    {
      return (java.lang.Long) data[0];
    }

    public RowBuilder_Student setName(java.lang.String value)
    {
      data[ 1 ] = value;
      return this;
    }
    public RowBuilder_Student setNameRaw(Object value)
    {
      data[ 1 ] = value;
      return this;
    }

    public java.lang.String getName()
    {
      return (java.lang.String) data[1];
    }

    public RowBuilder_Student setFirstName(java.lang.String value)
    {
      data[ 2 ] = value;
      return this;
    }
    public RowBuilder_Student setFirstNameRaw(Object value)
    {
      data[ 2 ] = value;
      return this;
    }

    public java.lang.String getFirstName()
    {
      return (java.lang.String) data[2];
    }

    public RowBuilder_Student setDegreeCourse(java.lang.String value)
    {
      data[ 3 ] = value;
      return this;
    }
    public RowBuilder_Student setDegreeCourseRaw(Object value)
    {
      data[ 3 ] = value;
      return this;
    }

    public java.lang.String getDegreeCourse()
    {
      return (java.lang.String) data[3];
    }

    public RowBuilder_Student setSemester(java.lang.Integer value)
    {
      data[ 4 ] = value;
      return this;
    }
    public RowBuilder_Student setSemesterRaw(Object value)
    {
      data[ 4 ] = value;
      return this;
    }

    public java.lang.Integer getSemester()
    {
      return (java.lang.Integer) data[4];
    }

    public RowBuilder_Student setEnrolledSince(String dateString)
    {
      data[ 5 ] = toDate(dateString);
      return this;
    }
    public RowBuilder_Student setEnrolledSince(DateBuilder datum)
    {
      data[ 5 ] = toDate(datum);
      return this;
    }
    public RowBuilder_Student setEnrolledSince(java.util.Date value)
    {
      data[ 5 ] = value;
      return this;
    }
    public RowBuilder_Student setEnrolledSinceRaw(Object value)
    {
      data[ 5 ] = value;
      return this;
    }

    public java.util.Date getEnrolledSince()
    {
      return (java.util.Date) data[5];
    }
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Student.insertRow()
    *   .setStudentNumber( null )
    *   .setName( null )
    *   .setFirstName( null )
    *   .setDegreeCourse( null )
    *   .setSemester( null )
    *   .setEnrolledSince( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Student insertRow()
    {
      return table.insertRow();
    }
    
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Student.insertRow()
    *   .setStudentNumber( null )
    *   .setName( null )
    *   .setFirstName( null )
    *   .setDegreeCourse( null )
    *   .setSemester( null )
    *   .setEnrolledSince( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Student insertRow(StudentModel row)
    {
      return table.insertRow(row);
    }
    
    /**
    * Insert a new Row at the given position
    * <code><pre>
    * ds.table_Student.this.insertRowAt(2)
    *   .setStudentNumber( null )
    *   .setName( null )
    *   .setFirstName( null )
    *   .setDegreeCourse( null )
    *   .setSemester( null )
    *   .setEnrolledSince( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Student insertRowAt(int index)
    {
      return table.insertRowAt(index);
    }

    /**
    * Insert a row at the end of the table
    * <code><pre>
    * ds.table_Student.insertRow(baseUser);
    * </pre></code>
    */
    public RowBuilder_Student insertRow(RowBuilder_Student theRow)
    {
      return table.insertRow(theRow);
    }
    
    public Object getValue(String column) throws RuntimeException {
      if(column.equalsIgnoreCase(Columns.StudentNumber) ) {
        return data[0];
      }
      if(column.equalsIgnoreCase(Columns.Name) ) {
        return data[1];
      }
      if(column.equalsIgnoreCase(Columns.FirstName) ) {
        return data[2];
      }
      if(column.equalsIgnoreCase(Columns.DegreeCourse) ) {
        return data[3];
      }
      if(column.equalsIgnoreCase(Columns.Semester) ) {
        return data[4];
      }
      if(column.equalsIgnoreCase(Columns.EnrolledSince) ) {
        return data[5];
      }
      throw new RuntimeException(NAME + " col = " + column);
    }
    
    
    @Override
    public RowBuilder_Student clone() {
      RowBuilder_Student clone = new RowBuilder_Student(table);
      clone.setStudentNumber(getStudentNumber());
      clone.setName(getName());
      clone.setFirstName(getFirstName());
      clone.setDegreeCourse(getDegreeCourse());
      clone.setSemester(getSemester());
      clone.setEnrolledSince(getEnrolledSince());
      return clone;
    }
  }
  
  public StudentWhere findWhere = new StudentWhere(this);

  public static class StudentWhere
  {
    public List<RowBuilder_Student> rows;
    StudentTable table;
    
    public StudentWhere(StudentTable theTable) {
       rows = theTable.rows;
       table = theTable;
    }
    
    public RowCollection_Student rowComparesTo(Comparable<RowBuilder_Student> toSearch) {
      RowCollection_Student modifiers = new RowCollection_Student(table);
      for (RowBuilder_Student row : rows) 
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
    public RowCollection_Student studentNumber(java.lang.Long toSearch) {
      RowCollection_Student modifiers = new RowCollection_Student(table);
      for (RowBuilder_Student row : rows) 
      {
        if (row.getStudentNumber().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with student_number = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Student studentNumber(Integer toSearch) 
    {
      return studentNumber( Long.valueOf(toSearch) );
    }
    public RowCollection_Student name(java.lang.String toSearch) {
      RowCollection_Student modifiers = new RowCollection_Student(table);
      for (RowBuilder_Student row : rows) 
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
    public RowCollection_Student firstName(java.lang.String toSearch) {
      RowCollection_Student modifiers = new RowCollection_Student(table);
      for (RowBuilder_Student row : rows) 
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
    public RowCollection_Student degreeCourse(java.lang.String toSearch) {
      RowCollection_Student modifiers = new RowCollection_Student(table);
      for (RowBuilder_Student row : rows) 
      {
        if (row.getDegreeCourse().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with degree_course = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Student semester(java.lang.Integer toSearch) {
      RowCollection_Student modifiers = new RowCollection_Student(table);
      for (RowBuilder_Student row : rows) 
      {
        if (row.getSemester().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with semester = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Student enrolledSince(java.util.Date toSearch) {
      RowCollection_Student modifiers = new RowCollection_Student(table);
      for (RowBuilder_Student row : rows) 
      {
        if (row.getEnrolledSince().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with enrolled_since = " + toSearch );
      }
      return modifiers;
    }
  }
  
  /** Inner class! Use RowCollection_Student in your code ! */
  public static class RowModify_Student extends RowBuilder_Student 
  {
      List<RowBuilder_Student> _rows;

      public RowModify_Student(StudentTable theTable) {
        super(theTable);
        _rows = new ArrayList<RowBuilder_Student>();
      }
      
      public void add(RowBuilder_Student row) {
          _rows.add(row);
      }
      
      public boolean isEmpty() {
          return _rows.isEmpty();
      }

      public RowModify_Student delete() {
          for(RowBuilder_Student row : _rows) {
            table.rows.remove(row);
          }
          return this;
      }
        

      public RowModify_Student setStudentNumber(Integer intValue)
      {
        for(RowBuilder_Student row : _rows) {
          row.setStudentNumber(intValue);
        }
        return this;
      }

      public RowModify_Student setStudentNumber(java.lang.Long value)
      {
        for(RowBuilder_Student row : _rows) {
          row.setStudentNumber(value);
        }
        return this;
      }
      public RowModify_Student setStudentNumberRaw(Object value)
      {
        for(RowBuilder_Student row : _rows) {
          row.setStudentNumberRaw(value);
        }
        return this;
      }
      public java.lang.Long getStudentNumber()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getStudentNumber();
      }

      public RowModify_Student setName(java.lang.String value)
      {
        for(RowBuilder_Student row : _rows) {
          row.setName(value);
        }
        return this;
      }
      public RowModify_Student setNameRaw(Object value)
      {
        for(RowBuilder_Student row : _rows) {
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

      public RowModify_Student setFirstName(java.lang.String value)
      {
        for(RowBuilder_Student row : _rows) {
          row.setFirstName(value);
        }
        return this;
      }
      public RowModify_Student setFirstNameRaw(Object value)
      {
        for(RowBuilder_Student row : _rows) {
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

      public RowModify_Student setDegreeCourse(java.lang.String value)
      {
        for(RowBuilder_Student row : _rows) {
          row.setDegreeCourse(value);
        }
        return this;
      }
      public RowModify_Student setDegreeCourseRaw(Object value)
      {
        for(RowBuilder_Student row : _rows) {
          row.setDegreeCourseRaw(value);
        }
        return this;
      }
      public java.lang.String getDegreeCourse()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getDegreeCourse();
      }

      public RowModify_Student setSemester(java.lang.Integer value)
      {
        for(RowBuilder_Student row : _rows) {
          row.setSemester(value);
        }
        return this;
      }
      public RowModify_Student setSemesterRaw(Object value)
      {
        for(RowBuilder_Student row : _rows) {
          row.setSemesterRaw(value);
        }
        return this;
      }
      public java.lang.Integer getSemester()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getSemester();
      }

      public RowModify_Student setEnrolledSince(String dateString)
      {
        for(RowBuilder_Student row : _rows) {
          row.setEnrolledSince(dateString);
        }
        return this;
      }
      public RowModify_Student setEnrolledSince(DateBuilder datum)
      {
        for(RowBuilder_Student row : _rows) {
          row.setEnrolledSince(datum);
        }
        return this;
      }
      public RowModify_Student setEnrolledSince(java.util.Date value)
      {
        for(RowBuilder_Student row : _rows) {
          row.setEnrolledSince(value);
        }
        return this;
      }
      public RowModify_Student setEnrolledSinceRaw(Object value)
      {
        for(RowBuilder_Student row : _rows) {
          row.setEnrolledSinceRaw(value);
        }
        return this;
      }
      public java.util.Date getEnrolledSince()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getEnrolledSince();
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
      public RowModify_Student clone() {
        RowModify_Student clone = new RowModify_Student(table);
        for(RowBuilder_Student row:_rows) {
          clone._rows.add(row.clone());
        }
        return clone;
      }

  }
  
  public static class RowCollection_Student extends RowModify_Student {
    public StudentWhere where = new StudentWhere(table);
    
    public RowCollection_Student(StudentTable theTable)
    {
      super(theTable);
    }
    
  }

  /**
  * Insert a new empty Row.
  * <code><pre>
  * ds.table_Student.insertRow()
  *   .setStudentNumber( null )
  *   .setName( null )
  *   .setFirstName( null )
  *   .setDegreeCourse( null )
  *   .setSemester( null )
  *   .setEnrolledSince( null )
  *   ;
  * </pre></code>
  */
  public RowBuilder_Student insertRow()
  {
    RowBuilder_Student row = new RowBuilder_Student(this);
    rows.add(row);
    return row;
  }
  
  /**
  * <code><pre>
  * ds.table_Student.insertRow()
  * </pre></code>
  */
  public RowBuilder_Student insertRow(StudentModel rowToAdd)
  {
    RowBuilder_Student row = new RowBuilder_Student(this);
    row.setStudentNumberRaw( rowToAdd.getStudentNumberRaw() );
    row.setNameRaw( rowToAdd.getNameRaw() );
    row.setFirstNameRaw( rowToAdd.getFirstNameRaw() );
    row.setDegreeCourseRaw( rowToAdd.getDegreeCourseRaw() );
    row.setSemesterRaw( rowToAdd.getSemesterRaw() );
    row.setEnrolledSinceRaw( rowToAdd.getEnrolledSinceRaw() );
    rows.add(row);
    return row;
  }

  /**
  * <code><pre>
  * ds.table_Student.insertRow(data);
  * </pre></code>
  */
  public RowBuilder_Student insertRow(RowBuilder_Student theRow)
  {
    rows.add(theRow);
    return theRow;
  }
  
  /**
  * <code><pre>
  * ds.table_Student.insertRows(data);
  * </pre></code>
  */
  public void insertRows(RowBuilder_Student...theRows)
  {
    rows.addAll(Arrays.asList(theRows));
  }
  
  /**
  * Insert new row at the given index
  * <code><pre>
  * ds.table_Student.insertRowAt(3)
  *   ;
  * </pre></code>
  */
  public RowBuilder_Student insertRowAt(int index)
  {
    RowBuilder_Student row = new RowBuilder_Student(this);
    rows.add(index, row);
    return row;
  }
  
  /**
  * Insert new row Object at the given index
  * <code><pre>
  * ds.table_Student.insertRowAt(3)
  * </pre></code>
  */
  public RowBuilder_Student insertRowAt(int index,RowBuilder_Student theRow)
  {
    rows.add(index, theRow);
    return theRow;
  }
  
  /**
   * Remove a row from the builder by the given index.
   *
   * @return the deleted row
   */ 
  public RowBuilder_Student deleteRow(int index)
  {
    RowBuilder_Student rowBuilder = rows.get(index);
    rows.remove(rowBuilder);
    return rowBuilder;
  }
  
  /**
   * Remove a row from the builder
   */ 
  public RowBuilder_Student deleteRow(RowBuilder_Student rowToDelete)
  {
    rows.remove(rowToDelete);
    return rowToDelete;
  }

  /**
  * Creates a new row but does not add it to the table
  */
  public RowBuilder_Student newRow()
  {
    RowBuilder_Student row = new RowBuilder_Student(this);
    return row;
  }
  
  /**
  * Returns the next Object. The internal iterator is started at 
  * the first call.
  */
  public RowBuilder_Student next()
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
