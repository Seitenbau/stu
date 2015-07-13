package com.seitenbau.stu.database.generator.values.constraints.functional;

import java.util.ArrayList;

import com.seitenbau.stu.database.generator.hints.EqualHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.constraints.FunctionConstraint;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

/**
 * <p>
 * An instance of this class is used to define a constraint between three
 * values. The condition between the three values is: {@code a % b = c}. A value
 * can be either a constant value of type
 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value Value<T>}
 * or a reference to a cell in the form of {@code table.column}. A maximum of
 * two parameters may be constant values. The reference is formed either between
 * three cells of the same entity in one table or between three cells that are
 * in a 1:1-relation.
 * </p>
 * 
 * <p>
 * <b>Examples:</b>
 * </p>
 * 
 * <code>
 * <pre>
 * ModConstraint modConstraint = new ModConstraint("calculation.a", "calculation.b", "calculation.c"); 
 * ModConstraint modConstraint = new ModConstraint("calculation.a", "calculation.b", new IntValue(10)); 
 * ModConstraint modConstraint = new ModConstraint("calculation.a", new IntValue(5), "calculation.c"); 
 * ModConstraint modConstraint = new ModConstraint(new DoubleValue(4.5), "calculation.b", "calculation.c"); 
 * ModConstraint modConstraint = new ModConstraint("calculation.a", new IntValue(5), new IntValue()); 
 * ModConstraint modConstraint = new ModConstraint(new IntValue(2), "calculation.b", new IntValue(10)); 
 * ModConstraint modConstraint = new ModConstraint(new IntValue(10), new IntValue(5), "calculation.c"); 
 * </pre> </code>
 */
public class ModConstraint extends FunctionConstraint {

	/**
	 * <p>
	 * {@code public ModConstraint(String sourceName1, String sourceName2,
			String resultSource)}
	 * </p>
	 * 
	 * <p>
	 * Creates an ModConstraint instance, which specifies that the remainder of the Euclidean division of the
	 * value of the sources with reference name {@code sourceName1} and
	 * {@code sourceName2} must be equal to the value of the source with the
	 * reference name {@code resultSource}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * a % b = c
	 * ModConstraint modConstraint = new ModConstraint("calculation.a", "calculation.b", "calculation.c"); 
	 * </pre>
	 * </code>
	 * 
	 * @param sourceName1
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @param sourceName2
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @param resultSource
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 */
	public ModConstraint(String sourceName1, String sourceName2,
			String resultSource) {
		super(sourceName1, sourceName2, resultSource);
	}

	/**
	 * <p>
	 * {@code public ModConstraint(String sourceName1, String sourceName2,
			Value<?> value)}
	 * </p>
	 * 
	 * <p>
	 * Creates an ModConstraint instance, which specifies that the remainder of the Euclidean division of the
	 * value of the sources with reference name {@code sourceName1} and
	 * {@code sourceName2} must be equal to the constant {@code value} of the
	 * type {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * a % b = 10
	 * ModConstraint modConstraint = new ModConstraint("calculation.a", "calculation.b", new IntValue(10)); 
	 * </pre>
	 * </code>
	 * 
	 * @param sourceName1
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @param sourceName2
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @param value
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @see com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *      Value<T>
	 */
	public ModConstraint(String sourceName1, String sourceName2, Value<?> value) {
		super(sourceName1, sourceName2, value);
	}

	/**
	 * <p>
	 * {@code public ModConstraint(String sourceName1, Value<?> value), String resultSource)}
	 * </p>
	 * 
	 * <p>
	 * Creates an ModConstraint instance, which specifies that the remainder of the Euclidean division of the
	 * value of the source with reference name {@code sourceName1} and the
	 * constant {@code value} of the type
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>} must be equal to the value of the source with the reference
	 * name {@code resultSource}. Value<T>}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * a % 5 = c
	 * ModConstraint modConstraint = new ModConstraint("calculation.a", new IntValue(5), "calculation.c"); 
	 * </pre>
	 * </code>
	 * 
	 * @param sourceName1
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @param value
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @param resultSource
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * 
	 * @see com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *      Value<T>
	 */
	public ModConstraint(String sourceName1, Value<?> value, String resultSource) {
		super(sourceName1, value, resultSource);
	}

