package com.seitenbau.stu.io.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.seitenbau.stu.logger.Logger;
import com.seitenbau.stu.logger.TestLoggerFactory;
import com.seitenbau.stu.util.Closure;

/**
 * Helper to load files from filesystem or the classpath.
 * 
 * Can process file URIs with an "file:" or "classpath:" pattern.
 */
public class FileUriFactory
{
  static Logger logger = TestLoggerFactory.get(FileUriFactory.class);

  /**
   * Creates an {@link FileReference} instance based on the given uris
   * 
   * @param fileLocation A URI, supproted schemes are :
   *        <ul>
   *        <li>"file:" load from filesystem
   *        <li>"classpath:" load from classpath
   *        <li>or if no protocol given. The file is search on the
   *        classpath or if not exists, on the filesystem.
   *        </ul>
   * @return a file reference
   */
  public FileReference find(String ... fileLocationUris)
  {
    return find(Arrays.asList(fileLocationUris));
  }

  /**
   * Creates an {@link FileReference} instance based on the given uris
   * 
   * @param fileLocation A URI, supproted schemes are :
   *        <ul>
   *        <li>"file:" load from filesystem
   *        <li>"classpath:" load from classpath
   *        <li>or if no protocol given. The file is search on the
   *        classpath or if not exists, on the filesystem.
   *        </ul>
   * @return a file reference
   */
  public FileReference find(List<String> fileLocationUris)
  {
    for (String file : fileLocationUris)
    {
      FileReference ref = create(file);
      if (ref.exists())
      {
        return ref;
      }
    }
    return null;
  }

  /**
   * Creates an {@link FileReference} instance based on the given uri
   * 
   * @param fileLocation A URI, supproted schemes are :
   *        <ul>
   *        <li>"file:" load from filesystem
   *        <li>"classpath:" load from classpath
   *        <li>or if no protocol given. The file is search on the
   *        classpath or if not exists, on the filesystem.
   *        </ul>
   * @return a file reference
   */
  public FileReference create(String fileLocation)
  {
    FileReference handler = doCreate(fileLocation);
    logger.debug("For fileLocation='" + fileLocation + "' created handler : " + handler);
    return handler;
  }

  Class<?> _clazz;

  protected FileUriFactory(Class<?> clazz)
  {
    _clazz = clazz;
  }

  /**
   * Creates an {@link FileUriFactory} instance with an modified
   * root-Class
   */
  public static FileUriFactory use(Class<?> clazz)
  {
    return new FileUriFactory(clazz);
  }

  /**
   * Creates an {@link FileUriFactory} instance with an modified
   * root-Class
   */
  public static FileUriFactory getInstance()
  {
    return new FileUriFactory(FileUriFactory.class);
  }

  FileReference doCreate(String fileUri)
  {
    if (fileUri == null)
    {
      throw new IllegalArgumentException();
    }
    if (fileUri.startsWith("file:"))
    {
      return new FileHandler(fileUri.substring(5));
    }
    if (fileUri.startsWith("classpath:"))
    {
      return new ClassPathHandler(_clazz, fileUri.substring(10));
    }
    if (_clazz.getResource(fileUri) != null)
    {
      return new ClassPathHandler(_clazz, fileUri);
    }
    return new FileHandler(fileUri);
  }

  abstract class BaseHandler implements FileReference
  {
    String uri;

    public BaseHandler(String protocol, String location)
    {
      uri = protocol + ":" + location;
    }

    public String readContentAsString() throws IOException
    {
      String data = withInputStream(new Closure<String, InputStream, IOException>()
      {
        public String call(InputStream input) throws IOException
        {
          String content = IOUtils.toString(input);
          return content;
        }
      });
      return data;
    }

    public String getUri()
    {
      return uri;
    }

    @Override
    public String toString()
    {
      return getUri();
    }
  }

  class FileHandler extends BaseHandler implements FileReference
  {
    String _path;

    public FileHandler(String pathAndFile)
    {
      super("file", pathAndFile);
      _path = pathAndFile;
    }

    public boolean exists()
    {
      return new File(_path).exists();
    }

    public <OUT, E extends Throwable> OUT withInputStream(Closure<OUT, InputStream, E> closure) throws E
    {
      try
      {
        FileInputStream fin;
        fin = new FileInputStream(new File(_path));
        try
        {
          return closure.call(fin);
        }
        finally
        {
          fin.close();
        }
      }
      catch (FileNotFoundException e)
      {
        throw new RuntimeException(e);
      }
      catch (IOException e) // from inner try !
      {
        throw new RuntimeException(e);
      }
    }
  }

  class ClassPathHandler extends BaseHandler implements FileReference
  {
    String _path;

    Class<?> _clazz;

    public ClassPathHandler(Class<?> clazz, String pathAndFile)
    {
      super("classpath", pathAndFile);
      _clazz = clazz;
      _path = pathAndFile;
    }

    public boolean exists()
    {
      return _clazz.getResource(_path) != null;
    }

    public <OUT, E extends Throwable> OUT withInputStream(Closure<OUT, InputStream, E> closure) throws E
    {
      InputStream in = _clazz.getResourceAsStream(_path);
      return closure.call(in);
    }

  }
}
