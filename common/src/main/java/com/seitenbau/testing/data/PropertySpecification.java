package com.seitenbau.testing.data;

import java.util.List;

import com.seitenbau.testing.data.detail.Representant;

public interface PropertySpecification<T>
{
  List<Representant<T>> getValidRepresentations();

  List<Representant<T>> getInValidRepresentations();
}
