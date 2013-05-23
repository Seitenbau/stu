package com.seitenbau.testing.dbunit.model.dsl;

import groovy.lang.Closure;

import java.util.HashMap;
import java.util.Map;

import com.seitenbau.testing.dbunit.model.TeamsTable.TeamsWhere;
import com.seitenbau.testing.dbunit.model.TeamsTable.RowBuilder_Teams;
import com.seitenbau.testing.dbunit.dsl.ColumnBinding;
import com.seitenbau.testing.dbunit.dsl.TableParser;
import com.seitenbau.testing.dbunit.dsl.GeneralTableRowCallback;
import com.seitenbau.testing.dbunit.dsl.ITableAdapter;


public class TeamsTable {

  public ColumnBinding<RowBuilder_Teams, TeamsWhere> REF = TeamsTableBinding.REF;

  ColumnBinding<RowBuilder_Teams, TeamsWhere> id = TeamsTableBinding.id;

  ColumnBinding<RowBuilder_Teams, TeamsWhere> title = TeamsTableBinding.title;

  ColumnBinding<RowBuilder_Teams, TeamsWhere> description = TeamsTableBinding.description;

  ColumnBinding<RowBuilder_Teams, TeamsWhere> membersize = TeamsTableBinding.membersize;

  com.seitenbau.testing.dbunit.model.TeamsTable _table;

  private final Map<TeamsRef, RowBuilder_Teams> _usedRefs;

  private final ITableAdapter<RowBuilder_Teams, TeamsWhere, TeamsRef> _adapter = new ITableAdapter<RowBuilder_Teams, TeamsWhere, TeamsRef>()
  {
    public RowBuilder_Teams insertRow() 
    {
      return _table.insertRow();
    }
	  
    public TeamsWhere getFindWhere()
    {
      return _table.findWhere;
    }
    
	public void referenceUsed(TeamsRef reference, RowBuilder_Teams row)
	{
	  if (row != null) {
        //System.out.println("Used ref: " + row.values[colRef]);
	    _usedRefs.put(reference, row);
	  }
	}

	public RowBuilder_Teams getRowByReference(TeamsRef reference)
	{
	  return _usedRefs.get(reference);
	}
    
    public String getTableName() {
      return "Teams";
    }
  };
    
  TeamsTable(com.seitenbau.testing.dbunit.model.TeamsTable table)
  {
    _table = table;
    _usedRefs = new HashMap<TeamsRef, RowBuilder_Teams>();
  }
  
  Map<TeamsRef, RowBuilder_Teams> getUsedRefs()
  {
    return _usedRefs;
  }
  
  com.seitenbau.testing.dbunit.model.TeamsTable getTableModel()
  {
    return _table;
  }
  
  public void rows(Closure<?> rows) {
    GeneralTableRowCallback<RowBuilder_Teams, TeamsWhere, TeamsRef> callback = 
        new GeneralTableRowCallback<RowBuilder_Teams, TeamsWhere, TeamsRef>(_adapter);
    TableParser.parseTable(rows, this, callback);
  }
}

