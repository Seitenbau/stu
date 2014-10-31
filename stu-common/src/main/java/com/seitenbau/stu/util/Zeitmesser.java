package com.seitenbau.stu.util;

import java.text.DecimalFormat;

/**
 * Hilfsklasse zum Ausmessen von Zeitaufwänden.
 * <br/> 
 * Intern wird dabei als Genauigkeit Nanosekunden benutzt.
 */
public class Zeitmesser
{
  public static final float ONE_MS_IN_NS = 1000000f;

  public static final float ONE_S_IN_MS = 1000f;

  public static final float ONE_S_IN_NS = ONE_MS_IN_NS * ONE_S_IN_MS;

  boolean isRunning = false;

  private long startTime;

  private long stopTime;

  /**
   * Erzeugt einen neuen Zeitmesser, und startet diesen automatisch via
   * {@link #start()}.
   */
  public Zeitmesser()
  {
    start();
  }

  /**
   * Startet den Zeitmesser. Jeder erneute Aufruf resettet den Startzeitpunkt!
   */
  public void start()
  {
    startTime = System.nanoTime();
    isRunning = true;
  }

  /**
   * Stoppt den den Zeitmesser und errechnet die verbrauchte Zeit. Jeder erneute Aufruf dieser 
   * Methode hat daher keine Auswirkung auf den Stopzeitpunkt. Siehe auch {@link #stopAgain()}.
   * 
   * @return
   *    Die Verbrauchte formatiert als String, entspricht {@link #toString()}.
   */
  public String stop()
  {
    if (isRunning)
    {
      isRunning = false;
      stopTime = System.nanoTime();
    }
    return toString();
  }

  /**
   * Stoppt den den Zeitmesser "erneut" und errechnet die verbrauchte Zeit. Jeder erneute Aufruf dieser 
   * Methode führt diesen Vorgang erneut aus! 
   */
  public String stopAgain()
  {
    isRunning = false;
    stopTime = System.nanoTime();
    return toString();
  }

  /**
   * Führt einen {@link #stop()} aus und liefert die verbrauchte Zeit in Nanosekunden zurück.
   *   
   * @return
   *    Die verbrauchte zeit in NS.
   */
  public long stopNS()
  {
    stop();
    return getDiffNano();
  }

  /**
   * Führt einen {@link #stop()} aus und liefert die verbrauchte Zeit in Milisekunden zurück.
   *   
   * @return
   *    Die verbrauchte zeit in MS.
   */
  public float stopMS()
  {
    stop();
    return getDiffMS();
  }

  /**
   * Gibt die Verbrauchte Zeit formatiert auf der Konsole aus.
   */
  public void print()
  {
    System.out.println(toString());
  }

  /**
   * Hilfsmethode um Nanosekunden als String zu formatieren.
   * <br/>
   * 
   * 
   * @param nanoSeconds
   *   zu formatierende Nanosekunden als long.
   * @return
   *   die formatierten nanosekunden
   */
  public static String formatNano(long nanoSeconds)
  {
    float sec = nanoSeconds / 1000000000f;
    return format(nanoSeconds, sec);
  }

  private static String format(long nanoSeconds, float sec)
  {
    String secS = new DecimalFormat("##0.00000").format(sec);
    String result = " " + secS + "s  [" + nanoSeconds + " ns";
    if (sec > 120)
    {
      int minutes = (int) (sec / 60);
      if (minutes > 120)
      {
        int hours = (int) (sec / 60 / 60);
        result += " ~" + hours + "h";
      }
      else
      {
        result += " ~" + minutes + "m";
      }
    }
    result += "]";
    return result;
  }

  /**
   * Gibt die verbrauchte zeit als String aus. 
   */
  public String toString()
  {
    long diff = getDiffNano();
    float sec = getDiffSeconds();
    return format(diff, sec);
  }

  /**
   * @return
   *  Liefert die verbrauchte Zeit in Milisekunden
   */
  public float getDiffMS()
  {
    long diff = getDiffNano();
    float ms = diff / ONE_MS_IN_NS;
    return ms;
  }

  /**
   * @return
   *  Liefert die verbrauchte Zeit in Sekunden
   */
  public float getDiffSeconds()
  {
    long diff = getDiffNano();
    float sec = diff / ONE_S_IN_NS;
    return sec;
  }

  /**
   * @return
   *  Liefert die verbrauchte Zeit in Nanosekunden
   */
  public long getDiffNano()
  {
    long diff = stopTime - startTime;
    return diff;
  }

}
