package com.seitenbau.stu.database.generator.values.constraints.logical;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.hints.NotEqualHint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.constraints.CompareConstraint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintBase;
import com.seitenbau.stu.database.generator.values.constraints.Source;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

/**
 * <p>
 * An instance of this class is used to define a constraint between two values.
 * The two values must not be equal. A value can be either a constant value of
 * type {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
 * Value<T>} or a reference to a cell in the form of {@code table.column}. The
 * reference is formed either between two cells of the same entity in one table
 * or between two cells that are in a 1:1-relation.
 * </p>
 * 
 * <p>
 * <b>Examples:</b>
 * </p>
 * 
 * <code>
 * <pre>
 * NotEqualConstraint notEqualConstraint = new NotEqualConstraint("author.country", "adresse.country"); 
 * NotEqualConstraint notEqualConstraint = new NotEqualConstraint("author.activated", new BooleanValue(true));
 * NotEqualConstraint notEqualConstraint = new NotEqualConstraint(new StringValue("available"), "book.status");
 * </pre>
 * </code>
 */
public class NotEqualConstraint extends CompareConstraint {

	/**
	 * <p>
	 * {@code public NotEqualConstraint(String sourceName1, String sourceName2)}
	 * </p>
	 * 
	 * <p>
	 * Creates an NotEqualConstraint instance, which specifies that the value of
	 * the sources with reference name {@code sourceName1} and
	 * {@code sourceName2} must not be equal.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * NotEqualConstraint notEqualConstraint = new NotEqualConstraint("author.country", "adresse.country"); 
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
	public NotEqualConstraint(String sourceName1, String sourceName2) {
		super(sourceName1, sourceName2);
	}

	/**
	 * <p>
	 * {@code public NotEqualConstraint(String sourceName, Value<?> value)}
	 * </p>
	 * 
	 * <p>
	 * Creates an NotEqualConstraint instance, which specifies that the value of
	 * the source with reference name {@code sourceName1} and the constant
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * value} must not be equal.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * NotEqualConstraint notEqualConstraint = new NotEqualConstraint("author.activated", new BooleanValue(true));
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
	public NotEqualConstraint(String sourceName, Value<?> value) {
		super(sourceName, value);
	}

	/**
	 * <p>
	 * {@code public NotEqualConstraint(Value<?> value, String sourceName)}
	 * </p>
	 * 
	 * <p>
	 * Creates an NotEqualConstraint instance, which specifies that the constant
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * value} and the value of the sources with reference name
	 * {@code sourceName1} must not be equal.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * NotEqualConstraint notEqualConstraint = new NotEqualConstraint(new StringValue("available"), "book.status");
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
	public NotEqualConstraint(Value<?> value, String sourceName) {
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
			return v1.compareTo(v2) != 0;
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
			NotEqualHint hint = new NotEqualHint(this);
			hint.setValue(this.getValue(0));
			hints.add(hint);
		} else {
			for (Source source : sources) {
				for (Result r : source.getResults()) {
					if (r != result) {
						if (r.isGenerated()) {
							NotEqualHint hint = new NotEqualHint(this);
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
