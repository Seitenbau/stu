package com.seitenbau.testing.dbunit;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.seitenbau.testing.dbunit.modifier.IDataSetModifier;
import com.seitenbau.testing.dbunit.tester.DatabaseTesterBase;
import com.seitenbau.testing.util.Future;

/**
 * Klasse für DBUnit Tests welche nicht abgeleitet sein muss. Für eine
 * Klasse die statischen Verbindungsdaten vorhält siehe
 * {@link AbstractDBUnitTests}
 * <p/>
 * Im JUnit Test am besten eine Feldvariable vorhalten: <code><pre>
 *  private DatabaseTester dbTester;
 * </pre></code> Beim Setup des Tests ist eine Instanzieren zu
 * erzeugen: <code><pre>
 *  &#064;Beforeass
 *  public void setUp()
 *  {
 *     dbTester = new DatabaseTester(
 *        "org.gjt.mm.mysql.Driver",
 *        "jdbc:mysql://192.168.0.42:3306/my_database_name",
 *        "user",
 *        "password",
 *        getClass()
 *       );
 *  }
 * </pre></code> Für einen Test stehen verschiedenste Funktionen
 * bereit :<code><pre>
 *  &#064;Test
 *  public void testDatabaseWhatever() {
 *     dbTester.cleanInsert("testDatabaseWhatever_prepare.xml");
 *     [... do call target ...]
 *     dbTester.assertDatabase("testDatabaseWhatever_xpect.xml");
 *  }
 *  </pre></code>
 */
public class DatabaseTester extends DatabaseTesterBase<DatabaseTester>
{
  /**
   * Konstruktor welcher gleich die Verbindungsdaten zur Datenbank
   * setzt.
   * 
   * <code><pre>
   *     dbTester = new DatabaseTester(
   *        "org.gjt.mm.mysql.Driver",
   *        "jdbc:mysql://192.168.0.42:3306/my_database_name",
   *        "user",
   *        "password"
   *       );
   * </pre></code>
   * 
   * @param driverName Klassen - Name für den Datenbank Treiber.
   * 
   * @param url URL der Datenbank
   * 
   * @param username Datenbank Benutzer
   * 
   * @param password Datenbank Benutzer Passwort
   */
  public DatabaseTester(String driverName, String url, String username, String password,
      IDataSetModifier... defaultModifiers)
  {
    super(driverName, url, username, password, defaultModifiers);
  }

  /**
   * Konstruktor welcher gleich die Verbindungsdaten zur Datenbank
   * setzt.
   * 
   * <code><pre>
   *     dbTester = new DatabaseTester(
   *        "org.gjt.mm.mysql.Driver",
   *        "jdbc:mysql://192.168.0.42:3306/my_database_name",
   *        "user",
   *        "password",
   *        getClass()
   *       );
   * </pre></code>
   * 
   * @param driverName Klassen - Name für den Datenbank Treiber.
   * 
   * @param url URL der Datenbank
   * 
   * @param username Datenbank Benutzer
   * 
   * @param clazz Class Objekt dessen package dazu genutzt wird um bei
   *        Laden von XML-DataSet-Dateien das korrekte
   *        Unterverzeichnis zu nutzen.
   * 
   * @param password Datenbank Benutzer Passwort
   */
  public DatabaseTester(String driverName, String url, String username, String password, Class<?> clazz,
      IDataSetModifier... defaultModifiers)
  {
    super(driverName, url, username, password, clazz, defaultModifiers);
  }

  /**
   * Konstruktor welcher gleich die Verbindungsdaten zur Datenbank
   * setzt.
   * 
   * <code><pre>
   *     dbTester = new DatabaseTester(
   *        "org.gjt.mm.mysql.Driver",
   *        "jdbc:mysql://192.168.0.42:3306/my_database_name",
   *        "user",
   *        "password",
   *        getClass()
   *       );
   * </pre></code>
   * 
   * @param driverName Klassen - Name für den Datenbank Treiber.
   * 
   * @param url URL der Datenbank
   * 
   * @param username Datenbank Benutzer
   * 
   * @param password Datenbank Benutzer Passwort
   * 
   * @param fSchema Datenbank Schema
   */
  public DatabaseTester(String driverName, String url, String username, String password, String schema,
      IDataSetModifier... defaultModifiers)
  {
    super(driverName, url, username, password, schema, defaultModifiers);
  }

  public DatabaseTester(DataSource ds, String schema, IDataSetModifier... defaultModifiers)
  {
    super(ds, schema, defaultModifiers);
  }

  public DatabaseTester(Future<DataSource> lazySource, IDataSetModifier... defaultModifiers)
  {
    super(lazySource, defaultModifiers);
  }

  public DatabaseTester(BasicDataSource dataSource, IDataSetModifier... defaultModifiers)
  {
    super(dataSource, defaultModifiers);
  }

  public DatabaseTester(Class<? extends TestConfigDatabase> configClass, IDataSetModifier... defaultModifiers)
  {
    super(configClass, defaultModifiers);
  }

}
