package com.seitenbau.stu.database.migrate.impl;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("table")
public class Table
{
  @XStreamImplicit(itemFieldName = "column")
  List<String> columns;

  @XStreamImplicit
  List<Row> rows;

  @XStreamAsAttribute
  public String name;
}
