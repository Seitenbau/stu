package com.seitenbau.testing.dbunit.dao;

public class Professor
{
  private int id;

  private String name;

  private String firstName;

  private String title;

  private String faculty;

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getFaculty()
  {
    return faculty;
  }

  public void setFaculty(String faculty)
  {
    this.faculty = faculty;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  @Override
  public String toString()
  {
    return title + " " + firstName + " " + name + " of faculty " + faculty + " has id " + id;
  }
}
