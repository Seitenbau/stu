package com.seitenbau.stu.rules;

import org.junit.runners.model.FrameworkMethod;

public interface IStatementWithInstance
{
  Object getTargetInstance();

  FrameworkMethod getFrameworkMethod();
}
