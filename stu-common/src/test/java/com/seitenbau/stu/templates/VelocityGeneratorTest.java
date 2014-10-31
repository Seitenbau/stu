package com.seitenbau.stu.templates;

import static com.seitenbau.stu.asserts.fest.Assertions.*;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.seitenbau.stu.rules.FileRule;

public class VelocityGeneratorTest
{

  @Rule
  public FileRule javaFile = new FileRule(
      "build/gen/test/sample/theFilename.java");

  @Rule
  public FileRule xmlFile = new FileRule(
      "build/gen/test/sample/theFilename.xml");

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testVelocityGeneratorNoTemplate() throws Exception
  {
    // prepare
    Model model = new Model("test.sample", "theFilename");
    VelocityGenerator sut = new VelocityGenerator();

    // verify
    exception.expect(ResourceNotFoundException.class);

    // execute
    sut.executeTemplate(model,
        "com/seitenbau/stu/templates/templateNone.vm", "build/gen/");
  }

  @Test
  public void testVelocityGeneratorNoPackage() throws Exception
  {
    // prepare
    Model model = new Model(null, "theFilename");
    VelocityGenerator sut = new VelocityGenerator();
    FileRule noPackageFile = new FileRule("build/gen/theFilename.java");
    noPackageFile.prepareTest();
    
    // execute
    sut.executeTemplate(model,
        "com/seitenbau/stu/templates/templateJava.vm", "build/gen/");

    // verify
    assertThat(noPackageFile.getFile()).exists().linesAreEqualTo(
        "content ...",
        " filename = theFilename",
        " package  = ${model.folder}");
  }
  
  @Test
  public void testVelocityGeneratorEmptyPackage() throws Exception
  {
    // prepare
    Model model = new Model("", "theFilename");
    VelocityGenerator sut = new VelocityGenerator();
    FileRule noPackageFile = new FileRule("build/gen/theFilename.java");
    noPackageFile.prepareTest();
    
    // execute
    sut.executeTemplate(model,
        "com/seitenbau/stu/templates/templateJava.vm", "build/gen/");
    
    // verify
    assertThat(noPackageFile).exists().linesAreEqualTo(
        "content ...",
        " filename = theFilename",
        " package  = ");
  }

  @Test
  public void testVelocityGeneratorNoFilename() throws Exception
  {
    // prepare
    Model model = new Model("test.sample", null);
    VelocityGenerator sut = new VelocityGenerator();

    // verify
    exception.expect(IllegalArgumentException.class);

    // execute
    sut.executeTemplate(model,
        "com/seitenbau/stu/templates/templateJava.vm", "build/gen/");
  }

  @Test
  public void testVelocityGenerator() throws Exception
  {
    // prepare
    Model model = new Model("test.sample", "theFilename");
    VelocityGenerator sut = new VelocityGenerator();

    // execute
    sut.executeTemplate(model,
        "com/seitenbau/stu/templates/templateJava.vm", "build/gen/");

    // verify
    assertThat(javaFile).exists().linesAreEqualTo(
        "content ...",
        " filename = theFilename",
        " package  = test.sample");
  }

  @Test
  public void testVelocityWithExtension() throws Exception
  {
    // prepare
    Model model = new Model("test.sample", "theFilename.java");
    VelocityGenerator sut = new VelocityGenerator();

    // execute
    sut.executeTemplate(model,
        "com/seitenbau/stu/templates/templateJava.vm", "build/gen/");

    // verify
    assertThat(javaFile).exists().linesAreEqualTo(
        "content ...",
        " filename = theFilename.java",
        " package  = test.sample");
  }

  @Test
  public void testVelocityWithXML() throws Exception
  {
    // prepare
    Model model = new Model("test.sample", "theFilename.xml");
    VelocityGenerator sut = new VelocityGenerator();

    // execute
    sut.executeTemplate(model,
        "com/seitenbau/stu/templates/templateXML.vm", "build/gen/");

    // verify
    assertThat(xmlFile.getFile()).exists().linesAreEqualTo(
        "<html>",
        " filename = theFilename.xml",
        " package  = test.sample",
        "</html>");
  }

  public class Model
  {
    public Model(String folder, String filename)
    {
      this.folder = folder;
      this.filename = filename;
    }

    public String filename;

    public String getFilename()
    {
      return filename;
    }

    public String getFolder()
    {
      return folder;
    }

    public String folder;
  }

}
