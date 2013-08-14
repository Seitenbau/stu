package com.seitenbau.testing.hochschule;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HochschuleService
{
  @Autowired
  ProfessorRepository professorRepo;

  @Autowired
  LehrveranstaltungRepository lehrveranstaltungRepo;

  @Autowired
  PruefungRepository pruefungRepo;

  @Autowired
  StudentRepository studentRepo;

  @Autowired
  SchreibtRepository schreibtRepo;

  @Transactional
  public List<Professor> findProfessoren()
  {
    return professorRepo.findAll();
  }

  // @Transactional
  // public List<Professor> findPersons(Team team)
  // {
  // List<Professor> persons = new LinkedList<Person>();
  // for (Person person : personRepo.findAll())
  // {
  // if (person.getTeam() == team.getId())
  // {
  // persons.add(person);
  // }
  // }
  // return persons;
  // }

  @Transactional
  public Professor addProfessor(Professor professor)
  {
    return professorRepo.saveAndFlush(professor);
  }

  @Transactional
  public void removeProfessor(Professor professor)
  {
    professorRepo.delete(professor);
  }

  @Transactional
  public List<Lehrveranstaltung> findLehrveranstaltungen()
  {
    return lehrveranstaltungRepo.findAll();
  }

  @Transactional
  public Lehrveranstaltung addLehrveranstaltung(Lehrveranstaltung lehrveranstaltung)
  {
    return lehrveranstaltungRepo.saveAndFlush(lehrveranstaltung);
  }

  @Transactional
  public void removeLehrveranstaltung(Lehrveranstaltung lehrveranstaltung)
  {
    lehrveranstaltungRepo.delete(lehrveranstaltung);
  }

  @Transactional
  public List<Pruefung> findPruefungen()
  {
    return pruefungRepo.findAll();
  }

  @Transactional
  public Pruefung addPruefung(Pruefung pruefung)
  {
    return pruefungRepo.saveAndFlush(pruefung);
  }

  @Transactional
  public void removePruefung(Pruefung pruefung)
  {
    pruefungRepo.delete(pruefung);
  }

  @Transactional
  public List<Student> findStudenten()
  {
    return studentRepo.findAll();
  }

  @Transactional
  public Student addStudent(Student student)
  {
    return studentRepo.saveAndFlush(student);
  }

  @Transactional
  public void removeStudent(Student student)
  {
    studentRepo.delete(student);
  }

  @Transactional
  public List<Lehrveranstaltung> findLehrveranstaltungen(Professor professor)
  {
    List<Lehrveranstaltung> result = new LinkedList<Lehrveranstaltung>();
    for (Lehrveranstaltung lehrveranstaltung : lehrveranstaltungRepo.findAll())
    {
      if (lehrveranstaltung.getProfessor() == professor.getId())
      {
        result.add(lehrveranstaltung);
      }
    }
    return result;
  }
}
