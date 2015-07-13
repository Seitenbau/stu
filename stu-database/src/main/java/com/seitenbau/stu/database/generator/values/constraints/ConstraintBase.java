package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.seitenbau.stu.database.generator.Table;
import com.seitenbau.stu.database.generator.data.DataGenerator;
import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.data.EntityFactory;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.logger.Logger;
import com.seitenbau.stu.logger.TestLoggerFactory;

//public interface List<E>
//extends Collection<E>
//An ordered collection (also known as a sequence). The user of this interface has precise control over where in the list each element is inserted. The user can access elements by their integer index (position in the list), and search for elements in the list.
//Unlike sets, lists typically allow duplicate elements. More formally, lists typically allow pairs of elements e1 and e2 such that e1.equals(e2), and they typically allow multiple null elements if they allow null elements at all. It is not inconceivable that someone might wish to implement a list that prohibits duplicates, by throwing runtime exceptions when the user attempts to insert them, but we expect this usage to be rare.
// efficiently insert and remove multiple elements at an arbitrary point in the list.
//
//Note: While it is permissible for lists to contain themselves as elements, extreme caution is advised: the equals and hashCode methods are no longer well defined on such a list.
//
//Some list implementations have restrictions on the elements that they may contain. For example, some implementations prohibit null elements, and some have restrictions on the types of their elements. Attempting to add an ineligible element throws an unchecked exception, typically NullPointerException or ClassCastException. Attempting to query the presence of an ineligible element may throw an exception, or it may simply return false; some implementations will exhibit the former behavior and some will exhibit the latter. More generally, attempting an operation on an ineligible element whose completion would not result in the insertion of an ineligible element into the list may throw an exception or it may succeed, at the option of the implementation. Such exceptions are marked as "optional" in the specification for this interface.
//
//This interface is a member of the Java Collections Framework.
//
//Since:
//1.2
//See Also:
//Collection, Set, ArrayList, LinkedList, Vector, Arrays.asList(Object[]), Collections.nCopies(int, Object), Collections.EMPTY_LIST, AbstractList, AbstractSequentialList

/*
 * Subclasses: RangeConstraint, DomainConstraint, LogicalConstraint, UniqueConstraint, FunctionalConstraint, ExpressionConstraint
 */
/**
 * Base class of all constraints
 * 
 */
public abstract class ConstraintBase {
	
	protected final Logger log = TestLoggerFactory.get(DataGenerator.class);
	
	protected String modelRef; // z.B. "table.column"

	// Quell-Namen werden zuvor eingetragen. Liste wird beim erstellen der Daten
	// nachträglich geladen
	protected String[] sourceNames; // {"table.column", "table.column", //
									// "table.column"}

	protected EntityFactory fab;
	protected ArrayList<Source> sources = new ArrayList<Source>();

	protected Scope scope;

	protected Integer priory = 10; // TODO: Priorität gibt an, welche
									// Constraints zuerst behandelt werden
	protected Integer mode;

	protected boolean sourcesLoaded;

	public EntityFactory getFab() {
		return fab;
	}

	public void setFab(EntityFactory fab) {
		this.fab = fab;
	}

