package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.syntax.ParserException;

import com.seitenbau.stu.database.generator.Edge;
import com.seitenbau.stu.database.generator.Table;
import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.data.EntityFactory;

public class GlobalConstraintWithSolver {
	public String sourceRef; // "table.column"
	public String[] targetNames; // {"table.column", "table.column",
									// "table.column"}
	public Target[] targets;
	public EntityFactory fab;

	public String expression;

	public GlobalConstraintWithSolver(String sourceRef, String expression, String... targetNames) {
		this.expression = expression;
		this.sourceRef = sourceRef;
		this.targetNames = targetNames;
	}

	private Map<Table, List<EntityBlueprint>> getEntities() {
		return fab.blueprints.getEntities();
	}

	public void addTargetNames(String... targetNames) {
		if (this.targetNames == null)
			this.targetNames = targetNames;
		else
			this.targetNames = (String[]) ArrayUtils.addAll(this.targetNames, targetNames);
	}

	public boolean loadTargets(EntityBlueprint eb) {
		if (targetNames == null)
			return true;

		targets = new Target[targetNames.length];
		for (int i = 0; i < targetNames.length; i++) {

			targets[i] = new Target();
			if (targets[i].hasValue(eb.getRefName()))
				continue;

			String[] array = targetNames[i].split("\\.");
			switch (array.length) {
			case 1:
				// TODO: Check
				// Target is an table
				targets[i].setValue(eb.getRefName(), getEntities().get(fab.model.getTableByName(array[0])));
				break;
			case 2:
				// TODO: Check

				if (StringUtils.isNumeric(array[1])) {
					// Target is an entity of the table
					targets[i].setValue(eb.getRefName(), getEntities().get(array[0]).get(Integer.parseInt(array[2])));
				} else {
					int size = getEntities().get(fab.model.getTableByName(array[0])).size();
					Object[] list = new Object[size];
					for (int j = 0; j < size; j++) {
						list[j] = getEntities().get(fab.model.getTableByName(array[0])).get(j);
					}

					// Target is an column of the table
					targets[i].setPath(list);
					targets[i].table = array[0];
					targets[i].column = array[1];
				}

				break;
			case 3:
				if (StringUtils.isNumeric(array[2])) {
					targets[i].setPath(getEntities().get(fab.model.getTableByName(array[0]))
							.get(Integer.parseInt(array[2])).getValue(array[1]));
					break;
				} else {
					// TODO: Regex
					return false; // break;
				}

			default:
				return false;
			}

		}
		return true;
	}

	public boolean loadValues(EntityBlueprint eb) {
		if (targets == null)
			return false;

		boolean flag = true;

		for (Target target : targets) {

			if (target.hasValue(eb.getRefName()))
				continue;

			String column = Character.toUpperCase(target.column.charAt(0)) + target.column.substring(1);

			for (Entry<String, Object> entry : eb.values.entrySet()) {

				String[] arraySource = sourceRef.split("\\.");

				if (entry.getValue() instanceof EntityBlueprint && target.table.compareTo(arraySource[0]) != 0) {

					EntityBlueprint bp = (EntityBlueprint) entry.getValue();

					if (!bp.values.containsKey(column)) {
						flag = false;
					} else {
						target.setValue(eb.getRefName(), bp.values.get(column));
					}
				} else {
					if (entry.getKey().toLowerCase().compareTo(column.replaceAll("_", "").toLowerCase()) == 0)
						target.setValue(eb.getRefName(), entry.getValue());
				}
			}
		}

		return flag;
	}

