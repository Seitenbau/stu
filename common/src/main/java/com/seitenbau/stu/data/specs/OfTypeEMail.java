package com.seitenbau.stu.data.specs;

import java.util.List;

import com.seitenbau.stu.data.detail.Representant;
import com.seitenbau.stu.data.detail.Representant.RepresentantType;
import com.seitenbau.stu.data.impl.RepresentativeList;
import com.seitenbau.stu.util.StringGenerator;

public class OfTypeEMail extends AbstractRangeDescription<String>
{
  
  private boolean _nullIsAValue = true;
  
  private boolean _useSubdomain;
  
  public static final String _defaultSubdomain = "example.com";
  
  private String _subdomain = null;

  public OfTypeEMail setNullIsAValue(boolean aNullIsAValue)
  {
    this._nullIsAValue = aNullIsAValue;
    return this;
  }
  
  public OfTypeEMail setSubdomain(String aSubdomain)
  {
    this._subdomain = aSubdomain;
    this._useSubdomain = true;
    return this;
  }
  
  public OfTypeEMail useSubdomain(boolean useSubdomain)
  {
    this._useSubdomain = useSubdomain;
    return this;
  }
  
  protected List<Representant<String>> getRepresentatives(RepresentantType type)
  {
    boolean nullIsAValue = safe(_nullIsAValue, true);
    boolean useSubdomain = safe(_useSubdomain, false);
    String subDomain = (this._subdomain == null) ? OfTypeEMail._defaultSubdomain : this._subdomain;
    RepresentativeList<String> rep = new RepresentativeList<String>(type);
    String fill = "fill";
    if (nullIsAValue)
    {
      rep.add(!isRequired(), (String[]) null);
    }
    if (useSubdomain)
    {
      rep.valid("x@" + subDomain);
      rep.inValid("@" + subDomain);
      rep.inValid("x@");
      if (getMaxLength() != null)
      {
        String max = StringGenerator.gen(fill, getMaxLength() - (subDomain.length() + 1));
        rep.valid(max + "@" + subDomain);
        rep.inValid(max + "@x" + subDomain);
        max = StringGenerator.gen(fill, getMaxLength() - (subDomain.length() + 2));
        rep.valid("x@" + max + subDomain);
        rep.inValid("xx@" + max + subDomain);
      }
    }
    else
    {
      rep.valid("x@x");
      rep.inValid("@x");
      rep.inValid("x@");
      if (getMaxLength() != null)
      {
        String max = StringGenerator.gen(fill, getMaxLength() - 2);
        rep.valid(max + "@x");
        rep.inValid(max + "@xx");
        rep.valid("x@" + max);
        rep.inValid("xx@" + max);
      }
    }
    
    
    return rep.getList();
  }

  @Override
  public AbstractRangeDescription<String> minLength(int length)
  {
    throw new UnsupportedOperationException("min Length not implemented");
  }
}
