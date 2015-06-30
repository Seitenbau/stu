package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.seitenbau.stu.database.generator.Edge;
import com.seitenbau.stu.database.generator.Table;
import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.data.EntityFactory;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.Result;

/*
 * Subclasses: RangeConstraint, DomainConstraint, LogicalConstraint, UniqueConstraint, FunctionalConstraint, ExpressionConstraint
 */
/**
 * Base class of all constraints
 *
 */
public abstract class ConstraintBase {
	protected String modelRef; // z.B. "table.column"

	// Quell-Namen werden zuvor eingetragen. Liste wird beim erstellen der Daten nachträglich geladen
	protected String[] sourceNames; // {"table.column", "table.column", // "table.column"}

	protected EntityFactory fab;
	protected ArrayList<Source> sources = new ArrayList<Source>();

	protected Scope scope;

	protected Integer priory = 10; // TODO: Priorität gibt an, welche Constraints zuerst behandelt werden
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
			if (constraint.sourceNames == this.sourceNames && constraint.modelRef == this.modelRef && constraint.sources == this.sources) {
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
			this.sourceNames = (String[]) ArrayUtils.addAll(this.sourceNames, sourceNames);
	}

	public boolean loadSources(EntityBlueprint eb) {
		if (sourcesLoaded)
			return true;

		if (sourceNames == null)
			return true;

		// TODO: Handle all scopes
		switch (scope) {
		case Cell:
			for (int i = 0; i < sourceNames.length; i++) {
				addSource(i, eb, sourceNames[i]);
			}
			break;
		case Entity:
		case Column:
			List<EntityBlueprint> blueprints = fab.blueprints.getTableBlueprints(eb.getTable());
			for (int i = 0; i < blueprints.size(); i++) {
				EntityBlueprint blueprint = blueprints.get(i);
				for (int j = 0; j < sourceNames.length; j++) {
					addSource(j, blueprint, sourceNames[j]);
				}
			}
			break;
		case Table:
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
			source.setValue(eb.getRefName(), getEntities().get(fab.model.getTableByName(array[0])));
			break;
		case 2:

			setCellSource(source, eb, array);
			break;

		case 3:
			if (StringUtils.isNumeric(array[2])) {
				source.setPath(getEntities().get(fab.model.getTableByName(array[0])).get(Integer.parseInt(array[2])).getValue(array[1]));
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
		source.setColumn(source.getTable().ref(source.getColumnString()));

		// This EntityBlueprint
		if (eb.getTable().getName().equals(array[0]) && eb.values.containsKey(firstUpperCase(array[1]))) {
			source.setEb(eb);
		} else {
			// 1:1 Relationship
			for(EntityBlueprint entity: fab.blueprints.getTableBlueprints(source.getTable())){
				if(eb.values.containsValue(entity)){
					source.setEb(entity);
				}
				
				if(entity.values.containsValue(eb)){
					source.setEb(entity);
				}
			}			

		}

		Object value = source.getEb().getValue(source.getColumn().getJavaName());
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

	public abstract boolean isValid(EntityBlueprint eb);

	public abstract ArrayList<Hint> getHint(Result result);

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
