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
 * An instance of this class is used to define a constraint between two values.
 * The first value must be smaller or equal than the second. A value can be
 * either a constant value of type
 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value Value<T>}
 * or a reference to a cell in the form of {@code table.column}. The reference
 * is formed either between two cells of the same entity in one table or between
 * two cells that are in a 1:1-relation.
 * </p>
 * 
 * <p>
 * <b>Examples:</b>
 * </p>
 *  
 * <code>
 * <pre>
 * SmallerEqualConstraint smallerEqualConstraint = new SmallerEqualConstraint("author.registerDate", "author.birthday"); 
 * SmallerEqualConstraint smallerEqualConstraint = new SmallerEqualConstraint("user.age", new IntegerValue(17));
 * SmallerEqualConstraint smallerEqualConstraint = new SmallerEqualConstraint(new DoubleValue(9.99), "item.price");
 * </pre>
 * </code>
 */
public class SmallerEqualConstraint extends CompareConstraint {

	/**
	 * <p>
	 * {@code public SmallerEqualConstraint(String sourceName1, String sourceName2)}
	 * </p>
	 * 
	 * <p>
	 * Creates an SmallerEqualConstraint instance, which specifies that the value of
	 * the source with reference name {@code sourceName1} must be smaller or equal than
	 * the value of the source with the reference name {@code sourceName2}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * SmallerEqualConstraint smallerEqualConstraint = new SmallerEqualConstraint("author.registerDate", "author.birthday"); 
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
	public SmallerEqualConstraint(String sourceName1, String sourceName2) {
		super(sourceName1, sourceName2);
	}

	/**
	 * <p>
	 * {@code public SmallerEqualConstraint(String sourceName, Value<?> value)}
	 * </p>
	 * 
	 * <p>
	 * Creates an SmallerEqualConstraint instance, which specifies that the
	 * value of the source with reference name {@code sourceName} must be
	 * smaller or equal than the constant {@code value} of type
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * SmallerEqualConstraint smallerEqualConstraint = new SmallerEqualConstraint("user.age", new IntegerValue(17));
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
	public SmallerEqualConstraint(String sourceName, Value<?> value) {
		super(sourceName, value);
	}

	/**
	 * <p>
	 * {@code public SmallerEqualConstraint(Value<?> value, String sourceName)</p>
	 * 
	 * <p>
	 * Creates an SmallerEqualConstraint instance, which specifies that the
	 * constant {@code value} of type of type
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>} must be smaller or equal than the value of the sources with the
	 * reference name {@code sourceName}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b> 
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * SmallerEqualConstraint smallerEqualConstraint = new SmallerEqualConstraint(new DoubleValue(9.99), "item.price");
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
	public SmallerEqualConstraint(Value<?> value, String sourceName) {
		super(value, sourceName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid() {

		Value<?>[] values = resolveValues();

		try {
			return values[0].compareTo(values[1]) < 0;
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
				SmallerEqualHint eqHint = new SmallerEqualHint(this);
				eqHint.setValue(values[1]);
				hints.add(eqHint);
			}
		} else if (resultPos == 1) {
			if (values[0] != null) {
				GreaterEqualHint eqHint = new GreaterEqualHint(this);
				eqHint.setValue(values[0]);
				hints.add(eqHint);
			}
		}

		return hints;
	}
}
