package com.seitenbau.stu.data.specs;

import java.util.List;

import com.seitenbau.stu.data.detail.Representant;
import com.seitenbau.stu.data.detail.Representant.RepresentantType;
import com.seitenbau.stu.data.impl.RepresentativeList;
import com.seitenbau.stu.util.StringGenerator;

public class OfTypeString extends AbstractRangeDescription<String>
{
  private boolean _nullIsAValue = true;

  protected static final String NULL_STRING = null;

  protected String _emptyString = "";

  public static final String DEFAULT_FILL_STRING = "Just a string, nothing special";

  protected String[] _emptyStringWhitespaces = {" ", "\t"};

  private Boolean _WsIsAemptyString;

  private Boolean _emptyStringCheck;

  private Boolean _trim = false;

  /**
   * If {@code true}, {@code null} is a possible return value
   * (default).
   *
   * If {@code false}, {@code null} is not returned in any of the
   * lists.
   */
  public OfTypeString setNullIsAValue(boolean aNullIsAValue)
  {
    this._nullIsAValue = aNullIsAValue;
    return this;
  }

  public OfTypeString setWhitspaceStrings(String... whitespaces)
  {
    _emptyStringWhitespaces = whitespaces;
    return this;
  }

  public OfTypeString setEmptyString(String emptyString)
  {
    _emptyString = emptyString;
    return this;
  }

  public OfTypeString assumeWhitspacesEqualsAnEmptyString()
  {
    _WsIsAemptyString = true;
    return this;
  }

  public OfTypeString assumeWhitspacesEqalsAnEmptyString(boolean trim)
  {
    _WsIsAemptyString = trim;
    return this;
  }

  @Override
  protected List<Representant<String>> getRepresentatives(RepresentantType type)
  {
    boolean nullIsAValue = safe(_nullIsAValue, true);
    RepresentativeList<String> rep = new RepresentativeList<String>(type);
    String fill = DEFAULT_FILL_STRING;
    @SuppressWarnings("unused")
    boolean wsEqualsEmpty = safe(_WsIsAemptyString, false);
    boolean emptyStringCheck = safe(_emptyStringCheck, true);
    boolean trim = safe(_trim, false);

    // Minimum String length
    Integer min = getMinLength();
    if (min != null && min > 0)
    {
      rep.inValid(StringGenerator.gen(fill, min - 1, trim));
      rep.valid(StringGenerator.gen(fill, min, trim));
    }
    if (isRequired())
    {
      if (nullIsAValue)
      {
        rep.inValid(NULL_STRING);
      }
      if (emptyStringCheck)
      {
        rep.inValid(_emptyString);
      }
      rep.inValid(_emptyStringWhitespaces);
    }
    else
    {
      if (nullIsAValue)
      {
        rep.valid(NULL_STRING);
      }
      if (emptyStringCheck)
      {
        rep.valid(_emptyString);
      }
      rep.valid(_emptyStringWhitespaces);
    }

    // Maximum Limits
    Integer max = getMaxLength();
    if (max != null && max > 0)
    {
      rep.valid(StringGenerator.gen(fill, max, trim));
      rep.inValid(StringGenerator.gen(fill, max + 1, trim));
    }
    else
    {
      rep.valid(StringGenerator.gen(fill, 8077, trim));
    }

    // In the middle
    if (max != null && min != null && min > 0 && min < max)
    {
      int middle = max - min;
      rep.valid(StringGenerator.gen(fill, middle, trim));
    }
    return rep.getList();
  }

  public void noEmptyStringCheck()
  {
    _emptyStringCheck = false;
  }

  public OfTypeString trim(boolean trim)
  {
    this._trim = trim;
    return this;
  }

}
