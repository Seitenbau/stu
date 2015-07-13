package com.seitenbau.stu.database.generator.values.constraints;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public abstract class CompareConstraint extends ConstraintBase {

	private ArrayList<Value<?>> values = new ArrayList<Value<?>>();
	
	/**
	 * @param sourceName
	 *            Defines the source in the form "table.column".
	 * @param value
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @see com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *      Value<T>
	 */
	public CompareConstraint(String sourceName1, String sourceName2) {
		this.scope = Scope.Cell;
		this.priory = 5;
		this.mode = 1;
		this.modelRef = sourceName1;
		this.sourceNames = new String[] { sourceName1, sourceName2 };		
	}

	public CompareConstraint(String sourceName, Value<?> value) {
		this.priory = 1;
		this.scope = Scope.Cell;
		this.mode = 2;
		this.modelRef = sourceName;
		this.sourceNames = new String[] { sourceName };
		values.add(value);
	}

	public CompareConstraint(Value<?> value, String sourceName) {
		this.priory = 1;
		this.scope = Scope.Cell;
		this.mode = 3;
		this.modelRef = sourceName;
		this.sourceNames = new String[] { sourceName };
		values.add(value);
	}

	public Value<?> getValue(int index) {
		if(values.size() > index)
			return values.get(index);
		else
			return null;
	}

	public void setValue(int index, Value<?> value) {
		values.add(index, value);
	}
	
	public void addValue(Value<?> value){
		values.add(value);
	}
	
	protected Value<?>[] resolveValues() {
		Value<?>[] values = new Value<?>[3];

		switch (mode) {
		case 1:
			values[0] = getSources().get(0).getResults().get(0).getValue();
			values[1] = getSources().get(1).getResults().get(0).getValue();
			break;

		case 2:
			values[0] = getSources().get(0).getResults().get(0).getValue();
			values[1] = getValue(0);
			break;

		case 3:
			values[1] = getValue(0);
			values[2] = getSources().get(0).getResults().get(0).getValue();
			break;
		}
		return values;
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
				cb = this.getClass().getDeclaredConstructor(String.class, String.class).newInstance(sourceNames[0], sourceNames[1]);
				break;
			case 2:
				cb = this.getClass().getDeclaredConstructor(String.class, Value.class).newInstance(sourceNames[0], getValue(0));
				break;
			case 3:
				cb = this.getClass().getDeclaredConstructor(Value.class, String.class).newInstance(getValue(0), sourceNames[0]);
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
	protected Integer getResultPos(Result result) {
		Integer pos = null;
		for (int i = 0; i < sourceNames.length; i++) {
			if (sourceNames[i].equals(result.getSourceName())) {
				pos = i;
			}
		}

		if (pos != null) {
			if (mode == 3) {
				if (pos == 0) {
					pos = 1;
				}
			}
		}

		return pos;
	}	
}
