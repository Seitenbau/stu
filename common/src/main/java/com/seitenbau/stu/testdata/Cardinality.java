package com.seitenbau.stu.testdata;

public enum Cardinality
{
  
  OneToMany("1..*"),
  
  ZeroToMany("0..*"),
  
  OneToOne("1..1");
  
  public final String value;

  Cardinality(String value){
    this.value = value;}
  
}
