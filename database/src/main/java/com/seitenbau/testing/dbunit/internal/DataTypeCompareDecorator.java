package com.seitenbau.testing.dbunit.internal;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;

import com.seitenbau.testing.dbunit.modifier.IDataSetModifier;
import com.seitenbau.testing.dbunit.modifier.IDataSetOverwriteCompare;

/**
 * Decorated DataType, which overwrites the Compare Method to handle
 * the {@link IDataSetModifier} hierarchy.
 */
public class DataTypeCompareDecorator extends DataTypeDecorator
{

  public DataTypeCompareDecorator(DataType delegateTo)
  {
    super(delegateTo);
  }

  @Override
  public int compare(Object o1, Object o2) throws TypeCastException
  {
    IDataSetOverwriteCompare dataSetCompare = null;
    if (o2 instanceof IDataSetOverwriteCompare)
    {
      dataSetCompare = (IDataSetOverwriteCompare) o2;
    }
    else if (o1 instanceof IDataSetOverwriteCompare)
    {
      dataSetCompare = (IDataSetOverwriteCompare) o1;
    }
    else
    {
      DataType delegate = getDelegate();
      return delegate.compare(o1, o2);
    }
    return dataSetCompare.compareDataSetElementTo(o2);
  }

}
