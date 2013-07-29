package com.seitenbau.testing.data;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.seitenbau.testing.data.detail.Representant;
import com.seitenbau.testing.data.detail.TestDataDetail;
import com.seitenbau.testing.data.detail.impl.TestPropertyValue;
import com.seitenbau.testing.data.impl.NameAndSpecGlue;

/**
 * Here a Sample Code:
 *
 * <pre>
 * &#064;RunWith(Parameterized.class)
 * public class SampleBasarTest
 * {
 *   static Builder&lt;Block&gt; builder = Builder.build(new Specs(Basar.class)
 *   {
 *     {
 *       property(&quot;number&quot;, new OfTypeInteger()
 *       {
 *         {
 *           notNegative();
 *           required(true);
 *         }
 *       });
 *       property(&quot;desc&quot;, new OfTypeString()
 *       {
 *         {
 *           required(true);
 *           minLength(10);
 *           maxLength(20);
 *         }
 *       });
 *     }
 *   });
 *
 *   Basar _basar;
 *
 *   public SampleBasarTest(Basar bazar)
 *   {
 *     _basar = bazar;
 *   }
 *
 *   &#064;Parameters
 *   public static Collection&lt;Object[]&gt; getTestData()
 *   {
 *     return builder.getFailureCases();
 *   }
 *
 *   &#064;Test
 *   public void bazarSaveTest() throws Exception
 *   {
 *     System.out.println(_basar);
 *   }
 *
 *   static public class Basar
 *   {
 *     Integer number;
 *
 *     String price;
 *
 *     String desc;
 *   }
 * }
 * </pre>
 */
public class Builder<T>
{

  private final Specs _specs;

  public Builder(Specs specification)
  {
    _specs = specification;
  }

  public static <T> Builder<T> build(Specs specification)
  {
    return new Builder<T>(specification);
  }

  @SuppressWarnings("unchecked")
  public <X> X[] getSuccessTwip()
  {
    List<List<TestPropertyValue>> dataSets = createSuccessCaseList();
    X[] result = (X[]) makeObjectArray(_specs, dataSets);
    return result;
  }

  protected Object[] makeObjectArray(Specs specs, List<List<TestPropertyValue>> dataSets)
  {
    Object[] result = newArray(specs.getTargetType(), dataSets.size());
    int i = 0;
    for (List<TestPropertyValue> rep : dataSets)
    {
      Object obj = makeObject(specs, rep);
      result[i++] = obj;
    }
    return result;
  }

  private Object[] newArray(Class<?> ofType, int size)
  {
    return (Object[]) Array.newInstance(ofType, size);
  }

  @SuppressWarnings("unchecked")
  public <X> X[] getFailureTwip()
  {
    List<List<TestPropertyValue>> dataSets = createFailureCaseList();
    X[] result = (X[]) makeObjectArray(_specs, dataSets);
    return result;
  }

  public Collection<Object[]> getSuccessCases()
  {
    return getSuccessCases(false);
  }

  public Collection<Object[]> getSuccessCases(boolean withDetails)
  {
    List<List<TestPropertyValue>> dataSets = createSuccessCaseList();
    Collection<Object[]> result = makeCollection(_specs, dataSets, withDetails);
    return result;
  }

