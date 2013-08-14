package com.seitenbau.testing.hochschule;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "pruefung")
public class Pruefung
{
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "pruefung_seq")
  @SequenceGenerator(name="pruefung_seq", sequenceName = "pruefung_seq")
  @Column(name="id")
  private Long id;

  @Column(name = "lehrveranstaltung_id")
  private Long lehrveranstaltung;

  @Column
  private String typ;

  @Column
  private Date zeitpunkt;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getTyp()
  {
    return typ;
  }

  public void setTyp(String typ)
  {
    this.typ = typ;
  }

  public Long getLehrveranstaltung()
  {
    return lehrveranstaltung;
  }

  public void setLehrveranstaltung(Long lehrveranstaltung)
  {
    this.lehrveranstaltung = lehrveranstaltung;
  }

}
