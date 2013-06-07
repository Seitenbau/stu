package com.seitenbau.testing.personmanager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "teams")
public class Team
{
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "teams_seq")
  @SequenceGenerator(name="teams_seq", sequenceName = "teams_seq")
  int id;

  @Column
  String title;

  @Column
  String description;

  @Column
  int membersize;

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }
  
  public void setId(Long id)
  {
    this.id = id.intValue();
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public int getMembersize()
  {
    return membersize;
  }

  public void setMembersize(int membersize)
  {
    this.membersize = membersize;
  }
  
  public void setMembersize(Long membersize)
  {
    setMembersize(membersize.intValue());
  }

}
