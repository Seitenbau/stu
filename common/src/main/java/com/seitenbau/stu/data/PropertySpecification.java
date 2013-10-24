package com.seitenbau.stu.data;

import java.util.List;

import com.seitenbau.stu.data.detail.Representant;

public interface PropertySpecification<T>
{
  List<Representant<T>> getValidRepresentations();

  List<Representant<T>> getInValidRepresentations();
}
