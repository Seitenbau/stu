package com.seitenbau.stu.dbunit.generator.data;

import java.util.ArrayList;
import java.util.List;

public class TabluarStringBuilder
{
  final String indentation = "      ";

  final String separator = "|";

  final String margin = " ";

  final List<Integer> columnWidths;

  final List<List<String>> rows;

  private int columnIndex;

  private List<String> activeRow;

  public TabluarStringBuilder()
  {
    columnWidths = new ArrayList<Integer>();
    rows = new ArrayList<List<String>>();

    columnIndex = 0;
    activeRow = new ArrayList<String>();
  }

  public void appendColumn(String value)
  {
    activeRow.add(value);

    if (columnWidths.size() <= columnIndex)
    {
      columnWidths.add(Integer.valueOf(value.length()));
    } else if (columnWidths.get(columnIndex) < value.length()) {
      columnWidths.set(columnIndex, Integer.valueOf(value.length()));
    }

    columnIndex++;
  }

  public void newLine()
  {
    rows.add(activeRow);
    columnIndex = 0;
    activeRow = new ArrayList<String>();
  }

  @Override
  public String toString()
  {
    StringBuilder result = new StringBuilder();
    for (List<String> row : rows) {
      result.append(indentation);
      int i = 0;
      for (String value : row) {
        if (i > 0) {
          result.append(separator);
          result.append(margin);
        }
        result.append(value);
        appendSpaces(result, columnWidths.get(i++) - value.length());
        result.append(margin);
      }

      trimSpaces(result);

      result.append("\n");
    }

    return result.toString();
  }

  private void appendSpaces(StringBuilder builder, int count)
  {
    for (int i = 0; i < count; i++) {
      builder.append(' ');
    }
  }

  private void trimSpaces(StringBuilder builder)
  {
    while (builder.length() > 0 && builder.charAt(builder.length()-1) == ' ') {
      builder.setLength(builder.length() - 1);
    }
  }
}
