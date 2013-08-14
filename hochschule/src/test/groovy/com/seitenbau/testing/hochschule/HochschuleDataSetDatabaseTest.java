package com.seitenbau.testing.hochschule;

import static com.seitenbau.testing.hochschule.HochschuleRefs.HAASE;
import static com.seitenbau.testing.hochschule.HochschuleRefs.MUSTERMANN;
import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seitenbau.testing.dbunit.extend.impl.ApacheDerbySequenceReset;
import com.seitenbau.testing.dbunit.rule.DatabaseSetup;
import com.seitenbau.testing.dbunit.rule.DatabaseTesterRule;
import com.seitenbau.testing.dbunit.rule.InjectDataSet;
import com.seitenbau.testing.hochschule.dataset.ExtendedHochschuleDataSet;
import com.seitenbau.testing.hochschule.dataset.HochschuleDataSet;
import com.seitenbau.testing.hochschule.model.HochschuleBuilder;
import com.seitenbau.testing.hochschule.model.StudentRef;
import com.seitenbau.testing.util.Future;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=HochschuleContext.class)
public class HochschuleDataSetDatabaseTest
{

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
  HochschuleService sut;

  @InjectDataSet
  HochschuleBuilder dataSet;

  @Test
  @DatabaseSetup(prepare = HochschuleDataSet.class)
  public void findProfessoren() throws Exception
  {
    // execute
    List<Professor> professoren = sut.findProfessoren();

    // verify
    assertThat(professoren).hasSize(dataSet.professorTable.getRowCount());
    dbTester.assertDataBase(dataSet);
  }

  @Test
  @DatabaseSetup(prepare = HochschuleDataSet.class)
  public void removeStudent() throws Exception {
    // prepare
    Student student = createStudent(MUSTERMANN);

    // execute
    sut.removeStudent(student);

    // verify
    dataSet.studentTable.deleteRow(MUSTERMANN);
    dataSet.besuchtTable.deleteAllAssociations(MUSTERMANN);

    dbTester.assertDataBase(dataSet);
  }

  @Test
  @DatabaseSetup(prepare = ExtendedHochschuleDataSet.class)
  public void assignedLehrveranstaltungen() throws Exception {
    // prepare
    Professor haase = new Professor();
    haase.setId(HAASE.getId());

    // execute
    List<Lehrveranstaltung> items = sut.findLehrveranstaltungen(haase);

    int count = dataSet.lehrveranstaltungTable.findWhere.professorId(HAASE).getRowCount();

    // verify
    assertThat(items).hasSize(count);
  }


  private Student createStudent(StudentRef studentRef)
  {
    Student student = new Student();
    student.setMatrikelnummer(studentRef.getMatrikelnummer());
    student.setVorname(studentRef.getVorname());
    student.setName(studentRef.getName());
    student.setStudiengang(studentRef.getStudiengang());
    student.setSemester(studentRef.getSemester());
    student.setImmatrikuliertSeit(studentRef.getImmatrikuliertSeit());
    return student;
  }
}
