package com.seitenbau.testing.dbunit.migrate.impl;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("row")
public class Row
{
  @XStreamImplicit(itemFieldName = "value")
  List<String> values;
}
