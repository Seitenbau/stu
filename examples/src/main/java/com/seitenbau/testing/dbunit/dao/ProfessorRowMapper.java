package com.seitenbau.testing.dbunit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProfessorRowMapper implements RowMapper<Professor>
{

  @Override
  public Professor mapRow(ResultSet rs, int rowNum) throws SQLException
  {
    ProfessorResultSetExtractor extractor = new ProfessorResultSetExtractor();
    Professor professor = extractor.extractData(rs);
    return professor;
  }

}
