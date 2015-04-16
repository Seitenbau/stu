package com.seitenbau.stu.database.generator.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.seitenbau.stu.database.generator.AssociativeTable;
import com.seitenbau.stu.database.generator.Column;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.Edge;
import com.seitenbau.stu.database.generator.Table;
import com.seitenbau.stu.database.generator.data.EntityCreationMode.Direction;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.ValueGenerator;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintInterface;
import com.seitenbau.stu.database.generator.values.constraints.Source;
import com.seitenbau.stu.logger.Logger;
import com.seitenbau.stu.logger.TestLoggerFactory;

public class DataGenerator {
	public enum Mode {
		GENERATE_AND_TEST, BACKTRACKING
	}

	private final Logger log = TestLoggerFactory.get(DataGenerator.class);

	private final DatabaseModel model;
	private EntityFactory fab;

	private final Set<Edge> visitedEdges;
	private final Set<Table> visitedTables;

	private Mode mode = Mode.GENERATE_AND_TEST;

	public DataGenerator(DatabaseModel model) {
		this.model = model;
		this.visitedEdges = new HashSet<Edge>();
		this.visitedTables = new HashSet<Table>();
	}

	public Entities generate() {
		return generate(new LinkedList<Table>());
	}

	public Entities generate(String startTable) {
		return generate(getTable(model, startTable));
	}

	public Entities generate(Table start) {
		List<Table> list = new LinkedList<Table>();
		list.add(start);
		return generate(list);
	}

	public Entities generate(List<Table> order) {
		fab = new EntityFactory(model);
		visitedEdges.clear();
		visitedTables.clear();

		log.debug("visiting tables");
		for (Table table : order) {
			visitTable(table);
		}

		log.debug("ensure all tables have been visitied");
		// visit all unvisited Tables...
		for (Table table : getTableOrder(model)) {
			visitTable(table);
		}

		log.debug("ensureEntityCount");
		fab.ensureEntityCount();

		log.debug("validate blueprints");
		int count = 0;
		while (count < 100 && !validateBlueprints(model)) {
			log.debug("Restarting validation (" + count + ")");
			count++;
		}

		// Walk through all constraints and add them to the columns
		for (ConstraintInterface sc : model.getConstraintsList()) {
			sc.setFab(fab);
			addContraintToColumns(sc);
		}

		generateAllValues();

		Entities result = fab.getEntities();
		result.setLoopCount(count);
		fab = null;
		return result;
	}

	// Notes
	// Indizes:
	// - Table // while
	// - Entity // while
	// - Column // while
	// - Value // while in function
	//
	// - Constraint einer Column // Schleife in function
	// - Sources pro Constraint // Rekursiver Aufruf mit neuen Parametern.

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addContraintToColumns(ConstraintInterface sc) {
		// TODO: Parse string and add Constraint to Table.Column

		String[] array = sc.modelRef.split("\\.");

		if (array.length == 3) {
			String tableName = array[0];
			String columnName = array[1];
			String index = array[2];
			// TODO: Constraint only to one field of a Table....
		} else if (array.length == 2) {
			String tableName = array[0];
			String columnName = array[1];

			addConstraintToBlueprintEntities(tableName, columnName, sc);

		} else if (array.length == 1) {
			String tableName = array[0];
		} else {
			// TODO ????
		}
	}

	/*
	 * 
	 */
	private void addConstraintToBlueprintEntities(String table, String column, ConstraintInterface sc) {
		List<EntityBlueprint> list = fab.blueprints.getTableBlueprints(model.getTableByName(table));

		if (list != null) {
			for (EntityBlueprint eb : list) {
				// if (!eb.constraints.containsKey(column)) {
				// eb.constraints.put(column, new ArrayList<ConstraintInterface>());
				// }

				String col = column.substring(0, 1).toUpperCase() + column.substring(1);

				Object obj = eb.getValue(col);
				if (Result.class.isInstance(obj)) {
					Result result = (Result) obj;
					ConstraintInterface constraintCopy = sc.getCopyInstance();
					constraintCopy.loadSources(eb);
					result.addConstraint(constraintCopy);
				}
			}
		}
	}

