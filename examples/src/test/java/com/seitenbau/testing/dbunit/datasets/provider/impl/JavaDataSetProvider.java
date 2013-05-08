package com.seitenbau.testing.dbunit.datasets.provider.impl;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.DataType;

import com.seitenbau.testing.dbunit.datasets.Parameters;
import com.seitenbau.testing.dbunit.datasets.provider.DataSetProvider;

public class JavaDataSetProvider implements DataSetProvider
{
  private IDataSet dataSet;

  public JavaDataSetProvider()
  {
    dataSet = createDataSet();
  }

  private IDataSet createDataSet()
  {
    DefaultDataSet dataSet = new DefaultDataSet();
    try
    {

      DefaultTable professor = new DefaultTable("PROFESSOR", new Column[] { //
          new Column("id", DataType.BIGINT), //
          new Column("first_name", DataType.VARCHAR), //
              new Column("name", DataType.VARCHAR), //
              new Column("faculty", DataType.VARCHAR), //
              new Column("title", DataType.VARCHAR), //
          });
      professor.addRow(new Object[] { //
          Parameters.Professor.HANSI_ID, //
          "Hansi", //
              "Krankl", //
              "Media", //
              "Dipl.-Med.-Sys.-Wiss.", //
          });
      professor.addRow(new Object[] { //
          Parameters.Professor.PAUL_ID, //
          "Paul", //
              "Breitner", //
              "Architecture", //
              "Dr.", //
          });
      professor.addRow(new Object[] { //
          Parameters.Professor.MARCO_ID, //
          "Marco", //
              "Polo", //
              "Dr. Dr.", //
              "Business Management", //
          });

      DefaultTable lecture = new DefaultTable("LECTURE", new Column[] { //
          new Column("id", DataType.BIGINT), //
              new Column("name", DataType.VARCHAR), //
              new Column("sws", DataType.BIGINT), //
              new Column("ects", DataType.BIGINT), //
              new Column("professor_id", DataType.BIGINT), //
          });

      lecture.addRow(new Object[] { //
          Parameters.Lecture.SEMIOTIK_ID, //
          "Semiotik Today", //
              2, //
              10, //
              Parameters.Professor.HANSI_ID, //
          });

      lecture.addRow(new Object[] { //
          Parameters.Lecture.INFORMATION_RETRIEVAL_ID, //
          "Information Retrieval", //
              5, //
              12, //
              Parameters.Professor.HANSI_ID, //
          });

      DefaultTable exam = new DefaultTable("EXAM", new Column[] { //
          new Column("id", DataType.BIGINT), //
              new Column("lecture_id", DataType.BIGINT), //
              new Column("lecture_type", DataType.VARCHAR), //
              new Column("point_in_time", DataType.DATE), //
          });

      DefaultTable student = new DefaultTable("STUDENT", new Column[] { //
          new Column("student_number", DataType.BIGINT), //
              new Column("name", DataType.VARCHAR), //
              new Column("first_name", DataType.VARCHAR), //
              new Column("degree_course", DataType.VARCHAR), //
              new Column("semester", DataType.BIGINT), //
              new Column("enrolled_since", DataType.DATE), //
          });

      DefaultTable give_lecture = new DefaultTable("GIVE_LECTURE", new Column[] { //
          new Column("professor_id", DataType.BIGINT), //
              new Column("exam_id", DataType.BIGINT), //
          });

      DefaultTable participate = new DefaultTable("PARTICIPATE", new Column[] { //
          new Column("student_id", DataType.BIGINT), //
              new Column("exam_id", DataType.BIGINT), //
          });

      DefaultTable is_tutor = new DefaultTable("IS_TUTOR", new Column[] { //
          new Column("student_id", DataType.BIGINT), //
              new Column("lecture_id", DataType.BIGINT), //
          });

      DefaultTable attend = new DefaultTable("ATTEND", new Column[] { //
          new Column("student_id", DataType.BIGINT), //
              new Column("lecture_id", DataType.BIGINT), //
          });

      dataSet.addTable(professor);
      dataSet.addTable(lecture);
      dataSet.addTable(exam);
      dataSet.addTable(student);
      dataSet.addTable(give_lecture);
      dataSet.addTable(participate);
      dataSet.addTable(is_tutor);
      dataSet.addTable(attend);
    }
    catch (Exception e)
    {
      // TODO: handle exception
    }
    return dataSet;
  }

  @Override
  public IDataSet getDataSet() throws DataSetException
  {
    if (dataSet == null)
    {
      throw new DataSetException("No dataSet provided");
    }
    else
    {
      return dataSet;
    }
  }
}
