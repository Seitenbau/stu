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
public class LectureTable implements ITable
{
  public final static String NAME = "LECTURE";

  public static class Columns
  {
    public static final String Id = "id";
    public static final String ProfessorId = "professor_id";
    public static final String Name = "name";
    public static final String Sws = "sws";
    public static final String Ects = "ects";
  }

  // @formatter:off
  public final static Column[] COLUMNS = new Column[] {
    // idx = 0
    new Column(Columns.Id, DataType.BIGINT),
    // idx = 1
    new Column(Columns.ProfessorId, DataType.BIGINT),
    // idx = 2
    new Column(Columns.Name, DataType.VARCHAR),
    // idx = 3
    new Column(Columns.Sws, DataType.INTEGER),
    // idx = 4
    new Column(Columns.Ects, DataType.INTEGER)
  };

  static Map<String, EnumSet<Flags>> GENERATOR_METADATA;
  static {
    GENERATOR_METADATA = new HashMap<String, EnumSet<Flags>>();
    GENERATOR_METADATA.put(Columns.Id,EnumSet.of( Flags.AutoInvokeNextIdMethod));
    GENERATOR_METADATA.put(Columns.ProfessorId,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.Name,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.Sws,EnumSet.noneOf( Flags.class ));
    GENERATOR_METADATA.put(Columns.Ects,EnumSet.noneOf( Flags.class ));
  }
  // @formatter:on

  ITableMetaData _metaData;
  
  STUExampleDBDataSet _dataSet;
  
  Iterator<RowBuilder_Lecture> _iterator;
  
  public LectureTable()
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

  public List<RowBuilder_Lecture> rows = new ArrayList<RowBuilder_Lecture>();
  
  public interface RowSetters_Lecture<T extends RowSetters_Lecture>
  {
    T setId(Integer intValue);
    T setId(java.lang.Long value);
    T setIdRaw(Object value);
    T nextId();
    T setProfessorId(Integer intValue);
    T setProfessorId(java.lang.Long value);
    T setProfessorIdRaw(Object value);
    T setName(java.lang.String value);
    T setNameRaw(Object value);
    T setSws(java.lang.Integer value);
    T setSwsRaw(Object value);
    T setEcts(java.lang.Integer value);
    T setEctsRaw(Object value);
     
  }
  
  public interface RowGetters_Lecture<T extends RowGetters_Lecture>
  {
    java.lang.Long getId();
    java.lang.Long getProfessorId();
    java.lang.String getName();
    java.lang.Integer getSws();
    java.lang.Integer getEcts();
     
  }

  public static class RowBuilder_Lecture implements RowSetters_Lecture<RowBuilder_Lecture>, RowGetters_Lecture<RowBuilder_Lecture>
  {

    Object[] data;
    
    LectureTable table;
    
    RowBuilder_Lecture(LectureTable tableDelegate) {
      data=new Object[COLUMNS.length];
      table = tableDelegate;
    }
    

