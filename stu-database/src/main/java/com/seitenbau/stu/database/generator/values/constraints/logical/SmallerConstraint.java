package com.seitenbau.stu.database.generator.values.constraints.logical;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.hints.SmallerHint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.constraints.CompareConstraint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.constraints.Source;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

/**
 * <p>
 * An instance of this class is used to define a constraint between two values.
 * The first value must be smaller than the second. A value can be either a
 * constant value of type
 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value Value<T>}
 * or a reference to a cell in the form of {@code table.column}. The reference
 * is formed either between two cells of the same entity in one table or between
 * two cells that are in a 1:1-relation.
 * </p>
 * 
 * <p>
 * <b>Examples:</b> <code>
 * <pre>
 * SmallerConstraint smallerConstraint = new SmallerConstraint("author.registerDate", "author.birthday"); 
 * SmallerConstraint smallerConstraint = new SmallerConstraint("user.age", new IntegerValue(17));
 * SmallerConstraint smallerConstraint = new SmallerConstraint(new DoubleValue(9.99), "item.price");
 * </pre>
 * </code>
 */
public class SmallerConstraint extends CompareConstraint {

	/**
	 * <p>
	 * {@code public SmallerConstraint(String sourceName1, String sourceName2)}
	 * </p>
	 * 
	 * <p>
	 * Creates an SmallerConstraint instance, which specifies that the value of
	 * the source with reference name {@code sourceName1} must be smaller than
	 * the value of the source with the reference name {@code sourceName2}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * SmallerConstraint smallerConstraint = new SmallerConstraint("author.registerDate", "author.birthday"); 
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
	public SmallerConstraint(String sourceName1, String sourceName2) {
		super(sourceName1, sourceName2);
	}

	/**
	 * <p>
	 * public SmallerConstraint(String sourceName, Value<?> value)</b>
	 * </p>
	 * 
	 * <p>
	 * Creates an SmallerConstraint instance, which specifies that the value of
	 * the source with reference name {@code sourceName} must be smaller than
	 * the constant {@code value} of type
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
	 * SmallerConstraint smallerConstraint = new SmallerConstraint("user.age", new IntegerValue(17));
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
	public SmallerConstraint(String sourceName, Value<?> value) {
		super(sourceName, value);
	}

	/**
	 * <p>
	 * {@code public SmallerConstraint(Value<?> value, String sourceName)}
	 * </p>
	 * 
	 * <p>
	 * Creates an SmallerConstraint instance, which specifies that the constant
	 * {@code value} of type of type
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>} must be smaller than the value of the sources with the
	 * reference name {@code sourceName}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * SmallerConstraint smallerConstraint = new SmallerConstraint(new DoubleValue(9.99), "item.price");
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
	public SmallerConstraint(Value<?> value, String sourceName) {
		super(value, sourceName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid() {

		try {
			if (sourceNames.length == 2)
				return getSources()
						.get(0)
						.getResults()
						.get(0)
						.getValue()
						.compareTo(
								getSources().get(1).getResults().get(0)
										.getValue()) < 0;

			return getSources().get(0).getResults().get(0).getValue()
					.compareTo(getValue(0)) < 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Hint> getHints(Result result) {
		ArrayList<Hint> hints = new ArrayList<Hint>();

		if (this.getValue(0) != null) {
			SmallerHint hint = new SmallerHint(this);
			hint.setValue(this.getValue(0));
			hints.add(hint);
		} else {
			for (Source source : sources) {
				for (Result r : source.getResults()) {
					if (r != result) {
						if (r.isGenerated()) {
							SmallerHint hint = new SmallerHint(this);
							hint.setValue(r.getValue());
							hints.add(hint);
							return hints;
						}
					}
				}
			}
		}

		return hints;
	}
}
