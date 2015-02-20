package com.seitenbau.stu.database.generator.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

import com.seitenbau.stu.database.generator.AssociativeTable;
import com.seitenbau.stu.database.generator.Column;
import com.seitenbau.stu.database.generator.ConstraintColumnPair;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.Edge;
import com.seitenbau.stu.database.generator.Table;
import com.seitenbau.stu.database.generator.data.EntityCreationMode.Direction;
import com.seitenbau.stu.database.generator.values.Result;
import com.seitenbau.stu.database.generator.values.ValueGenerator;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintInterface;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;
import com.seitenbau.stu.database.generator.values.constraints.GlobalConstraint;
import com.seitenbau.stu.database.generator.values.constraints.RConstraint;
import com.seitenbau.stu.database.generator.values.constraints.SolverConstraint;
import com.seitenbau.stu.logger.Logger;
import com.seitenbau.stu.logger.TestLoggerFactory;

public class DataGenerator {
	private final Logger log = TestLoggerFactory.get(DataGenerator.class);

	private final DatabaseModel model;
	private EntityFactory fab;

	private final Set<Edge> visitedEdges;
	private final Set<Table> visitedTables;
	private final HashMap<Table, HashMap<Column, ArrayList<ConstraintPair>>> constraintTable; // TODO: Old ????

	public DataGenerator(DatabaseModel model) {
		this.model = model;
		this.visitedEdges = new HashSet<Edge>();
		this.visitedTables = new HashSet<Table>();
		this.constraintTable = new HashMap<Table, HashMap<Column, ArrayList<ConstraintPair>>>();
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

		// Fill table with data...Run the generators
		for (int i = 0; i < 10; i++) {
			boolean areAllValuesGenerated = true;
			for (Table table : getTableOrder(model)) {
				List<EntityBlueprint> entityBlueprints = fab.getEntities().getTableBlueprints(table);
				for (EntityBlueprint eb : entityBlueprints) {
					for (Column col : eb.table.getColumns()) {

						if (col.getRelation() != null) {
							continue;
						}

						// Value already generated
						if (eb.values.containsKey(col.getJavaName())) {
							continue;
						}

						ValueGenerator g = fab.getValueGenerator(col);
						if (com.seitenbau.stu.database.generator.values.DataGenerator.class.isInstance(g)) {
							com.seitenbau.stu.database.generator.values.DataGenerator dg = (com.seitenbau.stu.database.generator.values.DataGenerator) g;
							dg.setConstraintsData(model.dataSource);
						}

						if (eb.constraints.get(col.getName()) != null) {

							col.getGenerator().setConstraints(eb.constraints.get(col.getName()));

							// if (col.getGenerator().getKey() == null)
							// col.getGenerator().setKey(col.getName());
						}

						if (col.get_set() != null) {
							String[] set = col.get_set();
							g.setSet(set);
							if (col.get_set().length > 0) {
								set = (String[]) ArrayUtils.removeElement(set, set[0]);
								col.set_set(set);
							} else {
								col.set_set(null);
							}
						}

						g.setAllowNull(col.is_allowNull());
						if (col.is_allowNull()) {
							col.set_allowNull(false);
						}

						Result result = g.nextValue(eb);

						if (result.isGenerated()) {
							eb.values.put(col.getJavaName(), (result.getValue() != null) ? result.getValue().toString() : null);
						} else {
							areAllValuesGenerated = false;
						}
					}
				}
			}

			// All values for all tables are generated --> break;
			if (areAllValuesGenerated)
				break;
		}

		Entities result = fab.getEntities();
		result.setLoopCount(count);
		fab = null;
		return result;
	}

	private Object parseString() {
		return null;
	}

	private void addContraintToColumns(ConstraintInterface sc) {
		// TODO: Parse string and add Constraint to Table.Column

		String[] array = sc.sourceRef.split("\\.");

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
				if (!eb.constraints.containsKey(column)) {
					eb.constraints.put(column, new ArrayList<ConstraintInterface>());
				}

				eb.constraints.get(column).add(sc);
			}
		}
	}

	private void visitTable(Table table) {
		if (visitedTables.contains(table)) {
			// table has been visited already
			return;
		}
		visitedTables.add(table);

		if (table.getName().toLowerCase().compareTo("assignment_status") == 0) {
			System.out.println("bla");
		}

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
				EntityBlueprint entity = fab
						.getEntity(leftColumn.getTable(), leftEdge, EntityCreationMode.fixedInt(1, Direction.OUT), null);

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
				EntityBlueprint entity = fab.getEntity(edge.getDestination().getTable(), edge,
						EntityCreationMode.fixed(sourceBorder, Direction.IN), null);
				for (int i = 0; i < sourceBorder.getValue(); i++) {
					EntityBlueprint result = fab.getEntity(edge.getSource().getTable(), edge,
							EntityCreationMode.fixedInt(1, Direction.OUT), entity);
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
