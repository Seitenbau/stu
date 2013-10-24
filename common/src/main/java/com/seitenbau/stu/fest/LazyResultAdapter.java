package com.seitenbau.stu.fest;

import com.seitenbau.stu.util.Future;

public class LazyResultAdapter
{
  static public <TYPE> Future<TYPE> forValue(final TYPE value)
  {
    return new Future<TYPE>()
    {
      public TYPE getFuture()
      {
        return value;
      }
    };
  }
}
