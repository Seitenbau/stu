package com.seitenbau.testing.dbunit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class PersonResultSetExtractor implements ResultSetExtractor<Person>
{

  @Override
  public Person extractData(ResultSet rs) throws SQLException, DataAccessException
  {
    Person person = new Person();
    person.setId(rs.getInt("id"));
    person.setFirstName(rs.getString("first_name"));
    person.setName(rs.getString("name"));
    person.setJob(rs.getInt("job_id"));
    person.setTeam(rs.getInt("team_id"));

    return person;
  }

}
