package com.seitenbau.testing.rules;

import static com.seitenbau.testing.asserts.fest.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileRuleTest
{
  private static final String OUTPUT_TXT = "target/output.txt";

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Rule
  public FileRule output = new FileRule(OUTPUT_TXT)
      .checkFileExistsAfterTest()
      .compareAfter("classpath:expectedContent.txt");

  @Test
  public void successTest() throws FileNotFoundException, IOException
  {
    FileOutputStream fos = new FileOutputStream(new File(OUTPUT_TXT));
    fos.write("Dies ist eine test datei\r".getBytes());
    fos.write("gleicher Inhalt\r".getBytes());
    fos.write("Hi\r".getBytes());
    fos.write("Inhalt 'dannach'".getBytes());
    fos.close();
  }

  @Test
  public void notSameTest() throws FileNotFoundException, IOException
  {
    // verify
    exception.expect(new TypeSafeMatcher<ComparisonFailure>()
    {
      public void describeTo(Description description)
      {
      }

      @Override
      protected boolean matchesSafely(ComparisonFailure item)
      {
        String act = clean(item.getActual());
        String exp = clean(item.getExpected());
        String msg = clean(item.getMessage());
        assertThat(act).isEqualTo("Line <3>\nnull\n");
        assertThat(exp).isEqualTo("Line <3>\nInhalt 'dannach'\n");
        assertThat(msg).isEqualTo(
            "File content comparison failed expected:<Line <3>\n[Inhalt 'dannach']\n> but was:<Line <3>\n[null]\n>");
        return true;
      }

      private String clean(String msg)
      {
        return msg.replace("\r\n", "\n");
      }
    });

    // execute
    FileOutputStream fos = new FileOutputStream(new File(OUTPUT_TXT));
    fos.write("Dies ist eine test datei\r".getBytes());
    fos.write("gleicher Inhalt\r".getBytes());
    fos.write("Hi\r".getBytes());
    fos.close();
  }

  @Test
  public void failTestNoFile() throws FileNotFoundException, IOException
  {
    exception.expectMessage("File didn't exist after Tests Execution : target/output.txt");
  }
}
