package com.seitenbau.testing.dbunit.services;

import java.util.List;

import dao.Professor;

public interface CRUDService
{
  public Professor addProfessor(Professor professor);

  public List<Professor> findProfessors();

  public Professor updateProfessor(Professor professor);

  public void removeProfessor(Professor professor);
}
