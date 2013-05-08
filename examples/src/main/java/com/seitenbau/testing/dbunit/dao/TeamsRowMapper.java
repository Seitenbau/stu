package com.seitenbau.testing.dbunit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TeamsRowMapper implements RowMapper<Team>
{

  @Override
  public Team mapRow(ResultSet rs, int rowNum) throws SQLException
  {
    TeamResultSetExtractor extractor = new TeamResultSetExtractor();
    Team team = extractor.extractData(rs);
    return team;
  }

}
