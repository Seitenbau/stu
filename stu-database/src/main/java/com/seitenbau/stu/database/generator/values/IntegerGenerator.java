package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.chocosolver.samples.AbstractProblem;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.IntConstraintFactory;
import org.chocosolver.solver.explanations.ExplanationFactory;
import org.chocosolver.solver.search.strategy.IntStrategyFactory;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.VF;
import org.chocosolver.solver.variables.VariableFactory;
import org.springframework.beans.factory.parsing.Problem;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.constraints.CompareValueConstraint;
import com.seitenbau.stu.database.generator.values.constraints.CompareValueConstraint.CompareType;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;

public class IntegerGenerator extends ValueGenerator {

	private Random random;

	private int min;

	private int max;

	private final long module;

	private final Strategy strategy;

	public IntegerGenerator(int min, int max) {
		this.min = min;
		this.max = max;
		module = (long) max - min;
		if (module < Integer.MAX_VALUE) {
			this.strategy = new IntRange();
		} else {
			this.strategy = new LongRange();
		}
	}

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
	}

	@Override
	public Result nextValue(EntityBlueprint eb) {

		Result result = new Result(null, false);

		if(random.nextInt() < 10)
			new ExplainedSimpleProblem().execute();

		if (!allTargetsLoaded(eb))
			return result;

		// TODO Check if all targets have values...
		if (!checkValues(eb))
			return result;

		Comparable<?> value = null;

		int i = 0;
		do {
			value = strategy.nextValue();
			i++;
		} while (checkSuperConstraints(value, eb) && i < 100);

		if (i >= 99) {
			return new Result(null, false);
		} else {
			result.setValue(value);
		}

		return result;

		// if (constraintPairs == null || constraintPairs.size() <= 0) {
		// return strategy.nextValue().toString();
		// } else {
		// Comparable value = null;
		// // lp(true);
		// // lp(false);
		//
		//
		// // Brute Force
		// do {
		// value = strategy.nextValue();
		// } while (checkConstraints(value));
		//
		// //lp(true);
		//
		//
		// // ConstraintSolver rd = new ConstraintSolver(constraintPairs);
		// //
		// // min = rd.getMin(this.getKey());
		// // max = rd.getMax(this.getKey());
		// // value = strategy.nextValue();
		//
		// setConstraintValues(value);
		// return value.toString();
		// }
	}

	public class ExplainedSimpleProblem extends AbstractProblem {
		IntVar[] vars;
		int n = 5;
		int vals = n + 1;

		@Override
		public void createSolver() {
			solver = new Solver();
		}

		@Override
		public void buildModel() {
			vars = VariableFactory.enumeratedArray("x", n, 1, vals, solver);
			for (int i = 0; i < vars.length - 1; i++) {
				solver.post(IntConstraintFactory.arithm(vars[i], ">",
						vars[i + 1]));
			}
		}

		@Override
		public void configureSearch() {
			solver.set(IntStrategyFactory.minDom_LB(vars));
		}

		@Override
		public void solve() {
			ExplanationFactory.CBJ.plugin(solver, false);
			if (solver.findSolution()) {
				do {
					this.prettyOut();
				} while (solver.nextSolution());
			}
		}

		@Override
		public void prettyOut() {
			for (IntVar v : vars) {
				// System.out.println("* variable " + v);
				for (int i = 1; i <= vals; i++) {
					if (!v.contains(i)) {
						System.out.println(v.getName() + " != " + i
								+ " because "
								+ solver.getExplainer().retrieve(v, i));
					}
				}
			}
		}		
	}

	// TODO: OLD....Range wird eingeschränkt... Wird durch Constraint Solver
	// ersetzt...
	private String getValue() {
		RandomList rl = new RandomList();
		rl.add(new Range(min, max));

		for (ConstraintPair cp : constraintPairs) {

			// reduceList => makeRangeList

			if (CompareValueConstraint.class.isInstance(cp.getMyConstraint())) {
				CompareValueConstraint cvc_my = (CompareValueConstraint) cp
						.getMyConstraint();
				CompareValueConstraint cvc_depending = (CompareValueConstraint) cp
						.getDependingConstraint();

				if (cvc_depending.getValue() != null) {

					// TODO
					if (cvc_my.getCompareType() == CompareType.GREATER) {
						if (cvc_depending.getValue().compareTo(max) > 0) {
							Range newRange = new Range(
									(Integer) cvc_depending.getValue() + 1, max);
							rl.add(newRange);
						} else {
							Range newRange = new Range(min, max);
							rl.add(newRange);
						}
					} else if (cvc_my.getCompareType() == CompareType.SMALLER) {
						if (cvc_depending.getValue().compareTo(min) > 0) {
							Range newRange = new Range(min,
									(Integer) cvc_depending.getValue() - 1);
							rl.add(newRange);
						} else {
							Range newRange = new Range(min, max);
							rl.add(newRange);
						}
					} else if (cvc_my.getCompareType() == CompareType.GREATER_EQUAL) {
						if (cvc_depending.getValue().compareTo(max) >= 0) {
							Range newRange = new Range(
									(Integer) cvc_depending.getValue(), max);
							rl.add(newRange);
						} else {
							Range newRange = new Range(min, max);
							rl.add(newRange);
						}
					} else if (cvc_my.getCompareType() == CompareType.SMALLER_EQUAL) {
						if (cvc_depending.getValue().compareTo(min) >= 0) {
							Range newRange = new Range(min,
									(Integer) cvc_depending.getValue());
							rl.add(newRange);
						} else {
							Range newRange = new Range(min, max);
							rl.add(newRange);
						}
					}
				}
			}
		}

		Comparable value = rl.randomValue();

		for (ConstraintPair cp : constraintPairs) {

			if (CompareValueConstraint.class.isInstance(cp.getMyConstraint())) {
				CompareValueConstraint cvc_my = (CompareValueConstraint) cp
						.getMyConstraint();
				cvc_my.setValue(value);
			}
		}

		return value.toString();
	}

	private interface Strategy {
		Comparable nextValue();

		void AddRange(Comparable min, Comparable max);
	}

	private class LongRange implements Strategy {
		@Override
		public Comparable nextValue() {
			long value = (Math.abs(random.nextLong()) % module) + min;
			return value;
		}

		@Override
		public void AddRange(Comparable min, Comparable max) {
			// TODO Auto-generated method stub

		}
	}

	private class IntRange implements Strategy {
		@Override
		public Comparable nextValue() {
			int value = random.nextInt(1 + max - min) + min;
			return value;
		}

		@Override
		public void AddRange(Comparable min, Comparable max) {

		}
	}

	public static class Factory implements ValueGeneratorFactory {

		private final int min;
		private final int max;

		public Factory(int min, int max) {
			this.min = min;
			this.max = max;
		}

		@Override
		public ValueGenerator createGenerator() {
			return new IntegerGenerator(min, max);
		}

	}

	public class RandomList {
		private ArrayList<Range> ranges = new ArrayList<Range>();

		public void add(Range range) {
			boolean modified = false;
			for (Range r : ranges) {

				if (range.min < r.min) {
					if (range.max > r.min && range.max <= r.max) {
						r.max = range.max;
						modified = true;
						continue;
					}
				}

				if (range.min > r.min) {
					if (range.min <= r.max) {
						if (range.max <= r.max) {
							r.min = range.min;
							r.max = range.max;
							modified = true;
							continue;
						} else {
							r.min = range.min;
							modified = true;
							continue;
						}
					}
				}
			}
			if (!modified)
				ranges.add(range);
		}

		public Integer getRangeCount() {
			Integer count = 0;
			for (Range r : ranges) {
				count += r.getCount();
			}
			return count;
		}

		public Integer randomValue() {
			HashMap<Double, Range> map = new HashMap<Double, Range>();
			Integer rangeCount = getRangeCount();
			Double key = 0.0;
			for (Range r : ranges) {
				map.put(key, r);
				key += ((double) r.getCount()) / rangeCount;
			}

			Range selectedRange = null;
			Double randomKey = random.nextDouble();
			Set<Entry<Double, Range>> set = map.entrySet();
			for (Entry<Double, Range> e : set) {
				if (e.getKey() < randomKey) {
					selectedRange = e.getValue();
				}
			}

			return random.nextInt(1 + selectedRange.max - selectedRange.min)
					+ selectedRange.min;
		}
	}

	public class Range {
		int min;
		int max;

		public Range(int min, int max) {
			this.min = min;
			this.max = max;
		}

		public Integer getCount() {
			return max - min;
		}
	}
}
