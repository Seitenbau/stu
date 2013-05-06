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
    professor.setId(rs.getInt("id"));
    professor.setName(rs.getString("name"));
    professor.setFirstName(rs.getString("first_name"));
    professor.setTitle(rs.getString("title"));
    professor.setFaculty(rs.getString("faculty"));
    return professor;
  }

}
