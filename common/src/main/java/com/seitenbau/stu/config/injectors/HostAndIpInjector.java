package com.seitenbau.stu.config.injectors;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.seitenbau.stu.logger.Logger;
import com.seitenbau.stu.logger.TestLoggerFactory;

/**
 * Detector for your local-external ip. In case this is not an
 * 192.168.x.x address, it tries to do heuristical detection over all
 * network interfaces.
 */
public class HostAndIpInjector implements ValueInjector
{
  Logger logger = TestLoggerFactory.get(HostAndIpInjector.class);

  public void injectValues(Properties prop)
  {
    try
    {
      doSetDnsNames(prop);
      
      String ip = InetAddress.getLocalHost().getHostAddress();
      if (ip.matches("192\\.168\\.\\d{1,3}\\.\\d+"))
      {
        oldStyleIpDetection(prop);
      }
      else
      {
        logger.debug("IP '" + ip
            + "' doesn't match : \"192\\.168\\.\\d{1,3}\\.\\d+\" -> try heuristic to detect your real ip");
        newStyleIpDetection(prop);
      }
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  protected void newStyleIpDetection(Properties prop) throws SocketException
  {
    List<NetworkInterface> ipInterfaces = findAllIpInterfaces();
    logger.debug("nic list: " + asList(ipInterfaces));
    Collections.sort(ipInterfaces, new Comparator<NetworkInterface>()
    {
      public int compare(NetworkInterface o1, NetworkInterface o2)
      {
        int rank1 = rank(o1);
        int rank2 = rank(o2);
        return rank2 - rank1;
      }
    });
    logger.debug("ranked  : " + asList(ipInterfaces));
    NetworkInterface first = ipInterfaces.get(0);
    injectIpAddresses(prop, first);
  }

  protected String asList(List<NetworkInterface> ipInterfaces)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (NetworkInterface netint : ipInterfaces)
    {
      sb.append("{ hash='");
      sb.append(netint.hashCode());
      sb.append("', displayName='");
      sb.append(netint.getDisplayName());
      sb.append("', name='");
      sb.append(netint.getName());
      Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
      for (InetAddress inetAddress : Collections.list(inetAddresses))
      {
        sb.append("', ip='");
        sb.append(inetAddress.getHostAddress());
      }
      sb.append("',");
      sb.append("},");
    }
    sb.append("]");
    return sb.toString();
  }

  protected int rank(NetworkInterface o1)
  {
    int rank = 0;
    // *********** Displayname ranking
    String dpn = o1.getDisplayName();
    // Virtual adapters
    if (dpn.contains("Loopback"))
    {
      rank -= 5;
    }
    if (dpn.contains("Host-Only Network"))
    {
      rank -= 5;
    }
    if (dpn.contains("Microsoft"))
    {
      rank -= 1;
    }
    if (dpn.contains("VMware Virtual Ethernet Adapter"))
    {
      rank -= 1;
    }
    else if (dpn.contains("VMware"))
    {
      rank -= 1;
    }
    if (dpn.contains("VirtualBox"))
    {
      rank -= 1;
    }

    // Physical Adapters
    if (dpn.contains("Intel"))
    {
      rank += 1;
    }
    if (dpn.contains("Gigabit Network Connection"))
    {
      rank += 1;
    }
    // *********** Name ranking
    String name = o1.getName();
    // Virtual adapters
    if (name.equalsIgnoreCase("Lo")) // debian loopback name
    {
      rank -= 5;
    }
    // Physical dapters
    if (name.startsWith("eth"))
    {
      rank += 1;
    }
    return rank;
  }

  protected List<NetworkInterface> findAllIpInterfaces() throws SocketException
  {
    List<NetworkInterface> result = new ArrayList<NetworkInterface>();
    for (NetworkInterface netint : getAllNetworkInterfaces())
    {
      if (hasIpAddress(netint))
      {
        result.add(netint);
      }
    }
    return result;
  }

  protected List<NetworkInterface> getAllNetworkInterfaces() throws SocketException
  {
    return Collections.list(NetworkInterface.getNetworkInterfaces());
  }

  protected boolean hasIpAddress(NetworkInterface netint)
  {
    return netint.getInetAddresses().hasMoreElements();
  }

  protected void doSetDnsNames(Properties prop) throws UnknownHostException
  {
    InetAddress host = InetAddress.getLocalHost();
    String hostName = host.getHostName();
    String hostDns = host.getCanonicalHostName();

    prop.put("_host.name", hostName);
    prop.put("_host.dns", hostDns);
  }
  
  protected void oldStyleIpDetection(Properties prop) throws UnknownHostException
  {
    InetAddress host = InetAddress.getLocalHost();
    String hostIP = host.getHostAddress();
    prop.put("_host.ip", hostIP);
  }

  protected void doSetPropertiesIp6(Properties prop, Inet6Address ipv6)
  {
    String hostIP = ipv6.getHostAddress();
    prop.put("_host.ipV6", hostIP);
  }

  protected void doSetPropertiesIp4(Properties prop, Inet4Address ipv4)
  {
    String hostIP = ipv4.getHostAddress();
    prop.put("_host.ipV4", hostIP);
  }

  protected void doSetPropertyIp(Properties prop, InetAddress host)
  {
    logger.debug("detected local ip as : " + host.getHostAddress());
    String hostIP = host.getHostAddress();
    prop.put("_host.ip", hostIP);
  }

  protected void injectIpAddresses(Properties prop, NetworkInterface nic)
  {
    ArrayList<InetAddress> inetadrs = Collections.list(nic.getInetAddresses());
    if (inetadrs.size() == 1)
    {
      InetAddress host = inetadrs.get(0);
      doSetPropertyIp(prop, host);
      return;
    }
    Inet4Address ipv4 = null;
    Inet6Address ipv6 = null;
    for (InetAddress ip : inetadrs)
    {
      if (ipv4 == null && ip instanceof Inet4Address)
      {
        ipv4 = (Inet4Address) ip;
        doSetPropertiesIp4(prop, ipv4);
      }
      if (ipv6 == null && ip instanceof Inet6Address)
      {
        ipv6 = (Inet6Address) ip;
        doSetPropertiesIp6(prop, ipv6);
      }
    }
    if (ipv4 != null)
    {
      doSetPropertyIp(prop, ipv4);
    }
    else if (ipv6 != null)
    {
      doSetPropertyIp(prop, ipv6);
    }
    else
    {
      logger.error("Failed to detect an ipv4 or an ipv6 adress!!");
    }
  }

}
