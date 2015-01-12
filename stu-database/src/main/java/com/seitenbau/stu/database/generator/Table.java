package com.seitenbau.stu.database.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.seitenbau.stu.database.generator.values.constraints.CompareValueConstraint;
import com.seitenbau.stu.database.generator.values.constraints.Constraint;
import com.seitenbau.stu.database.generator.values.constraints.Constraint.Mode;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintsData;
import com.seitenbau.stu.database.generator.values.constraints.DataConstraint;
import com.seitenbau.stu.database.generator.values.constraints.UniqueConstraint;
import com.seitenbau.stu.util.CamelCase;

public class Table {

	public static final String NAME_SUFFIX = "Table";

	private DataSet _dataSet;

	private final String _name;

	private final String _javaName;

	private final String _description;

	private final long _seed;

	private final Integer _infinite;

	private final int _minEntities;

	private final List<Column> _columns;

	private final List<ConstraintColumnPair> _constraintColumnPairs;

	private ConstraintsData _dataSource;

	public List<ConstraintColumnPair> get_constraintColumnPairs() {
		return _constraintColumnPairs;
	}

	public Table(String name, String javaName, String description, long seed, Integer infinite, int minEntities,
			List<ColumnBuilder> columnBuilders, List<ConstraintColumnPair> constraintColumnPairs,
			ConstraintsData dataSource) {
		_name = name;
		_javaName = javaName;
		_description = description;
		_seed = seed;
		_columns = new ArrayList<Column>();
		_infinite = infinite;
		_minEntities = minEntities;
		_dataSource = dataSource;

		for (ColumnBuilder columnBuilder : columnBuilders) {
			_columns.add(columnBuilder.buildColumn(this));
		}

		_constraintColumnPairs = constraintColumnPairs;
	}

	public String getName() {
		return _name;
	}

	public String getDescription() {
		return _description;
	}

	public long getSeed() {
		return _seed;
	}

	public int getMinEntities() {
		return _minEntities;
	}

	public List<Column> getColumns() {
		return Collections.unmodifiableList(_columns);
	}

	public String getJavaName() {
		return _javaName;
	}

	public String getJavaNameFirstLower() {
		return CamelCase.makeFirstLowerCase(getJavaName());
	}

	public String getPackage() {
		return getDataSet().getPackage();
	}

	public String getSuffix() {
		return NAME_SUFFIX;
	}

	public Integer getInfinite() {
		return _infinite;
	}

	void setParent(DataSet dataSet) {
		_dataSet = dataSet;
	}

	public DataSet getDataSet() {
		return _dataSet;
	}

	public Column ref(String colName) {
		for (Column col : getColumns()) {
			if (col.getName().equals(colName)) {
				return col;
			}
		}
		throw new RuntimeException("No column " + colName);
	}

	public List<Column> getIdentifierColumns() {
		List<Column> result = new LinkedList<Column>();
		for (Column col : getColumns()) {
			if (col.isIdentifier()) {
				result.add(col);
			}
		}
		return result;
	}

	public boolean isAssociativeTable() {
		return false;
	}

	public boolean hasDataColumns() {
		for (Column col : getColumns()) {
			if (col.getRelation() == null) {
				return true;
			}
		}
		return false;
	}

	public Column getDefaultIdentifierColumn() {
		for (Column col : _columns) {
			if (col.isDefaultIdentifier()) {
				return col;
			}
		}
		throw new RuntimeException("No default identifier column found");
	}

	/**
	 * Helper method for the Generator. Determines the requires imports for the
	 * table.
	 * 
	 * @return Set of Table, which have to be imported
	 */
	public Set<Table> getRequiredTableImports() {
		Set<Table> result = new HashSet<Table>();
		for (Column col : getColumns()) {
			if (col.getRelation() != null) {
				result.add(col.getRelation().getForeignColumn().getTable());
			}
		}
		return result;
	}

	public List<Column> getForeignColumns() {
		List<Column> result = new LinkedList<Column>();
		if (isAssociativeTable()) {
			return result;
		}
		for (Column col : _columns) {
			if (col.getRelation() == null) {
				continue;
			}

			result.add(col);
		}
		return result;
	}

	public List<Column> getReferencingColumns() {
		List<Column> result = new LinkedList<Column>();
		if (isAssociativeTable()) {
			return result;
		}
		for (Column col : _columns) {
			for (Column foreignCol : col.getReferencedByList()) {
				if (foreignCol.getTable().isAssociativeTable()) {
					continue;
				}

				result.add(foreignCol);
			}
		}
		return result;
	}

