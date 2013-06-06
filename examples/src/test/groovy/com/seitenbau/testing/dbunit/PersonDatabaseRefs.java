package com.seitenbau.testing.dbunit;

import com.seitenbau.testing.dbunit.model.JobsRef;
import com.seitenbau.testing.dbunit.model.PersonsRef;
import com.seitenbau.testing.dbunit.model.RefFactory;
import com.seitenbau.testing.dbunit.model.TeamsRef;

public class PersonDatabaseRefs
{
  public static JobsRef SWD = RefFactory.createJobsRef();
  public static JobsRef SWT = RefFactory.createJobsRef();
  public static JobsRef TM = RefFactory.createJobsRef();
  public static JobsRef SAT = RefFactory.createJobsRef();
  
  public static TeamsRef QA = RefFactory.createTeamsRef();
  public static TeamsRef HR = RefFactory.createTeamsRef();
  
  public static PersonsRef KAULBERSCH = RefFactory.createPersonsRef();
  public static PersonsRef GUITTON = RefFactory.createPersonsRef();
  public static PersonsRef BARANOWSKI = RefFactory.createPersonsRef();
}