	/**
	 * <p>
	 * {@code public ModConstraint(Value<?> value), String sourceName1, String resultSource)}
	 * </p>
	 * 
	 * <p>
	 * Creates an ModConstraint instance, which specifies that the remainder of the Euclidean division of the
	 * constant {@code value} of the type
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>} and the value of the source with reference name
	 * {@code sourceName1} must be equal to the value of the source with the
	 * reference name {@code resultSource}. Value<T>}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * 4.5 % b = c
	 * ModConstraint modConstraint = new ModConstraint(new DoubleValue(4.5), "calculation.b", "calculation.c"); 
	 * </pre>
	 * </code>
	 * 
	 * @param value
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @param sourceName1
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @param resultSource
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * 
	 * @see com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *      Value<T>
	 */
	public ModConstraint(Value<?> value, String sourceName1, String resultSource) {
		super(value, sourceName1, resultSource);
	}

	/**
	 * <p>
	 * {@code public ModConstraint(String sourceName1, Value<?> value1, Value<?> value2)}
	 * </p>
	 * 
	 * <p>
	 * Creates an ModConstraint instance, which specifies that the remainder of the Euclidean division of the
	 * value of the sources with reference name {@code sourceName1} and the
	 * constant {@code value1} of the type
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>} must be equal to the constant {@code value2} of the type
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
	 * a % 5 = 2
	 * ModConstraint modConstraint = new ModConstraint("calculation.a", new IntValue(5), new IntValue(2)); 
	 * </pre>
	 * </code>
	 * 
	 * @param sourceName1
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @param value1
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @param value2
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @see com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *      Value<T>
	 */
	public ModConstraint(String sourceName1, Value<?> value1, Value<?> value2) {
		super(sourceName1, value1, value2);
	}

	/**
	 * <p>
	 * {@code public ModConstraint(Value<?> value1, String sourceName1, Value<?> value2)}
	 * </p>
	 * 
	 * <p>
	 * Creates an ModConstraint instance, which specifies that the remainder of the Euclidean division of the
	 * constant {@code value1} of the type
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>} and the value of the sources with reference name
	 * {@code sourceName1} must be equal to the constant {@code value2} of the
	 * type {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * 2 % b = 10
	 * ModConstraint modConstraint = new ModConstraint(new IntValue(2), "calculation.b", new IntValue(10)); 
	 * </pre>
	 * </code>
	 *
	 * @param value1
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @param sourceName1
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @param value2
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @see com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *      Value<T>
	 */
	public ModConstraint(Value<?> value1, String sourceName1, Value<?> value2) {
		super(value1, sourceName1, value2);
	}

	/**
	 * <p>
	 * {@code public ModConstraint(Value<?> value1, Value<?> value2, String resultSource)}
	 * </p>
	 * 
	 * <p>
	 * Creates an ModConstraint instance, which specifies that the remainder of the Euclidean division of the
	 * constant {@code value1} of the type
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>} and the constant {@code value2} of the type
	 * {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 * Value<T>} must be equal to the value of the sources with reference name
	 * {@code resultSource}.
	 * </p>
	 * 
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * 
	 * <code>
	 * <pre>
	 * 10 % 5 = c
	 * ModConstraint modConstraint = new ModConstraint(new IntValue(10), new IntValue(5), "calculation.c"); 
	 * </pre>
	 * </code>
	 *
	 * @param value1
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @param value2
	 *            Defines a constant value of type
	 *            {@link com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *            Value<T>}.
	 * @param resultSource
	 *            Defines the name of the reference to the source in the form
	 *            {@code table.column}.
	 * @see com.seitenbau.stu.database.generator.values.valuetypes.Value
	 *      Value<T>
	 */
	public ModConstraint(Value<?> value1, Value<?> value2, String resultSource) {
		super(value1, value2, resultSource);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid() {

		Value<?>[] values = resolveValues();

		try {
			return values[0].mod(values[1]).equals(values[2]);
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
		Value<?>[] values = resolveValues();

		int resultPos = getResultPos(result);

		if (resultPos == 0) {
			if (values[1] != null && values[2] != null) {

				Value<?> mod = values[1];
				Value<?> possibleValue = values[2].clone();
				for (int i = 0; i < 1000; i++) {
					possibleValue = possibleValue.add(mod);
					EqualHint eqHint = new EqualHint(this);
					eqHint.setValue(possibleValue);
					hints.add(eqHint);
				}
			}
		} else if (resultPos == 1) {
			if (values[0] != null && values[2] != null) {
				// TODO
			}
		} else if (resultPos == 2) {
			if (values[0] != null && values[1] != null) {
				// TODO
			}
		}

		return hints;
	}
}
