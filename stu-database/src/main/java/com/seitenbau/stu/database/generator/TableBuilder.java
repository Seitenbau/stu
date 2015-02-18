package com.seitenbau.stu.database.generator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.seitenbau.stu.database.generator.values.constraints.CompareValueConstraint;
import com.seitenbau.stu.database.generator.values.constraints.Constraint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintsData;
import com.seitenbau.stu.database.generator.values.constraints.RangeConstraint;
import com.seitenbau.stu.database.generator.values.constraints.UniqueConstraint;

public class TableBuilder implements TableBuilderCommon {

	private Table builtTable;

	protected final DatabaseModel model;

	protected final String name;

	protected String javaName;

	protected String description;

	protected final List<ColumnBuilder> columnBuilders;

	protected final List<ConstraintColumnPair> constraintColumnPairs;

	protected long seed;

	protected int minEntities;

	protected Integer infinite;

	protected ConstraintsData dataSource;

	TableBuilder(DatabaseModel model, String name) {
		this.builtTable = null;
		this.model = model;
		this.name = name;
		seed = 0;
		minEntities = 1;
		javaName = DataSet.makeNiceJavaName(name);
		columnBuilders = new LinkedList<ColumnBuilder>();
		infinite = null;
		dataSource = model.dataSource;
		constraintColumnPairs = model.constraintColumnPairs; // TODO: Nur bekannte???
	}

	/**
	 * Finalizes the creation of the table.
	 * 
	 * @return The created table
	 */
	public Table build() {
		builtTable = new Table(name, javaName, getTableDescription(), seed,
				infinite, minEntities, columnBuilders, constraintColumnPairs,
				dataSource, model);
		model.addTable(builtTable);

		return builtTable;
	}

	protected String getTableDescription() {
		if (description != null) {
			return description;
		}

		return "The " + name + " table";
	}

	String getName() {
		return name;
	}

	/**
	 * Defines how the table is spelled in the Java source code.
	 * 
	 * @param javaName
	 *            The table name within the Java sources
	 * @return The table builder
	 */
	public TableBuilder javaName(String javaName) {
		this.javaName = javaName;
		return this;
	}

	String getJavaName() {
		return javaName;
	}

	/**
	 * Adds a description text to the table used for JavaDoc in the generated
	 * Java classes.
	 * 
	 * @param description
	 *            The description for the table
	 * @return The table builder
	 */
	public TableBuilder description(String description) {
		this.description = description;
		return this;
	}

	public TableBuilder seed(long seed) {
		this.seed = seed;
		return this;
	}

	public TableBuilder minEntities(int count) {
		this.minEntities = count;
		return this;
	}

	public TableBuilder infinite(int infinite) {
		this.infinite = Integer.valueOf(infinite);
		return this;
	}

	public ArrayList<ConstraintColumnPair> list = new ArrayList<ConstraintColumnPair>();

	public TableBuilder constraint(Constraint constraint, String column){
		constraint.setColumnName(column);
		
		if(UniqueConstraint.class.isInstance(constraint)){
			constraintColumnPairs.add(new ConstraintColumnPair(constraint,
					column, constraint, column));
		}
		
		if(RangeConstraint.class.isInstance(constraint)){
			constraintColumnPairs.add(new ConstraintColumnPair(constraint,
					column, constraint, column));
		}
		
		return this;
	}
	
	public TableBuilder constraint(Constraint constraint, String column1,
			String column2) {

		// TODO: Relation zu anderer Tabelle: model benutzen
		//model.constraintTable..... insert.....
		
		try {
			if (CompareValueConstraint.class.isInstance(constraint)) {
				constraint.setColumnName(column1);
				CompareValueConstraint cvc = (CompareValueConstraint) constraint;
				CompareValueConstraint partnerConstraint = (CompareValueConstraint) constraint.getClass().newInstance();
				partnerConstraint.setCompareType(cvc.getCompareType().partner());
				partnerConstraint.setColumnName(column2);
				return constraint(constraint, column1, partnerConstraint,
						column2);
			} else {
				// TODO Fehlermeldung
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;

	}

	public TableBuilder constraint(Constraint constraint1, String column1,
			Constraint constraint2, String column2) {
		constraintColumnPairs.add(new ConstraintColumnPair(constraint1,
				column1, constraint2, column2));
		return this;
	}

	/**
	 * Adds a column to the table.
	 * 
	 * @param name
	 *            The database name of the column.
	 * @param dataType
	 *            The column's data type.
	 * @return A builder to configure the column
	 */
	public ColumnBuilder column(String name, DataType dataType) {
		return new ColumnBuilder(this, name, dataType);
	}

	void addColumnBuilder(ColumnBuilder columnBuilder) {
		columnBuilders.add(columnBuilder);
	}

	public List<ColumnBuilder> getColumnBuilders() {
		return columnBuilders;
	}

	public ColumnBuilder getColumnBuilder(String name) {
		for (ColumnBuilder cb : columnBuilders) {
			if (cb.getColumnName() == name) {
				return cb;
			}
		}
		return null;
	}

	public FutureColumn ref(String colName) {
		final String finalColName = colName;
		return new FutureColumn() {

			@Override
			public Column getFuture() {
				return builtTable.ref(finalColName);
			}

			@Override
			public boolean isAvailable() {
				return builtTable != null;
			}
		};
	}

	public FutureColumn refDefaultIdentifierColumn() {
		return new FutureColumn() {

			@Override
			public Column getFuture() {
				return builtTable.getDefaultIdentifierColumn();
			}

			@Override
			public boolean isAvailable() {
				return builtTable != null;
			}

		};
	}

}
