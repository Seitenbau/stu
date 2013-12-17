package com.seitenbau.stu.database.modelgenerator.examples;

import com.seitenbau.stu.database.generator.DataType;
import com.seitenbau.stu.database.modelgenerator.ColumnModel;
import com.seitenbau.stu.database.modelgenerator.DatabaseModel;
import com.seitenbau.stu.database.modelgenerator.TableModel;

public class PersonDatabaseModel extends DatabaseModel
{
  public PersonDatabaseModel()
  {
    TableModel jobs = new TableModel("jobs");
    // jobsBuilder.description("The table containing the jobs of a great company");
    ColumnModel jobsId = new ColumnModel("id", DataType.BIGINT);
    jobsId.setIsNullable("NO");

    ColumnModel jobsTitle = new ColumnModel("title", DataType.VARCHAR);
    // jobsTitle.description("The job title");

    ColumnModel jobsDescription = new ColumnModel("description", DataType.VARCHAR);
    // jobsDescription.description("The description of the job") //

    jobs.addColumn(jobsId);
    jobs.addColumn(jobsTitle);
    jobs.addColumn(jobsDescription);

    TableModel teams = new TableModel("teams");
    // teams.description("The table containing the teams of a great company")
    ColumnModel teamsId = new ColumnModel("id", DataType.BIGINT);
    ColumnModel teamsTitle = new ColumnModel("title", DataType.VARCHAR);
    ColumnModel teamsDescription = new ColumnModel("description", DataType.VARCHAR);
    ColumnModel teamsMembersize = new ColumnModel("membersize", DataType.BIGINT);
    teams.addColumn(teamsId);
    teams.addColumn(teamsTitle);
    teams.addColumn(teamsDescription);
    teams.addColumn(teamsMembersize);

    TableModel persons = new TableModel("persons");
    // persons.description("The table containing the staff of a great company")
    ColumnModel personsId = new ColumnModel("id", DataType.BIGINT);
    ColumnModel personsFirstName = new ColumnModel("first_name", DataType.VARCHAR);
    ColumnModel personsName = new ColumnModel("name", DataType.VARCHAR);
    // personsName.description("Actually this column represents the last name of a person")
    ColumnModel personsTeamId = new ColumnModel("team_id", DataType.BIGINT);
    persons.addColumn(personsId);
    persons.addColumn(personsFirstName);
    persons.addColumn(personsName);
    persons.addColumn(personsTeamId);

    TableModel person_job = new TableModel("person_job");
    person_job.setAssociative(true);
    // person_job.description("The table that holds relations of persons to jobs");
    ColumnModel person_jobPersonId = new ColumnModel("person_id", DataType.BIGINT);
    ColumnModel person_jobJobId = new ColumnModel("job_id", DataType.BIGINT);
    ColumnModel person_jobEngagementStart = new ColumnModel("engagement_start", DataType.DATE);
    person_job.addColumn(person_jobPersonId);
    person_job.addColumn(person_jobJobId);
    person_job.addColumn(person_jobEngagementStart);

    personsTeamId.addForeignKey(teams, "id");
    // .local
    // .name("belongsTo")
    // .description("Assigns the person to a team")
    // .name("hasMembers")
    // .description("Assigns the team to one or more persons")

    person_jobPersonId.addForeignKey(persons, "id");
    // .name("worksAs")
    // .description("Assigns a job to the person")
    person_jobJobId.addForeignKey(jobs, "id");
    // .name("performedBy")
    // .description("Assigns a person to the job")
    

    add(jobs);
    add(persons);
    add(teams);
    add(person_job);
  }
}
