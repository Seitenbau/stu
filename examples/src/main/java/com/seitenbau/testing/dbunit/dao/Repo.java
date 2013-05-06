package com.seitenbau.testing.dbunit.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class Repo
{
  protected static final String DB_PROFESSORS_TABLE_NAME = "professor";

  private static final String ALL_PROFESSOR_COLUMNS = //
  "id" + ", " + //
      "name" + ", " + //
      "first_name" + ", " + //
      "title" + ", " + //
      "faculty";

  private static final String SQL_SELECT_ALL_PROFESSORS = "SELECT * FROM " + DB_PROFESSORS_TABLE_NAME;

  private static final String SQL_INSERT_PROFESSOR = //
  "INSERT INTO " + DB_PROFESSORS_TABLE_NAME + //
      " (" + ALL_PROFESSOR_COLUMNS + ")" + //
      " VALUES (?, ?, ?, ?, ?)";

  private static final String SQL_DELETE_PROFESSORS_BY_ID = "DELETE FROM " + DB_PROFESSORS_TABLE_NAME + //
      " WHERE " + "id" + " = (?)";

  private static DataSource dataSource;

  @Transactional(readOnly = false)
  public static boolean add(Professor professor)
  {
    professor.setId(RandomUtils.nextInt());

    JdbcTemplate insert = new JdbcTemplate(dataSource);
    int rows = insert.update(//
        SQL_INSERT_PROFESSOR, //
        new Object[] {//
        professor.getId(), //
            professor.getName(), //
            professor.getFirstName(), //
            professor.getTitle(), //
            professor.getFaculty() //
        });

    boolean result = false;
    if (rows == 1)
    {
      result = true;
    }

    return result;
  }

  @Transactional(readOnly = true)
  public static List<Professor> getAll()
  {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    List<Professor> selectedProfessors = select.query(SQL_SELECT_ALL_PROFESSORS, new ProfessorRowMapper());

    return selectedProfessors;
  }

  public static boolean update(Professor professor)
  {
    JdbcTemplate update = new JdbcTemplate(dataSource);
    ProfessorUpdateQuery updateQuery = new ProfessorUpdateQuery(update);
    int affectedRows = updateQuery.update(professor);
    
    boolean wasSuccessful = false;
    if (affectedRows == 1) {
        wasSuccessful = true;
    }

    return wasSuccessful;
  }

  @Transactional(readOnly = false)
  public static int remove(Professor professor)
  {
    JdbcTemplate delete = new JdbcTemplate(dataSource);
    int amountOfDeletedItems = delete.update(//
        SQL_DELETE_PROFESSORS_BY_ID, //
        new Object[] {professor.getId()});

    return amountOfDeletedItems;
  }

  public void setDataSource(DataSource dataSource)
  {
    Repo.dataSource = dataSource;
  }
}
