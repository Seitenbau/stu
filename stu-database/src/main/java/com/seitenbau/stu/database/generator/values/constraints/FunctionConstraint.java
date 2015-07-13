package com.seitenbau.stu.database.generator.values.constraints;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public abstract class FunctionConstraint extends ConstraintBase {

	private ArrayList<Value<?>> values = new ArrayList<Value<?>>();

	public FunctionConstraint(String sourceName1, String sourceName2, String resultSource) {
		this.scope = Scope.Cell;
		this.priory = 2;
		mode = 1;
		this.modelRef = sourceName1;
		this.sourceNames = new String[] { sourceName1, sourceName2, resultSource };
	}

	public FunctionConstraint(String sourceName1, String sourceName2, Value<?> value) {
		this.priory = 2;
		this.scope = Scope.Cell;
		mode = 2;
		this.modelRef = sourceName1;
		this.sourceNames = new String[] { sourceName1, sourceName2 };
		values.add(value);
	}

	public FunctionConstraint(String sourceName1, Value<?> value, String resultSource) {
		this.priory = 2;
		this.scope = Scope.Cell;
		mode = 3;
		this.modelRef = sourceName1;
		this.sourceNames = new String[] { sourceName1, resultSource };
		values.add(value);
	}

	public FunctionConstraint(Value<?> value, String sourceName1, String resultSource) {
		this.priory = 2;
		this.scope = Scope.Cell;
		mode = 4;
		this.modelRef = sourceName1;
		this.sourceNames = new String[] { sourceName1, resultSource };
		values.add(value);
	}

	public FunctionConstraint(String sourceName1, Value<?> value1, Value<?> value2) {
		this.priory = 1;
		this.scope = Scope.Cell;
		mode = 5;
		this.modelRef = sourceName1;
		this.sourceNames = new String[] { sourceName1 };
		values.add(value1);
		values.add(value2);
	}

	public FunctionConstraint(Value<?> value1, String sourceName1, Value<?> value2) {
		this.priory = 1;
		this.scope = Scope.Cell;
		mode = 6;
		this.modelRef = sourceName1;
		this.sourceNames = new String[] { sourceName1 };
		values.add(value1);
		values.add(value2);
	}

	public FunctionConstraint(Value<?> value1, Value<?> value2, String resultSource) {
		this.priory = 1;
		this.scope = Scope.Cell;
		mode = 7;
		this.modelRef = resultSource;
		this.sourceNames = new String[] { resultSource };
		values.add(value1);
		values.add(value2);
	}

	public Value<?> getValue(int index) {
		return values.get(index);
	}

	public void setValue(int index, Value<?> value) {
		values.add(index, value);
	}

	public void addValue(Value<?> value) {
		values.add(value);
	}

	protected Value<?>[] resolveValues() {
		Value<?>[] values = new Value<?>[3];

		switch (mode) {
		case 1:
			values[0] = getSources().get(0).getResults().get(0).getValue();
			values[1] = getSources().get(1).getResults().get(0).getValue();
			values[2] = getSources().get(2).getResults().get(0).getValue();
			break;

		case 2:
			values[0] = getSources().get(0).getResults().get(0).getValue();
			values[1] = getSources().get(1).getResults().get(0).getValue();
			values[2] = getValue(0);
			break;

		case 3:
			values[0] = getSources().get(0).getResults().get(0).getValue();
			values[1] = getValue(0);
			values[2] = getSources().get(1).getResults().get(0).getValue();
			break;

		case 4:
			values[0] = getValue(0);
			values[1] = getSources().get(0).getResults().get(0).getValue();
			values[2] = getSources().get(1).getResults().get(0).getValue();
			break;

		case 5:
			values[0] = getSources().get(0).getResults().get(0).getValue();
			values[1] = getValue(0);
			values[2] = getValue(1);
			break;

		case 6:
			values[0] = getValue(0);
			values[1] = getSources().get(0).getResults().get(0).getValue();
			values[2] = getValue(1);
			break;

		case 7:
			values[0] = getValue(0);
			values[1] = getValue(1);
			values[2] = getSources().get(0).getResults().get(0).getValue();
			break;
		}
		return values;
	}
	
	protected Integer getResultPos(Result result) {
		Integer pos = null;
		for (int i = 0; i < sourceNames.length; i++) {
			if (sourceNames[i].equals(result.getSourceName())) {
				pos = i;
			}
		}

		if (pos != null) {
			if (mode == 3) {
				if (pos == 1) {
					pos = 2;
				}
			} else if (mode == 4 || mode == 6) {
				pos += 1;
			} else if (mode == 7) {
				pos = 2;
			} 
		}

		return pos;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConstraintBase getCopyInstance() {
		ConstraintBase cb = null;

		try {
			switch (mode) {
			case 1:
				cb = this.getClass().getDeclaredConstructor(String.class, String.class, String.class).newInstance(sourceNames[0], sourceNames[1], sourceNames[2]);
				break;
			case 2:
				cb = this.getClass().getDeclaredConstructor(String.class, String.class, Value.class).newInstance(sourceNames[0], sourceNames[1], getValue(0));
				break;
			case 3:
				cb = this.getClass().getDeclaredConstructor(String.class, Value.class, String.class).newInstance(sourceNames[0], getValue(0), sourceNames[1]);
				break;
			case 4:
				cb = this.getClass().getDeclaredConstructor(Value.class, String.class, String.class).newInstance(getValue(0), sourceNames[0], sourceNames[1]);
				break;
			case 5:
				cb = this.getClass().getDeclaredConstructor(String.class, Value.class, Value.class).newInstance(sourceNames[0], getValue(0), getValue(1));
				break;
			case 6:
				cb = this.getClass().getDeclaredConstructor(Value.class, String.class, Value.class).newInstance(getValue(0), sourceNames[1], getValue(1));
				break;
			case 7:
				cb = this.getClass().getDeclaredConstructor(Value.class, Value.class, String.class).newInstance(getValue(0), getValue(1), sourceNames[2]);
				break;
			}	
			
		cb.fab = this.fab;
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cb;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Hint> getHints(Result result) {
		return new ArrayList<Hint>();
	}	
}
