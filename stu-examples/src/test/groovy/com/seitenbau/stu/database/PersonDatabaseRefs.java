package com.seitenbau.stu.database;

import com.seitenbau.stu.database.model.JobsRef;
import com.seitenbau.stu.database.model.PersonDatabaseRefFactory;
import com.seitenbau.stu.database.model.PersonsRef;
import com.seitenbau.stu.database.model.TeamsRef;

public class PersonDatabaseRefs
{
  public static JobsRef SWD = PersonDatabaseRefFactory.createJobsRef();
  public static JobsRef SWT = PersonDatabaseRefFactory.createJobsRef();
  public static JobsRef TM = PersonDatabaseRefFactory.createJobsRef();
  public static JobsRef SAT = PersonDatabaseRefFactory.createJobsRef();
  
  public static TeamsRef QA = PersonDatabaseRefFactory.createTeamsRef();
  public static TeamsRef HR = PersonDatabaseRefFactory.createTeamsRef();
  
  public static PersonsRef BARANOWSKI = PersonDatabaseRefFactory.createPersonsRef();
  public static PersonsRef GUITTON = PersonDatabaseRefFactory.createPersonsRef();
  public static PersonsRef KAULBERSCH = PersonDatabaseRefFactory.createPersonsRef();
  public static PersonsRef HOCHLEITER = PersonDatabaseRefFactory.createPersonsRef();
}
