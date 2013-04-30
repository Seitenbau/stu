package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.dbunit.generator.DataType;
import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.Flags;
import com.seitenbau.testing.dbunit.generator.Table;

public class Generator
{
  public static void main(String[] args) throws Exception
  {
    DatabaseModel db = new DatabaseModel()
    {
      {
        database("STUExampleDB");
        packageName("com.seitenbau.testing.dbunit");
      }
    };

    Table professors = db.addTable("professor") //
        .addColumn("id", DataType.BIGINT, Flags.AutoInvokeNextIdMethod) //
        .addColumn("name", DataType.VARCHAR) //
        .addColumn("first_name", DataType.VARCHAR) //
        .addColumn("title", DataType.VARCHAR) //
        .addColumn("faculty", DataType.VARCHAR); //

    Table lectures = db.addTable("lecture") //
        .addColumn("id", DataType.BIGINT, Flags.AutoInvokeNextIdMethod) //
        .addColumn("professor_id", DataType.BIGINT, professors.ref("id")) //
        .addColumn("name", DataType.VARCHAR) //
        .addColumn("sws", DataType.INTEGER) //
        .addColumn("ects", DataType.INTEGER); //

    Table exams = db.addTable("exam") //
        .addColumn("id", DataType.BIGINT, Flags.AutoInvokeNextIdMethod) //
        .addColumn("lecture_id", DataType.BIGINT, lectures.ref("id")) //
        .addColumn("lecture_type", DataType.VARCHAR) //
        .addColumn("point_in_time", DataType.DATE); //

    Table students = db.addTable("student") //
        .addColumn("student_number", DataType.BIGINT, Flags.AddNextIdMethod) //
        .addColumn("name", DataType.VARCHAR) //
        .addColumn("first_name", DataType.VARCHAR) //
        .addColumn("degree_course", DataType.VARCHAR) //
        .addColumn("semester", DataType.INTEGER) //
        .addColumn("enrolled_since", DataType.DATE); //

    db.addTable("give_lecture") //
        .addColumn("professor_id", DataType.BIGINT, professors.ref("id")) //
        .addColumn("exam_id", DataType.BIGINT, exams.ref("id")); //

    db.addTable("participate") //
        .addColumn("student_id", DataType.BIGINT, students.ref("student_number")) //
        .addColumn("lecture_id", DataType.BIGINT, lectures.ref("id")); //

    db.addTable("is_tutor") //
        .addColumn("student_id", DataType.BIGINT, students.ref("student_number")) //
        .addColumn("lecture_id", DataType.BIGINT, lectures.ref("id")); //

    db.addTable("attend") //
        .addColumn("student_id", DataType.BIGINT, students.ref("student_number")) //
        .addColumn("lecture_id", DataType.BIGINT, exams.ref("id")); //

    db.generate();
  }
}
