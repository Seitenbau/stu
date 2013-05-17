package com.seitenbau.testing.dbunit.generator.builder;

import com.seitenbau.testing.dbunit.generator.DataType;

public class Main
{
  public static void main(String[] args) {
    TableBuilder tableBuilder = new TableBuilder("sample");
    Table sampleTable = tableBuilder
        .column("id", DataType.BIGINT)
            .identifierColumn()
            .autoIdHandling()
        .column("title", DataType.VARCHAR)
    .build();
    
    System.out.println(sampleTable);
} 
}
