package com.seitenbau.stu.hochschule;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "schreibt")
@AssociationOverrides({
    @AssociationOverride(name = "pk.student", joinColumns = @JoinColumn(name = "student_id")),
    @AssociationOverride(name = "pk.pruefung", joinColumns = @JoinColumn(name = "pruefung_id"))
  })
public class Schreibt
{

  private SchreibtPK pk = new SchreibtPK();

  @EmbeddedId
  public SchreibtPK getPk()
  {
    return pk;
  }

  public void setPk(SchreibtPK pk)
  {
    this.pk = pk;
  }

  @Transient
  public Student getStudent(){
   return this.pk.getStudent();
  }

  @Transient
  public Pruefung getPruefung(){
   return this.pk.getPruefung();
  }

  public void setStudent(Student student){
      this.pk.setStudent(student);
  }

  public void setPruefung(Pruefung pruefung){
      this.pk.setPruefung(pruefung);
  }

  private Integer versuch;

  @Column
  public Integer getVersuch()
  {
    return versuch;
  }

  public void setVersuch(Integer versuch)
  {
    this.versuch = versuch;
  }

}
