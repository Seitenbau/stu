package com.seitenbau.testing.hochschule;

import com.seitenbau.testing.hochschule.model.LehrveranstaltungRef;
import com.seitenbau.testing.hochschule.model.ProfessorRef;
import com.seitenbau.testing.hochschule.model.PruefungRef;
import com.seitenbau.testing.hochschule.model.RefFactory;
import com.seitenbau.testing.hochschule.model.StudentRef;

public class HochschuleRefs {

  public static ProfessorRef WAESCH = RefFactory.createProfessorRef();
  public static ProfessorRef HAASE = RefFactory.createProfessorRef();
  public static ProfessorRef PROF3 = RefFactory.createProfessorRef();
  public static ProfessorRef PROF4 = RefFactory.createProfessorRef();
  public static ProfessorRef PROF5 = RefFactory.createProfessorRef();

  public static LehrveranstaltungRef VSYS = RefFactory.createLehrveranstaltungRef();
  public static LehrveranstaltungRef DPATTERNS = RefFactory.createLehrveranstaltungRef();
  public static LehrveranstaltungRef PROGR = RefFactory.createLehrveranstaltungRef();
  public static LehrveranstaltungRef SOFTARCH = RefFactory.createLehrveranstaltungRef();
  public static LehrveranstaltungRef LATEX = RefFactory.createLehrveranstaltungRef();

  public static PruefungRef P_VSYS = RefFactory.createPruefungRef();
  public static PruefungRef P_DPATTERNS = RefFactory.createPruefungRef();
  public static PruefungRef P_PROGR = RefFactory.createPruefungRef();
  public static PruefungRef P_SOFTARCH = RefFactory.createPruefungRef();

  public static StudentRef MOLL = RefFactory.createStudentRef();
  public static StudentRef MUSTERMANN = RefFactory.createStudentRef();
  public static StudentRef STUDENT3 = RefFactory.createStudentRef();
  public static StudentRef STUDENT4 = RefFactory.createStudentRef();
  public static StudentRef STUDENT5 = RefFactory.createStudentRef();

}