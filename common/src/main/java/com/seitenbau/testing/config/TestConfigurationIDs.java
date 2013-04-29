package com.seitenbau.testing.config;

/**
 * Some default ID's so the same keys can get used cross projects.
 */
public interface TestConfigurationIDs
{
  String SELENIUM_SERVER_HOST = "selenium.server.host";

  String SELENIUM_SERVER_PORT = "selenium.server.port";

  String SELENIUM_BROWSER_MODE = "selenium.browser.mode";

  String SELENIUM_BASE_URL = "selenium.base.url";

  String SELENIUM_PROFILE = "selenium.browser.profile";

  String SELENIUM_BINARY = "selenium.browser.binary";

  String SELENIUM_BROWSER_DOWNLOADDIR = "browser.download.dir";

  String PREFIX_SB_TESTING = "sb-testing.";

  String TestIssueRule_Enable = PREFIX_SB_TESTING + "TestIssueRule.enabled";

}
