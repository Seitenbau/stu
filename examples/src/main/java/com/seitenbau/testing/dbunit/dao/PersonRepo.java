package com.seitenbau.testing.dbunit.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class PersonRepo
{
  protected static final String DB_PERSONS_TABLE_NAME = "persons";

  private static final String ALL_PERSONS_COLUMNS = //
  "id" + ", " + // 
      "first_name" + ", " + //
      "name" + ", " + //
      "job_id" + ", " + //
      "team_id";

  private static final String SQL_SELECT_ALL_PERSONS = "SELECT * FROM " + DB_PERSONS_TABLE_NAME;

  private static final String SQL_INSERT_PERSON = //
  "INSERT INTO " + DB_PERSONS_TABLE_NAME + //
      " (" + ALL_PERSONS_COLUMNS + ")" + //
      " VALUES (?, ?, ?, ?, ?)";

  private static DataSource dataSource;

  @Transactional(readOnly = true)
  public List<Person> getAll()
  {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    List<Person> selectedPersons = select.query(SQL_SELECT_ALL_PERSONS, new PersonsRowMapper());

    return selectedPersons;
  }

  @Transactional(readOnly = false)
  public Person add(Person person)
  {
    JdbcTemplate insert = new JdbcTemplate(dataSource);
    person.setId(RandomUtils.nextInt());
    int rows = insert.update(//
        SQL_INSERT_PERSON, //
        new Object[] {//
        person.getId(), //
            person.getFirstName(), //
            person.getName(), //
            person.getJob(), //
            person.getTeam() //
        });

    if (rows == 1)
    {
      return person;
    }

    return null;
  }

  public void setDataSource(DataSource dataSource)
  {
    PersonRepo.dataSource = dataSource;
  }

}
