package com.seitenbau.stu.database.generator.values.constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.seitenbau.stu.database.generator.Column;
import com.seitenbau.stu.database.generator.Table;
import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.data.EntityFactory;
import com.seitenbau.stu.database.generator.values.Result;

/*
 * Subclasses: RangeConstraint, DomainConstraint, LogicalConstraint, UniqueConstraint, FunctionalConstraint, ExpressionConstraint
 */
public abstract class ConstraintBase {
	public String modelRef; // z.B. "table.column"	
	
	// Quell-Namen werden zuvor eingetragen. Liste wird beim erstellen der Daten nachträglich geladen
	protected String[] sourceNames; // {"table.column", "table.column",	// "table.column"}

	protected EntityFactory fab;
	protected ArrayList<Source> sources = new ArrayList<Source>();
	
	protected Scope scope;

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
	
	public Scope getScope(){
		return scope;
	}
	
	public void setScope(Scope scope){
		this.scope = scope;
	}
	
	@Override
	public boolean equals(Object o){
		if(ConstraintBase.class.isInstance(o)){
			ConstraintBase constraint = (ConstraintBase) o;
			if(constraint.sourceNames == this.sourceNames && constraint.modelRef == this.modelRef && constraint.sources == this.sources){
				return true;
			}
		}
		return false;
	}
	
	public boolean allTargetsLoaded() {
		if(sources == null)
			return true;
		
		for (Source target : sources) {			
			Table table = target.getTable();
			EntityBlueprint eb = target.getEb();
			Column col = target.getColumn();
			Result result;
			Object value = eb.getValue(col.getJavaName());
			if (value != null) {
				if (Result.class.isInstance(value)) {
					result = (Result) value;
					if (!result.isGenerated())
						return false;
				}
			} else {
				return false;
			}
		}

		return true;
	}
	
	public String[] getSourceNames() {
		return sourceNames;
	}

	protected Map<Table, List<EntityBlueprint>> getEntities() {
		return fab.blueprints.getEntities();
	}

	public void addSourceNames(EntityBlueprint eb, String... sourceNames) {
		if (this.sourceNames == null){
			if(sourceNames != null){
				this.sourceNames = sourceNames;
				
				Integer length = this.sourceNames.length;
				for(int i = 0; i < length; i++){				
					addSource(i, eb, sourceNames[i]);
				}
			}			
		}
		else
			this.sourceNames = (String[]) ArrayUtils.addAll(this.sourceNames, sourceNames);
	}

	public boolean loadSources(EntityBlueprint eb) {
		if(sourcesLoaded)
			return true;
		
		if (sourceNames == null)
			return true;

		if(scope == Scope.Column){
			List<EntityBlueprint> blueprints = fab.blueprints.getTableBlueprints(eb.getTable());
			for(int i = 0; i < blueprints.size(); i++){
				EntityBlueprint blueprint = blueprints.get(i);
				for (int j = 0; j < sourceNames.length; j++) {
					addSource(j, blueprint, sourceNames[j]);
				}
			}
		}else{		
			for (int i = 0; i < sourceNames.length; i++) {
				addSource(i, eb, sourceNames[i]);
			}
		}
		
		sourcesLoaded = true;
		return true;
	}
	
public boolean addSource(Integer i, EntityBlueprint eb, String name){
		
		Source source = new Source(name);
		if(!sources.contains(source)){
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
			// TODO: Check

			if (StringUtils.isNumeric(array[1])) {
				// Source is an entity of the table
				source.setValue(eb.getRefName(), getEntities().get(array[0]).get(Integer.parseInt(array[2])));
			} else {
				int size = getEntities().get(fab.model.getTableByName(array[0])).size();
				Object[] list = new Object[size];
				for (int j = 0; j < size; j++) {
					list[j] = getEntities().get(fab.model.getTableByName(array[0])).get(j);
				}

				// Source is an column of the table
				source.setPath(list);
				source.tableString = array[0];
				source.columnString = array[1];				
				
				String col = source.columnString.substring(0, 1).toUpperCase() + source.columnString.substring(1);
				
				source.setTable(fab.model.getTableByName(source.tableString));
				
				List<EntityBlueprint> bla_list = fab.blueprints.getEntities().get(source.getTable());				
				Integer index = bla_list.indexOf(eb);
				
				source.setEb(fab.blueprints.getTableBlueprints(source.getTable()).get(index));
				source.setColumn(source.getTable().getColumn(source.columnString));
				
				// Null
				
				Object value = source.getEb().getValue(source.getColumn().getJavaName());
				Result result = (Result) value;
				
				source.addResult(result);
			}

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

	public abstract boolean isValid(Comparable<?> value, EntityBlueprint eb);
	
	public abstract boolean isValid(EntityBlueprint eb);

	public abstract boolean loadValues(EntityBlueprint eb);
	
	public ConstraintBase getCopyInstance(){			
		ConstraintBase ci;
		if(scope == Scope.Column)
			return this;
		
		try {
			ci = this.getClass().newInstance();
			ci.fab = this.fab;
			ci.sourceNames = this.sourceNames;
			
			return ci;
			
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
