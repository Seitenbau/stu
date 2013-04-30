package dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

public class Repo
{
  private static List<Professor> professors = new LinkedList<Professor>();

  public static Professor add(Professor professor)
  {
    professor.setId(RandomUtils.nextInt());
    professors.add(professor);
    return professor;
  }

  public static List<Professor> getAll()
  {
    return professors.subList(0, professors.size());
  }

  public static Professor update(Professor professor)
  {
    for (Professor currentProfessor : professors)
    {
      int id = professor.getId();
      if (currentProfessor.getId() == id)
      {
        currentProfessor.setName(professor.getName());
        currentProfessor.setFirstName(professor.getFirstName());
        currentProfessor.setFaculty(professor.getFaculty());
        currentProfessor.setTitle(professor.getTitle());
        return currentProfessor;
      }
    }
    return professor;
  }

  public static void remove(Professor professor)
  {
    if (professors.contains(professor))
    {
      professors.remove(professor);
    }
  }

}
