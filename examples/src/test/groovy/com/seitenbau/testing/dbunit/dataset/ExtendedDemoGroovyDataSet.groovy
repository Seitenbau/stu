package com.seitenbau.testing.dbunit.dataset

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*

import com.seitenbau.testing.dbunit.model.PersonDatabaseBuilder

class ExtendedDemoGroovyDataSet extends PersonDatabaseBuilder
{

  def extendsDataSet() { ExtendedWithoutRelationsDemoGroovyDataSet }

  def relations() {
    HOCHLEITER.belongsTo(QA)
    HOCHLEITER.worksAs(SWD)
    HOCHLEITER.worksAs(SWT)
  }

}
