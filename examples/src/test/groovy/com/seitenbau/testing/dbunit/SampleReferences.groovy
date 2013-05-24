package com.seitenbau.testing.dbunit

import com.seitenbau.testing.dbunit.model.dsl.JobsRef;
import com.seitenbau.testing.dbunit.model.dsl.PersonsRef;
import com.seitenbau.testing.dbunit.model.dsl.TeamsRef;

class SampleReferences {
  public static JobsRef SOFTWAREDEVELOPER = new JobsRef();
  public static JobsRef SOFTWARETESTER = new JobsRef();
  public static JobsRef TEAMMANAGER = new JobsRef();

  public static TeamsRef QUALITYASSURANCE = new TeamsRef();

  public static PersonsRef KAULBERSCH = new PersonsRef();
  public static PersonsRef GUITTON = new PersonsRef();
  public static PersonsRef BARANOWSKI = new PersonsRef();
}
