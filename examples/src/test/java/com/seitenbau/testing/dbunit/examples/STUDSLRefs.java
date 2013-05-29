package com.seitenbau.testing.dbunit.examples;

import com.seitenbau.testing.dbunit.model.dsl.JobsRef;
import com.seitenbau.testing.dbunit.model.dsl.PersonsRef;
import com.seitenbau.testing.dbunit.model.dsl.RefFactory;
import com.seitenbau.testing.dbunit.model.dsl.TeamsRef;

public class STUDSLRefs
{
  public static JobsRef SWD = RefFactory.createJobsRef();
  public static JobsRef SWT = RefFactory.createJobsRef();
  public static JobsRef TM = RefFactory.createJobsRef();
  
  public static TeamsRef QA = RefFactory.createTeamsRef();
  
  public static PersonsRef KAULBERSCH = RefFactory.createPersonsRef();
  public static PersonsRef GUITTON = RefFactory.createPersonsRef();
  public static PersonsRef BARANOWSKI = RefFactory.createPersonsRef();
}
