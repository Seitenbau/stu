package com.seitenbau.testing.rules;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.seitenbau.testing.annotation.TestIssue;
import com.seitenbau.testing.rules.TestIssueRule.DisableTestIssueRule;
import com.seitenbau.testing.rules.TestIssueRule.TestIssueRuleException;

interface JiraTickets {
	String UNSOLVED = "JIRA-12";

	String SOLVED = "fixed:JIRA-12";

	String IGNORED = "ignored:JIRA-12";
}

@SuppressWarnings("serial")
class MyAssertionError extends AssertionError {
}

public class TestIssueRuleTest {
	public TestIssueRule issues = new TestIssueRule();

	public ExpectedException exception = ExpectedException.none();

	@Rule
	public CompositeRule rules = new CompositeRule().add(exception).add(issues);

	@Test(expected = MyAssertionError.class)
	public void assertFail_NoTicket_Test() {
		throw new MyAssertionError();

	}

	@Test
	public void success_NoTicket_Test() {
		// success
	}

	@Test
	@TestIssue(JiraTickets.UNSOLVED)
	public void assertFail_UnsolvedTicket_Test() {
		exception.expect(MyAssertionError.class);
		throw new MyAssertionError();
	}

	@Test
	@TestIssue(JiraTickets.UNSOLVED)
	public void success_UnsolvedTicket_Test() {
		exception.expect(TestIssueRuleException.class);
		// success
	}

	@Test
	@TestIssue(JiraTickets.UNSOLVED)
	@DisableTestIssueRule
	public void success_UnsolvedTicket_ButIgnore_Test() {
		// success
	}

	@Test(expected = MyAssertionError.class)
	@TestIssue(JiraTickets.SOLVED)
	public void assertFail_solvedTicket() {
		throw new MyAssertionError();
	}

}
