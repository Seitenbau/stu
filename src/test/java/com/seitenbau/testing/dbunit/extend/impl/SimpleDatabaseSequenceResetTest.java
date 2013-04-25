package com.seitenbau.testing.dbunit.extend.impl;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.junit.Test;

import com.seitenbau.testing.dbunit.DatabaseTester;
import com.seitenbau.testing.dbunit.tester.DatabaseTesterBase;


public class SimpleDatabaseSequenceResetTest
{
  List<String> _sequences = new ArrayList<String>();

  SimpleDatabaseSequenceReset sut = new SimpleDatabaseSequenceReset()
  {
    protected void clearSequence(DatabaseTesterBase tester, String sequenceName) throws Exception
    {
      _sequences.add(sequenceName);
    }

    @Override
    protected String getSqlCreateSequence(String sequenceName, Integer startIndex)
    {
      return null;
    }

    @Override
    protected String getSqlDropSequence(String sequenceName)
    {
      return null;
    };
  };

  IDataSet dataset = new DefaultDataSet()
  {
    {
      addTable(new DefaultTable("Tabelle1"));
      addTable(new DefaultTable("Tabelle2"));
      addTable(new DefaultTable("Tabelle3"));
    }
  };

  @Test
  public void testEmptyDatabase() throws Exception
  {
    // execute
    sut.doCleanDatabase(null, new DefaultDataSet());
    // verify
    assertThat(_sequences).isEmpty();
  }

  @Test
  public void testEmptyDatabaseFixedSequencesNull() throws Exception
  {
    // preapre
    sut.sequenceName(null);
    
    // execute
    sut.doCleanDatabase(null, new DefaultDataSet());
    
    // verify
    assertThat(_sequences).isEmpty();
  }
  
  @Test
  public void testEmptyDatabaseFixedSequences() throws Exception
  {
    // preapre
    sut.sequenceName("Rainer");

    // execute
    sut.doCleanDatabase(null, new DefaultDataSet());

    // verify
    assertThat(_sequences).containsExactly("Rainer");
  }

  @Test
  public void testAutoDerive_EmptyDataSet() throws Exception
  {
    // preapre
    sut.autoDerivateFromTablename("_SEQ");
    
    // execute
    sut.doCleanDatabase(null, new DefaultDataSet());
    
    // verify
    assertThat(_sequences).isEmpty();
  }
  
  @Test
  public void testAutoDerive_WithPrefix() throws Exception
  {
    // preapre
    sut.autoDerivateFromTablename("_SEQ");
    sut.noResetFor();

    // execute
    sut.doCleanDatabase(null, dataset);

    // verify
    assertThat(_sequences).containsExactly(
        "Tabelle1_SEQ",
        "Tabelle2_SEQ",
        "Tabelle3_SEQ"
        );
  }
  
  @Test
  public void testAutoDerive_WithPrefixAndBlacklist() throws Exception
  {
    // preapre
    sut.autoDerivateFromTablename("_SEQ");
    sut.noResetFor("Tabelle1");
    
    // execute
    sut.doCleanDatabase(null, dataset);
    
    // verify
    assertThat(_sequences).containsExactly(
        "Tabelle2_SEQ",
        "Tabelle3_SEQ"
        );
  }
}
