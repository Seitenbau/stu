package com.seitenbau.testing.io.files;

import java.io.IOException;
import java.io.InputStream;

import com.seitenbau.testing.util.Closure;
/**
 * Reference to an 'file', can be a file on the filesystem or a file
 * on the classpath.
 */
public interface FileReference
{
  /**
   * @return {@code true} when the file does exist.
   */
  boolean exists();

  /**
   * template method to get the InputStream for the file content.
   */
  <OUT, E extends Throwable> OUT withInputStream(Closure<OUT, InputStream, E> closure) throws E, IOException;

  /**
   * Get the file content as String
   * @return
   * @throws IOException
   */
  String readContentAsString() throws IOException;

  /**
   * Geht the file's uri
   * @return
   */
  String getUri();
}