	private void generateAllValues() {

		for (Table table : getTableOrder(model)) {
			List<EntityBlueprint> entityBlueprints = fab.getEntities().getTableBlueprints(table);
			for (EntityBlueprint eb : entityBlueprints) {
				for (Column col : eb.table.getColumns()) {
					Object obj = eb.getValue(col.getJavaName());
					if (obj != null) {
						if (EntityBlueprint.class.isInstance(obj))
							continue;

						Result value = (Result) obj;
						if (value.isGenerated())// check if generated
							continue;
					}

					recursiveCall(table, eb, col);					
					constraintList.clear(); // Aktuelle Liste aller gerade betrachteten Constraints leeren
					resultList.clear(); // Aktuelle ResultList leeren
				}
			}
		}
	}

	// In der ArrayList werden alle aktuellen Constraints abgelegt um eine schnelle Pr�fung zu erm�glichen.
	private ArrayList<ConstraintInterface> constraintList = new ArrayList<ConstraintInterface>();
	// In einer Liste werden alle zusammenh�ngenden Values festgehalten.
	private ArrayList<Result> resultList = new ArrayList<Result>();

	/*
	 * Wert einer einzelnen Zelle berechnen
	 */
	private boolean recursiveCall(Table table, EntityBlueprint eb, Column col) {

		//System.out.println(localDeepness.toString() + ": " + col.getName()+ ": " + eb.getRefName());
		
		
		Result result = getResult(table, eb, col);

		ValueGenerator g = fab.getValueGenerator(col);

		if (com.seitenbau.stu.database.generator.values.DataGenerator.class.isInstance(g)) {
			com.seitenbau.stu.database.generator.values.DataGenerator dg = (com.seitenbau.stu.database.generator.values.DataGenerator) g;
			dg.setConstraintsData(model.dataSource);
		}

		result = g.nextValue(result);
		result.setTable(table);
		result.setCol(col);
		result.setEb(eb);
		result.setGenerator(g);
		
		eb.values.put(col.getJavaName(), result);

		if (!resultList.contains(result)) {
			resultList.add(result);
		}else{
			return true;
		}

		// Constraints zur Liste hinzuf�gen
		ArrayList<ConstraintInterface> cons = result.getConstraints();
		if (cons != null && !cons.isEmpty()) {
			for (ConstraintInterface c : cons) {
				c.setResult(result);
				addConstraint(c);
				

//				if(!walkThroughResults())
//					return false;				
			}
		}
		
		ArrayList<Integer> indexesArrayList = new ArrayList<Integer>();
		for(int i = 0; i < resultList.size(); i++){
			indexesArrayList.add(0);
		}
		
		// TODO
		Integer[] combi = recursiveResultWalkthrough(0, convertIntegers(indexesArrayList));
		if(constraintList.size() > 0 && combi == null){
			for(Result res: resultList){
				res.setValue(null);
			}
			
			return false;
		}
			
		
				
		// Alle beteiligten Result-Werte anhand der Indizes-Kombination erstellen
//		for(int i = 0; i < resultList.size(); i++){
//			resultList.get(i).getGenerator().random = new Random(combi[i]);
//			resultList.get(i).setValue(resultList.get(i).getGenerator().nextValue());
//		}
		
		return true;

		// Constraint, welches als erstes nicht valid ist, wird zur�ckgegeben
//		ConstraintInterface notValidConstraint = checkConstraints();
//		if (notValidConstraint != null) {
//
//			ArrayList<Source> sources = notValidConstraint.getSources();
//			if (sources != null) {
//				// TODO: Sources leer
//				for (Source source : sources) {
//
//					Result res = source.getResults().get(0);
//					recursiveCall(res.getTable(), res.getEb(), res.getCol(), localDeepness + 1);
//
//					if (!resultList.contains(res)) {
//
//						if (!walkThroughResults()) {
//							return false;
//						}
//					}
//				}
//			}
//
//			// Alle Sources durchlaufen und rekursiveFunktion aufrufen.
//
//		}
		// try next value

		// Constraints vorhanden
//		if (eb.constraints != null && !eb.constraints.isEmpty()) {
//
//			// Durchlaufe alle zugeh�rigen Constraints und generiere alle abh�ngigen Werte
////			for (ConstraintInterface constraint : eb.constraints.get(col.getJavaName())) {
////
////				if (!constraintList.contains(constraint)) {
////					constraintList.add(constraint);
////				}
////
////				for (Source source : constraint.getSources()) {
////
////					Result sourceValue = (Result) source.getEb().getValue(source.getColumn().getJavaName());
////
////					if (sourceValue == null)
////						recursiveCall(source.getTable(), source.getEb(), source.getColumn(), 0);
////					else {
////						if (checkConstraints() == null) {
////							recursiveCall(source.getTable(), source.getEb(), source.getColumn(), 0);
////						}
////					}
////				}
////			}
//
//		} else { // Keine Constraints
//			eb.values.put(col.getJavaName(), result);
//		}
	}
	
