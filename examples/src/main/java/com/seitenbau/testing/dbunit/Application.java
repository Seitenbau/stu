package com.seitenbau.testing.dbunit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import com.seitenbau.testing.dbunit.dao.Professor;
import com.seitenbau.testing.dbunit.services.CRUDService;
import com.seitenbau.testing.dbunit.services.intern.CRUDServiceImpl;


public class Application
{
  public static void main(String[] args) throws Exception
  {
    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    Properties properties = new Properties();
    properties.put("user", "sampleuser");
    properties.put("password", "sampleuser");
    Connection connection = DriverManager.getConnection("jdbc:derby:memory:stuDB;create=true;characterEncoding=utf-8", properties);

    Professor mrSmith = new Professor();
    mrSmith.setFirstName("John");
    mrSmith.setName("Smith");
    mrSmith.setFaculty("Architecture");
    mrSmith.setTitle("Prof. Dr.");

    System.out.println("Before:" + mrSmith);

    CRUDService sampleService = new CRUDServiceImpl();
    boolean success = sampleService.addProfessor(mrSmith);
    System.out.println("After insert success state:" + success);

    List<Professor> professors = sampleService.findProfessors();
    System.out.println("All profs:" + professors);

    mrSmith.setName("Doe");
    success = sampleService.updateProfessor(mrSmith);
    professors = sampleService.findProfessors();
    System.out.println("After update success state:" + success);

    int rowsAffected = sampleService.removeProfessor(mrSmith);
    System.out.println("After remove rows affected:" + rowsAffected);
    professors = sampleService.findProfessors();
    System.out.println("All profs after remove:" + professors);

    connection.close();
  }
}
