package com.seitenbau.testing.rules;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import com.seitenbau.testing.annotation.TestIssue;
import com.seitenbau.testing.config.TestConfiguration;
import com.seitenbau.testing.config.TestConfigurationIDs;
import com.seitenbau.testing.dsl.tickets.TicketDsl;
import com.seitenbau.testing.dsl.tickets.TicketList;

/**
 * Enforces all Jira ticket to be marked as closed correctly.
 * <p/>
 * If a tests is successful, but the TestIssue Annotation contains a
 * unsolved ticket. The test is marked red.
 * <p/>
 * To disable either use {@link #enable(boolean)} or set the
 * "sb-testing.TestIssueRule.enabled" Config key to false.
 */
public class TestIssueRule extends BeforeAfterRule
{
  
  boolean _disabled;

  public TestIssueRule()
  {
    Boolean enabled = TestConfiguration.getInstance().getBoolean(TestConfigurationIDs.TestIssueRule_Enable);
    if (enabled != null)
    {
      _disabled = !enabled;
    }
  }

  /**
   * Allow to disable/enable this rule.
   * @param enableRule
   * @return
   */
  public TestIssueRule enable(boolean enableRule)
  {
    _disabled = !enableRule;
    return this;
  }

  @Override
  protected void afterSuccess(ITestMethodDescriptor descriptor)
      throws Throwable
  {
    DisableTestIssueRule ignore =
        descriptor.getAnnotation(DisableTestIssueRule.class, true);
    if (ignore != null)
    {
      return;
    }
    TestIssue annotation = descriptor.getAnnotation(TestIssue.class, true);
    if (annotation == null)
    {
      return;
    }
    if (_disabled)
    {
      System.out.println("WARN: TestIssueRule is globally disabled");
      return;
    }
    TicketList issues = TicketList.of(annotation);
    List<TicketDsl> unsolved = issues.getAllUnsolvedTickets();
    if (!unsolved.isEmpty())
    {
      throw new TestIssueRuleException(
          "Test was successfull. Not all Tickets are solved : "
              + unsolved.toString());
    }
  }

  public static class TestIssueRuleException extends Exception
  {
    private static final long serialVersionUID = 8954720155372105942L;

    public TestIssueRuleException(String message)
    {
      super(message);
    }
  }

  @Retention(RetentionPolicy.RUNTIME)
  public @interface DisableTestIssueRule {

  }

}
