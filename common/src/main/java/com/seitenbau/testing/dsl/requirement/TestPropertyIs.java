package com.seitenbau.testing.dsl.requirement;

import com.seitenbau.testing.config.TestConfiguration;

/**
*  Test if the given TestConfigurationProperty Is set.
 * 
 * Checks if 
 * <pre>custom:com.seitenbau.testing.dsl.requirement.TestPropertyIs:key=rainer</pre>
 * key equals "rainer"
 * <pre>custom:com.seitenbau.testing.dsl.requirement.TestPropertyIs:!key=rainer</pre>
 * key not equals "rainer"
 */
public class TestPropertyIs extends AbstractKeyValueDetector
{
  /**
   * This is how your requriement DSL String should begin ... <br/>
   * (just a hint, you cannot use this directly in your annotation) 
   */
  public static final String HINT = "custom:"+TestPropertyIs.class.getCanonicalName();

  @Override
  protected boolean readState(String key, String expect)
  {
    String current = readValue(key);
    return check(current, expect);
  }

  protected boolean check(String current, String expect)
  {
    if (expect == null)
    {
      return current == null;
    }
    if (current == null)
    {
      return false;
    }
    return current.equals(expect);
  }

  protected String readValue(String key)
  {
    return TestConfiguration.getString(key);
  }

}
