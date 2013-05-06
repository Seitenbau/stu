package com.seitenbau.testing.dbunit.dao;

import java.sql.Types;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class ProfessorUpdateQuery extends SqlUpdate
{
  private static final String PROFESSOR_UPDATE_QUERY = "UPDATE " + //
      Repo.DB_PROFESSORS_TABLE_NAME + //
      " SET " + //
      "name" + " = ?, " + //
      "first_name" + " = ?, " + //
      "title" + " = ?, " + //
      "faculty" + " = ?" + //
      " WHERE " + //
      "id" + " = ?";

  public ProfessorUpdateQuery(JdbcTemplate template)
  {
    super(template.getDataSource(), PROFESSOR_UPDATE_QUERY);

    declareParameter(new SqlParameter("name", Types.VARCHAR));
    declareParameter(new SqlParameter("first_name", Types.VARCHAR));
    declareParameter(new SqlParameter("title", Types.VARCHAR));
    declareParameter(new SqlParameter("faculty", Types.VARCHAR));
    compile();
  }

  public int update(Professor toUpdate) throws DataAccessException
  {
    int affectedRows = super.update( //
        toUpdate.getName(), //
        toUpdate.getFirstName(), //
        toUpdate.getTitle(), //
        toUpdate.getFaculty());

    return affectedRows;
  }
}
