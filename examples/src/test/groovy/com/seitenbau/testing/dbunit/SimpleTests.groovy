package com.seitenbau.testing.dbunit

import static com.seitenbau.testing.dbunit.SampleReferences.*;

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
        REF               | id  | title                   | description
        SOFTWAREDEVELOPER | 1   | "Software Developer"    | "Creating software"
        SOFTWARETESTER    | 2   | "Software Tester"       | "Testing software"
        TEAMMANAGER       | 3   | "Team Manager"          | "Makes the world go round"
      }

      teamsTable.rows {
        REF             | id  | title                   | description         | membersize
        QUALITYASSURANCE| 1   | "Quality Assurance"     | "Verifies software" | 3
      }

      personsTable.rows {
        REF           | id  | first_name              | name                | job_id            | team_id
        KAULBERSCH    | 1   | "Dennis"                | "Kaulbersch"        | SOFTWAREDEVELOPER | QUALITYASSURANCE
        GUITTON       | 2   | "Julien"                | "Guitton"           | SOFTWARETESTER    | QUALITYASSURANCE
        BARANOWSKI    | 3   | "Christian"             | "Baranowski"        | TEAMMANAGER       | QUALITYASSURANCE
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
