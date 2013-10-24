package com.seitenbau.stu.data.specs;

import java.util.List;

import com.seitenbau.stu.data.PropertySpecification;
import com.seitenbau.stu.data.detail.Representant;
import com.seitenbau.stu.data.detail.Representant.RepresentantType;
import com.seitenbau.stu.data.impl.RepresentativeList;

public class OfTypeInteger extends AbstractPropertySpecification<Integer> implements
    PropertySpecification<Integer>
{
  protected Integer _maxDigits;

  protected Integer _minValue;

  protected Integer _maxValue;

  protected Integer _minDigits;

  protected Boolean _allowNegative;

  protected Boolean _required;

  public OfTypeInteger required(boolean required)
  {
    _required = required;
    return this;
  }

  public OfTypeInteger notNegative()
  {
    _allowNegative = false;
    return this;
  }

  public OfTypeInteger digitLength(int min, int max)
  {
    minDigitLength(min);
    maxDigitLength(max);
    return this;
  }

  public OfTypeInteger minDigitLength(int min)
  {
    if (min < 1)
    {
      throw new IllegalArgumentException("min digits cannot be < 1");
    }
    _minDigits = min;
    return this;
  }

  public OfTypeInteger maxDigitLength(int maxdigits)
  {
    _maxDigits = maxdigits;
    return this;
  }

  public OfTypeInteger minValue(int minimal)
  {
    _minValue = minimal;
    return this;
  }

  public OfTypeInteger maxValue(int maximum)
  {
    _maxValue = maximum;
    return this;
  }

  protected List<Representant<Integer>> getRepresentatives(RepresentantType type)
  {
    RepresentativeList<Integer> rep = new RepresentativeList<Integer>(type);
    boolean required = safe(_required, false);
    boolean allowNegative = safe(_allowNegative, true);

    if (required)
    {
      rep.inValid((Integer) null);
    }
    else
    {
      rep.valid((Integer) null);
    }
    int lowerBound = Integer.MIN_VALUE + 1; // +1 coz Math.abs
    int upperBound = Integer.MAX_VALUE - 1;// -1 coz Math.abs
    boolean limitsViaDigits = false;
    if (_maxDigits != null)
    {
      limitsViaDigits = true;
      lowerBound = -1 * 10 * _maxDigits;
      upperBound = 10 * _maxDigits;
      StringBuffer valid = new StringBuffer();
      StringBuffer invalid = new StringBuffer();
      for (int i = 0; i < _maxDigits; i++)
      {
        valid.append("9");
        invalid.append("0");
      }
      rep.valid(Integer.valueOf(valid.toString()));
      invalid.insert(0, "1");
      rep.inValid(Integer.valueOf(invalid.toString()));
      if (allowNegative)
      {
        rep.valid(Integer.valueOf("-" + valid.toString()));
        rep.inValid(Integer.valueOf("-" + invalid.toString()));
      }
    }
    if (_minDigits != null)
    {
      limitsViaDigits = true;
      StringBuffer valid = new StringBuffer();
      StringBuffer invalid = new StringBuffer();

      for (int i = 0; i < _minDigits; i++)
      {
        valid.append("0");
        invalid.append("9");
      }
      valid.delete(0, 1);
      valid.insert(0, "1");
      rep.valid(Integer.valueOf(valid.toString()));
      if (allowNegative)
      {
        rep.valid(Integer.valueOf("-" + valid.toString()));
      }
      if (_minDigits == 0 && required)
      {
        throw new IllegalArgumentException("min digits = 0 and requried");
      }
      if (_minDigits == 1)
      {
        rep.valid(1);
        rep.add(allowNegative, -1);
      }
      else
      {
        invalid.delete(0, 1);
        rep.inValid(Integer.valueOf(invalid.toString()));
        if (allowNegative)
        {
          rep.inValid(Integer.valueOf("-" + invalid.toString()));
        }
      }
    }
    if (!limitsViaDigits)
    {
      if (_minValue != null && !allowNegative)
      {
        throw new IllegalArgumentException("min value set, allow negativ cannot be used");
      }
      if (_minValue == null)
      {
        if (allowNegative)
        {
          rep.valid(Integer.MIN_VALUE);
        }
        else
        {
          rep.valid(0);
        }
      }
      else
      {
        lowerBound = _minValue;
        rep.valid(_minValue);
        rep.inValid(_minValue - 1);
      }
      if (_maxValue == null)
      {
        rep.valid(Integer.MAX_VALUE);
      }
      else
      {
        upperBound = _maxValue;
        rep.valid(_maxValue);
        rep.inValid(_maxValue + 1);
      }
    }
    long range = (long) Math.abs(upperBound) + (long) Math.abs(lowerBound);
    rep.valid(lowerBound + 1 + (int) (range / 2L)); // +1 see
    // lowerBound decl
    return rep.getList();
  }

}
