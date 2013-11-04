package com.seitenbau.stu.data.detail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.internal.AssumptionViolatedException;

import com.seitenbau.stu.data.detail.Representant.RepresentantType;
import com.seitenbau.stu.data.detail.impl.TestPropertyValue;

public class TestDataDetail
{

  private List<TestPropertyValue> _representantList;

  public List<TestPropertyValue> getRepresentantList()
  {
    return _representantList;
  }

  public boolean isProperty(String name)
  {
    return getRepresentantByName(name, null) != null;
  }

  public boolean propertyIsValidRepresentant(String name)
  {
    return getRepresentantByName(name, RepresentantType.VALID) != null;
  }

  public boolean propertyIsInvalidRepresentant(String name)
  {
    return getRepresentantByName(name, RepresentantType.INVALID) != null;
  }

  public TestPropertyValue getRepresentantByName(String name,
      RepresentantType filter)
  {
    List<TestPropertyValue> reps = getRepresentantList();
    if (reps == null)
    {
      return null;
    }
    for (TestPropertyValue rep : reps)
    {
      if (rep.getName().equals(name))
      {
        if (filter == null || filter.equals(rep.getType()))
        {
          return rep;
        }
      }
    }
    return null;
  }

  protected List<TestPropertyValue> getRepresentantList(RepresentantType filter)
  {
    List<TestPropertyValue> reps = getRepresentantList();
    List<TestPropertyValue> result = new ArrayList<TestPropertyValue>();
    if (reps == null)
    {
      return result;
    }
    for (TestPropertyValue rep : reps)
    {
      if (rep.getType().equals(filter))
      {
        result.add(rep);
      }
    }
    return result;
  }

  public List<TestPropertyValue> getValidRepresentantList()
  {
    return getRepresentantList(RepresentantType.VALID);
  }

  public List<TestPropertyValue> getInvalidRepresentantList()
  {
    return getRepresentantList(RepresentantType.INVALID);
  }

  public TestDataDetail(List<TestPropertyValue> val)
  {
    _representantList = val;
  }

  @Override
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" --- invalid Values : --- \r\n");
    append(sb, RepresentantType.INVALID, false, null);
    sb.append(" --- valid Values : --- \r\n");
    append(sb, RepresentantType.VALID, false, null);
    return sb.toString();
  }

  private void append(StringBuffer sb, RepresentantType filter, boolean newline, Integer linelength)
  {
    if (_representantList == null)
    {
      return;
    }
    for (TestPropertyValue rep : _representantList)
    {
      if (rep.getType().equals(filter))
      {
        sb.append("property=");
        sb.append(rep.getName());
        appendNLorTab(sb, newline);
        sb.append("value=");
        Object val = rep.getValue();
        if (val != null && linelength != null)
        {
          String txt = val.toString();
          if (val.toString().length() > linelength)
          {
            sb.append(txt.substring(0, linelength - 5));
            sb.append("...");
          }
          else
          {
            sb.append(val);
          }
        }
        else
        {
          sb.append(val);
        }
        if (val instanceof CharSequence)
        {
          appendNLorTab(sb, newline);
          sb.append("length=");
          sb.append(((CharSequence) val).length());
        }
        sb.append("\r\n");
      }
    }
  }

  private void appendNLorTab(StringBuffer sb, boolean newline)
  {
    if (newline)
    {
      sb.append("\r\n");
    }
    else
    {
      sb.append("\t");
    }
  }

  public String printInvalidList()
  {
    return print(RepresentantType.INVALID, true, null);
  }

  private String print(RepresentantType filter, boolean newline, Integer linelength)
  {
    StringBuffer sb = new StringBuffer();
    append(sb, filter, newline, linelength);
    return sb.toString();
  }

  public String printValidList()
  {
    return print(RepresentantType.VALID, true, null);
  }

  public String printShortValidList()
  {
    return print(RepresentantType.VALID, true, 35);
  }

  public String printShortInvalidList()
  {
    if (_representantList == null)
    {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (TestPropertyValue rep : _representantList)
    {
      if (rep.getType().equals(RepresentantType.INVALID))
      {
        sb.append(rep.getName());
        sb.append("=");
        String text = getShortedValue(rep);
        sb.append(text);
        if (rep.getValue() instanceof CharSequence)
        {
          sb.append(" with length=");
          sb.append(((CharSequence) rep.getValue()).length());
        }
      }
    }
    return sb.toString();
  }

  protected String getShortedValue(TestPropertyValue rep)
  {
    String text = "";
    if (rep.getValue() == null)
    {
      text = "null";
    }
    else
    {
      text = rep.getValue().toString();
      if (text.length() > 100)
      {
        text = text.subSequence(0, 90) + " ...";
      }
    }
    return text;
  }

  public TestPropertyValue getInvalidRepresentant()
  {
    List<TestPropertyValue> reps = getInvalidRepresentantList();
    if (reps.size() != 1)
    {
      throw new RuntimeException(
          "There shoudl never be more than one invalid value. "
              + reps.size());
    }
    return reps.get(0);
  }

  public void assumeIsNotForField(String name)
  {
    assumeIsNotInCase("Test was ignored because it cannot handle field '" + name
        + "'.", name);
  }

  public void assumeIsNotInCase(String errorMessage, String name)
  {
    boolean result = getInvalidRepresentant().getName().equals(name);
    if (result)
    {
      // TODO : replace by direct call
      TestStateRecorder_tryMarkIgnored();
      // TestStateRecorder.tryMarkIgnored();
      // TestStateRecorder.tryRecordParameter(TestStateRecorder.COLUMN_PARAMETER,
      // "Ignored, because test does not support invalid value in  '"
      // + name
      // + "'");
      throw new AssumptionViolatedException(errorMessage);
    }
  }

  private void TestStateRecorder_tryMarkIgnored()
  {
    try
    {
      Class<?> clazz = getClass().getClassLoader().loadClass(
          "com.seitenbau.stu.report.TestStateRecorder");
      Method method = clazz.getDeclaredMethod("tryMarkIgnored");
      method.invoke(null);

    }
    catch (Throwable t)
    {
      // supress
    }
  }

}
