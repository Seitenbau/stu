package model;

import com.seitenbau.stu.database.generator.DataType;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.TableBuilder;
import com.seitenbau.stu.database.generator.values.BuchNameGenerator;
import com.seitenbau.stu.database.generator.values.DomainSpecificDataBuilder;
import com.seitenbau.stu.database.generator.values.DomainGenerator;
import com.seitenbau.stu.database.generator.values.IntegerGenerator;
import com.seitenbau.stu.database.generator.values.BuchNameGenerator;
import com.seitenbau.stu.database.generator.values.ValueGenerator;

public class TestLinkModel extends DatabaseModel {
	public TestLinkModel() {
		database("TestLink");
		packageName("com.seitenbau.stu.database.model");
		enableTableModelClassesGeneration();
		//disbaleTableDSLGeneration();

		dataSource(new DomainSpecificDataBuilder());

		TableBuilder assignment_status = table("assignment_status");
		TableBuilder assignment_types = table("assignment_types");
		TableBuilder attachments = table("attachments");
		TableBuilder builds = table("builds");
//		TableBuilder cfield_build_design_values = table("cfield_build_design_values");
//		TableBuilder cfield_design_values = table("cfield_design_values");
//		TableBuilder cfield_execution_values = table("cfield_execution_values");
//		TableBuilder cfield_node_types = table("cfield_node_types");
//		TableBuilder cfield_testplan_design_values = table("cfield_testplan_design_values");
//		TableBuilder cfield_testprojects = table("cfield_testprojects");
//		TableBuilder custom_fields = table("custom_fields");
//		TableBuilder db_version = table("db_version");
//		TableBuilder events = table("events");
//		TableBuilder executions = table("executions");
//		TableBuilder executions_bugs = table("executions_bugs");
//		TableBuilder executions_tcsteps = table("executions_tcsteps");
//		TableBuilder inventory = table("inventory");
//		TableBuilder issuetrackers = table("issuetrackers");
//		TableBuilder keywords = table("keywords");
//		TableBuilder milestones = table("milestones");
//		TableBuilder nodes_hierarchy = table("nodes_hierarchy");
//		TableBuilder node_types = table("node_types");
//		TableBuilder object_keywords = table("object_keywords");
//		TableBuilder platforms = table("platforms");
//		TableBuilder reqmgrsystems = table("reqmgrsystems");
//		TableBuilder requirements = table("requirements");
//		TableBuilder req_coverage = table("req_coverage");
//		TableBuilder req_relations = table("req_relations");
//		TableBuilder req_revisions = table("req_revisions");
//		TableBuilder req_specs = table("req_specs");
//		TableBuilder req_specs_revisions = table("req_specs_revisions");
//		TableBuilder req_versions = table("req_versions");
//		TableBuilder rights = table("rights");
//		TableBuilder risk_assignments = table("risk_assignments");
		TableBuilder roles = table("roles");
//		TableBuilder schema_version = table("schema_version");
//		TableBuilder tcsteps = table("tcsteps");
//		TableBuilder tcversions = table("tcversions");
//		TableBuilder testcase_relations = table("testcase_relations");
		TableBuilder testplans = table("testplans");
//		TableBuilder testplan_platforms = table("testplan_platforms");
//		TableBuilder testplan_tcversions = table("testplan_tcversions");
		TableBuilder testprojects = table("testprojects");
		TableBuilder testsuites = table("testsuites");
		TableBuilder transactions = table("transactions");
		TableBuilder users = table("users");
		TableBuilder user_assignments = table("user_assignments");
		TableBuilder user_group = table("user_group");

		// Menge dem Generator übergeben
		// null/not null
		// min_size, max_size
		// TIMESTAMP
		// TREE abbilden

//		// {1, open}, {2, closed}, {3, completed}, {4, todo_urgent}, {5, todo}
		assignment_status.minEntities(7)
		// length <= 10, id > 0, Unique, NotNull
				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
				// not null, <= 100
				.column("description", DataType.VARCHAR).set("\"open\"", "\"closed\"", "\"completed\"", "\"todo_urgent\"", "\"todo\"") //
				.build();

		// {1, testplan_tcversions, testcase_execution},{2, tcversions, testcase_review}
		assignment_types.minEntities(2)
		// length <= 10, id > 0, Unique, NotNull
				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
				// <= 30 or null
				.column("fk_table", DataType.VARCHAR).set("\"testplan_tcversions\"", "\"tcversions\"") //
				// not null, <= 100
				.column("description", DataType.VARCHAR).set("\"testcase_execution\"", "\"testcase_review\"") //
				.build();

		attachments.minEntities(2)
		// length <= 10, id > 0, Unique, NotNull
				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
				// length <= 10, id > 0, Unique, NotNull
				//.column("fk_id", DataType.INTEGER).reference.foreign(nodes_hierarchy) //
				// <= 250 or null
				.column("fk_table", DataType.VARCHAR).allowNull(true) //
				// <= 250 or null
				.column("title", DataType.VARCHAR).allowNull(true) //
				// <= 250 or null
				.column("description", DataType.VARCHAR).allowNull(true) //
				// <= 250 not null
				.column("file_name", DataType.VARCHAR) //
				// <= 250 or null
				.column("file_path", DataType.VARCHAR).allowNull(true) //
				// int 11, not null
				.column("file_size", DataType.INTEGER).generator(new IntegerGenerator(1, 100)) //
				// <= 250 not null
				.column("file_type", DataType.VARCHAR) //
				// DATETIME, Not null
				.column("date_added", DataType.DATE) //
				// LONGBLOB, Null
				//.column("content", DataType.BLOB).allowNull(true) //
				// int 11, not null
				.column("compression_type", DataType.INTEGER).generator(new IntegerGenerator(1, 100)) //
				.build();

		builds
		// length <= 10, id > 0, Unique, NotNull
		.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext().autoInvokeNext() //
				// length <= 10, id > 0, Unique, FOREIGN KEY
				.column("testplan_id", DataType.INTEGER).reference.foreign(testplans) //
				// not null, <= 100, UNIQUE
				.column("name", DataType.VARCHAR) //
				// null or text
				.column("notes", DataType.VARCHAR).allowNull(true) //
				// 0 or 1
				.column("active", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// 0 or 1
				.column("is_open", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// null, > 0, lenth <= 10
				.column("author_id", DataType.INTEGER).reference.foreign(users) //
				// Current Timestamp, not null
				.column("creation_ts", DataType.TIMESTAMP) //
				// DATE, not null
				.column("release_date", DataType.DATE) //
				// DATE or null
				//.column("closed_on_date", DataType.DATE).allowNull(true) //
				.build();
//
//		cfield_build_design_values
//		// length <= 10, id > 0, Unique, NotNull
//				.column("field_id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// length <= 10, id > 0, Unique, eigentlich FOREIGN KEY
//				.column("node_id", DataType.INTEGER)//.reference.foreign(nodes_hierarchy) //
//				// not null, <= 4000
//				.column("value", DataType.VARCHAR) //
//				.build();
//
//		cfield_design_values
//		// length <= 10, id > 0, Unique, NotNull
//				.column("field_id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// length <= 10, id > 0, Unique, eigentlich FOREIGN KEY
//				.column("node_id", DataType.INTEGER)//.reference.foreign(nodes_hierarchy) //
//				// not null, <= 4000
//				.column("value", DataType.VARCHAR) //
//				.build();
//
//		cfield_execution_values
//		// length <= 10, id > 0, Unique, NotNull
//				.column("field_id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// length <= 10, id > 0, Unique, eigentlich FOREIGN KEY
//				.column("execution_id", DataType.INTEGER).reference.foreign(executions) //
//				// length <= 10, id > 0, Unique, eigentlich FOREIGN KEY
//				.column("testplan_id", DataType.INTEGER).reference.foreign(testplans) //
//				// length <= 10, id > 0, Unique, eigentlich FOREIGN KEY
//				.column("tcversion_id", DataType.INTEGER)//.reference.foreign(tcversions) //
//				// not null, <= 4000
//				.column("value", DataType.VARCHAR) //
//				.build();
//
//		cfield_node_types
//		// length <= 10, id > 0, Unique, NotNull
//				.column("field_id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// length <= 10, id > 0, Unique, eigentlich FOREIGN KEY
//				.column("node_type_id", DataType.INTEGER).reference.foreign(node_types) //
//				.build();
//
//		cfield_testplan_design_values
//		// length <= 10, id > 0, Unique, NotNull
//				.column("field_id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// length <= 10, id > 0, Unique, eigentlich FOREIGN KEY
//				.column("link_id", DataType.INTEGER).generator(new IntegerGenerator(1, 100)) //
//				// not null, <= 4000
//				.column("value", DataType.VARCHAR) //
//				.build();
//
//		cfield_testprojects
//		// length <= 10, id > 0, Unique, PRIMARY
//				.column("field_id", DataType.INTEGER) //
//				// length <= 10, id > 0, Unique, eigentlich FOREIGN KEY
//				.column("testproject_id", DataType.INTEGER).reference.foreign(testprojects) //
//				// not null, SIZE = 5, > 0
//				.column("display_order", DataType.SMALLINT) //
//				// not null, SIZE = 5, > 0
//				.column("location", DataType.SMALLINT) //
//				// not null, SIZE = 1
//				.column("active", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
//				// not null, SIZE = 1
//				.column("required", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
//				// not null, SIZE = 1
//				.column("required_on_design", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
//				// not null, SIZE = 1
//				.column("required_on_execution", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
//				.build();
//
//		custom_fields
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// not null, <= 64, UNIQUE
//				.column("name", DataType.VARCHAR) //
//				// not null, <= 64
//				.column("label", DataType.VARCHAR) //
//				// not null, size 6
//				.column("type", DataType.SMALLINT) //
//				// not null, <= 4000
//				.column("possible_values", DataType.VARCHAR) //
//				// not null, <= 4000
//				.column("default_value", DataType.VARCHAR) //
//				// not null, <= 255
//				.column("valid_regexp", DataType.VARCHAR) //
//				// not null, size 10
//				.column("length_min", DataType.INTEGER) //
//				// not null, size 10
//				.column("length_max", DataType.INTEGER) //
//				// >= 0, size 3
//				.column("show_on_design", DataType.TINYINT) //
//				// >= 0, size 3
//				.column("enable_on_design", DataType.TINYINT) //
//				// >= 0, size 3
//				.column("show_on_execution", DataType.TINYINT) //
//				// >= 0, size 3
//				.column("enable_on_execution", DataType.TINYINT) //
//				// >= 0, size 3
//				.column("show_on_testplan_design", DataType.TINYINT) //
//				// >= 0, size 3
//				.column("enable_on_testplan_design", DataType.TINYINT) //
//				.build();
//
//		db_version
//		// length <= 50, Unique, NotNull
//				.column("version", DataType.VARCHAR).defaultIdentifier().autoInvokeNext() //
//				// DATETIME
//				.column("upgrade_ts", DataType.DATE) //
//				// null or text
//				.column("notes", DataType.VARCHAR).allowNull(true) //
//				.build();
//
//		events.minEntities(6)
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// length <= 10, id > 0, Unique, FOREIGN KEY
//				.column("transaction_id", DataType.INTEGER).reference.foreign(transactions) //
//				// not null, > 0, length <= 5
//				.column("log_level", DataType.SMALLINT) //
//				// null or length <= 45
//				.column("source", DataType.VARCHAR) //
//				// not null or TEXT:
//				// O:18:"tlMetaStringHelper":4:{s:5:"label";s:25:"audit_testproject_created";s:6:"params";a:1:{i:0;s:20:"Das neue Testprojekt";}s:13:"bDontLocalize";b:0;s:14:"bDontFireEvent";b:0;}
//				.column("description", DataType.VARCHAR) //
//				// Timestamp Int, Key
//				.column("fire_at", DataType.INTEGER) //
//				// {null, LOGIN, CREATE, ASSIGN, SAVE, LOCALIZATION,...}
//				.column("activity", DataType.VARCHAR).set("LOGIN", "CREATE", "ASSIGN", "SAVE", "LOCALIZATION").allowNull(true) //
//				// null, > 0, lenth <= 10
//				.column("object_id", DataType.INTEGER).reference.foreign(users) //
//				// {null, builds, users, testprojects, req_specs, users,...}
//				.column("object_type", DataType.VARCHAR).set("builds", "users", "testprojects", "req_specs").allowNull(true) //
//				.build();
//
//		executions.minEntities(5)
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// length <= 10, id > 0, Unique, FOREIGN KEY
//				.column("build_id", DataType.INTEGER).reference.foreign(builds) //
//				// length <= 10, id > 0, Unique, FOREIGN KEY, null erlaubt
//				.column("tester_id", DataType.INTEGER).reference.foreign(users) //
//				// null, DATETIME
//				.column("execution_ts", DataType.DATE) //
//				// null or length == 1
//				.column("status", DataType.CHAR).allowNull(true) //
//				// length <= 10, id > 0, Unique, FOREIGN KEY
//				.column("testplan_id", DataType.INTEGER).reference.foreign(testplans) //
//				// length <= 10, id > 0, Unique, FOREIGN KEY
//				.column("tcversion_id", DataType.INTEGER).reference.foreign(tcversions) //
//				// length <= 5, > 0, not null
//				.column("tcversion_number", DataType.SMALLINT) //
//				// length <= 10, id > 0, Unique, FOREIGN KEY
//				.column("platform_id", DataType.INTEGER).reference.foreign(platforms) //
//				// length == 1, not null
//				.column("exectution_type", DataType.TINYINT).generator(new IntegerGenerator(-1, 1)) //
//				// length <= 6,2, null
//				.column("execution_duration", DataType.DECIMAL).allowNull(true) //
//				// Text or null
//				.column("notes", DataType.VARCHAR).allowNull(true) //
//				.build();
//
//		executions_bugs
//		// length <= 10, id > 0, Unique, FOREIGN KEY
//				.column("execution_id", DataType.INTEGER) //reference.foreign(executions) //
//				// length <= 64, not null
//				.column("bug_id", DataType.VARCHAR) //
//				.build();
//
//		executions_tcsteps
//		// length <= 10, id > 0, Unique, FOREIGN KEY
//				.column("execution_id", DataType.INTEGER).defaultIdentifier().autoInvokeNext()//.reference.foreign(executions) //
//				// length <= 10, id > 0, Unique, FOREIGN KEY
//				.column("tcstep_id", DataType.INTEGER).reference.foreign(tcsteps) //
//				// Text or null
//				.column("notes", DataType.VARCHAR).allowNull(true) //
//				// 1, null
//				.column("status", DataType.CHAR).allowNull(true) //
//				.build();
//
//		inventory
//		// length <= 10, id > 0, Unique
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// length <= 10, id > 0, Unique, FOREIGN KEY
//				.column("testproject_id", DataType.INTEGER).reference.foreign(testprojects) //
//				// length <= 10, id > 0, Unique, FOREIGN KEY
//				.column("owner_id", DataType.INTEGER).reference.foreign(users) //
//				// 255, not null
//				.column("name", DataType.VARCHAR) //
//				// 255, not null
//				.column("ipaddress", DataType.VARCHAR) //
//				// TEXT null
//				.column("content", DataType.VARCHAR) //
//				// TIMESTAMP, not null, CURRENT
//				.column("creation_ts", DataType.TIMESTAMP) //
//				// TIMESTAMP, not null
//				.column("modification_ts", DataType.TIMESTAMP) //
//				.build();
//
//		issuetrackers
//		// length <= 10, id > 0, Unique
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// 100, not null, UNIQUE
//				.column("name", DataType.VARCHAR) //
//				// 10, null
//				.column("type", DataType.INTEGER).allowNull(true) //
//				// TEXT, null
//				.column("cfg", DataType.VARCHAR).allowNull(true) //
//				.build();
//
//		keywords
//		// length <= 10, id > 0, Unique, NotNull
//		.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// not null, <= 100
//				.column("keyword", DataType.VARCHAR) //
//				// foreign key
//				.column("testproject_id", DataType.INTEGER).reference.foreign(testprojects) //
//				// null, TEXT
//				.column("notes", DataType.VARCHAR).allowNull(true) //
//				.build();
//
//		milestones
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// length <= 10, id > 0, Unique, NotNull, FOREIGN
//				.column("testplan_id", DataType.INTEGER).reference.foreign(testplans) //
//				// DATE, Null
//				.column("target_date", DataType.DATE).allowNull(true) //
//				// DATE, not Null
//				.column("start_date", DataType.DATE) //
//				// TINYINT 3, not Null
//				.column("a", DataType.TINYINT) //
//				// TINYINT 3, not Null
//				.column("b", DataType.TINYINT) //
//				// TINYINT 3, not Null
//				.column("c", DataType.TINYINT) //
//				// not null, <= 100
//				.column("name", DataType.VARCHAR) //
//				.build();
//
//		nodes_hierarchy
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext().generator(new IntegerGenerator(1, 1000)) //
//				// null, <= 100
//				.column("name", DataType.VARCHAR).allowNull(true) //
//				// foreign, null
//				.column("parent_id", DataType.INTEGER).generator(new IntegerGenerator(1, 100)) //.reference.local.foreign(nodes_hierarchy) //
//				// foreign, not null
//				.column("node_type_id", DataType.INTEGER).reference.foreign(node_types) //
//				// INT 10, null
//				.column("node_order", DataType.INTEGER).generator(new IntegerGenerator(1, 10)) //
//				.build();
//
//		// {testproject, testsuite, testcase, testcase_version, testplan,
//		// requirement_spec, requirement, requirement_version, testcase_step,
//		// requirement_revision, requirement_spec_revision, build, plattform,
//		// user}
//		node_types.minEntities(10)
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext().generator(new IntegerGenerator(1, 1000)) //
////
//				.column("description", DataType.VARCHAR).set("\"testproject\"", "\"testsuite\"", "\"testcase\"", "\"testcase_version\"", "\"testplan\"",  "\"requirement_spec\"", "\"requirement\"", "\"requirement_version\"", "\"testcase_step\"", "\"requirement_revision\"", "\"requirement_spec_revision\"", "\"build\"", "\"plattform\"", "\"user\"") //
//				.build();
//
//		object_keywords
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// 10, not null
//				.column("fk_id", DataType.INTEGER) //
//				// VARCHAR 30, null
//				.column("fk_table", DataType.VARCHAR).allowNull(true) //
//				// not null, foreign
//				.column("keyword_id", DataType.INTEGER).reference.foreign(keywords) //
//				.build();
//
//		platforms
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext().generator(new IntegerGenerator(1, 100000)) //
//				// VARCHAR 100, not null, UNIQUE
//				.column("name", DataType.VARCHAR).generator(new BuchNameGenerator()) //
//				// 10, not null
//				.column("testproject_id", DataType.INTEGER).reference.foreign(testprojects) //
//				// text, not null
//				.column("notes", DataType.VARCHAR).generator(new BuchNameGenerator()) //
//				.build();
//
//		reqmgrsystems
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// VARCHAR 100, not null, UNIQUE
//				.column("name", DataType.VARCHAR) //
//				// 10, null
//				.column("type", DataType.INTEGER).allowNull(true) //
//				// text, null
//				.column("cfg", DataType.VARCHAR).allowNull(true) //
//				.build();
//
//		requirements
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() // nodes_hierachy_id
//				// foreign und unique mit req_doc_id
//				.column("srs_id", DataType.INTEGER) //
//				// length <= 64, not null, UNIQUE
//				.column("req_doc_id", DataType.VARCHAR) //
//				.build();
//
//		req_coverage
//		// length <= 10, id > 0, NotNull
//				.column("req_id", DataType.INTEGER) //
//
//				// length <= 10, id > 0, NotNull / RIchtiger Foreign??
//				.column("testcase_id", DataType.INTEGER).reference.foreign(testcase_relations) //
//				// length <= 10, id > 0, Null
//				.column("author_id", DataType.INTEGER).allowNull(true).reference.foreign(users) //
//				// TIMESTAMP
//				.column("creation_ts", DataType.TIMESTAMP) //
//				// length <= 10, id > 0, OR Null / RIchtiger Foreign??
//				.column("review_requester_id", DataType.INTEGER).allowNull(true).reference.foreign(users) //
//				// TIMESTAMP OR Null
//				.column("review_request_ts", DataType.DATE).allowNull(true) //
//				.build();
//
//		req_relations
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// length <= 10, id > 0, NotNull
//				.column("source_id", DataType.INTEGER) // Foreign-Tabelle???
//				// length <= 10, id > 0, NotNull
//				.column("destination_id", DataType.INTEGER) // Foreign-Tabelle???
//				//
//				.column("relation_type", DataType.SMALLINT) //
//				// length <= 10, id > 0, Null
//				.column("author_id", DataType.INTEGER).allowNull(true).reference.foreign(users) //
//				// TIMESTAMP
//				.column("creation_ts", DataType.TIMESTAMP) //
//				.build();
//
//		req_revisions
//		// length <= 10, id > 0, NotNull
//				.column("parent_id", DataType.INTEGER)//.reference.foreign(req_revisions) //
//				// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// SMALLINT 5, > 0
//				.column("revision", DataType.SMALLINT) //
//				// length 64, Null
//				.column("req_doc_id", DataType.VARCHAR) //
//				// 100, null
//				.column("name", DataType.VARCHAR) //
//				// Text, Null
//				.column("scope", DataType.VARCHAR) //
//				// 1, notnull
//				.column("status", DataType.CHAR) //
//				// 1, null
//				.column("type", DataType.CHAR) //
//				// notnull, 0 or 1
//				.column("active", DataType.TINYINT) //
//				// notnull, 0 or 1
//				.column("is_open", DataType.TINYINT) //
//				// not null, size 10
//				.column("expected_coverage", DataType.INTEGER) //
//				// NULL, Text
//				.column("log_message", DataType.VARCHAR) //
//				// length <= 10, id > 0, Null
//				.column("author_id", DataType.INTEGER).allowNull(true).reference.foreign(users) //
//				// not null, CURRENT_TIMESTAMP
//				.column("creation_ts", DataType.TIMESTAMP) //
//				// null, > 0, size 10
//				.column("modifier_id", DataType.INTEGER).allowNull(true).reference.foreign(users) //
//				// notnull
//				.column("modification_ts", DataType.DATE) //
//				.build();
//
//		req_specs
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// length <= 10, id > 0, NotNull, foreign
//				.column("testproject_id", DataType.INTEGER).reference.foreign(testprojects) //
//				// length 64, NotNull
//				.column("doc_id", DataType.VARCHAR) //
//				.build();
//
//		req_specs_revisions
//		// length <= 10, id > 0, NotNull
//				.column("parent_id", DataType.INTEGER) //
//				// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// SMALLINT 5, > 0
//				.column("revision", DataType.SMALLINT) //
//				// length 64, Null
//				.column("doc_id", DataType.VARCHAR) //
//				// 100, null
//				.column("name", DataType.VARCHAR) //
//				// Text, Null
//				.column("scope", DataType.VARCHAR).allowNull(true) //
//				// 1, notnull
//				.column("total_req", DataType.INTEGER) //
//				// 1, null
//				.column("status", DataType.INTEGER).allowNull(true) //
//				// 1, null
//				.column("type", DataType.CHAR) //
//				// NULL, Text
//				.column("log_message", DataType.VARCHAR) //
//				// int 10, > 0, foreign key
//				.column("author_id", DataType.INTEGER).reference.foreign(users) //
//				// not null, CURRENT_TIMESTAMP
//				.column("creation_ts", DataType.TIMESTAMP) //
//				// null, > 0, size 10
//				.column("modifier_id", DataType.INTEGER).allowNull(true).reference.foreign(users) //
//				// notnull
//				.column("modification_ts", DataType.DATE) //
//				.build();
//
//		req_versions
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// SMALLINT 5, > 0
//				.column("version", DataType.SMALLINT) //
//				// SMALLINT 5, > 0
//				.column("revision", DataType.SMALLINT) //
//				// Text, Null
//				.column("scope", DataType.VARCHAR).allowNull(true) //
//				// 1, notnull
//				.column("status", DataType.CHAR) //
//				// 1, null
//				.column("type", DataType.CHAR) //
//				// notnull, 0 or 1
//				.column("active", DataType.TINYINT) //
//				// notnull, 0 or 1
//				.column("is_open", DataType.TINYINT) //
//				// not null, size 10
//				.column("expected_coverage", DataType.INTEGER) //
//				// int 10, > 0, foreign key
//				.column("author_id", DataType.INTEGER).reference.foreign(users) //
//				// not null, CURRENT_TIMESTAMP
//				.column("creation_ts", DataType.TIMESTAMP) //
//				// null, > 0, size 10, foreign
//				.column("modifier_id", DataType.INTEGER).reference.foreign(users) //
//				// notnull
//				.column("modification_ts", DataType.DATE) //
//				// NULL, Text
//				.column("log_message", DataType.VARCHAR).allowNull(true) //
//				.build();
//
//		rights //
//		// length <= 10, id > 0, Unique, NotNull
//		.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// null or TEXT
//				.column("description", DataType.VARCHAR) //
//				.build();
//
//		risk_assignments //
//				// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// foreign, not null
//				.column("testplan_id", DataType.INTEGER).reference.foreign(testplans) //
//				// foreign, not null
//				.column("node_id", DataType.INTEGER).reference.foreign(nodes_hierarchy) //
//				// CHAR 1, standard = 2, not null
//				.column("risk", DataType.CHAR)
//				// CHAR 1, standard = M, not null
//				.column("importance", DataType.CHAR).build();

		roles //
		// length <= 10, id > 0, Unique, NotNull
		.column("id", DataType.INTEGER).generator(new IntegerGenerator(1, 1000)).defaultIdentifier().autoInvokeNext() //
				// not null, <= 100
				.column("description", DataType.VARCHAR).generator(new BuchNameGenerator()) //
				// null or TEXT
				.column("notes", DataType.VARCHAR) //
				.build();

		// role_id && right_id == UNIQUE
//		associativeTable("role_rights") //
//				// length <= 10, > 0, not null, role exists?
//				.column("role_id", DataType.INTEGER).reference.foreign(roles) //
//				// length <= 10, > 0,not null, right exists?
//				.column("right_id", DataType.INTEGER).reference.foreign(rights) //
//				.build();
//
//		schema_version //
//				// length <= 11, not null
//				.column("version_rank", DataType.INTEGER) //
//				// length <= 11, not null
//				.column("installed_rank", DataType.INTEGER) //
//				// primary, varchar 50
//				.column("version", DataType.VARCHAR).defaultIdentifier().autoInvokeNext() //
//				// not null, <= 200
//				.column("description", DataType.VARCHAR) //
//				// not null, <= 20
//				.column("type", DataType.VARCHAR) //
//				// not null, <= 1000
//				.column("script", DataType.VARCHAR) //
//				// length <= 11, null
//				.column("checksum", DataType.INTEGER) //
//				// not null, <= 100
//				.column("installed_by", DataType.VARCHAR) //
//				// not null, CURRENT_TIMESTAMP
//				.column("installed_on", DataType.TIMESTAMP) //
//				// length <= 11, not null
//				.column("execution_time", DataType.INTEGER) //
//				// length == 1, not null
//				.column("success", DataType.TINYINT) //
//				.build();
//
//		tcsteps //
//		// length <= 10, id > 0, Unique, NotNull
//		.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// 11, not null
//				.column("step_number", DataType.INTEGER) //
//				// TEXT, null
//				.column("actions", DataType.VARCHAR).allowNull(true) //
//				// TEXT, null
//				.column("expected_results", DataType.VARCHAR).allowNull(true) //
//				// 0 or 1
//				.column("active", DataType.TINYINT) //
//				// 0 or 1
//				.column("execution_type", DataType.TINYINT) //
//				.build(); //
//
//		tcversions //
//					// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext().generator(new IntegerGenerator(1, 100000)) //
//				// null, length 10, > 0
//				.column("tc_external_id", DataType.INTEGER).generator(new IntegerGenerator(0, 1)) //
//				// 5, > 0, standard = 1
//				.column("version", DataType.SMALLINT).generator(new IntegerGenerator(0, 1)) //
//				// 5, > 0, standard = 1
//				.column("layout", DataType.SMALLINT).generator(new IntegerGenerator(0, 1)) //
//				// 5, > 0, standard = 1
//				.column("status", DataType.SMALLINT).generator(new IntegerGenerator(0, 1)) //
//				// TEXT, null
//				//.column("summary", DataType.VARCHAR) //
//				// TEXT, null
//				//.column("preconditions", DataType.VARCHAR) //
//				// 5, > 0, standard = 2
//				.column("importance", DataType.SMALLINT).generator(new IntegerGenerator(0, 1)) //
//				// int 10, > 0, foreign key
//				.column("author_id", DataType.INTEGER).reference.foreign(users) //
//				// not null, CURRENT_TIMESTAMP
//				.column("creation_ts", DataType.TIMESTAMP) //
//				// null, > 0, size 10, foreign
//				.column("updater_id", DataType.INTEGER).reference.foreign(users) //
//				// notnull
//				.column("modification_ts", DataType.DATE) //
//				// notnull, 0 or 1, standard = 1
//				.column("active", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
//				// notnull, 0 or 1, standard = 1
//				.column("is_open", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
//				// notnull, 0 or 1, standard = 1
//				.column("execution_type", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
//				// null, length 6,2, standard = null
//				.column("estimated_Exec_duration", DataType.DECIMAL) //
//				.build(); //
//		
//		// testcase_id && keyword_id == UNIQUE
//		associativeTable("testcase_keywords") //
//				// not null, id exists?
//				.column("testcase_id", DataType.INTEGER).reference.foreign(testcase_relations) //
//				// not null, id exists?
//				.column("keyword_id", DataType.INTEGER).reference.foreign(keywords) //
//				.build();
//
//		testcase_relations //
//				// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// null, length 10, > 0
//				.column("source_id", DataType.INTEGER).allowNull(true) //
//				// null, length 10, > 0
//				.column("destination_id", DataType.INTEGER).allowNull(true) //
//				// size 5, not null
//				.column("relation_type", DataType.SMALLINT) //
//				// int 10, > 0, foreign key
//				.column("author_id", DataType.INTEGER).reference.foreign(users) //
//				// not null, CURRENT_TIMESTAMP
//				.column("creation_ts", DataType.TIMESTAMP) //
//				.build();
//
		testplans //
					// length <= 10, id > 0, Unique, NotNull
				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
				// not null, user_id exists?
				.column("testproject_id", DataType.INTEGER).reference.foreign(testprojects) //
				// Keine Constraints
				.column("notes", DataType.VARCHAR) //
				// 0 or 1
				.column("active", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// 0 or 1
				.column("is_open", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// 0 or 1
				.column("is_public", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// length == 64, 0-9 a-f, UNIQUE
				.column("api_key", DataType.VARCHAR).generator(new BuchNameGenerator()) //
				.build(); //

//		testplan_platforms
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext().generator(new IntegerGenerator(1, 100000)) //
//				// not null,
//				.column("testplan_id", DataType.INTEGER).reference.foreign(testplans) //
//				// not null,
//				.column("platform_id", DataType.INTEGER).reference.foreign(platforms) //
//				.build();
//
//		testplan_tcversions
//		// length <= 10, id > 0, Unique, NotNull
//				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
//				// not null,
//				.column("testplans_id", DataType.INTEGER).reference.foreign(testplans) //
//				// not null,
//				.column("tcversion_id", DataType.INTEGER).reference.foreign(tcversions) //
//				// > 0, length 10, not null
//				.column("node_order", DataType.INTEGER) //
//				// length 5, not null
//				.column("urgency", DataType.SMALLINT) //
//				// not null,
//				.column("platform_id", DataType.INTEGER).reference.foreign(platforms) //
//				// int 10, > 0, foreign key, null
//				.column("author_id", DataType.INTEGER).allowNull(true).reference.foreign(users) //
//				// not null, CURRENT_TIMESTAMP
//				.column("creation_ts", DataType.TIMESTAMP) //
//				.build();
//
		testprojects //
				// length <= 10, id > 0, Unique, NotNull
				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
				// Keine Constraints
				//.column("notes", DataType.VARCHAR) //
				// length <= 12, startsWidth("#9BD")
				.column("color", DataType.VARCHAR) //
				// 0 or 1
				.column("active", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// 0 or 1
				.column("option_reqs", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// 0 or 1
				.column("option_priority", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// 0 or 1
				.column("option_automation", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// Null or
				// O:8:"stdClass":4:{s:19:"requirementsEnabled";i:1;s:19:"testPriorityEnabled";i:1;s:17:"automationEnabled";i:1;s:16:"inventoryEnabled";i:0;}
				//.column("options", DataType.VARCHAR) //
				// length <= 12, NOT NULL
				.column("prefix", DataType.VARCHAR).generator(new BuchNameGenerator()) //
				// length <= 10, id > 0,
				.column("tc_counter", DataType.INTEGER).generator(new IntegerGenerator(1, 1000)) //
				// 0 or 1
				.column("is_public", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// 0 or 1
				.column("issue_tracker_enabled", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// 0 or 1
				.column("reqmgr_integration_enabled", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// length == 64, 0-9 a-f, UNIQUE
				.column("api_key", DataType.VARCHAR).generator(new BuchNameGenerator()) //
				.build(); //
//
//		// user_id && testproject_id == UNIQUE
//		associativeTable("testproject_issuetracker") //
//				// not null, id exists?
//				.column("testproject_id", DataType.INTEGER).reference.foreign(testprojects) //
//				// not null, id exists?
//				.column("issuetracker_id", DataType.INTEGER).reference.foreign(issuetrackers) //
//				.build();
//
//		// user_id && testproject_id == UNIQUE
//		associativeTable("testproject_reqmgrsystem") //
//				// not null, id exists?
//				.column("testproject_id", DataType.INTEGER).reference.foreign(testprojects) //
//				// not null, id exists?
//				.column("reqmgrsystem_id", DataType.INTEGER).reference.foreign(reqmgrsystems) //
//				.build();
//
		testsuites
		// length <= 10, id > 0, Unique, NotNull
				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
				// Keine Constraints, null
				.column("details", DataType.VARCHAR).allowNull(true) //
				.build();

		transactions
		// length <= 10, id > 0, Unique, NotNull
				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
				// 0 < length <= 45, not null
				.column("entry_point", DataType.VARCHAR) //
				// length = 10, > 0, not null
				.column("start_time", DataType.INTEGER).generator(new IntegerGenerator(0, 2400)) //
				// length = 10, > 0, not null
				.column("end_time", DataType.INTEGER).generator(new IntegerGenerator(0, 2400)) //
				// length = 10, > 0, not null
				.column("user_id", DataType.INTEGER).reference.foreign(users) //
				// 0 < length <= 45, null
				.column("session_id", DataType.VARCHAR) //
				.build();

		users //
		// length <= 10, id > 0, Unique, NotNull
		.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
				// 0 < length <= 30, not null
				.column("login", DataType.VARCHAR).generator(new BuchNameGenerator()) //
				// length == 32 (md5), not null
				.column("password", DataType.VARCHAR) //
				// length <= 10, not null, role exists?
				.column("role_id", DataType.INTEGER).reference.foreign(roles) //
				// Unique, length <= 100, E-Mail-Verification, Not null
				.column("email", DataType.VARCHAR) //
				// 0 length <= 30, not null
				.column("first", DataType.VARCHAR).generator(new DomainGenerator("vorname")) //
				// 0 length <= 30, not null
				.column("last", DataType.VARCHAR).generator(new BuchNameGenerator()) //
				// Null OR DataGenerator("locale")
				.column("locale", DataType.VARCHAR)//.allowNull(true) //
				// > 0 OR null, Testproject_id exists?
				.column("default_testproject_id", DataType.INTEGER).generator(new IntegerGenerator(1, 10000))//.reference.local.name("belongsTo").foreign(testprojects) //
				// 0 OR 1
				.column("active", DataType.TINYINT).generator(new IntegerGenerator(0, 1)) //
				// Null OR length == 32, 0-9 a-f
				.column("script_key", DataType.VARCHAR)//.allowNull(true) //
				// length == 64, 0-9 a-f
				.column("cookie_string", DataType.VARCHAR).generator(new BuchNameGenerator()) //
				// null or DB or LDAP ==> constraint(match({"", "DB", LDAP}))
				.column("auth_method", DataType.VARCHAR).set("\"DB\"", "\"LDAP\"").allowNull(true) //
				.build(); //

		user_assignments
		// length <= 10, id > 0, Unique, NotNull
				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
				// 0 < length <= 10, not null
				.column("type", DataType.INTEGER).generator(new IntegerGenerator(1, 100)) //
				// 0 < length <= 10, not null
				.column("feature_id", DataType.INTEGER).generator(new IntegerGenerator(1, 10000)) //
				// not null, user_id exists?
				.column("user_id", DataType.INTEGER).reference.foreign(users) //
				// not null, build_id exists?
				.column("build_id", DataType.INTEGER).reference.foreign(builds) //
				// null, DATETIME
				.column("deadline_ts", DataType.DATE) //
				// null, length = 10, > 0
				.column("assigner_id", DataType.INTEGER).allowNull(true).reference.foreign(users) //
				// not null, CURRENT_TIMESTAMP
				.column("creation_ts", DataType.TIMESTAMP) //
				// null, length = 10, > 0
				.column("status", DataType.INTEGER).generator(new IntegerGenerator(1, 100)) //
				.build();		
//
		user_group //
				// length <= 10, id > 0, Unique, NotNull
				.column("id", DataType.INTEGER).defaultIdentifier().autoInvokeNext() //
				// <= 100, UNIQUE
				.column("title", DataType.VARCHAR).generator(new BuchNameGenerator()) //
				// null or Text
				.column("description", DataType.VARCHAR) //
				.build(); //
//
//		// user_id && usergroup_id == UNIQUE
		associativeTable("user_group_assign") //
				// not null, exists?
				.column("usergroup_id", DataType.INTEGER).reference.foreign(user_group) //
				// not null, exists?
				.column("user_id", DataType.INTEGER).reference.foreign(users) //
				.build();

//		// user_id && testplan_id == UNIQUE
		associativeTable("user_testplan_roles") //
				// not null, exists?
				.column("user_id", DataType.INTEGER).reference.foreign(users) //
				// not null, exists?
				.column("testplan_id", DataType.INTEGER).reference.foreign(testplans) //
				// not null, exists?
				.column("role_id", DataType.INTEGER)//.reference.foreign(roles)
				.build();
//
//		// user_id && testproject_id == UNIQUE
		associativeTable("user_testproject_roles") //
				// not null, exists?
				.column("user_id", DataType.INTEGER).reference.foreign(users) //
				// not null, exists?
				.column("testproject_id", DataType.INTEGER).reference.foreign(testprojects) //
				// not null, exists?
				.column("role_id", DataType.INTEGER)//.reference.foreign(roles) //
				.build();
	}
}
