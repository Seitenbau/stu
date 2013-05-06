package com.seitenbau.testing.dbunit.services;

import java.util.List;

import com.seitenbau.testing.dbunit.dao.Lecture;
import com.seitenbau.testing.dbunit.dao.Professor;

public interface CRUDService
{
  public boolean addProfessor(Professor professor);

  public List<Professor> findProfessors();

  public boolean updateProfessor(Professor professor);

  public int removeProfessor(Professor professor);

  public boolean addLecture(Lecture lecture);

  public List<Lecture> findLectures();

  public int removeLecture(Lecture lecture);

}
