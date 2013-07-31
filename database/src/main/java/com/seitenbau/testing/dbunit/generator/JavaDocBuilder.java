package com.seitenbau.testing.dbunit.generator;

public class JavaDocBuilder
{
  private final NameProvider names;

  JavaDocBuilder(NameProvider names)
  {
    this.names = names;
  }

  public String createTableExample(Table table, String indention, String innerIndention)
  {
    String tableVar = names.getTableAdapterVariable(table);

    StringBuilder result = new StringBuilder();
    StringBuilder exampleRow = new StringBuilder();

    if (table.isAssociativeTable())
    {
      appendLineStart(result, indention, innerIndention);
      result.append("// Note: it is recommended to use the {@link #relations} API to model n:m associations\n");
    }

    appendLineStart(result, indention, innerIndention);
    result.append(tableVar);
    result.append(".rows {\n");

    appendLineStart(result, indention, innerIndention);
    result.append("  ");

    appendLineStart(exampleRow, indention, innerIndention);
    exampleRow.append("  ");

    boolean separator = false;
    if (!table.isAssociativeTable()) {
      separator = true;
      String ref = "REF";
      String refVar = getSampleRef(table);

      result.append(ref);
      appendSpaces(result, refVar.length() - ref.length());

      exampleRow.append(refVar);
      appendSpaces(exampleRow, ref.length() - refVar.length());
    }


    for (Column col : table.getColumns())
    {
      if (separator) {
        result.append(" | ");
        exampleRow.append(" | ");
      } else {
        separator = true;
      }

      String head = col.getGroovyName();
      String val = getSampleValue(col);

      result.append(head);
      exampleRow.append(val);

      appendSpaces(result, val.length() - head.length());
      appendSpaces(exampleRow, head.length() - val.length());
    }

    result.append("\n");

    result.append(exampleRow);
    result.append("\n");

    appendLineStart(result, indention, innerIndention);
    result.append("}");
    return result.toString();
  }

  public String createRelationExamples(DataSet dataSet, String indention, String innerIndention)
  {
    StringBuilder result = new StringBuilder();
    for (Table table : dataSet._tables) {
      result.append(createRelationsExample(table, indention, innerIndention));
    }
    if (result.length() == 0) {
      appendLineStart(result, indention, innerIndention);
      result.append("// no relations possible");
    }

    // cut off trailing line break
    if (result.substring(result.length()-1).equals("\n")) {
      result.setLength(result.length() - 1);
    }
    return result.toString();
  }

  public String createRelationsExample(Table table, String indention, String innerIndention)
  {
    if (table.isAssociativeTable()) {
      return createAssociativeTableRelationsExample((AssociativeTable)table, indention, innerIndention);
    }

    for (Column col : table.getColumns())
    {
      if (col.getRelation() == null)
      {
        continue;
      }

      StringBuilder result = new StringBuilder();
      appendLineStart(result, indention, innerIndention);

      result.append(getSampleRef(table));
      result.append(".");
      result.append(col.getRelation().getLocalName());
      result.append("(");
      result.append(getSampleRef(col.getRelation().getForeignColumn().getTable()));
      result.append(")\n");

      return result.toString();
    }
    return "";
  }

  private String createAssociativeTableRelationsExample(AssociativeTable table, String indention, String innerIndention)
  {
    Table table1 = table.getAssociativeColumns().get(0).getRelation().getForeignColumn().getTable();
    Table table2 = table.getAssociativeColumns().get(1).getRelation().getForeignColumn().getTable();
    String relation = table.getAssociativeColumns().get(0).getRelation().getForeignName();

    StringBuilder result = new StringBuilder();
    appendLineStart(result, indention, innerIndention);

    result.append(getSampleRef(table1));
    result.append(".");
    result.append(relation);
    result.append("(");
    result.append(getSampleRef(table2));
    result.append(")\n");
    return result.toString();
  }

  private void appendLineStart(StringBuilder builder, String indention, String innerIndention)
  {
    builder.append(indention);
    builder.append("* ");
    builder.append(innerIndention);
  }

  private void appendSpaces(StringBuilder builder, int count)
  {
    for (int i = 0; i < count; i++)
    {
      builder.append(' ');
    }
  }

  public String getSampleRef(Table table)
  {
    return "ANY" + table.getName().toUpperCase() + "REF";
  }

  private String getSampleValue(Column column)
  {
    if (column.getRelation() != null) {
      return getSampleRefValue(column);
    }

    DataType dataType = column.getDataType();
    switch (dataType) {
    case CHAR:
    case LONGVARCHAR:
    case CLOB:
    case VARCHAR:
      return "\"abc\"";

    case NUMERIC:
    case DECIMAL:
    case DOUBLE:
    case FLOAT:
    case REAL:
      return "3.14";

    case INTEGER:
    case TINYINT:
    case SMALLINT:
    case BIGINT:
      return "123";

    case BOOLEAN:
    case BIT:
      return "true";

    case DATE:
    case TIME:
    case TIMESTAMP:
      return "&lt;date&gt;";

    case VARBINARY:
    case BINARY:
    case LONGVARBINARY:
    case BLOB:
      return "aBlob";

    default:
      return "anyvar";
    }
  }

  private String getSampleRefValue(Column column)
  {
    return "ANY" + column.getRelation().getForeignColumn().getTable().getName().toUpperCase() + "REF";
  }
}
