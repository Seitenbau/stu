package com.seitenbau.testing.dbunit.examples

import static com.seitenbau.testing.dbunit.PersonDatabaseRefs.*
import com.seitenbau.testing.dbunit.dsl.ScopeRegistry
import com.seitenbau.testing.dbunit.dataset.DemoGroovyDataSet

DemoGroovyDataSet dataSet = new DemoGroovyDataSet()
println "Jobtitle for SWD = " + dataSet.jobsTable.findWhere.id(SWD).title
println "Jobid for SWD = " + dataSet.jobsTable.findWhere.title(SWD).id
println "Teamtitle for id QA = " + dataSet.teamsTable.findWhere.id(QA).title
println "Foreign Jobid for Dennis = " + dataSet.personsTable.findWhere.firstName("Dennis").jobId
println "Foreign Teamid for Dennis = " + dataSet.personsTable.findWhere.firstName("Dennis").teamId
println "Job SWD member count = " + dataSet.personsTable.findWhere.jobId(SWD).rowCount
println "Team QA member size = " + dataSet.teamsTable.findWhere.id(QA).membersize
println "Team QA member count = " +  dataSet.personsTable.findWhere.teamId(QA).rowCount
println "Access Team with membersize = " + dataSet.teamsTable.findWhere.membersize(3).title

ScopeRegistry.use(dataSet)
println "Dennis' last name = " + KAULBERSCH.name
println "TM title = " + TM.title
println "Persons Row Count = " + dataSet.personsTable.rowCount

println "Persons filtered by name length = " + dataSet.personsTable.find({ it.firstName.length() < 8 }).rowCount
