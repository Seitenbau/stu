package com.seitenbau.testing.dbunit.model.dsl;


import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;

public class TeamsRef extends DatabaseReference {

  // depending on relation type with or with ellipse (...)
  List<PersonsRef> hasMemberList = new LinkedList<PersonsRef>();

  void hasMember(PersonsRef ... refs) {
    hasMemberList.addAll(Arrays.asList(refs));
  }

}

