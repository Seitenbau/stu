package model;

import com.seitenbau.stu.dbunit.generator.DataType;
import com.seitenbau.stu.dbunit.generator.DatabaseModel;
import com.seitenbau.stu.dbunit.generator.Table;

public class PersonDatabaseModel extends DatabaseModel
{
  public PersonDatabaseModel()
  {
    database("PersonDatabase");
    packageName("com.seitenbau.stu.dbunit.model");
    enableTableModelClassesGeneration();
    //disbaleTableDSLGeneration();

    Table jobs = table("jobs") //
        .description("The table containing the jobs of a great company")
        .column("id", DataType.BIGINT) //
          .defaultIdentifier() //
          .autoInvokeNext() //
          // .autoIncrement() // requires DBUnit 2.4.3 or later
        .column("title", DataType.VARCHAR) //
          .description("The job title") //
          .setFlag("any_custom_flag") //
        .column("description", DataType.VARCHAR) //
          .description("The description of the job") //
      .build();

    Table teams = table("teams") //
        .description("The table containing the teams of a great company")
        .column("id", DataType.BIGINT) //
          .defaultIdentifier() //
          .autoInvokeNext() //
        .column("title", DataType.VARCHAR) //
        .column("description", DataType.VARCHAR) //
        .column("membersize", DataType.BIGINT) //
      .build();

    Table persons = table("persons") //
        .description("The table containing the staff of a great company")
        .column("id", DataType.BIGINT) //
          .defaultIdentifier() //
          .autoInvokeNext() //
        .column("first_name", DataType.VARCHAR) //
        .column("name", DataType.VARCHAR) //
          .description("Actually this column represents the last name of a person")
        .column("team_id", DataType.BIGINT) //
          .reference //
            .local
              .name("belongsTo")
              .description("Assigns the person to a team")
            .foreign(teams)
              .name("hasMembers")
              .description("Assigns the team to one or more persons")
      .build(); //

    associativeTable("person_job")
        .description("The table that holds relations of persons to jobs")
        .column("person_id", DataType.BIGINT)
          .reference
            .foreign(persons)
              .name("worksAs")
              .description("Assigns a job to the person")
        .column("job_id", DataType.BIGINT)
          .reference
            .foreign(jobs)
              .name("performedBy")
              .description("Assigns a person to the job")
        .column("engagement_start", DataType.DATE)
      .build();
  }

}
