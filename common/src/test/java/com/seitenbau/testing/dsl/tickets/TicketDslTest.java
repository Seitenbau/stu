package com.seitenbau.testing.dsl.tickets;

import static org.fest.assertions.Assertions.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.seitenbau.testing.dsl.tickets.TicketDsl;
import com.seitenbau.testing.dsl.tickets.TicketDsl.TicketState;

public class TicketDslTest
{
  @Rule
  public ExpectedException expect = ExpectedException.none();

  @Test(expected = IllegalArgumentException.class)
  public void test_ConstructNull()
  {
    new TicketDsl(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_Constructinvalid()
  {
    new TicketDsl("  : ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_ConstuctInvalid2()
  {
    new TicketDsl(" :  : ");
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_ConstuctInvalidType()
  {
    new TicketDsl("rainer:JIRA-12");
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_ConstuctInvalidID()
  {
    new TicketDsl("fixed:   ");
  }

  @Test
  public void test_ConstuctInvalid()
  {
    TicketDsl sut = new TicketDsl("fixed:JIRA-1");
    expect.expect(IllegalArgumentException.class);
    sut.parseId(null);
  }

  @Test
  public void test_ValidTicket()
  {
    TicketDsl ticket = new TicketDsl(" JIRA-12 ");
    assertThat(ticket.getId()).isEqualTo("JIRA-12");
    assertThat(ticket.getState()).isEqualTo(TicketState.OPEN);
    assertThat(ticket.isSolved()).isFalse();
  }

  @Test
  public void test_ValidTicketWithStateOpen()
  {
    TicketDsl ticket = new TicketDsl(" open :  JIRA-13 ");
    assertThat(ticket.getId()).isEqualTo("JIRA-13");
    assertThat(ticket.getState()).isEqualTo(TicketState.OPEN);
    assertThat(ticket.toString()).isEqualTo("OPEN:JIRA-13");
    assertThat(ticket.isSolved()).isFalse();
  }

  @Test
  public void test_ValidTicketWithStateFixed()
  {
    TicketDsl ticket = new TicketDsl(" fixed :  JIRA-13 ");
    assertThat(ticket.getId()).isEqualTo("JIRA-13");
    assertThat(ticket.getState()).isEqualTo(TicketState.FIXED);
    assertThat(ticket.toString()).isEqualTo("FIXED:JIRA-13");
    assertThat(ticket.isSolved()).isTrue();
  }

  @Test
  public void test_ValidTicketWithStateIgnored()
  {
    TicketDsl ticket = new TicketDsl("ignored :  JIRA-13 ");
    assertThat(ticket.getId()).isEqualTo("JIRA-13");
    assertThat(ticket.getState()).isEqualTo(TicketState.IGNORED);
    assertThat(ticket.toString()).isEqualTo("IGNORED:JIRA-13");
    assertThat(ticket.isSolved()).isTrue();
  }

  @Test
  public void test_isTicketDSLStateFailed()
  {
    assertThat(TicketDsl.isTicketDSLState("")).isFalse();
    assertThat(TicketDsl.isTicketDSLState("custom:JIRA-12")).isFalse();
    assertThat(TicketDsl.isTicketDSLState(" : : ")).isFalse();
    assertThat(TicketDsl.isTicketDSLState("JIRA-1")).isFalse();
  }

  @Test
  public void test_isTicketDSLStateSuccess()
  {
    assertThat(TicketDsl.isTicketDSLState(" open ")).isTrue();
    assertThat(TicketDsl.isTicketDSLState("fixed")).isTrue();
    assertThat(TicketDsl.isTicketDSLState("ignored   ")).isTrue();
  }

  @Test
  public void test_isTicketDslFails()
  {
    assertThat(TicketDsl.isTicketDSL("      ")).isFalse();
    assertThat(TicketDsl.isTicketDSL("")).isFalse();
    assertThat(TicketDsl.isTicketDSL(" :  : ")).isFalse();
    assertThat(TicketDsl.isTicketDSL(" : ")).isFalse();
    assertThat(TicketDsl.isTicketDSL("notImpl:rainer")).isFalse();
    assertThat(TicketDsl.isTicketDSL("fixed:  ")).isFalse();
  }
  
  @Test
  public void test_isTicketDslWorks()
  {
    assertThat(TicketDsl.isTicketDSL(" fixed :  JIRA-1")).isTrue();
    assertThat(TicketDsl.isTicketDSL("JIRA-2  ")).isTrue();
    assertThat(TicketDsl.isTicketDSL("ignored  :JIRA-1")).isTrue();
    assertThat(TicketDsl.isTicketDSL("FIXED:JIRA-1  ")).isTrue();
    assertThat(TicketDsl.isTicketDSL("  OpEN:JIRA-1")).isTrue();
  }

}
