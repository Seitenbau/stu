package com.seitenbau.testing.dbunit.services.intern;

import java.util.List;

import com.seitenbau.testing.dbunit.services.CRUDService;

import dao.Professor;
import dao.Repo;

public class CRUDServiceImpl implements CRUDService
{
  @Override
  public Professor addProfessor(Professor professor)
  {
    return Repo.add(professor);
  }

  @Override
  public List<Professor> findProfessors()
  {
    return Repo.getAll();
  }

  @Override
  public Professor updateProfessor(Professor professor)
  {
    return Repo.update(professor);

  }

  @Override
  public void removeProfessor(Professor professor)
  {
    Repo.remove(professor);
  }
}
