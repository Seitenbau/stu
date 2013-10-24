package com.seitenbau.stu.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.CollectionUtils;

import com.seitenbau.stu.config.impl.PersistentConfiguration;

/**
 * Util class that merges the TestConfiguration into the Spring Context.
 */
public class TestConfigurationPlaceholder extends PropertyPlaceholderConfigurer
{
  @Override
  protected Properties mergeProperties() throws IOException
  {
    Properties result = super.mergeProperties();

    ValueProvider v = TestConfiguration.getInstance();
    Properties p = ((PersistentConfiguration) v).getProperties();

    if (p != null)
    {
      CollectionUtils.mergePropertiesIntoMap(p, result);
    }
    return result;
  }
}
