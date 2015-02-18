package com.seitenbau.stu.database.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.seitenbau.stu.database.generator.values.ValueGenerator;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;
import com.seitenbau.stu.util.CamelCase;

public class Column {

	private final Table _table;

	private final DataType _dataType;

	private final String _name;

	private final String _javaName;

	private final String _headerName;

	private final String _description;

	private final ColumnReference _relation;

	private final ColumnMetaData _metaData;

	final List<Column> _referencedBy;

	private final ValueGenerator _generator;

	private final long _seed;

	private final Integer _infinite;

	private final ArrayList<ConstraintPair> _constraints;	

	public boolean is_allowNull() {
		return _allowNull;
	}

	public void set_allowNull(boolean _allowNull) {
		this._allowNull = _allowNull;
	}

	private String[] _set;
	
	private boolean _allowNull;

	public void set_set(String[] _set) {
		this._set = _set;
	}

	Column(Table table, String name, String javaName, String headerName, String description, DataType dataType, ColumnReference relation,
			Set<String> flags, ValueGenerator generator, long seed, Integer infinite, ArrayList<ConstraintPair> constraints, String[] set, boolean allowNull) {
		_table = table;
		_name = name;
		_javaName = javaName;
		_headerName = headerName;
		_description = description;
		_dataType = dataType;
		_relation = relation;
		_generator = generator;
		_seed = seed;
		_infinite = infinite;
		_constraints = constraints;
		_set = set;
		_allowNull = allowNull;

		_metaData = new ColumnMetaData(flags);

		_referencedBy = new ArrayList<Column>();
		if (_relation != null) {
			_relation.addReferencedBy(this);
		}
	}

	public Table getTable() {
		return _table;
	}

	public String getJavaType() {
		return _dataType.getJavaType();
	}

	public String getType() {
		return _dataType.getDataType();
	}

	DataType getDataType() {
		return _dataType;
	}

	public String getName() {
		return _name;
	}

	public String getJavaName() {
		return _javaName;
	}

	public String getJavaNameFirstLower() {
		return CamelCase.makeFirstLowerCase(getJavaName());
	}

	public String getHeaderName() {
		return _headerName;
	}

	public String getDescription() {
		return _description;
	}

	public ColumnReference getRelation() {
		return _relation;
	}

	public ColumnMetaData getMetaData() {
		return _metaData;
	}

	public List<Column> getReferencedByList() {
		return Collections.unmodifiableList(_referencedBy);
	}

	public boolean isDefaultIdentifier() {
		return _metaData.hasFlag(ColumnMetaData.DEFAULT_IDENTIFIER);
	}

	public boolean isIdentifier() {
		return _metaData.hasFlag(ColumnMetaData.IDENTIFIER);
	}

	public boolean isImmutable() {
		return _metaData.hasFlag(ColumnMetaData.IMMUTABLE);
	}

	public boolean isNextValueMethodGenerated() {
		return _metaData.hasFlag(ColumnMetaData.ADD_NEXT_METHOD);
	}

	public boolean isAutoInvokeValueGeneration() {
		return _metaData.hasFlag(ColumnMetaData.AUTO_INVOKE_NEXT);
	}

	public boolean isAutoIncrement() {
		return _metaData.hasFlag(ColumnMetaData.AUTO_INCREMENT);
	}

	public boolean isFutureValueSupported() {
		return !isImmutable() && _relation == null;
	}

	public ValueGenerator getGenerator() {
		return _generator;
	}

	public long getSeed() {
		return _seed;
	}

	public Integer getInfinite() {
		return _infinite;
	}

	public ArrayList<ConstraintPair> getConstraints() {
		return _constraints;
	}

	public String[] get_set() {
		return _set;
	}

}