    public RowBuilder_Lecture setId(Integer intValue)
    {
      data[ 0 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Lecture setId(java.lang.Long value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_Lecture setIdRaw(Object value)
    {
      data[ 0 ] = value;
      return this;
    }
    public RowBuilder_Lecture nextId()
    {
      DatasetIdGenerator generator = table.getDataset().getIdGenerator();
      Long nextId = generator.nextId(LectureTable.NAME,"id");
      setId(nextId);
      return this;
    }

    public java.lang.Long getId()
    {
      return (java.lang.Long) data[0];
    }

    public RowBuilder_Lecture setProfessorId(Integer intValue)
    {
      data[ 1 ] = (intValue==null?null:Long.valueOf(intValue));
      return this;
    }
    public RowBuilder_Lecture setProfessorId(java.lang.Long value)
    {
      data[ 1 ] = value;
      return this;
    }
    public RowBuilder_Lecture setProfessorIdRaw(Object value)
    {
      data[ 1 ] = value;
      return this;
    }

    public java.lang.Long getProfessorId()
    {
      return (java.lang.Long) data[1];
    }

    public RowBuilder_Lecture setName(java.lang.String value)
    {
      data[ 2 ] = value;
      return this;
    }
    public RowBuilder_Lecture setNameRaw(Object value)
    {
      data[ 2 ] = value;
      return this;
    }

    public java.lang.String getName()
    {
      return (java.lang.String) data[2];
    }

    public RowBuilder_Lecture setSws(java.lang.Integer value)
    {
      data[ 3 ] = value;
      return this;
    }
    public RowBuilder_Lecture setSwsRaw(Object value)
    {
      data[ 3 ] = value;
      return this;
    }

    public java.lang.Integer getSws()
    {
      return (java.lang.Integer) data[3];
    }

    public RowBuilder_Lecture setEcts(java.lang.Integer value)
    {
      data[ 4 ] = value;
      return this;
    }
    public RowBuilder_Lecture setEctsRaw(Object value)
    {
      data[ 4 ] = value;
      return this;
    }

    public java.lang.Integer getEcts()
    {
      return (java.lang.Integer) data[4];
    }
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Lecture.insertRow()
    *   .setId( null )
    *   .setProfessorId( null )
    *   .setName( null )
    *   .setSws( null )
    *   .setEcts( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Lecture insertRow()
    {
      return table.insertRow();
    }
    
    /**
    * Insert a new Row at the end of the Table
    * <code><pre>
    * ds.table_Lecture.insertRow()
    *   .setId( null )
    *   .setProfessorId( null )
    *   .setName( null )
    *   .setSws( null )
    *   .setEcts( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Lecture insertRow(LectureModel row)
    {
      return table.insertRow(row);
    }
    
    /**
    * Insert a new Row at the given position
    * <code><pre>
    * ds.table_Lecture.this.insertRowAt(2)
    *   .setId( null )
    *   .setProfessorId( null )
    *   .setName( null )
    *   .setSws( null )
    *   .setEcts( null )
    *   ;
    * </pre></code>
    */
    public RowBuilder_Lecture insertRowAt(int index)
    {
      return table.insertRowAt(index);
    }

    /**
    * Insert a row at the end of the table
    * <code><pre>
    * ds.table_Lecture.insertRow(baseUser);
    * </pre></code>
    */
    public RowBuilder_Lecture insertRow(RowBuilder_Lecture theRow)
    {
      return table.insertRow(theRow);
    }
    
    public Object getValue(String column) throws RuntimeException {
      if(column.equalsIgnoreCase(Columns.Id) ) {
        return data[0];
      }
      if(column.equalsIgnoreCase(Columns.ProfessorId) ) {
        return data[1];
      }
      if(column.equalsIgnoreCase(Columns.Name) ) {
        return data[2];
      }
      if(column.equalsIgnoreCase(Columns.Sws) ) {
        return data[3];
      }
      if(column.equalsIgnoreCase(Columns.Ects) ) {
        return data[4];
      }
      throw new RuntimeException(NAME + " col = " + column);
    }
    

    public RowBuilder_Lecture refProfessorId(RowGetters_Professor reference)
    {
      setProfessorId(reference.getId());
      return this;
    }
    
    @Override
    public RowBuilder_Lecture clone() {
      RowBuilder_Lecture clone = new RowBuilder_Lecture(table);
      clone.setId(getId());
      clone.setProfessorId(getProfessorId());
      clone.setName(getName());
      clone.setSws(getSws());
      clone.setEcts(getEcts());
      return clone;
    }
  }
  
  public LectureWhere findWhere = new LectureWhere(this);

  public static class LectureWhere
  {
    public List<RowBuilder_Lecture> rows;
    LectureTable table;
    
    public LectureWhere(LectureTable theTable) {
       rows = theTable.rows;
       table = theTable;
    }
    
    public RowCollection_Lecture rowComparesTo(Comparable<RowBuilder_Lecture> toSearch) {
      RowCollection_Lecture modifiers = new RowCollection_Lecture(table);
      for (RowBuilder_Lecture row : rows) 
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
    public RowCollection_Lecture id(java.lang.Long toSearch) {
      RowCollection_Lecture modifiers = new RowCollection_Lecture(table);
      for (RowBuilder_Lecture row : rows) 
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
    public RowCollection_Lecture id(Integer toSearch) 
    {
      return id( Long.valueOf(toSearch) );
    }
    public RowCollection_Lecture professorId(java.lang.Long toSearch) {
      RowCollection_Lecture modifiers = new RowCollection_Lecture(table);
      for (RowBuilder_Lecture row : rows) 
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
    public RowCollection_Lecture professorId(Integer toSearch) 
    {
      return professorId( Long.valueOf(toSearch) );
    }
    public RowCollection_Lecture name(java.lang.String toSearch) {
      RowCollection_Lecture modifiers = new RowCollection_Lecture(table);
      for (RowBuilder_Lecture row : rows) 
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
    public RowCollection_Lecture sws(java.lang.Integer toSearch) {
      RowCollection_Lecture modifiers = new RowCollection_Lecture(table);
      for (RowBuilder_Lecture row : rows) 
      {
        if (row.getSws().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with sws = " + toSearch );
      }
      return modifiers;
    }
    public RowCollection_Lecture ects(java.lang.Integer toSearch) {
      RowCollection_Lecture modifiers = new RowCollection_Lecture(table);
      for (RowBuilder_Lecture row : rows) 
      {
        if (row.getEcts().equals(toSearch)) 
        {
          modifiers.add(row);
        }
      }
      if(modifiers.isEmpty()) {
        throw new RuntimeException("No Row with ects = " + toSearch );
      }
      return modifiers;
    }
  }
  
  /** Inner class! Use RowCollection_Lecture in your code ! */
  public static class RowModify_Lecture extends RowBuilder_Lecture 
  {
      List<RowBuilder_Lecture> _rows;

      public RowModify_Lecture(LectureTable theTable) {
        super(theTable);
        _rows = new ArrayList<RowBuilder_Lecture>();
      }
      
      public void add(RowBuilder_Lecture row) {
          _rows.add(row);
      }
      
      public boolean isEmpty() {
          return _rows.isEmpty();
      }

      public RowModify_Lecture delete() {
          for(RowBuilder_Lecture row : _rows) {
            table.rows.remove(row);
          }
          return this;
      }
        

      public RowModify_Lecture setId(Integer intValue)
      {
        for(RowBuilder_Lecture row : _rows) {
          row.setId(intValue);
        }
        return this;
      }

      public RowModify_Lecture setId(java.lang.Long value)
      {
        for(RowBuilder_Lecture row : _rows) {
          row.setId(value);
        }
        return this;
      }
      public RowModify_Lecture setIdRaw(Object value)
      {
        for(RowBuilder_Lecture row : _rows) {
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

      public RowModify_Lecture setProfessorId(Integer intValue)
      {
        for(RowBuilder_Lecture row : _rows) {
          row.setProfessorId(intValue);
        }
        return this;
      }

      public RowModify_Lecture setProfessorId(java.lang.Long value)
      {
        for(RowBuilder_Lecture row : _rows) {
          row.setProfessorId(value);
        }
        return this;
      }
      public RowModify_Lecture setProfessorIdRaw(Object value)
      {
        for(RowBuilder_Lecture row : _rows) {
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

      public RowModify_Lecture setName(java.lang.String value)
      {
        for(RowBuilder_Lecture row : _rows) {
          row.setName(value);
        }
        return this;
      }
      public RowModify_Lecture setNameRaw(Object value)
      {
        for(RowBuilder_Lecture row : _rows) {
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

      public RowModify_Lecture setSws(java.lang.Integer value)
      {
        for(RowBuilder_Lecture row : _rows) {
          row.setSws(value);
        }
        return this;
      }
      public RowModify_Lecture setSwsRaw(Object value)
      {
        for(RowBuilder_Lecture row : _rows) {
          row.setSwsRaw(value);
        }
        return this;
      }
      public java.lang.Integer getSws()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getSws();
      }

      public RowModify_Lecture setEcts(java.lang.Integer value)
      {
        for(RowBuilder_Lecture row : _rows) {
          row.setEcts(value);
        }
        return this;
      }
      public RowModify_Lecture setEctsRaw(Object value)
      {
        for(RowBuilder_Lecture row : _rows) {
          row.setEctsRaw(value);
        }
        return this;
      }
      public java.lang.Integer getEcts()
      {
        if(_rows.size()!=1) {
          throw new RuntimeException("There where multiple Row in the result! " + _rows.size() );
        }
        return _rows.get(0).getEcts();
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
      public RowModify_Lecture clone() {
        RowModify_Lecture clone = new RowModify_Lecture(table);
        for(RowBuilder_Lecture row:_rows) {
          clone._rows.add(row.clone());
        }
        return clone;
      }

  }
  
  public static class RowCollection_Lecture extends RowModify_Lecture {
    public LectureWhere where = new LectureWhere(table);
    
    public RowCollection_Lecture(LectureTable theTable)
    {
      super(theTable);
    }
    
  }

  /**
  * Insert a new empty Row.
  * <code><pre>
  * ds.table_Lecture.insertRow()
  *   .setId( null )
  *   .setProfessorId( null )
  *   .setName( null )
  *   .setSws( null )
  *   .setEcts( null )
  *   ;
  * </pre></code>
  */
  public RowBuilder_Lecture insertRow()
  {
    RowBuilder_Lecture row = new RowBuilder_Lecture(this);
    row.nextId();
    rows.add(row);
    return row;
  }
  
  /**
  * <code><pre>
  * ds.table_Lecture.insertRow()
  * </pre></code>
  */
  public RowBuilder_Lecture insertRow(LectureModel rowToAdd)
  {
    RowBuilder_Lecture row = new RowBuilder_Lecture(this);
    row.setIdRaw( rowToAdd.getIdRaw() );
    row.setProfessorIdRaw( rowToAdd.getProfessorIdRaw() );
    row.setNameRaw( rowToAdd.getNameRaw() );
    row.setSwsRaw( rowToAdd.getSwsRaw() );
    row.setEctsRaw( rowToAdd.getEctsRaw() );
    rows.add(row);
    return row;
  }

  /**
  * <code><pre>
  * ds.table_Lecture.insertRow(data);
  * </pre></code>
  */
  public RowBuilder_Lecture insertRow(RowBuilder_Lecture theRow)
  {
    rows.add(theRow);
    return theRow;
  }
  
  /**
  * <code><pre>
  * ds.table_Lecture.insertRows(data);
  * </pre></code>
  */
  public void insertRows(RowBuilder_Lecture...theRows)
  {
    rows.addAll(Arrays.asList(theRows));
  }
  
  /**
  * Insert new row at the given index
  * <code><pre>
  * ds.table_Lecture.insertRowAt(3)
  *   ;
  * </pre></code>
  */
  public RowBuilder_Lecture insertRowAt(int index)
  {
    RowBuilder_Lecture row = new RowBuilder_Lecture(this);
    rows.add(index, row);
    return row;
  }
  
  /**
  * Insert new row Object at the given index
  * <code><pre>
  * ds.table_Lecture.insertRowAt(3)
  * </pre></code>
  */
  public RowBuilder_Lecture insertRowAt(int index,RowBuilder_Lecture theRow)
  {
    rows.add(index, theRow);
    return theRow;
  }
  
  /**
   * Remove a row from the builder by the given index.
   *
   * @return the deleted row
   */ 
  public RowBuilder_Lecture deleteRow(int index)
  {
    RowBuilder_Lecture rowBuilder = rows.get(index);
    rows.remove(rowBuilder);
    return rowBuilder;
  }
  
  /**
   * Remove a row from the builder
   */ 
  public RowBuilder_Lecture deleteRow(RowBuilder_Lecture rowToDelete)
  {
    rows.remove(rowToDelete);
    return rowToDelete;
  }

  /**
  * Creates a new row but does not add it to the table
  */
  public RowBuilder_Lecture newRow()
  {
    RowBuilder_Lecture row = new RowBuilder_Lecture(this);
    return row;
  }
  
  /**
  * Returns the next Object. The internal iterator is started at 
  * the first call.
  */
  public RowBuilder_Lecture next()
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
