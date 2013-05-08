package com.seitenbau.testing.dbunit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class JobResultSetExtractor implements ResultSetExtractor<Job>
{

  @Override
  public Job extractData(ResultSet rs) throws SQLException, DataAccessException
  {
    Job job = new Job();
    job.setId(rs.getInt("id"));
    job.setTitle(rs.getString("title"));
    job.setDescription(rs.getString("description"));
    return job;
  }

}
