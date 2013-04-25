package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.dbunit.extend.DatabaseTesterCleanAction;
import com.seitenbau.testing.dbunit.extend.DatasetIdGenerator;

public enum Flags
{
  /**
   * Erzeugt in der DBUnit Spalte eine AutoIncrement Flag. Sinnvoll
   * falls Beispielsweise ein {@link DatabaseTesterCleanAction} diese
   * Information benötigt. Gleichzeitig wird aber auch eine nextId()
   * Methode für das Feld generiert. Gleich wie beim AddNextIdMethod.
   */
  AutoIncrement,
  /**
   * Erzeugt zusätzlich zu den setter Methoden der Spalte noch eine
   * nextId Methode (Wobei Id = Spaltenname). Diese Methode erzeugt
   * über einen {@link DatasetIdGenerator} aus dem DataSetModel beim
   * Aufruf die nächste ID. Außerdem werden in der Builder Klasse des
   * erzeugten DataSets bei einem create*() die nextMethoden
   * automatisch gerufen.
   */
  AddNextIdMethod,
  /**
   * 
   */
  AutoInvokeNextIdMethod;
}
