package com.seitenbau.testing.dbunit.referencesexample


import static com.seitenbau.testing.dbunit.referencesexample.DemoRefs.*

import com.seitenbau.testdatadsl.dbunitdemo.sbtesting.dsl.DBUnitExamplesDSL
import com.seitenbau.testdatadsl.dbunitdemo.sbtesting.dsl.LehrveranstaltungRef;
import com.seitenbau.testdatadsl.dbunitdemo.sbtesting.dsl.ProfessorRef;
import com.seitenbau.testdatadsl.dbunitdemo.sbtesting.dsl.PruefungRef;
import com.seitenbau.testdatadsl.dbunitdemo.sbtesting.dsl.StudentRef;

class DemoRefs {
  
    public static ProfessorRef WAESCH = new ProfessorRef();
    public static ProfessorRef HAASE = new ProfessorRef();
    
    public static LehrveranstaltungRef VSYS = new LehrveranstaltungRef();
    public static LehrveranstaltungRef DPATTERNS = new LehrveranstaltungRef();
  
    public static PruefungRef P_VSYS = new PruefungRef();
    public static PruefungRef P_DPATTERNS = new PruefungRef();
  
    public static StudentRef MOLL = new StudentRef();
    public static StudentRef MUSTERMANN = new StudentRef();
}

DBUnitExamplesDSL hochschule = new DBUnitExamplesDSL()
hochschule.tables {
  
  professorTable.rows {
    REF    | name    | vorname  | titel            | fakultaet
    WAESCH | "Wäsch" | "Jürgen" | "Prof. Dr.-Ing." | "Informatik"
    HAASE  | "Haase" | "Oliver" | "Prof. Dr."      | "Informatik"

    REF    | id | name    
    WAESCH | 5  | "Test" 

    id | name    
    5  | "Test 2"

  }

  lehrveranstaltungTable.rows {
    REF       | name                | sws | ects | professor
    VSYS      | "Verteilte Systeme" | 4   | 5    | HAASE
    DPATTERNS | "Design Patterns"   | 4   | 3    | HAASE
  }

  pruefungTable.rows {
    REF         | lehrveranstaltung | typ   | zeitpunkt
    P_VSYS      | VSYS              | "K90" | DateUtil.getDate(2013, 4, 1, 14, 0, 0)
    P_DPATTERNS | DPATTERNS         | "M30" | DateUtil.getDate(2013, 1, 6, 12, 0, 0)
  }

  studentTable.rows {
    REF        | matrikelnummer | name         | vorname    | studiengang | semester | immatrikuliert_seit
    MOLL       | 287336         | "Moll"       | "Nikolaus" | "MSI"       | 4        | DateUtil.getDate(2011, 9, 1)
    MUSTERMANN | 123456         | "Mustermann" | "Max"      | "BIT"       | 3        | DateUtil.getDate(2012, 3, 1)
  }

  beaufsichtigtTable.rows {
    professor | pruefung
    HAASE     | P_DPATTERNS
  }

}

hochschule.relations {
  HAASE.leitet(DPATTERNS)
  DPATTERNS.geleitetVon(HAASE)
  WAESCH.beaufsichtigt(P_VSYS)
  // HAASE.beaufsichtigt(P_DPATTERNS)
  MOLL.besucht(VSYS)
  MOLL.istTutor(VSYS)
  P_VSYS.geschriebenVon(MOLL) //MOLL.schreibt(P_VSYS)
  MUSTERMANN.besucht(DPATTERNS)
}

println hochschule.createDataSet()
println "Vor Änderung [erwartet: Haase]: " + hochschule.dataset.table_Professor.findWhere.id(2).getName()

hochschule.tables {
  professorTable.rows {
    REF    | name
    HAASE  | "Und nochmal"
  }
}
println "Nach Änderung [erwartet: Und nochmal]: " + hochschule.dataset.table_Professor.findWhere.id(2).getName()
hochschule.tables {
  professorTable.rows {
    id | name
    2  | "auch id geht"
  }
}
println "Nach Änderung 2 [erwartet: auch id geht]: " + hochschule.dataset.table_Professor.findWhere.id(2).getName()
println "Wäsch [erwartet: Test 2]: " + hochschule.dataset.table_Professor.findWhere.id(5).getName()

try {
  hochschule.tables {
    professorTable.rows {
      REF    | id
      WAESCH | 1
    }
  }
  println "Should have thrown exception..."
}
catch (Exception) {
  println "ID redefinition detected"
}


