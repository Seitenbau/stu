package com.seitenbau.testing.dbunit.model.dsl;

import groovy.lang.Closure;

import java.util.HashMap;
import java.util.Map;

import com.seitenbau.testing.dbunit.model.PersonsTable.PersonsWhere;
import com.seitenbau.testing.dbunit.model.PersonsTable.RowBuilder_Persons;
import com.seitenbau.testing.dbunit.dsl.ColumnBinding;
import com.seitenbau.testing.dbunit.dsl.TableParser;
import com.seitenbau.testing.dbunit.dsl.GeneralTableRowCallback;
import com.seitenbau.testing.dbunit.dsl.ITableAdapter;


public class PersonsTable {

  public ColumnBinding<RowBuilder_Persons, PersonsWhere> REF = PersonsTableBinding.REF;

  ColumnBinding<RowBuilder_Persons, PersonsWhere> id = PersonsTableBinding.id;

  ColumnBinding<RowBuilder_Persons, PersonsWhere> first_name = PersonsTableBinding.first_name;

  ColumnBinding<RowBuilder_Persons, PersonsWhere> name = PersonsTableBinding.name;

  ColumnBinding<RowBuilder_Persons, PersonsWhere> job_id = PersonsTableBinding.job_id;

  public ColumnBinding<RowBuilder_Persons, PersonsWhere> job = PersonsTableBinding.job_id;

  ColumnBinding<RowBuilder_Persons, PersonsWhere> team_id = PersonsTableBinding.team_id;

  public ColumnBinding<RowBuilder_Persons, PersonsWhere> team = PersonsTableBinding.team_id;

  com.seitenbau.testing.dbunit.model.PersonsTable _table;

  private final Map<PersonsRef, RowBuilder_Persons> _usedRefs;

  private final ITableAdapter<RowBuilder_Persons, PersonsWhere> _adapter = new ITableAdapter<RowBuilder_Persons, PersonsWhere>()
  {
    public RowBuilder_Persons insertRow() {
      return _table.insertRow();
    }
	  
    public PersonsWhere getFindWhere() {
      return _table.findWhere;
    }
  };
    
  PersonsTable(com.seitenbau.testing.dbunit.model.PersonsTable table)
  {
    _table = table;
    _usedRefs = new HashMap<PersonsRef, RowBuilder_Persons>();
  }
  
  Map<PersonsRef, RowBuilder_Persons> getUsedRefs()
  {
    return _usedRefs;
  }
  
  com.seitenbau.testing.dbunit.model.PersonsTable getTableModel()
  {
    return _table;
  }
  
  public void rows(Closure<?> rows) {
    GeneralTableRowCallback<RowBuilder_Persons, PersonsWhere, PersonsRef> callback = 
        new GeneralTableRowCallback<RowBuilder_Persons, PersonsWhere, PersonsRef>(_adapter, _usedRefs);
    TableParser.parseTable(rows, this, callback);
  }
}

