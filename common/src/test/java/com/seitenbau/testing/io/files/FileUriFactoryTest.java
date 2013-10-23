package com.seitenbau.testing.io.files;

import static com.seitenbau.testing.asserts.fest.Assertions.*;
import static org.fest.assertions.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

public class FileUriFactoryTest
{
  @Test
  public void loadClasspath_existingFileRelative() throws IOException, ClassNotFoundException
  {
    // prepare

    // execute
    FileReference ref = FileUriFactory.use(FileUriFactory.class).create("classpath:sample-iso.txt");

    // verify
    assertThat(ref.exists()).isTrue();
    assertThat(ref).hasContentEqualsTo("fun\r\n" +
        "with \r\n" +
        "?");
  }

  @Test
  public void loadClasspath_autodetect() throws IOException, ClassNotFoundException
  {
    // prepare

    // execute
    FileReference ref = FileUriFactory.use(FileUriFactory.class).create("sample-iso.txt");

    // verify
    assertThat(ref.exists()).isTrue();
    assertThat(ref).hasContentEqualsTo("fun\r\n" +
        "with \r\n" +
        "?");
  }

  @Test
  public void loadClasspath_autodetectAndFind() throws IOException, ClassNotFoundException
  {
    // prepare

    // execute
    FileReference ref = FileUriFactory.getInstance().find("none-exisintg...txt",
        "classpath:/com/seitenbau/testing/io/sample-data.txt");

    // verify
    assertThat(ref.exists()).isTrue();
    assertThat(ref).hasContentEqualsTo("data");
  }
  
  @Test
  public void loadClasspath_autodetectAndFindNone() throws IOException, ClassNotFoundException
  {
    // prepare
    
    // execute
    FileReference ref = FileUriFactory.getInstance().find("none-exisintg...txt",
        "classpath:/com/seitenbau/testing/io/sample-data3.txt");
    
    // verify
    assertThat(ref).isNull();
  }

  @Test
  public void loadClasspath_existingFileAbsolute() throws IOException, ClassNotFoundException
  {
    // prepare

    // execute
    FileReference ref = FileUriFactory.use(FileUriFactory.class).create(
        "classpath:/com/seitenbau/testing/io/sample-data.txt");

    // verify
    assertThat(ref.exists()).isTrue();
    assertThat(ref).hasContentEqualsTo("data");
  }

  @Test
  public void loadClasspath_globalClass() throws IOException, ClassNotFoundException
  {
    // prepare

    // execute
    FileReference ref = FileUriFactory.getInstance().create("classpath:/com/seitenbau/testing/io/sample-data.txt");

    // verify
    assertThat(ref.exists()).isTrue();
    assertThat(ref).hasContentEqualsTo("data");
  }

  @Test
  public void loadClasspath_NotExistingFile() throws IOException, ClassNotFoundException
  {
    // prepare

    // execute
    FileReference ref = FileUriFactory.use(FileUriFactory.class).create("classpath:sample-none.txt");

    // verify
    assertThat(ref.exists()).isFalse();
  }

  @Test
  public void loadFile_existingFile() throws IOException, ClassNotFoundException, URISyntaxException
  {
    // prepare
    URI uri = getClass().getResource("sample-iso.txt").toURI();
    String uriString = uri.toString();

    // execute
    FileReference ref = FileUriFactory.use(FileUriFactory.class).create(uriString);

    // verify
    assertThat(ref.exists()).isTrue();
    assertThat(ref).hasContentEqualsTo("fun\r\n" +
        "with \r\n" +
        "?");
  }

  @Test
  public void loadFile_NotExistingFile() throws IOException, ClassNotFoundException
  {
    // prepare

    // execute
    FileReference ref = FileUriFactory.use(FileUriFactory.class).create("file:not-there-sample-none.txt");

    // verify
    assertThat(ref.exists()).isFalse();
  }
}
