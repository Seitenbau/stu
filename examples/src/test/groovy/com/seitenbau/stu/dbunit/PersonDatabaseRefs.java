package com.seitenbau.stu.dbunit;

import com.seitenbau.stu.dbunit.model.JobsRef;
import com.seitenbau.stu.dbunit.model.PersonsRef;
import com.seitenbau.stu.dbunit.model.RefFactory;
import com.seitenbau.stu.dbunit.model.TeamsRef;

public class PersonDatabaseRefs
{
  public static JobsRef SWD = RefFactory.createJobsRef();
  public static JobsRef SWT = RefFactory.createJobsRef();
  public static JobsRef TM = RefFactory.createJobsRef();
  public static JobsRef SAT = RefFactory.createJobsRef();
  
  public static TeamsRef QA = RefFactory.createTeamsRef();
  public static TeamsRef HR = RefFactory.createTeamsRef();
  
  public static PersonsRef BARANOWSKI = RefFactory.createPersonsRef();
  public static PersonsRef GUITTON = RefFactory.createPersonsRef();
  public static PersonsRef KAULBERSCH = RefFactory.createPersonsRef();
  public static PersonsRef HOCHLEITER = RefFactory.createPersonsRef();
}
