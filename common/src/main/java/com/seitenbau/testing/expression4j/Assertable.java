package com.seitenbau.testing.expression4j;


public interface Assertable<T>
{

  T getAssertBuilder();

  public static class build
  {
    public static <Y> Assertable<Y> of(final Y theAssert)
    {
      return new Assertable<Y>()
      {
        public Y getAssertBuilder()
        {
          return theAssert;
        }

      };
    }
  }

  public static class Util  {

    public static <X> Assertable<X> wrap(final X toWrap)
    {
      return new Assertable<X>(){
        public X getAssertBuilder()
        {
          return toWrap;
        }};
    }
  }
}