	public boolean isValid(Comparable<?> value, EntityBlueprint eb) {

		if (expression != null) {
			for (Target target : targets) {

				if (!target.hasValue(eb.getRefName()))
					return false;
			}

			boolean v = false;
			try {
				v = (Boolean) parseString(expression, eb, value);
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return v;
		}

		String index = eb.getRefName();

		for (Target target : targets) {

			if (!target.hasValue(index))
				return false;

			if (target.getValue(index) != value)
				return false;
		}
		return true;
	}

	private int find(String s, String c) {
		int count = 0;
		// searching the string from back to front
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == '(')
				count++; // count openening brackets
			if (s.charAt(i) == ')')
				count--; // count closing brackets
			// count = 0 means we are outside all brackets
			if (i + c.length() < s.length()) {
				if (s.substring(i, i + c.length()).equals(c) && count == 0)
					// found the rightmost occurence outside all brackets
					return i;
			}
		}
		return -1;
	}

	
	private Object parseString(String s, EntityBlueprint eb, Comparable<?> value) throws ParserException {
		int index;
		s = s.replaceAll("\\s+", "");

		if (s.isEmpty())
			throw new ParserException("Empty string", null);

		s = s.replaceAll("\\-\\-", "+"); // -- ==> +
		s = s.replaceAll("\\-\\+", "-"); // -+ ==> -
		s = s.replaceAll("\\+\\-", "-"); // +- ==> -
		s = s.replaceAll("\\+\\+", "+"); // ++ ==> +

		if ((index = find(s, "||")) >= 0) {
			return ((Boolean) parseString(s.substring(0, index), eb, value) || (Boolean) parseString(
					s.substring(index + 2, s.length()), eb, value));
		} else if ((index = find(s, "&&")) >= 0) {
			return ((Boolean) parseString(s.substring(0, index), eb, value) && (Boolean) parseString(
					s.substring(index + 2, s.length()), eb, value));
		} else if ((index = find(s, "==")) >= 0) {
			Comparable<Comparable<?>> vv1 = (Comparable<Comparable<?>>) parseString(s.substring(0, index), eb, value);
			Comparable<?> vv2 = (Comparable<?>) parseString(s.substring(index + 2, s.length()), eb, value);
			return (compareTwo(vv1, vv2) == 0);
		} else if ((index = find(s, "!=")) >= 0) {
			return ((Comparable<?>) parseString(s.substring(0, index), eb, value) != (Comparable<?>) parseString(
					s.substring(index + 2, s.length()), eb, value));
		} else if ((index = find(s, "<=")) >= 0) {
			return (compareTwo((Comparable<Comparable<?>>) parseString(s.substring(0, index), eb, value),
					(Comparable<?>) parseString(s.substring(index + 2, s.length()), eb, value)) <= 0);
		} else if ((index = find(s, ">=")) >= 0) {
			Comparable<Comparable<?>> vv1 = (Comparable<Comparable<?>>) parseString(s.substring(0, index), eb, value);
			Comparable<?> vv2 = (Comparable<?>) parseString(s.substring(index + 2, s.length()), eb, value);
			return (compareTwo(vv1, vv2) >= 0);
		} else if ((index = find(s, "<")) >= 0) {
			return (compareTwo((Comparable<Comparable<?>>) parseString(s.substring(0, index), eb, value),
					(Comparable<?>) parseString(s.substring(index + 1, s.length()), eb, value)) < 0);
		} else if ((index = find(s, ">")) >= 0) {
			return (compareTwo((Comparable<Comparable<?>>) parseString(s.substring(0, index), eb, value),
					(Comparable<?>) parseString(s.substring(index + 1, s.length()), eb, value)) > 0);
		} else if ((index = find(s, "-")) >= 0 && index > 0) {
			return sub(parseString(s.substring(0, index), eb, value),
					parseString(s.substring(index + 1, s.length()), eb, value));
		} else if ((index = find(s, "+")) >= 0) {
			return add(parseString(s.substring(0, index), eb, value),
					parseString(s.substring(index + 1, s.length()), eb, value));
		} else if ((index = find(s, "*")) >= 0) {
			return multi(parseString(s.substring(0, index), eb, value),
					parseString(s.substring(index + 1, s.length()), eb, value));
		} else if ((index = find(s, "/")) >= 0) {
			return div(parseString(s.substring(0, index), eb, value),
					parseString(s.substring(index + 1, s.length()), eb, value));
		} else if ((index = find(s, "%")) >= 0) {
			return mod(parseString(s.substring(0, index), eb, value),
					parseString(s.substring(index + 1, s.length()), eb, value));
		} else if ((index = find(s, "!")) >= 0) {
			return !(Boolean) parseString(s.substring(1), eb, value);
		}
		// } else if ((index = find(s, "=")) >= 0) {
		// return set(evaluateIntern(s.substring(0, index), eb, value),
		// evaluateIntern(s.substring(index + 1, s.length()), eb, value));
		// }

		// && s.substring(index, index + 1).compareTo("==") != 0
		// && s.substring(index - 1, index).compareTo("!=") != 0
		// && s.substring(index - 1, index).compareTo("<=") != 0
		// && s.substring(index - 1, index).compareTo(">=") != 0

		// Remove brackets
		if (s.charAt(0) == '(') {
			if (s.charAt(s.length() - 1) == ')')
				return (parseString(s.substring(1, s.length() - 1), eb, value));
			else
				throw new ParserException("Invalid brackets: " + s, null);
		}

		try {
			if (s.matches("true|false")) // Boolean
				return Boolean.parseBoolean(s);
			else if (s.matches("^-?\\d{1,19}$")) { // Long / Int
				long l = Long.parseLong(s);
				if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE)
					return l;
				else
					return (int) l;
			} else if (s.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")) // Double
				return Double.parseDouble(s);
			else if (s.startsWith("'") && s.endsWith("'")) // String
				return s.replaceAll("'", "");
			else if (s.startsWith("SUM(") && s.endsWith(")")) {
				s = s.replaceAll("SUM\\(", "");
				s = s.replaceAll("\\)", "");
				return sum(s, eb);
			} else if (s.startsWith("COUNT(") && s.endsWith(")")) {
				s = s.replaceAll("COUNT\\(", "");
				s = s.replaceAll("\\)", "");
				return count(s, eb);
			} else if (s.startsWith("LENGTH(") && s.endsWith(")")) {
				s = s.replaceAll("LENGTH\\(", "");
				s = s.replaceAll("\\)", "");
				return length(s, eb);
			}
			// else if (s.startsWith("$")) // Variable
			// return getValueFromTarget(s.substring(1), eb, value);
			else
				return getValueFromTarget(s, eb, value);// throw new
														// ParserException("Parsing exception: "
														// + s, null);
		} catch (NumberFormatException ex) {
			throw new ParserException("Parsing exception: " + s, null);
		}
	}

	private Object add(Object a, Object b) {
		if (Integer.class.isInstance(a)) {
			return ((Integer) a) + ((Integer) b);
		} else if (Long.class.isInstance(a)) {
			return ((Long) a) + ((Long) b);
		} else if (Float.class.isInstance(a)) {
			return ((Float) a) + ((Float) b);
		} else if (Double.class.isInstance(a)) {
			return ((Double) a) + ((Double) b);
		} else {
			return null;
		}
	}

	private Object sub(Object a, Object b) {
		if (Integer.class.isInstance(a)) {
			return ((Integer) a) - ((Integer) b);
		} else if (Long.class.isInstance(a)) {
			return ((Long) a) - ((Long) b);
		} else if (Float.class.isInstance(a)) {
			return ((Float) a) - ((Float) b);
		} else if (Double.class.isInstance(a)) {
			return ((Double) a) - ((Double) b);
		} else {
			return null;
		}
	}

	private Object multi(Object a, Object b) {
		if (Integer.class.isInstance(a)) {
			return ((Integer) a) * ((Integer) b);
		} else if (Long.class.isInstance(a)) {
			return ((Long) a) * ((Long) b);
		} else if (Float.class.isInstance(a)) {
			return ((Float) a) * ((Float) b);
		} else if (Double.class.isInstance(a)) {
			return ((Double) a) * ((Double) b);
		} else {
			return null;
		}
	}

	private Object div(Object a, Object b) {
		if (Integer.class.isInstance(a)) {
			return ((Integer) a) / ((Integer) b);
		} else if (Long.class.isInstance(a)) {
			return ((Long) a) / ((Long) b);
		} else if (Float.class.isInstance(a)) {
			return ((Float) a) / ((Float) b);
		} else if (Double.class.isInstance(a)) {
			return ((Double) a) / ((Double) b);
		} else {
			return null;
		}
	}

	private Object mod(Object a, Object b) {
		if (Integer.class.isInstance(a)) {
			return ((Integer) a) % ((Integer) b);
		} else if (Long.class.isInstance(a)) {
			return ((Long) a) % ((Long) b);
		} else if (Float.class.isInstance(a)) {
			return ((Float) a) % ((Float) b);
		} else if (Double.class.isInstance(a)) {
			return ((Double) a) % ((Double) b);
		} else {
			return null;
		}
	}

	private Integer length(String expr, EntityBlueprint eb) throws ParserException {

		int length = 0;

		String[] array = expr.split("\\.");
		if (array.length < 2)
			throw new ParserException("Wrong format. The format should be $table.column", null);

		array[1] = array[1].substring(0, 1).toUpperCase() + array[1].substring(1);

		if (eb.getTable().getName().compareTo(array[0]) == 0) {
			length = eb.getValue(array[1]).toString().length();

		} else { // walk through 1:1

			List<EntityBlueprint> list = getEntities().get(fab.model.getTableByName(array[0]));
			for (Entry<String, Object> value : eb.values.entrySet()) {
				for (EntityBlueprint eb_entry : list) {
					if(EntityBlueprint.class.isInstance(value.getValue())){
						EntityBlueprint e = (EntityBlueprint) value.getValue();
						if (eb_entry.getRefName().compareTo(e.getRefName()) == 0)
							length += eb_entry.getValue(array[1]).toString().length();
					}					
				}
			}
		}

		return length;
	}

	private Integer count(String expr, EntityBlueprint eb) {

		int count = 0;

		String[] array = expr.split("\\.");

		if (eb.getTable().getName().compareTo(array[0]) == 0) {
			List<EntityBlueprint> list = getEntities().get(fab.model.getTableByName(array[0]));
			count = list.size();
		} else { // walk through m:n

			List<EntityBlueprint> list = getEntities().get(fab.model.getTableByName(array[0]));
			for (EntityBlueprint eb_entry : list) {
				Map<Edge, List<EntityBlueprint>> referencedBy = eb_entry.getReferencedBy();
				for (Entry<Edge, List<EntityBlueprint>> entry : referencedBy.entrySet()) {

					ArrayList<EntityBlueprint> blue_list = (ArrayList<EntityBlueprint>) entry.getValue();
					for (EntityBlueprint ebbb : blue_list) {

						for (Entry<String, Object> obj : ebbb.values.entrySet()) {
							if (((EntityBlueprint) obj.getValue()).getRefName() == eb.getRefName()) {
								count += 1;
							}
						}
					}
				}
			}
		}

		return count;
	}

	private Integer sum(String expr, EntityBlueprint eb) throws ParserException {

		int sum = 0;

		String[] array = expr.split("\\.");
		if (array.length < 2)
			throw new ParserException("Wrong format. The format should be $table.column", null);

		array[1] = array[1].substring(0, 1).toUpperCase() + array[1].substring(1);

		if (eb.getTable().getName().compareTo(array[0]) == 0) {
			List<EntityBlueprint> list = getEntities().get(fab.model.getTableByName(array[0]));
			for (EntityBlueprint eb_entry : list) {
				sum += Integer.parseInt((String) eb_entry.values.get(array[1]));
			}
		} else { // walk through m:n

			List<EntityBlueprint> list = getEntities().get(fab.model.getTableByName(array[0]));
			for (EntityBlueprint eb_entry : list) {
				Map<Edge, List<EntityBlueprint>> referencedBy = eb_entry.getReferencedBy();
				for (Entry<Edge, List<EntityBlueprint>> entry : referencedBy.entrySet()) {

					ArrayList<EntityBlueprint> blue_list = (ArrayList<EntityBlueprint>) entry.getValue();
					for (EntityBlueprint ebbb : blue_list) {
						for (Entry<String, Object> obj : ebbb.values.entrySet()) {
							if (((EntityBlueprint) obj.getValue()).getRefName() == eb.getRefName()) {
								sum += Integer.parseInt((String) eb_entry.values.get(array[1]));
							}
						}
					}
				}
			}
		}

		return sum;
	}

	// TODO: set
	// private boolean set(Object var1, Object var2) {
	// var1 = var2;
	// return true;
	// }

	// TODO: Data types
	private Object getValueFromTarget(String s, EntityBlueprint eb, Comparable<?> value) {

		if(s.compareTo("this") == 0){
			return Integer.parseInt(eb.getRefName().split("_")[1]);
		}
		
		if (sourceRef.compareTo(s) == 0) {
			return value;
		}

		String[] array = s.split("\\.");
		Integer instance = -1;
		String refName = eb.getRefName();
		
		if(array[1].contains("[")){
			String inner = array[1].substring(array[1].indexOf("[")+1, array[1].indexOf("]"));
			instance = Integer.parseInt(inner);
			String[] split = refName.split("_");
			
			refName = split[0] + "_" + instance.toString();
		}

		for (Target target : targets) {
			if (target.table.compareTo(array[0]) == 0 && target.column.compareTo(array[1]) == 0) {
				String str = target.getValue(refName).toString();
				if (str.matches("\\d+"))
					return Integer.parseInt((String) target.getValue(eb.getRefName()));
				else
					return target.getValue(refName);
			}
		}

		Object v = null;

		if (array.length == 1) {
			eb.values.put(array[0], v);
		}
		return v;
	}

	private Integer compareTwo(Comparable<Comparable<?>> v1, Comparable<?> v2) {
		if(v1 == null || v2 == null){
			if(v1 != v2)
				return -1;
			else
				return 0;
		}
		return v1.compareTo(v2);
	}

	/*
	 * Was soll die Klasse Constraint können: - sourceRef ist immer ein Wert der
	 * generiert werden soll, aber abhängig von allen "targets" ist und mit
	 * 
	 * Abhängigkeiten: - konstante Werte - Werte aus gleicher Zeile - Werte aus
	 * gleicher Spalte - Werte aus beliebiger Zeile und Spalte - Zugriff auf
	 * andere Tabellen
	 */

	public class Target {
		private HashMap<String, Boolean> hasValue = new HashMap<String, Boolean>();
		private boolean hasPath = false;
		private HashMap<String, Object> value = new HashMap<String, Object>();
		private Object path;
		private String table;
		private String column;

		public void setValue(String index, Object value) {
			this.value.put(index, value);
			this.hasValue.put(index, true);
		}

		public Object getValue(String index) {
			return value.get(index);
		}

		public boolean hasValue(String index) {
			if (hasValue.containsKey(index))
				return hasValue.get(index);
			else
				return false;
		}

		public Object getPath() {
			return path;
		}

		public void setPath(Object path) {
			this.path = path;
			this.hasPath = true;
		}

		public boolean hasPath() {
			return hasPath;
		}
	}
}
/**
 * http://www.sunshine2k.de/coding/java/SimpleParser/ SimpleParser.html
 */
