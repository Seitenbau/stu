package com.seitenbau.testing.dbunit.generator;

import com.seitenbau.testing.dbunit.model.Model;

public class Generator {
  
  public static void main(String[] args) throws Exception {
    new Model().generate();
  }
}