	public List<AssociativeRelation> getAssociativeRelations() {
		List<AssociativeRelation> result = new LinkedList<AssociativeRelation>();
		if (isAssociativeTable()) {
			return result;
		}
		for (Column col : _columns) {
			for (Column referencingCol : col.getReferencedByList()) {
				if (!referencingCol.getTable().isAssociativeTable()) {
					continue;
				}

				AssociativeRelation relation = new AssociativeRelation();

				relation.localColumn = col;
				relation.localName = referencingCol.getRelation().getForeignName();
				relation.localAssociationColumn = referencingCol;

				relation.foreignColumn = referencingCol.getTable().getAssociatedColumn(this);
				relation.foreignName = relation.foreignColumn.getRelation().getForeignName();
				relation.foreignAssociationColumn = relation.foreignColumn.getRelation().getForeignColumn();

				relation.description = referencingCol.getRelation().getForeignDescription();

				result.add(relation);
			}
		}
		return result;
	}

	private Column getAssociatedColumn(Table table) {
		for (Column col : getColumns()) {
			final ColumnReference relation = col.getRelation();
			if (relation == null) {
				continue;
			}
			if (relation.getForeignColumn().getTable() == table) {
				continue;
			}

			return col;
		}

		throw new RuntimeException("No associating column found");
	}

	public final class AssociativeRelation {
		private Column localColumn;
		private String localName;
		private Column localAssociationColumn;

		private Column foreignColumn;
		private String foreignName;
		private Column foreignAssociationColumn;

		private String description;

		public Column getLocalColumn() {
			return localColumn;
		}

		public String getLocalName() {
			return localName;
		}

		public Column getForeignColumn() {
			return foreignColumn;
		}

		public String getForeignName() {
			return foreignName;
		}

		public Column getLocalAssociationColumn() {
			return localAssociationColumn;
		}

		public Column getForeignAssociationColumn() {
			return foreignAssociationColumn;
		}

		public String getForeignRowbuilderClass() {
			return _dataSet.getNames().getRowBuilderClass(foreignColumn.getTable());
		}

		public String getForeignRefVariable() {
			return _dataSet.getNames().getRefVariable(foreignAssociationColumn.getTable());
		}

		public String getDescription() {
			return description;
		}
	}

	public int getIncomingEdgeCount() {
		int result = 0;

		for (Column col : _columns) {
			result += col.getReferencedByList().size();
		}

		return result;
	}

	public List<Edge> getOutgoingEdges() {
		List<Edge> result = new LinkedList<Edge>();

		for (Column col : _columns) {
			if (col.getRelation() == null) {
				continue;
			}

			result.add(new Edge(col));
		}

		return result;
	}

	public List<Edge> getIncomingEdges() {
		List<Edge> result = new LinkedList<Edge>();

		for (Column col : _columns) {
			for (Column ref : col.getReferencedByList()) {
				result.add(new Edge(ref));
			}
		}

		return result;
	}

	public void setColumnConstraints() {
		if (_constraintColumnPairs.size() > 0) {
			for (Column col : _columns) {
				col.getGenerator().clearConstraints();
			}

			for (ConstraintColumnPair ccp : _constraintColumnPairs) {

				try {
					Constraint const1;
					Constraint const2;

					if(UniqueConstraint.class.isInstance(ccp.getConstraint1())){
						const1 = ccp.getConstraint1();
						const2 = ccp.getConstraint2();
					}else{
						const1 = ccp.getConstraint1().getClass().newInstance();
						const2 = ccp.getConstraint2().getClass().newInstance();
					}

					if (DataConstraint.class.isInstance(const1) && DataConstraint.class.isInstance(const2)) {
						((DataConstraint) const1).setKey(((DataConstraint) ccp.getConstraint1()).getKey());
						((DataConstraint) const2).setKey(((DataConstraint) ccp.getConstraint2()).getKey());
					} else if (CompareValueConstraint.class.isInstance(const1)
							&& CompareValueConstraint.class.isInstance(const2)) {
						((CompareValueConstraint) const1)
								.setCompareType(((CompareValueConstraint) ccp.getConstraint1()).getCompareType());
						((CompareValueConstraint) const2)
								.setCompareType(((CompareValueConstraint) ccp.getConstraint2()).getCompareType());
					}

					ConstraintPair cp1 = new ConstraintPair(const1, const2);
					ConstraintPair cp2 = new ConstraintPair(const2, const1);

					Column c1 = getColumn(ccp.getName1());
					if (c1 == null) {
						System.out.println("Column " + ccp.getName1() + " not found!");
					} else {
						c1.getGenerator().addConstraint(cp1);
					}

					Column c2 = getColumn(ccp.getName2());
					if (c2 == null) {
						System.out.println("Column " + ccp.getName2() + " not found!");
					} else {
						c2.getGenerator().addConstraint(cp2);
					}

				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public Column getColumn(String name) {
		for (Column column : _columns) {
			if (column.getName() == name) {
				return column;
			}
		}
		return null;
	}

	public ConstraintsData getDataSource() {
		return _dataSource;
	}

	public void setDataSource(ConstraintsData dataSource) {
		this._dataSource = dataSource;
	}
}
