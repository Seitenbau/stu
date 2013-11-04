package com.seitenbau.stu.database.dataset

import static com.seitenbau.stu.database.PersonDatabaseRefs.*

import com.seitenbau.stu.database.model.*
import com.seitenbau.stu.database.util.DateUtil


class DemoGroovyDataSet extends PersonDatabaseBuilder
{

  def tables() {
    teamsTable.rows {
      REF         | title                 | description         | membersize
      QA          | "Quality Assurance"   | "Verifies software" | "QA Size"()
    }

    jobsTable.rows {
      REF         | title                 | description
      SWD         | "Software Developer"  | "Creating software"
      SWT         | "Software Tester"     | "Testing software"
      TM          | "Team Manager"        | "Makes the world go round"
    }

    personsTable.rows {
      REF         | first_name            | name
      KAULBERSCH  | "Dennis"              | "Kaulbersch"
      GUITTON     | "Julien"              | "Guitton"
      BARANOWSKI  | "Christian"           | "Baranowski"
    }

  }

  def relations() {
    QA.hasMembers(KAULBERSCH, GUITTON, BARANOWSKI)
    KAULBERSCH.worksAs(SWD).engagementStart(DateUtil.getDate(2013, 4, 1, 14, 0, 0))
    GUITTON.worksAs(SWT)
    BARANOWSKI.worksAs(TM)
  }

  def "QA Size"() {
    return { personsTable.findWhere.teamId(QA).getRowCount() }
  }
}
