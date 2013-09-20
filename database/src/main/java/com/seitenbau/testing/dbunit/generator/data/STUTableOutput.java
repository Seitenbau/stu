package com.seitenbau.testing.dbunit.generator.data;

import com.seitenbau.testing.dbunit.generator.Column;
import com.seitenbau.testing.dbunit.generator.DatabaseModel;
import com.seitenbau.testing.dbunit.generator.NameProvider;
import com.seitenbau.testing.dbunit.generator.Table;

public class STUTableOutput
{
  public String createRefs(Entities entities)
  {
    DatabaseModel model = entities.getModel();
    // TODO initialize correctly
    NameProvider names = new NameProvider("", "");

    StringBuilder result = new StringBuilder();

    for (Table table : model.getTables()) {
      if (table.isAssociativeTable()) {
        continue;
      }

      final String prefix = names.getRefClass(table) + " ";
      final String suffix = " = new " + names.getRefClass(table) + "();\n";
      for (EntityBlueprint bp : entities.getTableBlueprints(table)) {
        result.append(prefix);
        result.append(bp.getRefName());
        result.append(suffix);
      }

      result.append("\n");
    }

    return result.toString();
  }

  public String create(Entities entities)
  {
    DatabaseModel model = entities.getModel();

    StringBuilder result = new StringBuilder();

    for (Table table : model.getTables()) {
      result.append(table.getJavaNameFirstLower() + "Table.rows() {\n");

      TabluarStringBuilder builder = new TabluarStringBuilder();

      if (!table.isAssociativeTable()) {
        builder.appendColumn("REF");
      }
      for (Column col : table.getColumns()) {
        if (isSkipColumn(col)) {
          continue;
        }
        builder.appendColumn(col.getName());
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
      result.append("}\n\n");
    }
    return result.toString();
  }

  private boolean isSkipColumn(Column col)
  {
    return col.isAutoInvokeValueGeneration();
  }

  private String getValue(EntityBlueprint bp, Column column)
  {
    Object value = bp.getValue(column.getJavaName());

    if (value == null) {
      return "_";
    }
    if (value instanceof EntityBlueprint) {
      EntityBlueprint f = (EntityBlueprint)value;
      return f.getRefName();
    }

//    if (value instanceof String) {
//      return "\"" + value.toString() + "\"";
//    }

    return value.toString();
  }
}
