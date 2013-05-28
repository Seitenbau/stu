package com.seitenbau.testing.dbunit.demomodel.dsl;

public class RefFactory 
{

  public static JobsRef createJobsRef()
  {
    return new JobsRef();
  }

  public static TeamsRef createTeamsRef()
  {
    return new TeamsRef();
  }

  public static PersonsRef createPersonsRef()
  {
    return new PersonsRef();
  }
}