	public static Integer[] convertIntegers(List<Integer> integers)
	{
		Integer[] ret = new Integer[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}

	// Durchlaufe alle bis jetzt hinzugef�gten Results solange, bis alle Constraints valid sind
	private boolean walkThroughResults() {

		int resultIndex = resultList.size() - 1;
		ConstraintInterface constraint = checkConstraints();
		while (constraint != null) {

			// TODO: 
			ArrayList<Source> constraintSourceList = constraint.getSources();			
			ArrayList<Result> constraintResultList = new ArrayList<Result>();
			
			for(Source s: constraintSourceList){
				for(Result r: s.getResults()){
					if(!constraintResultList.contains(r)){
						constraintResultList.add(r);
					}
				}
			}			
		
			Result result = getResultFromList(constraintResultList);
			
			if(result == null)
				return false;			
			

			result.nextValueIndex();			
			
			result.getGenerator().nextValue(result);			
			
			// Get next Value
//
//			// Maximalen Index ermitteln
//			Integer maxIndex = result.getMaxIndex();
//
//			if (result.getValueIndex() < maxIndex)
//				resultList.get(resultIndex);
//			else {
//				if (resultIndex > 0)
//					resultList.get(resultIndex - 1);
//				else
//					return false;
//			}
			constraint = checkConstraints();
		}

		return true;
	}
	
	// TODO: Im result seed festhalten, damit bei sp�terem walkthrough Teilmenge schon richtig ist
	private Integer[] recursiveResultWalkthrough(int depth, Integer[] indexes){		
		
		// Alle beteiligten Result-Werte anhand der Indizes-Kombination erstellen
		for(int i = 0; i < resultList.size(); i++){
			resultList.get(i).getGenerator().random = new Random(indexes[i]);
			resultList.get(i).setValue(resultList.get(i).getGenerator().nextValue());
		}
		
		// Kombination zur�ckgeben, falls alle Constraints erf�llt sind
		if(constraintList.size() > 0 && checkConstraints() == null){
			for(int i = 0; i < indexes.length; i++){
				//if(indexes[i] > 0)
					//System.out.println(indexes[i]);
			}
			return indexes;
		}	
		
		if(depth > 5)
			return null;
		
		for(int j = 0; j < indexes.length; j++){
			Integer[] newIndexes = indexes.clone();
			newIndexes[j] = indexes[j] + 1;
			
			if(indexes[j] > 5)
				return null;
			
			// RecursiveCall
			Integer[] returnValue = recursiveResultWalkthrough(depth+1, newIndexes);
			if(returnValue != null)
				return returnValue;
		}			
	
		return null;
	}
	
	private Result getResultFromList(ArrayList<Result> results){
				
		Integer index = results.size()-1;
		if(index < 0)
			return null;
		
		Result result = results.get(index);
		
		// TODO Backtracking korrigieren
		while(result.getValueIndex() >= result.getMaxIndex()){
			index--;
			if(index >= 0)
				result = results.get(index);
			else
				return null;
		}
		
		return result;	
	}

	private void addConstraint(ConstraintInterface constraint) {
		if (!constraintList.contains(constraint)) {
			constraintList.add(constraint);
			
			// Durchlaufe alle Sources eines Constraints und f�ge alle Results aller Sources zur ResultList hinzu
			ArrayList<Source> sources = constraint.getSources();			
			for (int i = 0; i<sources.size(); i++) {
				Source source = sources.get(i);
				ArrayList<Result> results = source.getResults();
				for (Result result : results) {					

					if (!resultList.contains(result)) {
						recursiveCall(result.getTable(), result.getEb(), result.getCol());
					}
				}
			}

		}
	}

	// Pr�fe alle Constraints und gebe im Fall eines nicht validen Constraints das Constraint-Objekt zur�ck
	// Zu diesem Zeitpunkt sind alle Constraints bekannt
	private ConstraintInterface checkConstraints() {
		for (ConstraintInterface constraint : constraintList) {

			// Alte Vorgehensweise:
			// �berspringe Constaint-Pr�fung, da Constraint nicht aufgel�st werden kann.
			// if(!constraint.allTargetsLoaded())
			// continue;

			// TODO EB und Value �berlegen
			// Pr�fe, ob das Constraint-Bedingung erf�llt ist
			Result result = constraint.getResult();
			if (!constraint.isValid((Comparable<?>) result.getValue(), result.getEb())) {
				String str = ""; 
				for(Result r: resultList){
					str += r.toString() + " - ";
				}
				
				//System.out.println("Fail: " + constraint.getResult().getEb().getRefName() + ": " + str);
				return constraint;
			}else{
				String str = ""; 
				for(Result r: resultList){
					str += r.toString() + " - ";
				}
				
				System.out.println("OK: " + result.getEb().getRefName() + ": " + str);
			}
		}
		return null;
	}

	// ///////////////////////

	// TODO Werden diese Hilfsfunktionen noch ben�tigt???
	private Integer getTableIndex(Table table) {
		return getTableOrder(model).indexOf(table);
	}

	private Integer getEntityIndex(Table table, EntityBlueprint eb) {
		return fab.blueprints.getTableBlueprints(table).indexOf(eb);
	}

	private Integer getColumnIndex(Table table, EntityBlueprint eb, Column col) {
		Integer bpIndex = getEntityIndex(table, eb);
		EntityBlueprint ebElement = fab.blueprints.getTableBlueprints(table).get(bpIndex);
		Object[] array = ebElement.values.values().toArray();
		return Arrays.asList(array).indexOf(col);
	}

	private Result getResult(Table table, EntityBlueprint eb, Column col) {

		ArrayList<EntityBlueprint> entities = (ArrayList<EntityBlueprint>) fab.blueprints.getTableBlueprints(table);
		Integer entityIndex = getEntityIndex(table, eb);
		Object obj = entities.get(entityIndex).getValue(col.getJavaName());
		if (Result.class.isInstance(obj))
			return (Result) obj;

		return null;
	}

	// ///////////////////////

	private void visitTable(Table table) {
		if (visitedTables.contains(table)) {
			// table has been visited already
			return;
		}
		visitedTables.add(table);

		if (table.isAssociativeTable()) {
			handleAssociativeTable((AssociativeTable) table);
		} else {
			handleTable(table);
		}
	}

	private void handleTable(Table table) {

		log.info("handleTable " + table.getName());
		for (Edge edge : table.getOutgoingEdges()) {
			handleEdge(edge, edge.getDestination().getTable());
		}

		for (Edge edge : table.getIncomingEdges()) {
			handleEdge(edge, edge.getSource().getTable());
		}
	}

	private void handleAssociativeTable(AssociativeTable table) {
		log.info("handleAssociativeTable " + table.getName());
		Column leftColumn = table.getAssociativeColumns().get(0);
		Column rightColumn = table.getAssociativeColumns().get(1);

		Multiplicity leftMultiplicity = Multiplicity.parse(leftColumn.getRelation().getForeignMultiplicity());
		Multiplicity rightMultiplicity = Multiplicity.parse(rightColumn.getRelation().getForeignMultiplicity());

		generateAssociative(leftColumn, leftMultiplicity.getMin(), rightColumn, rightMultiplicity.getMin());
		generateAssociative(leftColumn, leftMultiplicity.getMax(), rightColumn, rightMultiplicity.getMin());
		generateAssociative(leftColumn, leftMultiplicity.getMin(), rightColumn, rightMultiplicity.getMax());
		generateAssociative(leftColumn, leftMultiplicity.getMax(), rightColumn, rightMultiplicity.getMax());

		// visit the associated tables :-)
		visitTable(leftColumn.getRelation().getForeignColumn().getTable());
		visitTable(rightColumn.getRelation().getForeignColumn().getTable());
	}

	private void generateAssociative(Column leftColumn, Multiplicity.Border leftBorder, Column rightColumn, Multiplicity.Border rightBorder) {
		Table leftTable = leftColumn.getRelation().getForeignColumn().getTable();
		Table rightTable = rightColumn.getRelation().getForeignColumn().getTable();

		Edge leftEdge = new Edge(leftColumn);
		Edge rightEdge = new Edge(rightColumn);
		visitedEdges.add(leftEdge);
		visitedEdges.add(rightEdge);

		// swap border counts :-)
		int leftCount = Math.max(1, getCount(rightBorder, rightColumn).getValue());
		int rightCount = Math.max(1, getCount(leftBorder, leftColumn).getValue());

		if (isOptional(rightBorder)) {
			fab.getEntity(leftTable, leftEdge, EntityCreationMode.noRelation(), null);
		}
		if (isOptional(leftBorder)) {
			fab.getEntity(rightTable, rightEdge, EntityCreationMode.noRelation(), null);
		}

		EntityBlueprint[] leftBps = new EntityBlueprint[leftCount];
		EntityBlueprint[] rightBps = new EntityBlueprint[rightCount];

		for (int l = 0; l < leftCount; l++) {
			leftBps[l] = fab.getEntity(leftTable, leftEdge, EntityCreationMode.fixedInt(1, Direction.IN), null);
			leftBps[l].setReferencing(leftEdge);
		}
		for (int r = 0; r < rightCount; r++) {
			rightBps[r] = fab.getEntity(rightTable, rightEdge, EntityCreationMode.fixedInt(1, Direction.IN), null);
			rightBps[r].setReferencing(rightEdge);
		}

		for (int l = 0; l < leftCount; l++) {
			for (int r = 0; r < rightCount; r++) {
				EntityBlueprint entity = fab.getEntity(leftColumn.getTable(), leftEdge, EntityCreationMode.fixedInt(1, Direction.OUT), null);

				entity.setReference(leftEdge, leftBps[l]);
				entity.setReference(rightEdge, rightBps[r]);
			}
		}
	}

	private boolean isOptional(Multiplicity.Border border) {
		return border.getValue() == 0;
	}

	private BorderValue getCount(Multiplicity.Border border, Column column) {
		if (border.isInfinite()) {
			if (column.getInfinite() != null) {
				return BorderValue.infinite(column.getInfinite());
			}
			if (column.getTable().getInfinite() != null) {
				return BorderValue.infinite(column.getTable().getInfinite());
			}
			return BorderValue.infinite(model.getInfinite());
		}

		return BorderValue.valueOf(border.getValue());
	}

	private void handleEdge(Edge edge, Table next) {
		if (visitedEdges.contains(edge)) {
			return;
		}

		if (!next.isAssociativeTable()) {
			visitedEdges.add(edge);
			new EdgeHandler(edge, fab).handle();
		}

		visitTable(next);
	}

	private Table getTable(DatabaseModel model, String name) {
		for (Table table : model.getTables()) {
			if (table.getJavaName().equalsIgnoreCase(name)) {
				return table;
			}
		}

		return null;
	}

	private List<Table> getTableOrder(DatabaseModel model) {
		List<Table> result = new LinkedList<Table>();

		for (Table table : model.getTables()) {
			result.add(table);
		}

		Collections.sort(result, new Comparator<Table>() {

			@Override
			public int compare(Table left, Table right) {
				int l = left.getIncomingEdgeCount();
				int r = right.getIncomingEdgeCount();
				return l < r ? -1 : l == r ? 0 : 1;
			}
		});

		return result;
	}

	private boolean validateBlueprints(DatabaseModel model) {
		for (Table table : model.getTables()) {
			if (!table.getOutgoingEdges().isEmpty()) {
				List<EntityBlueprint> bps = new LinkedList<EntityBlueprint>(fab.getEntities().getTableBlueprints(table));
				for (EntityBlueprint bp : bps) {
					if (!validateBlueprintOutgoing(bp, table)) {
						return false;
					}
				}
			}
			if (!table.getIncomingEdges().isEmpty()) {
				List<EntityBlueprint> bps = new LinkedList<EntityBlueprint>(fab.getEntities().getTableBlueprints(table));
				for (EntityBlueprint bp : bps) {
					if (!validateBlueprintIncoming(bp, table)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private boolean validateBlueprintOutgoing(EntityBlueprint bp, Table table) {
		for (Edge edge : table.getOutgoingEdges()) {
			// skip optional relations
			if (edge.getDestination().getMin().getValue() == 0) {
				continue;
			}

			Column col = edge.getColumn();
			if (bp.getValue(col.getJavaName()) == null) {
				EntityBlueprint entity = fab.getEntity(edge.getDestination().getTable(), edge,
						EntityCreationMode.minMax(edge.getSource().getMin(), edge.getSource().getMax(), Direction.IN), bp);
				bp.setReference(edge, entity);
				entity.appendLog("used for validation");
				return false;
			}
		}

		return true;
	}

	private boolean validateBlueprintIncoming(EntityBlueprint bp, Table table) {
		if (table.isAssociativeTable()) {
			return true;
		}

		for (Edge edge : table.getIncomingEdges()) {
			if (edge.getSource().getTable().isAssociativeTable()) {
				return true;
			}

			int min = edge.getSource().getMin().getValue();

			if (min == 0) {
				continue;
			}

			List<EntityBlueprint> referencedByList = bp.getReferencedByList(edge);
			int count = referencedByList.size();
			if (count >= min) {
				continue;
			}

			for (int i = count; i < min; i++) {
				EntityBlueprint entity = fab.getEntity(edge.getSource().getTable(), edge,
						EntityCreationMode.minMax(edge.getDestination().getMin(), edge.getDestination().getMax(), Direction.OUT), bp);
				entity.setReference(edge, bp);
			}

			return false;
		}
		return true;
	}

	static class TableGenerationData {
		final Table table;

		TableGenerationData(Table table) {
			this.table = table;
		}
	}

	class EdgeHandler {

		Edge edge;
		EntityFactory fab;
		Set<String> generated = new HashSet<String>();

		EdgeHandler(Edge edge, EntityFactory fab) {
			this.edge = edge;
			this.fab = fab;
		}

		void handle() {
			// Destination is always m..1
			generate(edge.getDestination().getMin(), edge.getSource().getMin());
			generate(edge.getDestination().getMin(), edge.getSource().getMax());
			generate(edge.getDestination().getMax(), edge.getSource().getMin());
			generate(edge.getDestination().getMax(), edge.getSource().getMax());

			if (edge.getDestination().getMin().getValue() > 0) {
				for (EntityBlueprint bp : fab.getUnrelatedBlueprints(edge.getSource().getTable(), edge)) {
					EntityBlueprint entity = fab.getEntity(edge.getDestination().getTable(), edge,
							EntityCreationMode.minMax(edge.getDestination().getMin(), edge.getDestination().getMax(), Direction.IN), bp);
					bp.setReference(edge, entity);
				}
			}
		}

		void generate(Multiplicity.Border destBorder, Multiplicity.Border sourceBorder) {
			BorderValue dbValue = getCount(destBorder, edge.getColumn().getRelation().getForeignColumn());
			BorderValue sbValue = getCount(sourceBorder, edge.getColumn());
			generate(dbValue, sbValue);
		}

		void generate(BorderValue destBorder, BorderValue sourceBorder) {
			String id = destBorder + ":" + sourceBorder;
			if (generated.contains(id)) {
				return;
			}

			generated.add(id);
			log.info("generate " + id + " for " + edge);

			if (destBorder.getValue() == 0) {
				fab.getEntity(edge.getSource().getTable(), edge, EntityCreationMode.noRelation(), null);
			} else if (sourceBorder.getValue() == 0) {
				fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.noRelation(), null);
			} else {
				EntityBlueprint entity = fab.getEntity(edge.getDestination().getTable(), edge, EntityCreationMode.fixed(sourceBorder, Direction.IN), null);
				for (int i = 0; i < sourceBorder.getValue(); i++) {
					EntityBlueprint result = fab.getEntity(edge.getSource().getTable(), edge, EntityCreationMode.fixedInt(1, Direction.OUT), entity);
					result.setReference(edge, entity);
				}
			}

			if (destBorder.getValue() == 0) {
				generate(destBorder.derive(1), sourceBorder);
			}
			if (sourceBorder.getValue() == 0) {
				generate(destBorder, sourceBorder.derive(1));
			}
		}
	}

	public Entities generate(DatabaseModel model) {
		return generate(model.getTables().get(0));
	}

}
