package com.seitenbau.stu.rules;

import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Interface for Parameterized Testrunner to inject
 * the current test parameters. Mainly : SBParmeterizedRunner
 */
public interface ParameterAwareRule 
{
  Statement applyParamTest(Statement result, FrameworkMethod method,
      Object target, int fParameterSetNumber, List<Object[]> fParameterList);
}
