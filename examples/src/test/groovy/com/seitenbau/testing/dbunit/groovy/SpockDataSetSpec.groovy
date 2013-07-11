package com.seitenbau.testing.dbunit.groovy

import com.seitenbau.testing.dbunit.dsl.DataSetRegistry
import spock.lang.Unroll

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*

import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet
import com.seitenbau.testing.dbunit.extend.impl.ApacheDerbySequenceReset
import com.seitenbau.testing.dbunit.model.PersonDatabaseBuilder
import com.seitenbau.testing.dbunit.rule.DatabaseSetup
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule
import com.seitenbau.testing.dbunit.rule.InjectDataSet
import com.seitenbau.testing.personmanager.PersonManagerContext
import com.seitenbau.testing.personmanager.PersonService
import com.seitenbau.testing.util.Future
import org.junit.Rule
import org.junit.rules.ExpectedException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.sql.DataSource

@ContextConfiguration(classes=[PersonManagerContext])
class SpockDataSetSpec extends Specification {

    @Autowired
    DataSource dataSource;

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Rule
    public DatabaseTesterRule dbTester = new DatabaseTesterRule({ dataSource })
             .addCleanAction(new ApacheDerbySequenceReset().autoDerivateFromTablename("_SEQ"))

    @Autowired
    PersonService sut

    @InjectDataSet
    PersonDatabaseBuilder dataSet;

    @DatabaseSetup(prepare = DemoGroovyDataSet)
    def "find all persons"() {
       when:
          def persons = sut.findPersons()
       then:
          persons.size() == dataSet.personsTable.getRowCount()
    }

    @Unroll
    @DatabaseSetup(prepare = DemoGroovyDataSet)
    def "find person by name = '#name'"() {
        when:
            def person = sut.findPersonByName(name)
        then:
            person.id        == personTableRow.id
            person.name      == personTableRow.name
            person.firstName == personTableRow.firstName
        where:
            name         | personTableRow
            "Kaulbersch" | KAULBERSCH
            "Guitton"    | GUITTON
            "Baranowski" | BARANOWSKI
    }

}
