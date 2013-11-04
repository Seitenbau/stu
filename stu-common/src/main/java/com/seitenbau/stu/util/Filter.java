package com.seitenbau.stu.util;

public interface Filter<T>
{
  
  boolean accept(T value);

}
