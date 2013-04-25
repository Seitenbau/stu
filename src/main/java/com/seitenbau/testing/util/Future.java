package com.seitenbau.testing.util;

/**
 * Very simple Future implementation.
 * 
 * Helps getting a given value at some point in the future. Normally
 * the builder "Future.value.of( YourValue )" should be sufficient.
 * 
 * @param <TYPE> Type of the Future value to return
 */
public interface Future<TYPE>
{
  TYPE getFuture();

  class value
  {
    public static <T> Future<T> of(final T futureObject)
    {
      return new Future<T>()
      {
        public T getFuture()
        {
          return futureObject;
        }

      };
    }
  }
}
