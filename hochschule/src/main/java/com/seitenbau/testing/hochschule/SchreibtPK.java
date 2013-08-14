package com.seitenbau.testing.hochschule;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class SchreibtPK implements Serializable
{

  private static final long serialVersionUID = -4238398829225596843L;

  private Student student;

  private Pruefung pruefung;

  @ManyToOne
  public Student getStudent()
  {
    return student;
  }

  public void setStudent(Student student)
  {
    this.student = student;
  }

  @ManyToOne
  public Pruefung getPruefung()
  {
    return pruefung;
  }

  public void setPruefung(Pruefung pruefung)
  {
    this.pruefung = pruefung;
  }

  public SchreibtPK(Student student, Pruefung pruefung)
  {
    this.student = student;
    this.pruefung = pruefung;
  }

  public SchreibtPK()
  {
  }

}