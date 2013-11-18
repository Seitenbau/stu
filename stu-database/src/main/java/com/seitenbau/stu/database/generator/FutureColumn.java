package com.seitenbau.stu.database.generator;

import com.seitenbau.stu.util.Future;

public interface FutureColumn extends Future<Column>
{

  boolean isAvailable();
  
}
