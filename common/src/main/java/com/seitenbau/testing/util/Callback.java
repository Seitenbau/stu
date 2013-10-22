package com.seitenbau.testing.util;

public interface Callback<R, T, E extends Throwable>
{
  R invokeCallback(T input) throws E;
}

