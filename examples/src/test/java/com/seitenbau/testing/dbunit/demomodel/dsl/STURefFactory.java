package com.seitenbau.testing.dbunit.demomodel.dsl;

import com.seitenbau.testing.dbunit.demomodel.dsl.JobsRef;
import com.seitenbau.testing.dbunit.demomodel.dsl.PersonsRef;
import com.seitenbau.testing.dbunit.demomodel.dsl.TeamsRef;

public class STURefFactory 
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