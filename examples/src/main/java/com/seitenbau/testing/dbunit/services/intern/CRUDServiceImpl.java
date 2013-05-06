package com.seitenbau.testing.dbunit.services.intern;

import java.util.List;

import org.springframework.stereotype.Service;

import com.seitenbau.testing.dbunit.dao.Professor;
import com.seitenbau.testing.dbunit.dao.Repo;
import com.seitenbau.testing.dbunit.services.CRUDService;


@Service
public class CRUDServiceImpl implements CRUDService
{
  @Override
  public boolean addProfessor(Professor professor)
  {
    return Repo.add(professor);
  }

  @Override
  public List<Professor> findProfessors()
  {
    return Repo.getAll();
  }

  @Override
  public boolean updateProfessor(Professor professor)
  {
    return Repo.update(professor);

  }

  @Override
  public int removeProfessor(Professor professor)
  {
    return Repo.remove(professor);
  }
}
