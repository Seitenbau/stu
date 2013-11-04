package com.seitenbau.stu.hochschule;

import java.util.ArrayList;
import java.util.Date;
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
@Table(name = "student")
public class Student
{
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "student_seq")
  @SequenceGenerator(name="student_seq", sequenceName = "student_seq")
  @Column(name="matrikelnummer")
  private Long matrikelnummer;

  @Column
  private String vorname;

  @Column
  private String name;

  @Column
  private String studiengang;

  @Column
  private Integer semester;

  @Column(name="immatrikuliert_seit")
  private Date immatrikuliertSeit;

  @OneToMany
  @JoinTable (
    name="besucht",
        joinColumns={@JoinColumn(name="student_id", referencedColumnName="matrikelnummer")},
        inverseJoinColumns={@JoinColumn(name="lehrveranstaltung_id", referencedColumnName="id")}
  )
  private List<Lehrveranstaltung> besucht;

  @OneToMany
  @JoinTable (
    name="isttutor",
        joinColumns={@JoinColumn(name="student_id", referencedColumnName="matrikelnummer")},
        inverseJoinColumns={@JoinColumn(name="lehrveranstaltung_id", referencedColumnName="id")}
  )
  private List<Lehrveranstaltung> isttutor;

  @OneToMany
  @JoinTable (
    name="schreibt",
        joinColumns={@JoinColumn(name="student_id", referencedColumnName="matrikelnummer")},
        inverseJoinColumns={@JoinColumn(name="pruefung_id", referencedColumnName="id")}
  )
  private List<Pruefung> schreibt;

  public Long getMatrikelnummer()
  {
    return matrikelnummer;
  }

  public void setMatrikelnummer(Long matrikelnummer)
  {
    this.matrikelnummer = matrikelnummer;
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

  public String getStudiengang()
  {
    return studiengang;
  }

  public void setStudiengang(String studiengang)
  {
    this.studiengang = studiengang;
  }

  public Integer getSemester()
  {
    return semester;
  }

  public void setSemester(Integer semester)
  {
    this.semester = semester;
  }

  public Date getImmatrikuliertSeit()
  {
    return immatrikuliertSeit;
  }

  public void setImmatrikuliertSeit(Date immatrikuliertSeit)
  {
    this.immatrikuliertSeit = immatrikuliertSeit;
  }

  public List<Lehrveranstaltung> getBesucht()
  {
    return besucht;
  }

  public void setBesucht(List<Lehrveranstaltung> besucht)
  {
    this.besucht = besucht;
  }

  public void addBesucht(Lehrveranstaltung lehrveranstaltung)
  {
    if (besucht == null) {
      besucht = new ArrayList<Lehrveranstaltung>();
    }
    besucht.add(lehrveranstaltung);
  }

  public List<Lehrveranstaltung> getIsttutor()
  {
    return isttutor;
  }

  public void setIsttutor(List<Lehrveranstaltung> isttutor)
  {
    this.isttutor = isttutor;
  }

  public void addIsttutor(Lehrveranstaltung lehrveranstaltung)
  {
    if (isttutor == null) {
      isttutor = new ArrayList<Lehrveranstaltung>();
    }
    isttutor.add(lehrveranstaltung);
  }

  public List<Pruefung> getSchreibt()
  {
    return schreibt;
  }

  public void setSchreibt(List<Pruefung> schreibt)
  {
    this.schreibt = schreibt;
  }

  public void addSchreibt(Pruefung pruefung)
  {
    if (schreibt == null) {
      schreibt = new ArrayList<Pruefung>();
    }
    schreibt.add(pruefung);
  }

}
