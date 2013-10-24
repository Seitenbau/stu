package com.seitenbau.stu.dbunit.datasets;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.datatype.DataType;

import com.seitenbau.stu.dbunit.util.DateUtil;


public class JavaDataSet extends DefaultDataSet
{

  public JavaDataSet() throws DataSetException
  {
    DefaultTable persons = new DefaultTable("persons", new Column[] { //
        new Column("id", DataType.BIGINT), //
            new Column("first_name", DataType.VARCHAR), //
            new Column("name", DataType.VARCHAR), //
            new Column("team_id", DataType.BIGINT), //
        });
    persons.addRow(new Object[] { //
        Parameters.Persons.DENNIS, //
            "Dennis", //
            "Kaulbersch", //
            Parameters.Teams.QUALITY_ASSURANCE, //
        });
    persons.addRow(new Object[] { //
        Parameters.Persons.JULIEN, //
            "Julien", //
            "Guitton", //
            Parameters.Teams.QUALITY_ASSURANCE, //
        });
    persons.addRow(new Object[] { //
        Parameters.Persons.CHRISTIAN, //
            "Christian", //
            "Baranowski", //
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
        "Makes the world go round", //
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
            "Verifies software", //
            3, //
        });

    DefaultTable personJobs = new DefaultTable("person_job", new Column[] { //
        new Column("person_id", DataType.BIGINT), //
        new Column("job_id", DataType.BIGINT), //
        new Column("engagement_start", DataType.DATE), //
        });

    personJobs.addRow(new Object[] { //
        Parameters.Persons.DENNIS, //
        Parameters.Jobs.SOFTWARE_DEVELOPER, //
        DateUtil.getDate(2013, 4, 1, 14, 0, 0),
    });

    personJobs.addRow(new Object[] { //
        Parameters.Persons.JULIEN, //
        Parameters.Jobs.SOFTWARE_TESTER, //
        null,
    });

    personJobs.addRow(new Object[] { //
        Parameters.Persons.CHRISTIAN, //
        Parameters.Jobs.TEAM_MANAGER, //
        null,
    });

    addTable(jobs);
    addTable(teams);
    addTable(persons);
    addTable(personJobs);
  }

}
