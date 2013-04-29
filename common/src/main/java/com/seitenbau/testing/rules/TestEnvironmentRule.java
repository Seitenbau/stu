package com.seitenbau.testing.rules;

import com.seitenbau.testing.annotation.TestRequiresEnvironment;
import com.seitenbau.testing.config.TestConfiguration;
import com.seitenbau.testing.config.ValueProvider;

public class TestEnvironmentRule extends BeforeAfterRule
{
  @Override
   protected void before(ITestMethodDescriptor descriptor) throws Throwable {
      TestRequiresEnvironment anno 
            = descriptor.getAnnotation(TestRequiresEnvironment.class, false);
      if (anno != null)
      {
        process(TestConfiguration.getInstance(), anno.value());
      }
  }
 

  protected void process(ValueProvider values, String[] rules)
  {
    if (rules == null || rules.length == 0)
    {
      return;
    }
    for (String rule : rules)
    {
      interpretRule(values, rule);
    }
  }

  protected void interpretRule(ValueProvider values, String rule)
  {
    String key = rule;
    boolean invert = false;
    if (key.startsWith("!"))
    {
      key = rule.substring(1);
      invert = true;
    }
    Boolean value = values.getBoolean(key);
    if (invert)
    {
      value = !value;
    }
    if (!value)
    {
      doIgnoreTest("Test was ignored because Environment '" + key + "' was '" + value + "'");
    }
  }

}
