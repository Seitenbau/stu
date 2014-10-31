package com.seitenbau.stu.data.specs;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import com.seitenbau.stu.data.detail.Representant;
import com.seitenbau.stu.util.DelegateEqualsToComparable;

public class OfTypeDateTest extends OfTypeTestBase
{
  private final Comparable<Representant<Date>> NOW_100ms = compareToNow(100);

  private OfTypeDate sut = new OfTypeDate();

  @Test
  public void withoutParametersValid() throws Exception
  {
    List<Representant<Date>> all = sut.getValidRepresentations();
    assertEquals(2, all.size());
    assertThat(all, hasItem(valid((Date) null)));
    
    assertThat(all, SbCollectionMatcher.hasItem(NOW_100ms));
  }

  @Test
  public void withoutParametersValidRequired() throws Exception
  {
    List<Representant<Date>> all = sut.required(true).getValidRepresentations();
    assertEquals(1, all.size());
    
    assertThat(all, SbCollectionMatcher.hasItem(NOW_100ms));
  }

  @Test
  public void withoutParametersInValid() throws Exception
  {
    List<Representant<Date>> all = sut.getInValidRepresentations();
    assertEquals(0, all.size());
  }

  @Test
  public void withoutParametersInValidRequired() throws Exception
  {
    List<Representant<Date>> all = sut.required(true).getInValidRepresentations();
    assertEquals(1, all.size());
    assertThat(all, hasItem(invalid((Date) null)));
  }

  private Comparable<Representant<Date>> compareToNow(final int maxTimeDiff)
  {
    final Date reference = new Date();
    Comparable<Representant<Date>> y = new Comparable<Representant<Date>>()
    {
      public int compareTo(Representant<Date> anotherDate)
      {
        if (anotherDate == null || anotherDate.getValue()==null)
        {
          return -1;
        }
        long diff = anotherDate.getValue().getTime() - reference.getTime();
        if (diff > maxTimeDiff || diff < (-1 * maxTimeDiff))
        {
          return -1;
        }
        return 0;
      }
    };
    return y;
  }

  public static class SbCollectionMatcher
  {
    @SuppressWarnings("unchecked")
    public static <T> Matcher<Collection<T>> hasItem(final Comparable<T> expectedItem)
    {
      return hasItems(expectedItem);
    }

    public static <T> Matcher<Collection<T>> hasItems(final List<Comparable<T>> expectedItems)
    {
      return new BaseMatcher<Collection<T>>()
      {
        public boolean matches(Object obj)
        {
          if (!(obj instanceof Collection))
          {
            return false;
          }
          Collection<?> collection = (Collection<?>) obj;
          for (Comparable<T> item : expectedItems)
          {
            if (!collection.contains(new DelegateEqualsToComparable<T>(item)))
            {
              return false;
            }
          }
          return true;
        }

        public void describeTo(Description arg0)
        {
        }
      };
    }

    public static <T> Matcher<Collection<T>> hasItems(final Comparable<T>... expectedItem)
    {
      return hasItems(Arrays.asList(expectedItem));
    }

  }
}
