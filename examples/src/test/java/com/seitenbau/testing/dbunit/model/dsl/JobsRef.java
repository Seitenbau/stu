package com.seitenbau.testing.dbunit.model.dsl;


import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;

public class JobsRef extends DatabaseReference {

  // depending on relation type with or with ellipse (...)
  List<PersonsRef> isJobOfList = new LinkedList<PersonsRef>();

  void isJobOf(PersonsRef ... refs) {
    isJobOfList.addAll(Arrays.asList(refs));
  }

}

