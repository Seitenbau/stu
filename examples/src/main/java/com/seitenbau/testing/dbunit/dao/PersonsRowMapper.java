package com.seitenbau.testing.dbunit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PersonsRowMapper implements RowMapper<Person>
{

  @Override
  public Person mapRow(ResultSet rs, int rowNum) throws SQLException
  {
    PersonResultSetExtractor extractor = new PersonResultSetExtractor();
    Person person = extractor.extractData(rs);
    return person;
  }

}
