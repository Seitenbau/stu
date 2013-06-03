package com.seitenbau.testing.dbunit

import static com.seitenbau.testing.dbunit.examples.STUDSLRefs.*
import spock.lang.Specification

import com.seitenbau.testing.dbunit.model.dsl.STUDSL

class SimpleTests extends Specification {

  STUDSL sut;
  
  def setup() {
    sut = new STUDSL();
    sut.tables {
      jobsTable.rows {
        REF           | id  | title                   | description
        SWD           | 1   | "Software Developer"    | "Creating software"
        SWT           | 2   | "Software Tester"       | "Testing software"
        TM            | 3   | "Team Manager"          | "Makes the world go round"
      }
      
      personsTable.rows {
        REF           | id  | first_name              | name                | job | team
        KAULBERSCH    | 1   | "Dennis"                | "Kaulbersch"        | SWD | QA
        GUITTON       | 2   | "Julien"                | "Guitton"           | SWT | QA
        BARANOWSKI    | 3   | "Christian"             | "Baranowski"        | TM  | QA
      }
      
      // Because of the query in column membersize, it is important that the persons table
      // is defined before
      teamsTable.rows {
        REF           | id  | title                   | description         | membersize
        QA            | 1   | "Quality Assurance"     | "Verifies software" | { personsTable.findWhere.teamId(QA).getRowCount() } 
      }
    }

    sut.createDataSet()
  }
  
  def "find all persons"() {
    when: "collect all persons"
      def personcount = sut.dataset.table_Persons.rowCount
    then: "verify that 3 persons exist"
      personcount == 3
  }
  
  def "verify QA teamsize"() {
    when: "get the number of QA teammembers"
      def teamsize = sut.dataset.table_Teams.findWhere.id(1).membersize
    then: "verify that teamsize is 3"
      teamsize == 3
  }
}
