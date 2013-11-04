package com.seitenbau.stu.dsl.tickets;

import static org.fest.assertions.Assertions.*;

import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.Test;

import com.seitenbau.stu.annotation.TestIssue;
import com.seitenbau.stu.dsl.tickets.TicketDsl;
import com.seitenbau.stu.dsl.tickets.TicketList;
import com.seitenbau.stu.dsl.tickets.TicketDsl.TicketState;

public class TicketListTest
{

  @Test
  public void test_OfStrings_Nothing()
  {
    TicketList sut = TicketList.of();
    assertThat(sut.size()).isEqualTo(0);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void test_OfStrings_Null()
  {
    TicketList.of("JIRA-12", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_OfStrings_invalid()
  {
    TicketList.of(" open rainer: JIRA-12");
  }
  
  @Test
  public void test_OfStrings_valid()
  {
    TicketList sut = TicketList.of("open : JIRA-12", "fixed : JIRA-14");
    assertThat(sut.size()).isEqualTo(2);
    assertThat(sut._tickets).onProperty("id").contains("JIRA-12", "JIRA-14");
  }

  @Test
  public void test_OfStrings_unsolved()
  {
    TicketList sut = TicketList.of("open : JIRA-12", "fixed : JIRA-14");
    assertThat(sut.size()).isEqualTo(2);
    assertThat(sut.hasAllTicketsSolved()).isFalse();
  }

  @Test
  public void test_OfStrings_solved()
  {
    TicketList sut = TicketList.of("ignored : JIRA-12", "fixed : JIRA-14");
    assertThat(sut.size()).isEqualTo(2);
    assertThat(sut.hasAllTicketsSolved()).isTrue();
  }

  @Test
  public void test_OfStrings_unSolved()
  {
    TicketList sut = TicketList.of("ignored : JIRA-12", "JIRA-14 ",
        "open : JIRA-16");
    List<TicketDsl> tickets = sut.getAllUnsolvedTickets();
    assertThat(tickets).hasSize(2);
    assertThat(tickets.get(0).getId()).isEqualTo("JIRA-14");
    assertThat(tickets.get(1).getId()).isEqualTo("JIRA-16");
    assertThat(sut.toString()).isEqualTo("[IGNORED:JIRA-12, OPEN:JIRA-14, OPEN:JIRA-16]");
  }

  @Test
  public void test_OfAnnoation_null()
  {
    TicketList sut = TicketList.of((TestIssue) null);
    assertThat(sut.size()).isEqualTo(0);
  }

  @Test
  public void test_OfAnnoation_valueNull()
  {
    TestIssue annotation = create();
    TicketList sut = TicketList.of(annotation);
    assertThat(sut.size()).isEqualTo(0);
  }

  @Test
  public void test_OfAnnoation_valid()
  {
    TestIssue annotation = create("fixed:N123");
    TicketList sut = TicketList.of(annotation);
    assertThat(sut.size()).isEqualTo(1);
    assertThat(sut._tickets.get(0).getId()).isEqualTo("N123");
    assertThat(sut._tickets.get(0).getState()).isEqualTo(TicketState.FIXED);
    assertThat(sut.toString()).isEqualTo("[FIXED:N123]");
  }

  TestIssue create(final String... value)
  {
    TestIssue annotation = new TestIssue()
    {
      public Class<? extends Annotation> annotationType()
      {
        return TestIssue.class;
      }

      public String[] value()
      {
        return value;
      }
    };
    return annotation;
  }

@Test
public void testToString()
{

}

}