  @SuppressWarnings("unchecked")
  protected List<List<TestPropertyValue>> createSuccessCaseList()
  {
    List<NameAndSpecGlue<?>> allPropertySpecifications = _specs.getAllPropertySpecifications();

    int max = 0;
    for (NameAndSpecGlue<?> propertyDescription : allPropertySpecifications)
    {
      List<?> rep = propertyDescription.getSpec().getValidRepresentations();
      if (rep != null)
      {
        int actualSize = rep.size();
        max = Math.max(max, actualSize);
      }
    }
    List<List<TestPropertyValue>> dataSets = new ArrayList<List<TestPropertyValue>>();
    for (int i = 0; i < max; i++)
    {
      List<TestPropertyValue> testData = new ArrayList<TestPropertyValue>();
      for (NameAndSpecGlue<?> propertyDescription : allPropertySpecifications)
      {
        List<?> successRepresentations = propertyDescription.getSpec().getValidRepresentations();
        if (successRepresentations != null && successRepresentations.size() > 0)
        {
          List<Representant<?>> casted = (List<Representant<?>>) successRepresentations;
          String name = propertyDescription.getName();
          Enum<?> marker = propertyDescription.getMarker();
          Representant<?> rep = getRepresentant(casted, i);
          testData.add(new TestPropertyValue(name, marker, rep));
        }
      }
      dataSets.add(testData);
    }
    return dataSets;
  }

  public Collection<Object[]> getFailureCases()
  {
    return getFailureCases(false);
  }

  /**
   * The parameterized Tests will be passed an additional constructor
   * parameter of type TestDataDetail
   */
  public Collection<Object[]> getFailureCases(boolean withDetails)
  {
    List<List<TestPropertyValue>> dataSets = createFailureCaseList();
    Collection<Object[]> result = makeCollection(_specs, dataSets, withDetails);
    return result;
  }

  private List<List<TestPropertyValue>> createFailureCaseList()
  {
    List<NameAndSpecGlue<?>> invalidSpecs = _specs.getAllPropertySpecifications();
    List<NameAndSpecGlue<?>> validSpecs = _specs.getAllPropertySpecifications();

    List<List<TestPropertyValue>> dataSets = new ArrayList<List<TestPropertyValue>>();
    for (NameAndSpecGlue<?> invalidSpec : invalidSpecs)
    {
      List<?> invalid = invalidSpec.getSpec().getInValidRepresentations();
      if (invalid != null)
      {
        @SuppressWarnings("unchecked")
        List<Representant<?>> casted = (List<Representant<?>>) invalid;
        for (Representant<?> value : casted)
        {
          List<TestPropertyValue> invalidRepresentants = buildInvalidObject(validSpecs,
              invalidSpec, value);
          dataSets.add(invalidRepresentants);
        }
      }
    }
    return dataSets;
  }

  protected List<TestPropertyValue> buildInvalidObject(List<NameAndSpecGlue<?>> validSpecs,
      NameAndSpecGlue<?> invalidSpec, Representant<?> invalidRep)
  {
    String invalidSpecName = invalidSpec.getName();
    List<TestPropertyValue> testData = new ArrayList<TestPropertyValue>();
    for (NameAndSpecGlue<?> propertyDescription : validSpecs)
    {
      String name = propertyDescription.getName();
      if (propertyDescription.getName().equals(invalidSpecName))
      {
        testData.add(new TestPropertyValue(name, invalidSpec.getMarker(), invalidRep));
      }
      else
      {
        List<?> sucessRepresentations = propertyDescription.getSpec().getValidRepresentations();
        if (sucessRepresentations != null && sucessRepresentations.size() > 0)
        {
          @SuppressWarnings("unchecked")
          List<Representant<?>> casted = (List<Representant<?>>) sucessRepresentations;
          Representant<?> rep = getRepresentant(casted, 0);
          testData.add(new TestPropertyValue(name, invalidSpec.getMarker(), rep));
        }
      }
    }
    return testData;
  }

  protected Representant<?> getRepresentant(List<Representant<?>> successRepresentations, int index)
  {
    Representant<?> value = null;
    if (index >= successRepresentations.size())
    {
      value = successRepresentations.get(0);
    }
    else
    {
      value = successRepresentations.get(index);
    }
    return value;
  }

  protected Collection<Object[]> makeCollection(Specs specs,
      List<List<TestPropertyValue>> dataSets, boolean withDetails)
  {
    Collection<Object[]> result = new ArrayList<Object[]>();
    for (List<TestPropertyValue> val : dataSets)
    {
      Object obj = makeObject(specs, val);
      if (withDetails)
      {
        result.add(new Object[] {obj, new TestDataDetail(val)});
      }
      else
      {
        result.add(new Object[] {obj});
      }
    }
    return result;
  }

