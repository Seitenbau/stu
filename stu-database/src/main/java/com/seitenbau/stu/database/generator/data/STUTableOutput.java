package com.seitenbau.stu.database.generator.data;

import com.seitenbau.stu.database.generator.Column;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.NameProvider;
import com.seitenbau.stu.database.generator.Table;

public class STUTableOutput {
	public String createRefs(Entities entities) {
		return createRefs(entities, "");
	}

	private String createRefs(Entities entities, String linePrefix) {
		DatabaseModel model = entities.getModel();
		NameProvider names = new NameProvider(model.getName(), model.getPackageName());

		StringBuilder result = new StringBuilder();
		result.append("package " + model.getPackageName() + ";\n\n");
		
		result.append("public class " + model.getName() + "DataSetRefs\n{\n");

		for (Table table : model.getTables()) {
			if (table.isAssociativeTable()) {
				continue;
			}
			

			final String prefix = "public static " + names.getRefClass(table) + " ";
			final String suffix = " = " + model.getName() + "RefFactory.create" + names.getRefClass(table) + "();\n";
			for (EntityBlueprint bp : entities.getTableBlueprints(table)) {
				result.append("\t");
				result.append(linePrefix);
				result.append(prefix);
				result.append(bp.getRefName());
				result.append(suffix);
			}
		}

		result.append("}\n");

		return result.toString();
	}

	public String create(Entities entities) {
		DatabaseModel model = entities.getModel();

		NameProvider names = new NameProvider(model.getName(), model.getPackageName());
		final String builderClass = names.getBuilderClass();

		StringBuilder result = new StringBuilder();

		result.append("package " + model.getPackageName() + ";\n\n");
		// result.append("import static " + model.getPackageName() + ".HochschuleRefs.*\n\n");
		result.append("import static " + model.getPackageName() + "." + model.getName() + "DataSetRefs.*\n"); //SonferenzDataSetRefs.*\n");

		
		for(Table table: model.getTables()){
			result.append("import static " + model.getPackageName() + "." + table.getJavaName() + "TableAdapter.*\n");
		}		
		
		result.append("import static com.seitenbau.stu.util.DateUtil.*\n\n");

		result.append("class " + model.getName() + "DataSetDefault extends " + builderClass + "\n");
		result.append("{\n");
		result.append("\n");
		// result.append(createRefs(entities, "  "));
		result.append("  def tables() {\n\n");

		for (Table table : model.getTables()) {
			result.append("    ");
			result.append(table.getJavaNameFirstLower());
			result.append("Table.rows() {\n");

			TabluarStringBuilder builder = new TabluarStringBuilder();

			if (!table.isAssociativeTable()) {
				builder.appendColumn("REF");
			}
			for (Column col : table.getColumns()) {
				if (isSkipColumn(col)) {
					continue;
				}
				builder.appendColumn(col.getName().toLowerCase());
			}
			builder.newLine();

			for (EntityBlueprint bp : entities.getTableBlueprints(table)) {
				if (!table.isAssociativeTable()) {
					builder.appendColumn(bp.getRefName());
				}
				for (Column col : table.getColumns()) {
					if (isSkipColumn(col)) {
						continue;
					}
					builder.appendColumn(getValue(bp, col));
				}
				builder.newLine();
			}

			result.append(builder);
			result.append("    }\n\n");
		}
		result.append("  }\n");
		result.append("}\n");
		return result.toString();
	}

	private boolean isSkipColumn(Column col) {
		return col.isAutoInvokeValueGeneration();
	}

	private String getValue(EntityBlueprint bp, Column column) {
		Object value = bp.getValue(column.getJavaName());

		if (value == null) {
			return "_";
		}
		if (value instanceof EntityBlueprint) {
			EntityBlueprint f = (EntityBlueprint) value;
			return f.getRefName();
		}

		// if (value instanceof String) {
		// return "\"" + value.toString() + "\"";
		// }

		return value.toString();
	}
}
