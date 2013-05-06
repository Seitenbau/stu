package com.seitenbau.testing.dbunit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ProfessorResultSetExtractor implements ResultSetExtractor<Professor>
{

  @Override
  public Professor extractData(ResultSet rs) throws SQLException, DataAccessException
  {
    Professor professor = new Professor();
    professor.setId(rs.getInt(ProfessorColumnNames.ID.getColumnName()));
    professor.setName(rs.getString(ProfessorColumnNames.NAME.getColumnName()));
    professor.setFirstName(rs.getString(ProfessorColumnNames.FIRST_NAME.getColumnName()));
    professor.setTitle(rs.getString(ProfessorColumnNames.TITLE.getColumnName()));
    professor.setFaculty(rs.getString(ProfessorColumnNames.FACULTY.getColumnName()));
    return professor;
  }

}
