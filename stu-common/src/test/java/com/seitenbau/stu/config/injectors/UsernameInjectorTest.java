package com.seitenbau.stu.config.injectors;

import static org.fest.assertions.Assertions.*;

import java.net.UnknownHostException;
import java.util.Properties;

import org.junit.Test;

public class UsernameInjectorTest
{
  @Test
  public void testInjection() throws UnknownHostException
  {
    /* prepare */
    Properties properties = new Properties();

    /* execute */
    new UsernameInjector().injectValues(properties);

    /* verify */
    assertThat(properties).hasSize(1);
    assertThat(properties.get("_username")).isNotNull();
  }
}
