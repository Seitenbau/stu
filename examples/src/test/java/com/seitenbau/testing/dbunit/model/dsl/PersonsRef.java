package com.seitenbau.testing.dbunit.model.dsl;


import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import com.seitenbau.testing.dbunit.dsl.DatabaseReference;

public class PersonsRef extends DatabaseReference {

  List<JobsRef> worksAsList = new LinkedList<JobsRef>();

  // depending on relation type with or without ellipse (...)
  void worksAs(JobsRef ... refs) {
    worksAsList.addAll(Arrays.asList(refs));
  }

  List<TeamsRef> worksInList = new LinkedList<TeamsRef>();

  // depending on relation type with or without ellipse (...)
  void worksIn(TeamsRef ... refs) {
    worksInList.addAll(Arrays.asList(refs));
  }

}

