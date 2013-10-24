package com.seitenbau.stu.dsl.requirement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.seitenbau.stu.dsl.requirement.RequirementDsl.RequirementState;

public abstract class AbstractKeyValueDetector implements FeatureDetector
{
  protected Pattern pattern = Pattern.compile("^([^=]+)(=(.*?))?$");

  protected boolean _not;

  protected String _key;

  protected String _value;

  public void initWithParameter(String parameters)
  {
    if(parameters == null ) 
    {
      initParameter(null, null);
      return;
    }
    Matcher matcher = pattern.matcher(parameters);
    if (!matcher.matches())
    {
      initParameter(null, null);
      return;
    }
    String key = matcher.group(1);
    String value = matcher.group(3);
    initParameter(key, value);
  }

  protected void initParameter(String key, String value)
  {
    _not = false;
    _key = key;
    _value = value;

    if (key != null && key.startsWith("!"))
    {
      _key = _key.substring(1);
      _not = true;
    }
    if (value != null && value.startsWith("!"))
    {
      _value = value.substring(1);
      _not = true;
    }
  }

  public String getFeatureId()
  {
    return this.getClass().getName();
  }

  public RequirementState getState()
  {
    boolean ok = readAndParseState();
    return getState(ok);
  }

  protected RequirementState getState(boolean ok)
  {
    if (ok)
    {
      return RequirementState.IMPLEMENTED;
    }
    return RequirementState.NOT_IMPLEMENTED;
  }

  protected boolean readAndParseState()
  {
    boolean active = readState(_key, _value);
    boolean ok = isActive(_not, active);
    return ok;
  }

  abstract protected boolean readState(String key, String value);

  protected boolean isActive(boolean not, boolean value)
  {
    if (not)
    {
      return !value;
    }
    else
    {
      return value;
    }
  }

}
