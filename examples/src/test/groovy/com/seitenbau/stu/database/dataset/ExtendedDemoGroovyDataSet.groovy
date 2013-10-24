package com.seitenbau.stu.database.dataset

import static com.seitenbau.stu.database.PersonDatabaseRefs.*

import com.seitenbau.stu.database.model.PersonDatabaseBuilder

class ExtendedDemoGroovyDataSet extends PersonDatabaseBuilder
{

  def extendsDataSet() { ExtendedWithoutRelationsDemoGroovyDataSet }

  def relations() {
    HOCHLEITER.belongsTo(QA)
    HOCHLEITER.worksAs(SWD)
    HOCHLEITER.worksAs(SWT)
  }

}
