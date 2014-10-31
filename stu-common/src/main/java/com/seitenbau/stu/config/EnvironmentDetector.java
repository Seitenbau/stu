package com.seitenbau.stu.config;

public interface EnvironmentDetector
{
  /**
   * Return environment Names
   * @return
   *   List of environments, ordered by their priority, lower comming first
   *   {"jenkins","weinhold","b13-test1"} 
   *     - jenkins has lowest priority, is the less specific one
   *     - ibib b13-test1 is the specificest environment id, hast the hightes priority
   */
  String[] getEnvironmentIds();
}
