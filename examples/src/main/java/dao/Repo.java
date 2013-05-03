package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

public class Repo
{
  private static List<Professor> professors = new LinkedList<Professor>();

  private static Connection connection;

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

  public static void setConnection(Connection connection) throws SQLException
  {
    Repo.connection = connection;
    setupDatabase();
  }

  private static void setupDatabase() throws SQLException
  {
    System.out.println("TODO setup database.");

    ResultSet resultSet = connection.getMetaData().getTables("%", "%", "%", new String[] {"TABLE"});
    boolean shouldCreateTable = true;
    while (resultSet.next() && shouldCreateTable)
    {
      if (resultSet.getString("TABLE_NAME").equalsIgnoreCase("PROFESSOR"))
      {
        shouldCreateTable = false;
      }
    }
    resultSet.close();
    if (shouldCreateTable)
    {
      System.out.println("Creating Table professor...");
      Statement statement = connection.createStatement();
      statement.execute("CREATE TABLE professor (" + //
          "id INT NOT NULL PRIMARY KEY" + //
          ", name VARCHAR(80) NOT NULL" + //
          ", first_name VARCHAR(80) NOT NULL" + //
          ", title VARCHAR(80) NOT NULL" + //
          ", faculty VARCHAR(80) NOT NULL" + //
          ")");
      System.out.println("finshed.");
      
      System.out.println("Creating Table lecture...");
      statement = connection.createStatement();
      statement.execute("CREATE TABLE lecture (" + //
          "id INT NOT NULL PRIMARY KEY" + //
          ", professor_id INT NOT NULL" + //
          ", name VARCHAR(80) NOT NULL" + //
          ", sws INT NOT NULL" + //
          ", ects INT NOT NULL" + //
          ")");
      System.out.println("finshed.");

      statement.close();
    }
  }
}
