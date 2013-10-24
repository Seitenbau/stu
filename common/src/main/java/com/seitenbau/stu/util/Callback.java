package com.seitenbau.stu.util;

public interface Callback<R, T, E extends Throwable>
{
  R invokeCallback(T input) throws E;
}

