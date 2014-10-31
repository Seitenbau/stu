package com.seitenbau.stu.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import com.seitenbau.stu.data.specs.OfTypeElements;

public class BuilderTest
{
  // @formatter:off
  Builder<Bean> oneOnOne = Builder.build(new Specs(Bean.class){{
          property("value1", new OfTypeElements(){{
            valid("rw1");
            inValid("bad1");
          }});
          property("value2", new OfTypeElements(){{
            valid("rw2");
            inValid("bad2");
          }});
  }});
  // @formatter:on

  // @formatter:off
  Builder<Bean> oneOnTwo = Builder.build(new Specs(Bean.class){{
          property("value1", new OfTypeElements(){{
            valid("rw1");
            inValid("bad1");
          }});
          property("value2", new OfTypeElements(){{
            valid("rw2");
            valid("rw3");
            inValid("bad2");
            inValid("bad3");
          }});
  }});
  // @formatter:on

  // @formatter:off
  Builder<Bean> threeOnThree = Builder.build(new Specs(Bean.class){{
    property("value1", new OfTypeElements(){{
      valid("rwa1");
      valid("rwa2");
      valid("rwa3");
      inValid("bada1");
      inValid("bada2");
      inValid("bada3");
    }});
    property("value2", new OfTypeElements(){{
      valid("rwb1");
      valid("rwb2");
      valid("rwb3");
      inValid("badb1");
      inValid("badb2");
      inValid("badb3");
    }});
  }});
  // @formatter:on

  OfTypeElements value1 = new OfTypeElements()
  {
    {
      valid("rwf1");
      valid("rwf2");
      valid("rwf3");
      inValid("badf1");
      inValid("badf2");
      inValid("badf3");
    }
  };

  // @formatter:off
  Builder<Bean> sameOnSpec = Builder.build(new Specs(Bean.class){{
    property("value1", value1);
    property("value2", value1);
  }});
  // @formatter:on

  @Test
  public void emptySpec() throws Exception
  {
    Specs spec = new Specs(Bean.class);
    Builder<String> builder = new Builder<String>(spec);
    Collection<Object[]> cases = builder.getSuccessCases();
    assertEquals(0, cases.size());
  }

  @Test
  public void validOneAndOneStrings() throws Exception
  {
    Collection<Object[]> cases = oneOnOne.getSuccessCases();
    assertEquals(1, cases.size());
    assertThat(nice(cases), hasCombination("rw1", "rw2"));
  }

  @Test
  public void validOneAndTwoStrings() throws Exception
  {

    Collection<Object[]> cases = oneOnTwo.getSuccessCases();
    assertEquals(2, cases.size());
    assertThat(nice(cases), hasCombination("rw1", "rw2"));
    assertThat(nice(cases), hasCombination("rw1", "rw3"));
  }

  @Test
  public void validThreeOnThreeStrings() throws Exception
  {
    Collection<Object[]> cases = threeOnThree.getSuccessCases();
    assertEquals(3, cases.size());
    assertThat(nice(cases), hasCombination("rwa1", "rwb1"));
    assertThat(nice(cases), hasCombination("rwa2", "rwb2"));
    assertThat(nice(cases), hasCombination("rwa3", "rwb3"));
  }

  @Test
  public void validSameStrings() throws Exception
  {
    Collection<Object[]> cases = sameOnSpec.getSuccessCases();
    assertEquals(3, cases.size());
    assertThat(nice(cases), hasCombination("rwf1", "rwf1"));
    assertThat(nice(cases), hasCombination("rwf2", "rwf2"));
    assertThat(nice(cases), hasCombination("rwf3", "rwf3"));
  }

  @Test
  public void inValidOneAndOneStrings() throws Exception
  {
    Collection<Object[]> cases = oneOnOne.getFailureCases();
    assertEquals(2, cases.size());
    assertThat(nice(cases), hasCombination("bad1", "rw2"));
    assertThat(nice(cases), hasCombination("rw1", "bad2"));
  }

  @Test
  public void inValidOneAndTwoStrings() throws Exception
  {
    Collection<Object[]> cases = oneOnTwo.getFailureCases();
    assertEquals(3, cases.size());
    assertThat(nice(cases), hasCombination("bad1", "rw2"));
    assertThat(nice(cases), hasCombination("rw1", "bad2"));
    assertThat(nice(cases), hasCombination("rw1", "bad3"));
  }

  @Test
  public void inValidThreeOnThreeStrings() throws Exception
  {
    Collection<Object[]> cases = threeOnThree.getFailureCases();
    assertEquals(6, cases.size());
    assertThat(nice(cases), hasCombination("bada1", "rwb1"));
    assertThat(nice(cases), hasCombination("bada2", "rwb1"));
    assertThat(nice(cases), hasCombination("bada3", "rwb1"));
    assertThat(nice(cases), hasCombination("rwa1", "badb1"));
    assertThat(nice(cases), hasCombination("rwa1", "badb2"));
    assertThat(nice(cases), hasCombination("rwa1", "badb3"));
  }

  @Test
  public void inValidSameStrings() throws Exception
  {
    Collection<Object[]> cases = sameOnSpec.getFailureCases();
    assertEquals(6, cases.size());
    assertThat(nice(cases), hasCombination("badf1", "rwf1"));
    assertThat(nice(cases), hasCombination("badf2", "rwf1"));
    assertThat(nice(cases), hasCombination("badf3", "rwf1"));
    assertThat(nice(cases), hasCombination("rwf1", "badf1"));
    assertThat(nice(cases), hasCombination("rwf1", "badf2"));
    assertThat(nice(cases), hasCombination("rwf1", "badf3"));
  }

  // **************************************************************************
  // * Helpers
  // **************************************************************************
  private Matcher<Collection<Object[]>> hasCombination(final String value1,
      final String value2)
  {
    return new BaseMatcher<Collection<Object[]>>()
    {
      public boolean matches(Object arg)
      {
        Collection<Object[]> col = (Collection<Object[]>) arg;
        for (Object[] c : col)
        {
          Bean bean = (Bean) c[0];
          if (bean.value1.equals(value1) && bean.value2.equals(value2))
          {
            return true;
          }
        }
        return false;
      }

      public void describeTo(Description arg)
      {
      }
    };
  }

  private Collection<Object[]> nice(Collection<Object[]> cases)
  {
    ArrayList<Object[]> list = new ArrayList<Object[]>()
    {
      public String toString()
      {
        try
        {
          StringBuffer sb = new StringBuffer();
          for (Object obj : toArray())
          {
            Bean b = (Bean) ((Object[]) obj)[0];
            sb.append("[");
            sb.append(b.value1);
            sb.append(",");
            sb.append(b.value2);
            sb.append("]");
            sb.append("\r\n");
          }
          return sb.toString();
        }
        catch (Throwable t)
        {
          t.printStackTrace();
          return "Error in toString()" + super.toString();
        }
      }
    };
    list.addAll(cases);
    return list;
  }

  public static class Bean
  {
    public String value1;

    public String value2;

    public void setValue1(String value1)
    {
      this.value1 = value1;
    }

    public void setValue2(String value2)
    {
      this.value2 = value2;
    }
  }
}
