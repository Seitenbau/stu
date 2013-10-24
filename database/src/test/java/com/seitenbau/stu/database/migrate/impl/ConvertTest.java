package com.seitenbau.stu.database.migrate.impl;

import org.junit.Test;

import com.seitenbau.stu.database.migrate.impl.Convert;
import com.seitenbau.stu.database.migrate.impl.DataSet;
import com.seitenbau.stu.database.migrate.impl.Row;
import com.seitenbau.stu.database.migrate.impl.Table;

import static com.seitenbau.stu.asserts.fest.Assertions.*;

public class ConvertTest
{
  @Test
  public void asModel()
  {
    Convert sut = new Convert("");
    DataSet model = sut.modelOf("input01.xml");

    assertThat(model).isNotNull();
    assertThat(model.tables).hasSize(5);
    {
      Table table = model.tables.get(0);
      assertThat(table.name).isEqualTo("ANHANG");
      assertThat(table.rows).hasSize(2);
      {
        Row row = table.rows.get(0);
        assertThat(row.values)
            .hasSize(6)
            .containsSequence("2",
                "Der erste Anhang kommt sofort",
                "Eine Beschreibung",
                "AnhangDokumentWord.doc",
                "0 kB",
                "application/msword");
      }
      {
        Row row = table.rows.get(1);
        assertThat(row.values)
            .hasSize(6)
            .containsSequence("2",
                "Ein netter Artikel",
                "Eine Beschreibung",
                "AnhangWord.doc",
                "33 kB",
                "application/msword");
      }
    }
    {
      Table table = model.tables.get(1);
      assertThat(table.name).isEqualTo("ANHANG_DOKUMENT");
      assertThat(table.columns).hasSize(1)
          .contains("ID");
      assertThat(table.rows).hasSize(2);
      {
        Row row = table.rows.get(0);
        assertThat(row.values)
            .hasSize(1)
            .containsSequence("1");
      }
      {
        Row row = table.rows.get(1);
        assertThat(row.values)
            .hasSize(1)
            .containsSequence("2");
      }
    }
    {
      Table table = model.tables.get(2);
      assertThat(table.name).isEqualTo("NULL");
      assertThat(table.rows).isNull();
      assertThat(table.columns).isNull();
    }
    {
      Table table = model.tables.get(3);
      assertThat(table.name).isEqualTo("EMPTY");
      assertThat(table.rows).hasSize(1);
      assertThat(table.columns).isNull();
    }
    {
      Table table = model.tables.get(4);
      assertThat(table.name).isEqualTo("COLS");
      assertThat(table.rows).isNull();
      assertThat(table.columns).hasSize(1)
          .contains("VORGANG_ID");
    }
  }
}
