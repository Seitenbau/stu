package com.seitenbau.testing.dbunit.model.dsl;

import groovy.lang.Closure;

import java.util.HashMap;
import java.util.Map;

import com.seitenbau.testing.dbunit.model.JobsTable.JobsWhere;
import com.seitenbau.testing.dbunit.model.JobsTable.RowBuilder_Jobs;
import com.seitenbau.testing.dbunit.dsl.ColumnBinding;
import com.seitenbau.testing.dbunit.dsl.TableParser;
import com.seitenbau.testing.dbunit.dsl.GeneralTableRowCallback;
import com.seitenbau.testing.dbunit.dsl.ITableAdapter;


public class JobsTable {

  public ColumnBinding<RowBuilder_Jobs, JobsWhere> REF = JobsTableBinding.REF;

  ColumnBinding<RowBuilder_Jobs, JobsWhere> id = JobsTableBinding.id;

  ColumnBinding<RowBuilder_Jobs, JobsWhere> title = JobsTableBinding.title;

  ColumnBinding<RowBuilder_Jobs, JobsWhere> description = JobsTableBinding.description;

  com.seitenbau.testing.dbunit.model.JobsTable _table;

  private final Map<JobsRef, RowBuilder_Jobs> _usedRefs;

  private final ITableAdapter<RowBuilder_Jobs, JobsWhere, JobsRef> _adapter = new ITableAdapter<RowBuilder_Jobs, JobsWhere, JobsRef>()
  {
    public RowBuilder_Jobs insertRow() 
    {
      return _table.insertRow();
    }
	  
    public JobsWhere getFindWhere()
    {
      return _table.findWhere;
    }
    
	public void referenceUsed(JobsRef reference, RowBuilder_Jobs row)
	{
	  if (row != null) {
        //System.out.println("Used ref: " + row.values[colRef]);
	    _usedRefs.put(reference, row);
	  }
	}

	public RowBuilder_Jobs getRowByReference(JobsRef reference)
	{
	  return _usedRefs.get(reference);
	}
    
    public String getTableName() {
      return "Jobs";
    }
  };
    
  JobsTable(com.seitenbau.testing.dbunit.model.JobsTable table)
  {
    _table = table;
    _usedRefs = new HashMap<JobsRef, RowBuilder_Jobs>();
  }
  
  Map<JobsRef, RowBuilder_Jobs> getUsedRefs()
  {
    return _usedRefs;
  }
  
  com.seitenbau.testing.dbunit.model.JobsTable getTableModel()
  {
    return _table;
  }
  
  public void rows(Closure<?> rows) {
    GeneralTableRowCallback<RowBuilder_Jobs, JobsWhere, JobsRef> callback = 
        new GeneralTableRowCallback<RowBuilder_Jobs, JobsWhere, JobsRef>(_adapter);
    TableParser.parseTable(rows, this, callback);
  }
}

