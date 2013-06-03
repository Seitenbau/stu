package com.seitenbau.testing.dbunit

import static com.seitenbau.testing.dbunit.examples.STUDSLRefs.*;

import com.seitenbau.testing.dbunit.config.TestConfig;
import com.seitenbau.testing.dbunit.dao.Person;
import com.seitenbau.testing.dbunit.model.dsl.JobsRef;
import com.seitenbau.testing.dbunit.model.dsl.PersonsRef;
import com.seitenbau.testing.dbunit.model.dsl.STUDSL
import com.seitenbau.testing.dbunit.model.dsl.TeamsRef;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.dbunit.services.PersonService;

import javax.swing.text.html.CSS.LengthUnit;

import org.dbunit.dataset.IDataSet;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spock.lang.Specification;

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
}