	public ArrayList<Source> getSources() {
		return sources;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Integer getPriory() {
		return priory;
	}

	@Override
	public boolean equals(Object o) {
		if (ConstraintBase.class.isInstance(o)) {
			ConstraintBase constraint = (ConstraintBase) o;
			if (constraint.sourceNames == this.sourceNames
					&& constraint.modelRef == this.modelRef
					&& constraint.sources == this.sources) {
				return true;
			}
		}
		return false;
	}

	public String[] getSourceNames() {
		return sourceNames;
	}

	protected Map<Table, List<EntityBlueprint>> getEntities() {
		return fab.blueprints.getEntities();
	}

	public void addSourceNames(EntityBlueprint eb, String... sourceNames) {
		if (this.sourceNames == null) {
			if (sourceNames != null) {
				this.sourceNames = sourceNames;

				Integer length = this.sourceNames.length;
				for (int i = 0; i < length; i++) {
					addSource(i, eb, sourceNames[i]);
				}
			}
		} else
			this.sourceNames = (String[]) ArrayUtils.addAll(this.sourceNames,
					sourceNames);
	}

	public boolean loadSources(EntityBlueprint eb) {
		if (sourcesLoaded)
			return true;

		if (sourceNames == null)
			return true;

		switch (scope) {
		case Cell:
			for (int i = 0; i < sourceNames.length; i++) {
				addSource(i, eb, sourceNames[i]);
			}
			break;
		case Column:
			List<EntityBlueprint> blueprints = fab.blueprints
					.getTableBlueprints(eb.getTable());
			for (int i = 0; i < blueprints.size(); i++) {
				EntityBlueprint blueprint = blueprints.get(i);
				for (int j = 0; j < sourceNames.length; j++) {
					addSource(j, blueprint, sourceNames[j]);
				}
			}
			break;
		default:
			throw new IllegalArgumentException();
		}

		sourcesLoaded = true;
		return true;
	}

	public boolean addSource(Integer i, EntityBlueprint eb, String name) {

		Source source = new Source(name);
		if (!sources.contains(source)) {
			sources.add(source);
		}

		String[] array = sourceNames[i].split("\\.");
		switch (array.length) {
		case 1:
			// TODO: Check
			// Source is an table
			source.setValue(eb.getRefName(),
					getEntities().get(fab.model.getTableByName(array[0])));
			break;
		case 2:

			setCellSource(source, eb, array);
			break;

		case 3:
			if (StringUtils.isNumeric(array[2])) {
				source.setPath(getEntities()
						.get(fab.model.getTableByName(array[0]))
						.get(Integer.parseInt(array[2])).getValue(array[1]));
				break;
			} else {
				// TODO: Regex
				return false; // break;
			}

		default:
			return false;
		}

		return true;
	}

	private void setCellSource(Source source, EntityBlueprint eb, String[] array) {

		source.setTableString(array[0]);
		source.setColumnString(array[1]);
		source.setTable(fab.model.getTableByName(source.getTableString()));
		
		if(source.getTable() == null)
			log.error("Constraint source with reference name " + source.getName() + " not found!");
		
		source.setColumn(source.getTable().ref(source.getColumnString()));

		// This EntityBlueprint
		if (eb.getTable().getName().equals(array[0])
				&& eb.values.containsKey(firstUpperCase(array[1]))) {
			source.setEb(eb);
		} else {
			// 1:1 Relationship
			for (EntityBlueprint entity : fab.blueprints
					.getTableBlueprints(source.getTable())) {
				if (eb.values.containsValue(entity)) {
					source.setEb(entity);
				}

				if (entity.values.containsValue(eb)) {
					source.setEb(entity);
				}
			}

		}

		Object value = source.getEb()
				.getValue(source.getColumn().getJavaName());
		Result result = (Result) value;
		source.addResult(result);
	}

	private String firstUpperCase(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	public boolean allSourcesLoad() {
		for (Source source : sources) {
			for (Result result : source.getResults()) {
				if (!result.isGenerated())
					return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		String returnString = this.getClass().getSimpleName().toString();

		if (sourceNames != null && sourceNames.length > 0) {
			returnString += ": Sources => ";

			for (String sourceName : getSourceNames()) {
				returnString += sourceName + ", ";
			}

			return returnString.substring(0, returnString.length() - 2);
		}

		return returnString;
	}

	/**
	 * <p>
	 * {@code public boolean isValid()}
	 * </p>
	 * 
	 * <p>
	 * Checks if the constraint with the given values is valid. Returns
	 * {@code true} if the check is successful and returns {@code false} if not.
	 * </p>
	 * 
	 * @return {@code true} if the check is successful and returns
	 *         {@code false} if not.
	 */
	public abstract boolean isValid();

	/**
	 * <p>
	 * {@code public ArrayList<Hint> getHints(Result result)}
	 * </p>
	 * 
	 * <p>
	 * Generates a list of hints from all known values. The hints can be used by
	 * the specified ValueGenerator to generate a valid result.
	 * </p>
	 * 
	 * @param result
	 *            The result object for which the hints should apply.
	 * 
	 * @return a list of generated hints.
	 * 
	 * @see com.seitenbau.stu.database.generator.hints.Hint Hint
	 * @see com.seitenbau.stu.database.generator.values.Result Result
	 * @see com.seitenbau.stu.database.generator.values.ValueGenerator ValueGenerator
	 */
	public abstract ArrayList<Hint> getHints(Result result);

	/**
	 * <p>
	 * {@code public ConstraintBase getCopyInstance()}
	 * </p>
	 * 
	 * <p>
	 * Creates a deep copy of the constraint object instance with all attributes.
	 * </p>
	 * 
	 * @return a copy of the constraint object instance.
	 */
	public abstract ConstraintBase getCopyInstance();

	public void clearAllResults() {
		for (Source source : sources) {
			for (Result result : source.getResults()) {
				result.setGenerated(false);
			}
		}
	}

	public boolean containtsResult(Result result) {
		for (Source source : sources) {
			if (source.getResults().contains(result)) {
				return true;
			}
		}
		return false;
	}

	public Integer getCellCount() {
		Integer count = 0;
		for (Source source : sources) {
			count += source.getResults().size();
		}
		return count;
	}
}
