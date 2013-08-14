package com.seitenbau.testing.hochschule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "lehrveranstaltung")
public class Lehrveranstaltung
{
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "lehrveranstaltung_seq")
  @SequenceGenerator(name="lehrveranstaltung_seq", sequenceName = "lehrveranstaltung_seq")
  @Column(name="id")
  private Long id;

  @Column
  private String name;

  @Column(name = "professor_id")
  private Long professor;

  @Column
  private Integer sws;

  @Column
  private Double ects;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Long getProfessor()
  {
    return professor;
  }

  public void setProfessor(Long professor)
  {
    this.professor = professor;
  }

  public Integer getSws()
  {
    return sws;
  }

  public void setSws(Integer sws)
  {
    this.sws = sws;
  }

  public Double getEcts()
  {
    return ects;
  }

  public void setEcts(Double ects)
  {
    this.ects = ects;
  }

}
