package com.seitenbau.stu.database.generator;

import java.util.ArrayList;
import java.util.List;

import com.seitenbau.stu.database.generator.values.DomainData;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;

public abstract class DatabaseModel {

	DataSetGenerator generator;

	String databaseName;

	String packageName;

	String targetPath = DataSetGenerator.DEFAULT_OUTPUT_FOLDER;

	boolean isModelClassGeneration;

	boolean isTableDSLGeneration;

	protected String __forceCaller;

	long seed;

	int infinite;
	
	DomainData dataSource;

	public ArrayList<ConstraintBase> getConstraintsList() {
		return constraintsList;
	}

	private ArrayList<ConstraintBase> constraintsList = new ArrayList<ConstraintBase>();
	
	public DatabaseModel() {
		isModelClassGeneration = false;
		isTableDSLGeneration = true;
		infinite = 5;
	}

	public DataSetGenerator getDataSetGenInstance() {
		if (generator == null) {
			generator = new DataSetGenerator(packageName, databaseName,
					targetPath, isTableDSLGeneration, isModelClassGeneration);
		}
		return generator;
	}

	public void database(String name) {
		this.databaseName = name;
	}

	public void packageName(String name) {
		this.packageName = name;
	}

	public void seed(long seed) {
		this.seed = seed;
	}

	public void infinite(int infinite) {
		this.infinite = infinite;
	}

	public void domainDataSource(DomainData constraintsData) {
		this.setDataSource(constraintsData);
	}


	/**
	 * Adds a constraint to the database model.
	 * 
	 * @param constraint
	 * The constraint object that is attached to the model.
	 */
	public void constraint(ConstraintBase constraint){
		constraintsList.add(constraint);
	}
	
//	public void constraint(ConstraintBuilder constraint){
//		constraintsList.add(constraint.builder().build()));
//	}
	
	public void generatedSourceFolder(String folder) {
		if (generator == null) {
			this.targetPath = folder;
		} else {
			generator.setTargetPath(folder);
		}
	}

	public void catchException(String exception) {
		getDataSetGenInstance().catchException(exception);
	}

	/**
	 * Adds a table to the database model.
	 * 
	 * @param name
	 *            The database name of the table
	 * @return The builder to configure the table.
	 */
	public TableBuilder table(String name) {
		return new TableBuilder(this, name);
	}

	/**
	 * Adds an associative table to the database model. Associative tables
	 * should be used to model n:m relations, although they can be used for
	 * modeling every binary relation.
	 * <p>
	 * Associative tables consist of two columns with foreign relations and can
	 * have further columns for attributes, which describe the relation.
	 * <p>
	 * The following example shows a table with images and and a table with tags
	 * to categorize the images. Each image requires at least one tag, while a
	 * tag does not need to have associated images. <code>
	 * <pre class="groovyTestCase">
	 * import com.seitenbau.stu.database.generator.*;
	 * 
	 * public class DemoDatabaseModel extends DatabaseModel {
	 * 
	 *   public DemoDatabaseModel() {
	 *     Table image = table("image")
	 *       .column("id", DataType.BIGINT)
	 *         .defaultIdentifier()
	 *       .column("name", DataType.VARCHAR)
	 *       .column("content", DataType.BLOB)
	 *     .build();
	 * 
	 *     Table tag = table("tag")
	 *       .column("name", DataType.VARCHAR)
	 *         .defaultIdentifier()
	 *     .build();
	 * 
	 *     associativeTable("image_tag")
	 *       .column("image_id", DataType.BIGINT)
	 *         .reference
	 *           .foreign(image)
	 *             .name("hasTags")
	 *             .multiplicity("1..*")
	 *       .column("tag_name", DataType.VARCHAR)
	 *         .reference
	 *           .foreign(tag)
	 *             .name("containsImages")
	 *             .multiplicity("0..*")
	 *     .build();
	 *   }
	 * }
	 * 
	 * DemoDatabaseModel model = new DemoDatabaseModel();
	 * DataSetGenerator generator = model.getDataSetGenInstance();
	 * DataSet dataSet = generator.getDataSet();
	 * 
	 * assert dataSet.getTables().size() == 3;
	 * </pre>
	 * </code>
	 *
	 * @param name
	 *            The name of the table
	 * @return The builder to configure the associative table
	 */
	public TableBuilder associativeTable(String name) {
		return new AssociativeTableBuilder(this, name);
	}

	/**
	 * Adds a built table to the data set
	 * 
	 * @param table
	 *            The Table built by a TableBuilder
	 */
	void addTable(Table table) {
		getDataSetGenInstance().addTable(table);
	}

	/**
	 * Starts the generation of the DSL model classes
	 * 
	 * @throws Exception
	 */
	public void generate() throws Exception {
		DataSetGenerator gen = getAndConfigureDataSetGenInstance();
		gen.generate();
	}

	public void generateInto(String folder) throws Exception {
		DataSetGenerator gen = getAndConfigureDataSetGenInstance();
		gen.generateInto(folder);
	}

	public void enableTableModelClassesGeneration() {
		isModelClassGeneration = true;
	}

	public void disableTableDSLGeneration() {
		isTableDSLGeneration = false;
	}

	private DataSetGenerator getAndConfigureDataSetGenInstance() {
		DataSetGenerator gen = getDataSetGenInstance();
		if (__forceCaller != null) {
			gen.setCaller(__forceCaller);
		}
		return gen;
	}

	public List<Table> getTables() {
		return getDataSetGenInstance().getDataSet().getTables();
	}
	
	public Table getTableByName(String name) {
		List<Table> tables = getTables();

		for (Table table : tables) {
			if (table.getName().compareTo(name) == 0)
				return table;
		}

		return null;
	}

	public long getSeed() {
		return seed;
	}

	public int getInfinite() {
		return infinite;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getName() {
		return databaseName;
	}

	public DomainData getDataSource() {
		return dataSource;
	}

	public void setDataSource(DomainData dataSource) {
		this.dataSource = dataSource;
	}

}
