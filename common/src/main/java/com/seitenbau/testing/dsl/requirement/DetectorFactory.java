package com.seitenbau.testing.dsl.requirement;

public class DetectorFactory
{
  public static FeatureDetector create(String customDsl)
  {
    if (customDsl == null || customDsl.length() == 0)
    {
      throw new IllegalArgumentException("dsl not set");
    }
    String[] data = customDsl.split(":");
    if (data.length == 0 || data.length > 2)
    {
      throw new IllegalArgumentException("dsl not correct");
    }
    String clazz = data[0];
    FeatureDetector instance = createDetectorInstance(clazz);
    if (data.length == 2)
    {
      String parameters = data[1];
      instance.initWithParameter(parameters);
    }
    return instance;
  }

  protected static FeatureDetector createDetectorInstance(String clazz)
  {
    FeatureDetector instance = newInstance(clazz);
    if (instance == null)
    {
      throw new RuntimeException("Unable to create instance");
    }
    return instance;
  }

  protected static FeatureDetector newInstance(String clazz)
  {
    try
    {
      FeatureDetector instance = (FeatureDetector) Class.forName(
          clazz).newInstance();
      return instance;
    }
    catch (Exception e)
    {
      return null;
    }
  }
}
