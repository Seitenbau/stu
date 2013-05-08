package com.seitenbau.testing.dbunit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class JobsRowMapper implements RowMapper<Job>
{

  @Override
  public Job mapRow(ResultSet rs, int rowNum) throws SQLException
  {
    JobResultSetExtractor extractor = new JobResultSetExtractor();
    Job job = extractor.extractData(rs);
    return job;
  }

}
