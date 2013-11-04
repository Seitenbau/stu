package com.seitenbau.stu.database.migrate.impl;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("dataset")
public class DataSet
{
  @XStreamImplicit
  List<Table> tables;

}
