package com.seitenbau.testing.config;

import java.util.Properties;

/**
 * Interfaces for postprocessors after loading the Values from the
 * store
 */
public interface ValueProcessor
{

  void beforeLoading(Properties prop);

  void afterLoading(Properties prop);

}
