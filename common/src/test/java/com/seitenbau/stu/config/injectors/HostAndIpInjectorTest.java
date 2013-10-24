package com.seitenbau.stu.config.injectors;

import static com.seitenbau.stu.asserts.fest.Assertions.*;
import static org.fest.assertions.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.junit.Test;

import com.seitenbau.stu.config.injectors.HostAndIpInjector;

public class HostAndIpInjectorTest
{
  @Test
  public void testInjectionNewStyle() throws UnknownHostException
  {
    /* prepare */
    InetAddress host = InetAddress.getLocalHost();
    String expectedHostname = host.getHostName();
    String expectedDnsname = host.getCanonicalHostName();
    Properties properties = new Properties();

    /* execute */
    new HostAndIpInjector().injectValues(properties);

    /* verify */

    // add logic to detect this
    assertThat(properties.size()).isGreaterThanOrEqualTo(3);
    assertThat((String) properties.get("_host.name")).startsWith(expectedHostname);
    assertThat(properties.get("_host.ip")).isNotNull();
    assertThat(properties.get("_host.dns")).isEqualTo(expectedDnsname);
  }

  @Test
  public void testInjectionOldStyle() throws UnknownHostException
  {
    /* prepare */
    InetAddress host = InetAddress.getLocalHost();
    String expectedAddress = host.getHostAddress();
    Properties properties = new Properties();

    /* execute */
    HostAndIpInjector sut = new HostAndIpInjector();
    sut.oldStyleIpDetection(properties);

    /* verify */
    assertThat(properties.size()).isEqualTo(1);
    assertThat(properties.get("_host.ip")).isEqualTo(expectedAddress);
  }

  @Test
  public void testDnsInjection() throws UnknownHostException
  {
    /* prepare */
    InetAddress host = InetAddress.getLocalHost();
    String expectedHostname = host.getHostName();
    String expectedDnsname = host.getCanonicalHostName();
    Properties properties = new Properties();

    /* execute */
    HostAndIpInjector sut = new HostAndIpInjector();
    sut.doSetDnsNames(properties);

    /* verify */
    assertThat(properties.size()).isEqualTo(2);
    assertThat(properties.get("_host.name")).isEqualTo(expectedHostname);
    assertThat(properties.get("_host.dns")).isEqualTo(expectedDnsname);
  }
}
