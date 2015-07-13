package com.seitenbau.stu.database.generator.values.constraints.logical;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.hints.GreaterEqualHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.hints.SmallerEqualHint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.constraints.CompareConstraint;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;


/**
 * <p>
 * An instance of this class is used to define a constraint between two
 * values. The first value must be greater or equal than the second. A value
 * can be either a constant value of type
 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
 * Value<T>} or a reference to a cell in the form of {@code table.column}.
 * The reference is formed either between two cells of the same entity in
 * one table or between two cells that are in a 1:1-relation.
 * </p>
 * 
 * <p>
 * <b>Examples:</b> 
 * </p>
 * 
 * <code>
 * <pre>
 * GreaterEqualConstraint greaterEqualConstraint = new GreaterEqualConstraint("author.registerDate", "author.birthday"); 
 * GreaterEqualConstraint greaterEqualConstraint = new GreaterEqualConstraint("user.age", new IntegerValue(17));
 * GreaterEqualConstraint greaterEqualConstraint = new GreaterEqualConstraint(new DoubleValue(9.99), "item.price");
 * </pre>
 * </code>
 */
public class GreaterEqualConstraint extends CompareConstraint {

	/**
	 * <p>
	 * {@code public GreaterEqualConstraint(String sourceName1, String sourceName2)}
	 * </p>
	 * 
	 * <p>
	 * Creates an GreaterEqualConstraint instance, which specifies that the value of
	 * the source with reference name {@code sourceName1} must be greater or equal than
	 * the value of the source with the reference name {@code sourceName2}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * GreaterEqualConstraint greaterEqualConstraint = new GreaterEqualConstraint("author.registerDate", "author.birthday"); 
	 * </pre>
	 * </code>
	 * 
	 * @param sourceName1
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @param sourceName2
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 */
	public GreaterEqualConstraint(String sourceName1, String sourceName2) {
		super(sourceName1, sourceName2);
	}

	/**
	 * <b>public GreaterEqualConstraint(String sourceName, Value<?> value)</b> <br />
	 * Creates an GreaterEqualConstraint instance, which specifies that the
	 * value of the source with reference name {@code sourceName} must be
	 * greater or equal than the constant {@code value} of type
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>}. <br />
	 * <br />
	 * <b>Example:</b> <code>
	 * <pre>
	 * GreaterEqualConstraint greaterEqualConstraint = new GreaterEqualConstraint("user.age", new IntegerValue(17));
	 * </pre>
	 * </code>
	 * 
	 * @param sourceName
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @param value
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @see com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *      Value<T>
	 */
	public GreaterEqualConstraint(String sourceName, Value<?> value) {
		super(sourceName, value);
	}

	/**
	 * <p>
	 * {@code public GreaterEqualConstraint(Value<?> value, String sourceName)}
	 * </p>
	 * 
	 * <p>
	 * Creates an GreaterEqualConstraint instance, which specifies that the
	 * constant {@code value} of type of type
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>} must be greater or equal than the value of the sources with the
	 * reference name {@code sourceName}. 
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 *  
	 * <code>
	 * <pre>
	 * GreaterEqualConstraint greaterEqualConstraint = new GreaterEqualConstraint(new DoubleValue(9.99), "item.price");
	 * </pre>
	 * </code>
	 * 
	 * @param value
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @param sourceName
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @see com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *      Value<T>
	 */
	public GreaterEqualConstraint(Value<?> value, String sourceName) {
		super(value, sourceName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid() {

		Value<?>[] values = resolveValues();

		try {
			return values[0].compareTo(values[1]) >= 0;
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Hint> getHints(Result result) {
		ArrayList<Hint> hints = new ArrayList<Hint>();
		Value<?>[] values = resolveValues();

		int resultPos = getResultPos(result);

		if (resultPos == 0) {
			if (values[1] != null) {
				GreaterEqualHint eqHint = new GreaterEqualHint(this);
				eqHint.setValue(values[1]);
				hints.add(eqHint);
			}
		} else if (resultPos == 1) {
			if (values[0] != null) {
				SmallerEqualHint eqHint = new SmallerEqualHint(this);
				eqHint.setValue(values[0]);
				hints.add(eqHint);
			}
		}

		return hints;
	}
}
