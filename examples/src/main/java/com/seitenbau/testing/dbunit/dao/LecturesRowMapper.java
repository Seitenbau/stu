package com.seitenbau.testing.dbunit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LecturesRowMapper implements RowMapper<Lecture>
{

  @Override
  public Lecture mapRow(ResultSet rs, int rowNum) throws SQLException
  {
    LectureResultSetExtractor extractor = new LectureResultSetExtractor();
    Lecture lecture = extractor.extractData(rs);
    return lecture;
  }

}
