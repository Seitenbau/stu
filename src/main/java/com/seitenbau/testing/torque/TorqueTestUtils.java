package com.seitenbau.testing.torque;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Klasse initalsiert die Torque Umgebung f�r die Tests.
 */
public final class TorqueTestUtils
{

  private TorqueTestUtils()
  {

  }

  /**
   * Key f�r das Torque Property das beschreibt wor die Test Torque
   * Konfiguration zufinden ist.
   * <p>
   * Wird dieses Property nicht gesetzt wird den default Pfad:<br>
   * "src/test/resources/Torque.properties"<br>
   * gew�hlt.
   * </p>
   */
  public static final String TORQUE_PROPERTY_FILE = "com.seitenbau.torque.property.file";

  private static final String DEFAULT_TORQUE_PROPERTY_FILE = "src/test/resources/Torque.properties";

  private static boolean torqueInitialized = false;

  /**
   * Wenn Torque noch nicht initalisiert wurde, wird die Torque
   * Umgebung initalisiert.
   * @throws TorqueException Wird geworfen wenn bei der Torque
   *         Initialisierung ein Fehler aufgtretten ist.
   */
  public static void initializeTorque() throws Exception
  {
    if (!torqueInitialized)
    {
      String torquePropertyFile = System.getProperty(TORQUE_PROPERTY_FILE);
      if (torquePropertyFile == null)
      {
        torquePropertyFile = DEFAULT_TORQUE_PROPERTY_FILE;
      }

      // (Bad) linux patch
      if (!new File(torquePropertyFile).exists() && new File(torquePropertyFile.toLowerCase()).exists())
      {
        torquePropertyFile = torquePropertyFile.toLowerCase();
      }

      // invoke Torque dynamic
      // TODO: the torque init class should be removed at all
      Class<?> torqueClass = TorqueTestUtils.class.getClassLoader().loadClass("org.apache.torque.Torque");
      Method initMethod = torqueClass.getMethod("init", String.class);
      initMethod.invoke(null, torquePropertyFile);
    }
  }

  /**
   * Initialisiert Torque mit entsprechenden ConfigFile
   * @param configFile TorqueConfig File mit Pfad
   */
  public static void initializeTorque(String configFile) throws Exception
  {
    System.setProperty(TORQUE_PROPERTY_FILE, configFile);
    initializeTorque();
  }

}
