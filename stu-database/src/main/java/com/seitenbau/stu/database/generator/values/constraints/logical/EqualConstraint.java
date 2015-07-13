package com.seitenbau.stu.database.generator.values.constraints.logical;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.hints.EqualHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.constraints.CompareConstraint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.constraints.Source;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

/**
 * <p>
 * An instance of this class is used to define a constraint between two values.
 * The two values must be equal. A value can be either a constant value of type
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
 * EqualConstraint equalConstraint = new EqualConstraint("author.country", "adresse.country"); 
 * EqualConstraint equalConstraint = new EqualConstraint("author.activated", new BooleanValue(true));
 * EqualConstraint equalConstraint = new EqualConstraint(new StringValue("available"), "book.status");
 * </pre>
 * </code>
 */
public class EqualConstraint extends CompareConstraint {

	/**
	 * <p>
	 * {@code public EqualConstraint(String sourceName1, String sourceName2)}
	 * </p>
	 * 
	 * <p>
	 * Creates an EqualConstraint instance, which specifies that the value of
	 * the sources with reference name {@code sourceName1} and
	 * {@code sourceName2} must be equal.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * EqualConstraint equalConstraint = new EqualConstraint("author.country", "adresse.country"); 
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
	public EqualConstraint(String sourceName1, String sourceName2) {
		super(sourceName1, sourceName2);
	}

	/**
	 * <p>
	 * {@code public EqualConstraint(String sourceName, Value<?> value)}
	 * </p>
	 * 
	 * <p>
	 * Creates an EqualConstraint instance, which specifies that the value of
	 * the source with reference name {@code sourceName1} and the constant
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * value} must be equal.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * <code>
	 * <pre>
	 * EqualConstraint equalConstraint = new EqualConstraint("author.activated", new BooleanValue(true));
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
	public EqualConstraint(String sourceName, Value<?> value) {
		super(sourceName, value);
	}

	/**
	 * <b>public EqualConstraint(Value<?> value, String sourceName)</b> <br />
	 * Creates an EqualConstraint instance, which specifies that the constant
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * value} and the value of the sources with reference name
	 * {@code sourceName1} must be equal. <br />
	 * <b>Example:</b> <code>
	 * <pre>
	 * EqualConstraint equalConstraint = new EqualConstraint(new StringValue("available"), "book.status");
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
	public EqualConstraint(Value<?> value, String sourceName) {
		super(value, sourceName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid() {
		Value<?> v1 = getSources().get(0).getResults().get(0).getValue();
		Value<?> v2 = null;

		if (sourceNames.length == 2)
			v2 = getSources().get(1).getResults().get(0).getValue();
		else
			v2 = getValue(0);

		try {
			return v1.compareTo(v2) == 0;
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
			EqualHint hint = new EqualHint(this);
			hint.setValue(this.getValue(0));
			hints.add(hint);
		} else {
			for (Source source : sources) {
				for (Result r : source.getResults()) {
					if (r != result) {
						if (r.isGenerated()) {
							EqualHint hint = new EqualHint(this);
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