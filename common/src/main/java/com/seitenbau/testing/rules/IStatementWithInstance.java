package com.seitenbau.testing.rules;

import org.junit.runners.model.FrameworkMethod;

public interface IStatementWithInstance
{
  Object getTargetInstance();

  FrameworkMethod getFrameworkMethod();
}
