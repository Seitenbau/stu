package com.seitenbau.testing.dbunit.generator;


public class JavaDocBuilder
{
  private final NameProvider names;

  JavaDocBuilder(NameProvider names)
  {
    this.names = names;
  }

  public String createTableExample(Table table, String intentation)
  {
    String tableVar = names.getTableAdapterVariable(table);

    StringBuilder result = new StringBuilder();
    StringBuilder exampleRow = new StringBuilder();
    appendLineStart(result, intentation);
    result.append(tableVar);
    result.append(".rows {\n");

    appendLineStart(result, intentation);
    result.append("  REF   ");
    exampleRow.append("  ANYREF");
    for (Column col : table.getColumns())
    {
      String head = col.getGroovyName();
      String val = getSampleValue(col);

      result.append(" | ");
      result.append(head);
      appendSpaces(result, val.length() - head.length());

      exampleRow.append(" | ");
      exampleRow.append(val);
      appendSpaces(exampleRow, head.length() - val.length());
    }

    result.append("\n");

    appendLineStart(result, intentation);
    result.append(exampleRow);
    result.append("\n");

    appendLineStart(result, intentation);
    result.append("}");
    return result.toString();
  }

  private void appendLineStart(StringBuilder builder, String intentation)
  {
    builder.append(intentation);
    builder.append("* ");
  }

  private void appendSpaces(StringBuilder builder, int count)
  {
    for (int i = 0; i < count; i++)
    {
      builder.append(' ');
    }
  }

  private String getSampleValue(Column column)
  {
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
      return "<date>";

    case VARBINARY:
    case BINARY:
    case LONGVARBINARY:
    case BLOB:
      return "aBlob";

    default:
      return "anyvar";
    }
  }
}
