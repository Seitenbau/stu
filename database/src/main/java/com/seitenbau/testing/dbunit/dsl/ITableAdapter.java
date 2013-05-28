package com.seitenbau.testing.dbunit.dsl;

public interface ITableAdapter<R, F, D extends DatabaseReference>
{

  R insertRow();

  F getWhere();

  void bindToScope(D reference, R row);

  void handleReferences(D reference, R row);

  R getRowByReference(D reference);

  String getTableName();

}
