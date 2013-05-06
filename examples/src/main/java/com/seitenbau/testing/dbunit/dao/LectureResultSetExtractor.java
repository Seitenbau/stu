package com.seitenbau.testing.dbunit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class LectureResultSetExtractor implements ResultSetExtractor<Lecture>
{

  @Override
  public Lecture extractData(ResultSet rs) throws SQLException, DataAccessException
  {
    Lecture lecture = new Lecture();
    lecture.setId(rs.getInt(LectureColumnNames.ID.getColumnName()));
    lecture.setTitle(rs.getString(LectureColumnNames.NAME.getColumnName()));
    lecture.setGivenBy(rs.getInt(LectureColumnNames.PROFESSOR_ID.getColumnName()));
    lecture.setWeeklyHours(rs.getInt(LectureColumnNames.SWS.getColumnName()));
    lecture.setSemesterCredits(rs.getInt(LectureColumnNames.ECTS.getColumnName()));
    
    return lecture;
  }

}
