package com.seitenbau.testing.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.CollectionUtils;

import com.seitenbau.testing.config.impl.PersistentConfiguration;

/**
 * Hilfsklasse. Merged die TestConfiguration in den Spring Context
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
