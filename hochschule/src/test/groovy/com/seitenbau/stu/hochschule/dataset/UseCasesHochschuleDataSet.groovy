package com.seitenbau.stu.hochschule.dataset

import static com.seitenbau.stu.hochschule.HochschuleRefs.*

import com.seitenbau.stu.hochschule.model.HochschuleBuilder
import com.seitenbau.stu.hochschule.util.DateUtil

class UseCasesHochschuleDataSet extends HochschuleBuilder
{

  def tables() {

    professorTable.rows {
      REF    | name     | vorname   | titel            | fakultaet
      WAESCH | "Wäsch"  | "Jürgen"  | "Prof. Dr.-Ing." | "Informatik"
      HAASE  | "Haase"  | "Oliver"  | "Prof. Dr."      | "Informatik"
      PROF3  | "Pommer" | "Pommer"  | "Prof. Dr."      | ""
      PROF4  | "Bien"   | "Maike"   | "Prof. Dr."      | ""
      PROF5  | "Helwig" | "Sibylle" | "Prof. Dr."      | ""
    }

    lehrveranstaltungTable.rows {
      REF       | name                     | sws | ects
      VSYS      | "Verteilte Systeme"      | 4   | 5
      DPATTERNS | "Design Patterns"        | 4   | 3
      PROGR     | "Programmieren"          | 4   | 6
      SOFTARCH  | "Software-Architekturen" | 6   | 7
      LATEX     | "Einführung in LaTeX"    | 2   | 2
    }

    pruefungTable.rows {
      REF         | typ   | zeitpunkt
      P_VSYS      | "K90" | DateUtil.getDate(2013, 4, 1, 14, 0, 0)
      P_DPATTERNS | "M30" | DateUtil.getDate(2013, 1, 6, 12, 0, 0)
      P_PROGR     | "K90" | DateUtil.getDate(2013, 1, 6, 12, 0, 0)
      P_SOFTARCH  | "M30" | DateUtil.getDate(2013, 1, 6, 12, 0, 0)
    }

    studentTable.rows {
      REF        | matrikelnummer | name         | vorname    | studiengang | semester | immatrikuliert_seit
      MUSTERMANN | 123456         | "Mustermann" | "Max"      | "BIT"       | 3        | DateUtil.getDate(2012, 3, 1)
      STUDENT3   | 123457         | "Krahn"      | "Vanessa"  | ""          | 0        | DateUtil.getDate(2011, 9, 1)
      STUDENT4   | 123458         | "Rasche"     | "Michelle" | ""          | 0        | DateUtil.getDate(2011, 9, 1)
      STUDENT5   | 123459         | "Seidl"      | "Daniel"   | ""          | 0        | DateUtil.getDate(2011, 9, 1)
      MOLL       | 287336         | "Moll"       | "Nikolaus" | "MSI"       | 4        | DateUtil.getDate(2011, 9, 1)
    }

  }

  def relations() {
    WAESCH.beaufsichtigt(P_VSYS)
    HAASE.leitet(VSYS, DPATTERNS)
    HAASE.beaufsichtigt(P_DPATTERNS)
    P_VSYS.stoffVon(VSYS)
    DPATTERNS.hatPruefung(P_DPATTERNS)
    MOLL.schreibt(P_VSYS).versuch(1)
    MOLL.besucht(VSYS)
    VSYS.hatTutor(MOLL)
    MUSTERMANN.besucht(DPATTERNS)
    P_SOFTARCH.stoffVon(SOFTARCH)
  }

}
