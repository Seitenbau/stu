package com.seitenbau.testing.expression4j;

public interface Actionable<T>
{
  T getActionBuilder();

  public static class Util
  {

    public static <X> Actionable<X> wrap(final X toWrap)
    {
      return new Actionable<X>()
      {
        public X getActionBuilder()
        {
          return toWrap;
        }
      };
    }

  }
}