  protected Object makeObject(Specs specs, List<TestPropertyValue> val)
  {
    Object obj = newInstance(specs.getTargetType());
    for (TestPropertyValue rep : val)
    {
      String name = rep.getName();
      Object value = rep.getValue();
      Method method = findSetterOrAddMethod(specs, name, value);
      if (method == null)
      {
        trySetFieldDirectly(specs, obj, name, value);
      }
      else
      {
        setViaSetOrAddMethod(method, obj, value);
      }
    }
    return obj;
  }

  private void setViaSetOrAddMethod(Method method, Object obj, Object value)
  {
    method.setAccessible(true);
    try
    {
      method.invoke(obj, value);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  private void trySetFieldDirectly(Specs specs, Object obj, String name, Object value)
  {
    Field field = findField(specs.getTargetType(), name);
    if (field == null)
    {
      throw new RuntimeException("no add or set '" + name + "' Method found on "
          + specs.getTargetType().toString());
    }
    field.setAccessible(true);
    try
    {
      field.set(obj, value);
    }
    catch (Exception e1)
    {
      throw new RuntimeException(e1);
    }
  }

  private Method findSetterOrAddMethod(Specs specs, String name, Object value)
  {
    Method method = null;
    if (value != null)
    {
      method = findMethod(specs.getTargetType(), value.getClass(), "add", name);
    }
    if (method == null)
    {
      method = quickFindMethod(specs.getTargetType(), "set", name);
    }
    if (method == null)
    {
      method = quickFindMethod(specs.getTargetType(), "add", name);
    }
    return method;
  }

  private Field findField(Class<?> targetType, String name)
  {
    for (Field f : targetType.getDeclaredFields())
    {
      if (f.getName().equalsIgnoreCase(name))
      {
        return f;
      }
    }
    if (!targetType.getSuperclass().equals(Object.class))
    {
      return findField(targetType.getSuperclass(), name);
    }
    return null;
  }

  /**
   * find first matching method
   */
  private Method quickFindMethod(Class<?> targetType, String prefix, String name)
  {
    String mName = prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
    try
    {
      return findMethod(targetType, mName);
    }
    catch (Exception e)
    {
      return null;
    }
  }

  private Method findMethod(Class<?> target, String name)
  {
    for (Method method : target.getDeclaredMethods())
    {
      if (method.getName().equals(name))
      {
        if (method.getParameterTypes().length == 1)
        {
          return method;
        }
      }
    }
    if (!target.getSuperclass().equals(Object.class))
    {
      return findMethod(target.getSuperclass(), name);
    }
    return null;
  }

  private Method findMethod(Class<?> target, Class<?> paramType, String prefix, String name)
  {
    String methodName = prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
    try
    {
      return findMethod(target, paramType, methodName);
    }
    catch (Exception e)
    {
      return null;
    }
  }

  private Method findMethod(Class<?> target, Class<?> paramType, String methodName)
      throws SecurityException, NoSuchMethodException
  {
    for (Method method : target.getDeclaredMethods())
    {
      if (method.getName().equals(methodName))
      {
        if (method.getParameterTypes().length == 1)
        {
          Class<?> type = method.getParameterTypes()[0];
          try
          {
            paramType.asSubclass(type);
            return method;
          }
          catch (ClassCastException e)
          {
          }
        }
      }
    }
    if (!target.getSuperclass().equals(Object.class))
    {
      return findMethod(target.getSuperclass(), paramType, methodName);
    }
    return null;
  }

  protected Object newInstance(Class<?> target)
  {
    try
    {
      return target.newInstance();
    }
    catch (InstantiationException e)
    {
      throw new RuntimeException(e);
    }
    catch (IllegalAccessException e)
    {
      throw new RuntimeException(e);
    }
  }

}
