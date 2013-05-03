package com.seitenbau.testing.dbunit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import com.seitenbau.testing.dbunit.services.CRUDService;
import com.seitenbau.testing.dbunit.services.intern.CRUDServiceImpl;

import dao.Professor;
import dao.Repo;

public class Application
{
  public static void main(String[] args) throws Exception
  {
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    Properties properties = new Properties();
    properties.put("user", "sampleuser");
    properties.put("password", "sampleuser");
    Connection connection = DriverManager.getConnection("jdbc:derby:memory:stuDB;create=true;characterEncoding=utf-8", properties);
    Repo.setConnection(connection);
    
    Professor mrSmith = new Professor();
    mrSmith.setFirstName("John");
    mrSmith.setName("Smith");
    mrSmith.setFaculty("Architecture");
    mrSmith.setTitle("Prof. Dr.");

    System.out.println("Before:" + mrSmith);

    CRUDService sampleService = new CRUDServiceImpl();
    mrSmith = sampleService.addProfessor(mrSmith);
    System.out.println("After insert:" + mrSmith);

    List<Professor> professors = sampleService.findProfessors();
    System.out.println("All profs:" + professors);

    mrSmith.setName("Doe");
    mrSmith = sampleService.updateProfessor(mrSmith);
    professors = sampleService.findProfessors();
    System.out.println("All profs after update:" + professors);

    sampleService.removeProfessor(mrSmith);
    professors = sampleService.findProfessors();
    System.out.println("All profs after remove:" + professors);

    connection.close();
  }
}
