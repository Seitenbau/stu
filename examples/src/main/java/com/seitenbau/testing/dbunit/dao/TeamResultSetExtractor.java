package com.seitenbau.testing.dbunit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class TeamResultSetExtractor implements ResultSetExtractor<Team>
{

  @Override
  public Team extractData(ResultSet rs) throws SQLException, DataAccessException
  {
    Team team = new Team();
    team.setId(rs.getInt("id"));
    team.setTitle(rs.getString("title"));
    team.setDescription(rs.getString("description"));
    team.setMembersize(rs.getInt("membersize"));
    return team;
  }

}
