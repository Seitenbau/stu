package com.seitenbau.testing.dbunit.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class TeamRepo
{

  protected static final String DB_TEAM_TABLE_NAME = "teams";

  private static final String ALL_TEAM_COLUMNS = //
  TeamColumns.ID.getColumnName() + ", " + //
      TeamColumns.TITLE.getColumnName() + ", " + //
      TeamColumns.DESCRIPTION.getColumnName() + ", " + //
      TeamColumns.MEMBERSIZE.getColumnName();

  private static final String SQL_SELECT_ALL_TEAMS = "SELECT * FROM " + DB_TEAM_TABLE_NAME;

  private static final String SQL_INSERT_TEAM = //
  "INSERT INTO " + DB_TEAM_TABLE_NAME + //
      " (" + ALL_TEAM_COLUMNS + ")" + //
      " VALUES (?, ?, ?, ?)";

  private static final String SQL_DELETE_TEAM_BY_ID = "DELETE FROM " + DB_TEAM_TABLE_NAME + //
      " WHERE " + TeamColumns.ID.getColumnName() + " = (?)";

  private static DataSource dataSource;

  @Transactional(readOnly = true)
  public List<Team> getAll()
  {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    List<Team> selectedTeams = select.query(SQL_SELECT_ALL_TEAMS, new TeamsRowMapper());

    return selectedTeams;
  }

  @Transactional(readOnly = false)
  public Team add(Team team)
  {
    JdbcTemplate insert = new JdbcTemplate(dataSource);
    team.setId(RandomUtils.nextInt());
    int rows = insert.update(//
        SQL_INSERT_TEAM, //
        new Object[] {//
        team.getId(), //
            team.getTitle(), //
            team.getDescription(), //
            team.getMembersize() //
        });

    if (rows == 1)
    {
      return team;
    }

    return null;
  }
  
  @Transactional(readOnly = false)
  public int remove(Team team)
  {
    JdbcTemplate delete = new JdbcTemplate(dataSource);

    int amountOfDeletedItems = delete.update(//
        SQL_DELETE_TEAM_BY_ID, //
        new Object[] {team.getId()});

    return amountOfDeletedItems;
  }

  public void setDataSource(DataSource dataSource)
  {
    TeamRepo.dataSource = dataSource;
  }

}
