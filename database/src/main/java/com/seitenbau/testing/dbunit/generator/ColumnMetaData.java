package com.seitenbau.testing.dbunit.generator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ColumnMetaData
{

  /**
   * The column value is used to identify the entity (the whole table row). Although a database
   * primary key column is an identifier column, there is no need for an identifier column to be
   * explicit a primary key.
   */
  public static final String IDENTIFIER = "identifier";

  /**
   * Determines, if the corresponding {@code nextValue()} method is
   * generated for the column
   */
  public static final String ADD_NEXT_METHOD = "add_next_method";

  /**
   * The the corresponding {@code nextValue()} method is automatically
   * called on insertRow
   */
  public static final String AUTO_INVOKE_NEXT = "auto_invoke_next";

  public static final String AUTO_INCREMENT = "auto_increment";

  private final Map<String, Boolean> _values;

  ColumnMetaData(Set<String> flags)
  {
    _values = new HashMap<String, Boolean>();
    for (String flag : flags)
    {
      _values.put(flag, true);
    }

    if (requiredNextMethod(flags))
    {
      _values.put(ADD_NEXT_METHOD, true);
    }
  }

  private static boolean requiredNextMethod(Set<String> flags)
  {
    return flags.contains(AUTO_INCREMENT) || flags.contains(AUTO_INVOKE_NEXT);
  }

  public boolean hasFlag(String flag)
  {
    Boolean value = _values.get(flag);
    if (value == null)
    {
      return false;
    }
    return value;
  }

  public Set<String> getFlags()
  {
    return Collections.unmodifiableSet(_values.keySet());
  }

}
