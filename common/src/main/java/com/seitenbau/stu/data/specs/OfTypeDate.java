package com.seitenbau.stu.data.specs;

import java.util.Date;
import java.util.List;

import com.seitenbau.stu.data.PropertySpecification;
import com.seitenbau.stu.data.detail.Representant;
import com.seitenbau.stu.data.detail.Representant.RepresentantType;
import com.seitenbau.stu.data.impl.RepresentativeList;

public class OfTypeDate extends AbstractPropertySpecification<Date> implements PropertySpecification<Date>
{
  private Boolean _required;

  public OfTypeDate required(boolean required)
  {
    _required = required;
    return this;
  }
  
  protected boolean isRequired()
  {
    if (_required != null)
    {
      return _required;
    }
    return false;
  }

  protected List<Representant<Date>> getRepresentatives(RepresentantType type)
  {
    RepresentativeList<Date> rep = new RepresentativeList<Date>(type);
    if (isRequired())
    {
      rep.inValid((Date)null);
    }
    else
    {
      rep.valid((Date)null);
    }
    rep.valid(new Date());
    return rep.getList();
  }
}
