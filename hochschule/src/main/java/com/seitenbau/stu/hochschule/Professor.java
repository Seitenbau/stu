package com.seitenbau.stu.hochschule;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "professor")
public class Professor
{
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "professor_seq")
  @SequenceGenerator(name="professor_seq", sequenceName = "professor_seq")
  @Column(name="id")
  private Long id;

  @Column
  private String vorname;

  @Column
  private String name;

  @Column
  private String titel;

  @Column
  private String fakultaet;

  @OneToMany
  @JoinTable (
    name="beaufsichtigt",
        joinColumns={@JoinColumn(name="professor_id", referencedColumnName="id")},
        inverseJoinColumns={@JoinColumn(name="pruefung_id", referencedColumnName="id")}
  )
  private List<Pruefung> beaufsichtigt;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getVorname()
  {
    return vorname;
  }

  public void setVorname(String vorname)
  {
    this.vorname = vorname;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getTitel()
  {
    return titel;
  }

  public void setTitel(String titel)
  {
    this.titel = titel;
  }

  public String getFakultaet()
  {
    return fakultaet;
  }

  public void setFakultaet(String fakultaet)
  {
    this.fakultaet = fakultaet;
  }

  public List<Pruefung> getBeaufsichtigt()
  {
    return beaufsichtigt;
  }

  public void setBeaufsichtigt(List<Pruefung> beaufsichtigt)
  {
    this.beaufsichtigt = beaufsichtigt;
  }

  public void addBeaufsichtigt(Pruefung pruefung)
  {
    if (beaufsichtigt == null) {
      beaufsichtigt = new ArrayList<Pruefung>();
    }
    beaufsichtigt.add(pruefung);
  }

}
