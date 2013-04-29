package com.seitenbau.testing.dsl.requirement;


public abstract class AbstractDetector implements FeatureDetector
{
  protected String _parameters;

  protected String getParameters()
  {
    return _parameters;
  }

  public void initWithParameter(String parameters)
  {
    _parameters = parameters;
  }

  public String getFeatureId()
  {
    return this.getClass().getName();
  }

}
