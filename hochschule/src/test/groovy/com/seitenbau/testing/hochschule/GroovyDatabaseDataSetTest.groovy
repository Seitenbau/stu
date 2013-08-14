package com.seitenbau.testing.hochschule

import static com.seitenbau.testing.hochschule.HochschuleRefs.*
import static org.fest.assertions.Assertions.assertThat

import javax.sql.DataSource

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import com.seitenbau.testing.dbunit.extend.impl.ApacheDerbySequenceReset
import com.seitenbau.testing.dbunit.rule.DatabaseSetup
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule
import com.seitenbau.testing.dbunit.rule.InjectDataSet
import com.seitenbau.testing.hochschule.dataset.*
import com.seitenbau.testing.hochschule.model.HochschuleBuilder
import com.seitenbau.testing.util.Future

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=[HochschuleContext])
class GroovyDatabaseDataSetTest {

  @Autowired
  DataSource dataSource;

  @Rule
  public DatabaseTesterRule dbTester =
    new DatabaseTesterRule(new Future<DataSource>(){
       @Override
       public DataSource getFuture()
       {
         return dataSource;
       }
     }).addCleanAction(new ApacheDerbySequenceReset().autoDerivateFromTablename("_SEQ"));

  @Autowired
  HochschuleService sut

  @InjectDataSet
  HochschuleBuilder dataSet;


  @Test
  @DatabaseSetup(prepare = HochschuleDataSet.class)
  void addLehrveranstaltung() {
    // prepare
    Lehrveranstaltung lv = new Lehrveranstaltung()
    lv.setName("Programmieren")
    lv.setProfessor(HAASE.id)
    lv.setSws(4)
    lv.setEcts(6.0)

    // execute
    def addedLv = sut.addLehrveranstaltung(lv)

    // verify
    dataSet.lehrveranstaltungTable.rows {

      id         | professor | name            | sws | ects
      addedLv.id | HAASE     | "Programmieren" | 4   | 6.0

    }

    dbTester.assertDataBase(dataSet)
  }

  @Test
  @DatabaseSetup(prepare = ExtendedHochschuleDataSet.class)
  public void assignedLehrveranstaltungen() throws Exception {
    // prepare

    // execute

    // verify
    dbTester.assertDataBase(dataSet);
  }

}
