package com.seitenbau.stu.dbunit.dataset

import static com.seitenbau.stu.dbunit.PersonDatabaseRefs.*

import com.seitenbau.stu.dbunit.model.PersonDatabaseBuilder

class ExtendedDemoGroovyDataSet extends PersonDatabaseBuilder
{

  def extendsDataSet() { ExtendedWithoutRelationsDemoGroovyDataSet }

  def relations() {
    HOCHLEITER.belongsTo(QA)
    HOCHLEITER.worksAs(SWD)
    HOCHLEITER.worksAs(SWT)
  }

}
