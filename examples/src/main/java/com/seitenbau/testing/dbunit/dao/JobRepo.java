package com.seitenbau.testing.dbunit.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class JobRepo
{
  protected static final String DB_JOBS_TABLE_NAME = "jobs";

  private static final String ALL_JOBS_COLUMNS = //
  "id" + ", " + //
      "title" + ", " + //
      "description";

  private static final String SQL_SELECT_ALL_JOBS = "SELECT * FROM " + DB_JOBS_TABLE_NAME;

  private static final String SQL_INSERT_JOB = //
  "INSERT INTO " + DB_JOBS_TABLE_NAME + //
      " (" + ALL_JOBS_COLUMNS + ")" + //
      " VALUES (?, ?, ?)";
  
  private static final String SQL_DELETE_JOB_BY_ID = "DELETE FROM " + DB_JOBS_TABLE_NAME + //
      " WHERE " + "id" + " = (?)";

  private static DataSource dataSource;
  
  @Transactional(readOnly = true)
  public List<Job> getAll()
  {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    List<Job> selectedJobs = select.query(SQL_SELECT_ALL_JOBS, new JobsRowMapper());

    return selectedJobs;
  }

  @Transactional(readOnly = false)
  public Job add(Job job)
  {
    JdbcTemplate insert = new JdbcTemplate(dataSource);
    job.setId(RandomUtils.nextInt());
    int rows = insert.update(//
        SQL_INSERT_JOB, //
        new Object[] {//
        job.getId(), //
            job.getTitle(), //
            job.getDescription() //
        });

    if (rows == 1)
    {
      return job;
    }

    return null;
  }
  
  @Transactional(readOnly = false)
  public int remove(Job job)
  {
    JdbcTemplate delete = new JdbcTemplate(dataSource);

    int amountOfDeletedItems = delete.update(//
        SQL_DELETE_JOB_BY_ID, //
        new Object[] {job.getId()});

    return amountOfDeletedItems;
  }

  public void setDataSource(DataSource dataSource)
  {
    JobRepo.dataSource = dataSource;
  }
}
