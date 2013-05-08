package com.seitenbau.testing.dbunit.datasets.provider.impl;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.DataType;

import com.seitenbau.testing.dbunit.datasets.Parameters;
import com.seitenbau.testing.dbunit.datasets.provider.DataSetProvider;

public class JavaDataSetProvider implements DataSetProvider
{
  private IDataSet dataSet;

  public JavaDataSetProvider()
  {
    dataSet = createDataSet();
  }

  private IDataSet createDataSet()
  {
    DefaultDataSet dataSet = new DefaultDataSet();
    try
    {

      DefaultTable persons = new DefaultTable("persons", new Column[] { //
          new Column("id", DataType.BIGINT), //
              new Column("first_name", DataType.VARCHAR), //
              new Column("name", DataType.VARCHAR), //
              new Column("job_id", DataType.BIGINT), //
              new Column("team_id", DataType.BIGINT), //
          });
      persons.addRow(new Object[] { //
          Parameters.Persons.DENNIS, //
              "Dennis", //
              "Kaulbersch", //
              Parameters.Jobs.SOFTWARE_DEVELOPER, //
              Parameters.Teams.QUALITY_ASSURANCE, //
          });
      persons.addRow(new Object[] { //
          Parameters.Persons.JULIEN, //
              "Julien", //
              "Guitton", //
              Parameters.Jobs.SOFTWARE_TESTER, //
              Parameters.Teams.QUALITY_ASSURANCE, //
          });
      persons.addRow(new Object[] { //
          Parameters.Persons.CHRISTIAN, //
              "Christian", //
              "Baranowski", //
              Parameters.Jobs.TEAM_MANAGER, //
              Parameters.Teams.QUALITY_ASSURANCE, //
          });

      DefaultTable jobs = new DefaultTable("jobs", new Column[] { //
          new Column("id", DataType.BIGINT), //
              new Column("title", DataType.VARCHAR), //
              new Column("description", DataType.VARCHAR), //
          });

      jobs.addRow(new Object[] { //
      Parameters.Jobs.SOFTWARE_DEVELOPER, //
          "Software Developer", //
          "Creating software", //
      });

      jobs.addRow(new Object[] { //
      Parameters.Jobs.SOFTWARE_TESTER, //
          "Software Tester", //
          "Testing software", //
      });

      jobs.addRow(new Object[] { //
      Parameters.Jobs.TEAM_MANAGER, //
          "Team Manager", //
          "Nobody knows", //
      });

      DefaultTable teams = new DefaultTable("teams", new Column[] { //
          new Column("id", DataType.BIGINT), //
              new Column("title", DataType.VARCHAR), //
              new Column("description", DataType.VARCHAR), //
              new Column("membersize", DataType.BIGINT), //
          });

      teams.addRow(new Object[] { //
          Parameters.Teams.QUALITY_ASSURANCE, //
              "Quality Assurance", //
              "Verifies that requirments for a product is fulfilled", //
              3, //
          });

      dataSet.addTable(jobs);
      dataSet.addTable(teams);
      dataSet.addTable(persons);
    }
    catch (Exception e)
    {
      // TODO: handle exception
    }
    return dataSet;
  }

  @Override
  public IDataSet getDataSet() throws DataSetException
  {
    if (dataSet == null)
    {
      throw new DataSetException("No dataSet provided");
    }
    else
    {
      return dataSet;
    }
  }
}
