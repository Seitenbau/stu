package com.seitenbau.stu.database.generator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ColumnMetaData
{

  /**
   * The column value can be used to identify the table row.
   * Although a database primary key column is an identifier column, there is no need for an
   * identifier column to be explicit a primary key.
   */
  public static final String IDENTIFIER = "stu_identifier";

  public static final String DEFAULT_IDENTIFIER = "stu_default_identifier";

  /**
   * Determines, if the corresponding {@code nextValue()} method is
   * generated for the column
   */
  public static final String ADD_NEXT_METHOD = "stu_add_next_method";

  /**
   * The the corresponding {@code nextValue()} method is automatically
   * called on insertRow
   */
  public static final String AUTO_INVOKE_NEXT = "stu_auto_invoke_next";

  public static final String AUTO_INCREMENT = "stu_auto_increment";

  public static final String IMMUTABLE = "stu_immutable";

  private final Map<String, Boolean> _values;

  ColumnMetaData(Set<String> flags)
  {
    _values = new HashMap<String, Boolean>();
    for (String flag : flags)
    {
      _values.put(flag, true);
    }

    if (isNextMethodRequired(flags))
    {
      _values.put(ADD_NEXT_METHOD, true);
    }
  }

  /**
   * Checks if there are flags which essentially require the generation of the nextValue methods
   */
  private static boolean isNextMethodRequired(Set<String> flags)
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
