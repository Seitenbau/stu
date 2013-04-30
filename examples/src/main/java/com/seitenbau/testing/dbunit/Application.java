package com.seitenbau.testing.dbunit;

import java.util.List;

import com.seitenbau.testing.dbunit.services.CRUDService;
import com.seitenbau.testing.dbunit.services.intern.CRUDServiceImpl;

import dao.Professor;

public class Application
{
  public static void main(String[] args)
  {
    Professor mrSmith = new Professor();
    mrSmith.setFirstName("John");
    mrSmith.setName("Smith");
    mrSmith.setFaculty("Architecture");
    mrSmith.setTitle("Prof. Dr.");

    System.out.println("Before:"+mrSmith);
    
    CRUDService sampleService = new CRUDServiceImpl();
    mrSmith = sampleService.addProfessor(mrSmith);
    System.out.println("After insert:"+mrSmith);
    
    List<Professor> professors = sampleService.findProfessors();
    System.out.println("All profs:"+professors);
    
    mrSmith.setName("Doe");
    mrSmith = sampleService.updateProfessor(mrSmith);
    professors = sampleService.findProfessors();
    System.out.println("All profs after update:"+professors);
   
    sampleService.removeProfessor(mrSmith);
    professors = sampleService.findProfessors();
    System.out.println("All profs after remove:"+professors);
  }
}
